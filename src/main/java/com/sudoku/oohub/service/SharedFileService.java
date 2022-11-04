package com.sudoku.oohub.service;

import com.sudoku.oohub.converter.Converter;
import com.sudoku.oohub.domain.Member;
import com.sudoku.oohub.domain.Organization;
import com.sudoku.oohub.domain.SharedFile;
import com.sudoku.oohub.dto.request.ContentDto;
import com.sudoku.oohub.dto.request.CreateSharedFileDto;
import com.sudoku.oohub.dto.request.GetFilePathDto;
import com.sudoku.oohub.dto.response.SharedFileDto;
import com.sudoku.oohub.exception.ChangeFileException;
import com.sudoku.oohub.exception.DuplicateFileException;
import com.sudoku.oohub.exception.FileNotFoundException;
import com.sudoku.oohub.exception.NameNotFoundException;
import com.sudoku.oohub.repository.MemberRepository;
import com.sudoku.oohub.repository.OrganizationRepository;
import com.sudoku.oohub.repository.SharedFileRepository;
import com.sudoku.oohub.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class SharedFileService {

    private final SharedFileRepository sharedFileRepository;
    private final MemberRepository memberRepository;
    private final OrganizationRepository organizationRepository;
    private final Converter converter;

    @Value("${local.home}")
    private String homeDir;

    public List<SharedFileDto> findAllByOrganizationName(String organizationName) {
        Organization organization = organizationRepository.findByName(organizationName)
                .orElseThrow(() -> new NameNotFoundException(organizationName + "명의 orgaization이 존재하지 않습니다."));

        List<SharedFileDto> sharedFiles = sharedFileRepository.findByOrganizationId(organization.getId())
                .stream()
                .map((sharedFile) -> converter.convertSharedFileDto(sharedFile))
                .collect(Collectors.toList());
        return sharedFiles;
    }

    public SharedFileDto viewSharedFile(String organizationName, String filepath){
        String username = SecurityUtil.getCurrentUsername()
                .orElseThrow(()-> new NameNotFoundException("현재 username을 가져올 수 없습니다."));
        Organization organization = organizationRepository.findByName(organizationName)
                .orElseThrow(() -> new NameNotFoundException(organizationName + "명의 orgaization이 존재하지 않습니다."));

        SharedFileDto sharedFileDto = converter.convertSharedFileDto(
                sharedFileRepository.findByOrganizationIdAndFilepath(organization.getId(), filepath)
                    .orElseThrow(() -> new FileNotFoundException("파일경로: " + filepath + " 해당 파일이 존재하지 않습니다."))
        );

        if(username.equals(sharedFileDto.getWriter())) {
            String path = homeDir +sharedFileDto.getFilepath();
            File localFile = new File(path);
            if(!localFile.exists()){
                throw new ChangeFileException("공유 파일에 대한 파일 정보(파일명,경로 등)가 변경되었습니다. 공유파일 삭제 후 다시 공유해주세요");
            }
        }
        return sharedFileDto;
    }

    @Transactional
    public SharedFileDto saveSharedFile(String organizationName, CreateSharedFileDto createSharedFileDto) throws IOException {
        String username = SecurityUtil.getCurrentUsername()
                .orElseThrow(()-> new NameNotFoundException("현재 username을 가져올 수 없습니다."));
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new NameNotFoundException("username: "+username+" 을 가진 유저를 찾을 수 없습니다."));
        Organization organization = organizationRepository.findByName(organizationName)
                .orElseThrow(() -> new NameNotFoundException(organizationName + "명의 orgaization이 존재하지 않습니다."));

        // 로컬 파일 존재 확인
        String filePath = homeDir + createSharedFileDto.getFilePath();
        File file = new File(filePath);

        if (!file.exists()) {
            throw new FileNotFoundException("파일이 존재하지 않습니다.");
        }

        if(sharedFileRepository.findByFilepath(createSharedFileDto.getFilePath()).isPresent()){
                throw new DuplicateFileException("이미 공유된 파일입니다.");
        }

        List<String> contentList = Arrays.asList(createSharedFileDto.getContents().split("/n"));
        StringBuilder contents = new StringBuilder();
        for(String str : contentList){
            contents.append(str).append("\n");
        }

        SharedFile sharedFile = SharedFile.builder()
                .filename(createSharedFileDto.getName())
                .filepath(createSharedFileDto.getFilePath())
                .contents(contents.toString())
                .member(member)
                .organization(organization)
                .build();

        SharedFileDto savedFile = converter.convertSharedFileDto(sharedFileRepository.save(sharedFile));
        return savedFile;

    }

    @Transactional
    public void deleteFile(String organizationName, String filepath) {
        Organization organization = organizationRepository.findByName(organizationName)
                .orElseThrow(() -> new NameNotFoundException(organizationName + "명의 orgaization이 존재하지 않습니다."));

        sharedFileRepository.deleteByOrganizationIdAndFilepath(organization.getId(), filepath);
    }
}
