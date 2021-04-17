package com.example.demo.services.impl;

import com.example.demo.models.dtos.UserSeedDto;
import com.example.demo.models.entities.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import com.example.demo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void seedUsers(List<UserSeedDto> userSeedDtos) {
        if(this.userRepository.count() != 0){
            return;
        }
        userSeedDtos
                .forEach(userSeedDto -> {
                    if (this.validationUtil.isValid(userSeedDto)) {
                        //if(this.userRepository.count() == 0){
                        User user = this.modelMapper
                                .map(userSeedDto, User.class);
                        System.out.println();
                        this.userRepository.saveAndFlush(user);
//                        }else {
//                            System.out.println("User already in db");
//                        }
                    } else {
                        this.validationUtil.getViolations(userSeedDto)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                });
    }

    @Override
    public User getRandomUser() {
        Random random = new Random();
        // nextInt(); returns a random int from 0 to repo.count() not including the last element
        // that is why we add 1 and the random number will be from 1 to the repo.count() including last
        long randomId = random.nextInt((int) this.userRepository.count()) + 1;
        return this.userRepository.getOne(randomId);
    }

}
