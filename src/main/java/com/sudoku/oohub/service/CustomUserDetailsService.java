package com.sudoku.oohub.service;

import com.sudoku.oohub.domain.Member;
import com.sudoku.oohub.exception.NameNotFoundException;
import com.sudoku.oohub.jwt.CustomUserDetails;
import com.sudoku.oohub.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername 동작 확인");
        Member memberEntity = memberRepository.findByUsername(username).orElseThrow(
                () -> new NameNotFoundException("username:"+username + " 을 가진 유저가 존재하지 않습니다."));
        return new CustomUserDetails(memberEntity);
    }

}
