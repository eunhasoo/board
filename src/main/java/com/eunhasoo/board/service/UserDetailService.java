package com.eunhasoo.board.service;

import com.eunhasoo.board.controller.dto.UserForm;
import com.eunhasoo.board.domain.Role;
import com.eunhasoo.board.domain.User;
import com.eunhasoo.board.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UserMapper userMapper;

    @Transactional
    public int joinUser(UserForm form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setLoginId(form.getUsername());
        user.setName(form.getName());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        return userMapper.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        User user = userMapper.findById(loginId);

        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("ADMIN".equals(user.getUserType())) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        return new org.springframework.security.core.userdetails.User(user.getLoginId(), user.getPassword(), authorities);
    }

    public User findUserById(String userId) {
        return userMapper.findById(userId);
    }

    public User findUserByEmail(String email) {
        return userMapper.findByEmail(email);
    }
}
