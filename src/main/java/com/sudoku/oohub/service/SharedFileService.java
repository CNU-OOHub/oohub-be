package com.sudoku.oohub.service;

import com.sudoku.oohub.converter.Converter;
import com.sudoku.oohub.domain.Member;
import com.sudoku.oohub.domain.Organization;
import com.sudoku.oohub.domain.SharedFile;
import com.sudoku.oohub.dto.request.CreateSharedFileDto;
import com.sudoku.oohub.dto.response.SharedFileDto;
import com.sudoku.oohub.exception.FileNotFoundException;
import com.sudoku.oohub.exception.NameNotFoundException;
import com.sudoku.oohub.repository.MemberRepository;
import com.sudoku.oohub.repository.OrganizationRepository;
import com.sudoku.oohub.repository.SharedFileRepository;
import com.sudoku.oohub.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
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

    public List<SharedFileDto> findAllByOrganizationName(String organizationName) {
        Organization organization = organizationRepository.findByName(organizationName)
                .orElseThrow(() -> new NameNotFoundException(organizationName + "명의 orgaization이 존재하지 않습니다."));

        List<SharedFileDto> sharedFiles = sharedFileRepository.findByOrganizationId(organization.getId())
                .map((sharedFile) -> converter.convertSharedFileDto(sharedFile))
                .stream().collect(Collectors.toList());
        return sharedFiles;
    }

    public SharedFileDto viewSharedFile(String organizationName, String fileName){
        String username = SecurityUtil.getCurrentUsername()
                .orElseThrow(()-> new NameNotFoundException("현재 username을 가져올 수 없습니다."));
        Organization organization = organizationRepository.findByName(organizationName)
                .orElseThrow(() -> new NameNotFoundException(organizationName + "명의 orgaization이 존재하지 않습니다."));

        SharedFileDto sharedFileDto = converter.convertSharedFileDto(
                sharedFileRepository.findByOrganizationIdAndFilename(organization.getId(), fileName)
                    .orElseThrow(() -> new FileNotFoundException("파일명: " + fileName + " 해당 파일이 존재하지 않습니다."))
        );

        if(username.equals(sharedFileDto.getWriter())) {
            String userHomeDir = System.getProperty("user.home");
            String path = userHomeDir +"/"+sharedFileDto.getFilepath();
            File localFile = new File(path);
            if(!localFile.exists()){
                throw new FileNotFoundException("공유 파일에 대한 파일 정보(파일명,경로 등)가 변경되었습니다. 공유파일 삭제 후 다시 공유해주세요");
            }
        }
        return sharedFileDto;
    }

    public SharedFileDto saveSharedFile(String organizationName, CreateSharedFileDto createSharedFileDto) {
        String username = SecurityUtil.getCurrentUsername()
                .orElseThrow(()-> new NameNotFoundException("현재 username을 가져올 수 없습니다."));
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new NameNotFoundException("username: "+username+" 을 가진 유저를 찾을 수 없습니다."));
        Organization organization = organizationRepository.findByName(organizationName)
                .orElseThrow(() -> new NameNotFoundException(organizationName + "명의 orgaization이 존재하지 않습니다."));
        SharedFile sharedFile = SharedFile.builder()
                .filename(createSharedFileDto.getMultipartFile().getName())
                .filepath(createSharedFileDto.getFilePath())
                .contents(createSharedFileDto.getMultipartFile().toString())
                .member(member)
                .organization(organization)
                .build();
        SharedFileDto savedFile = converter.convertSharedFileDto(sharedFileRepository.save(sharedFile));
        return savedFile;

    }

    @Transactional
    public void deleteFile(String organizationName, String fileName) {
        Organization organization = organizationRepository.findByName(organizationName)
                .orElseThrow(() -> new NameNotFoundException(organizationName + "명의 orgaization이 존재하지 않습니다."));

        sharedFileRepository.deleteByOrganizationIdAndFilename(organization.getId(), fileName);
    }
}
