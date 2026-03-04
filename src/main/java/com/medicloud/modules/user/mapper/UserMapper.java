package com.medicloud.modules.user.mapper;

import com.medicloud.modules.user.dto.UserResponse;
import com.medicloud.modules.user.entity.User;

public class UserMapper {

    public static UserResponse toResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getShopId()
        );
    }
}