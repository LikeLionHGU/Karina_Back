package com.example.karina_project.sehyukPage.login_page.service;

import com.example.karina_project.domain.User;
import com.example.karina_project.repository.UserRepository;
import com.example.karina_project.sehyukPage.login_page.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        User userData = userRepository.findByLoginId(loginId);

        if(userData != null) {
            return new CustomUserDetail(userData); //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
        }

        throw new UsernameNotFoundException("Invalid ID: " + loginId);
    }
}
