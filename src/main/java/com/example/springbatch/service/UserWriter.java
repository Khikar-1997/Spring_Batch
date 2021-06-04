package com.example.springbatch.service;

import com.example.springbatch.model.User;
import com.example.springbatch.repository.UserRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserWriter implements ItemWriter<User> {

    private final UserRepository userRepository;

    public UserWriter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void write(@NonNull List<? extends User> users) {
        userRepository.saveAll(users);
    }
}