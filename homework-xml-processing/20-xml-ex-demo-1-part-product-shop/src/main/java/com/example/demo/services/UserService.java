package com.example.demo.services;

import com.example.demo.models.dtos.UserSeedDto;
import com.example.demo.models.entities.User;

import java.util.List;

public interface UserService {
    void seedUsers(List<UserSeedDto> userSeedDtos);
    User getRandomUser();
}
