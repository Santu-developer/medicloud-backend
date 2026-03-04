package com.medicloud.modules.user.service.impl;

import com.medicloud.common.exception.BusinessException;
import com.medicloud.common.exception.ResourceNotFoundException;
import com.medicloud.common.util.TenantUtil;
import com.medicloud.modules.user.dto.UserRequest;
import com.medicloud.modules.user.dto.UserResponse;
import com.medicloud.modules.user.entity.User;
import com.medicloud.modules.user.mapper.UserMapper;
import com.medicloud.modules.user.repository.UserRepository;
import com.medicloud.modules.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TenantUtil tenantUtil;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TenantUtil tenantUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tenantUtil = tenantUtil;
    }

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BusinessException("Email already exists");
        }

        Long currentShopId = tenantUtil.getCurrentShopId();

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setShopId(currentShopId);

        userRepository.save(user);

        return UserMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUser(Long id) {

        Long currentShopId = tenantUtil.getCurrentShopId();

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.getShopId().equals(currentShopId)) {
            throw new BusinessException("Access denied: user does not belong to your shop");
        }

        return UserMapper.toResponse(user);
    }
}