package com.example.fran365.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StatusServiceImpl implements StatusService{

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public void create(Status status) {

        status.setCreateDate(LocalDateTime.now());

        statusRepository.save(status);
    }
}
