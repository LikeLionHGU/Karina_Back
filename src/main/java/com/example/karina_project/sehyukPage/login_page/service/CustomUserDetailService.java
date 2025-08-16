package com.example.karina_project.sehyukPage.login_page.service;

import com.example.karina_project.domain.User;
import com.example.karina_project.repository.UserRepository;
import com.example.karina_project.sehyukPage.login_page.domain.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userData = userRepository.findByLoginId(username);

        if(userData != null) {
            return new CustomUserDetail(userData); //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
        }

        return null;
    }
}
