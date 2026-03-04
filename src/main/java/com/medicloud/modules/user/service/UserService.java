package com.medicloud.modules.user.service;

import com.medicloud.modules.user.dto.UserRequest;
import com.medicloud.modules.user.dto.UserResponse;

public interface UserService {

    UserResponse createUser(UserRequest request);

    UserResponse getUser(Long id);
}