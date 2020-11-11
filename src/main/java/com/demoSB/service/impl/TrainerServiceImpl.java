package com.demoSB.service.impl;

import com.demoSB.model.Trainer;
import com.demoSB.repository.TrainerRepository;
import com.demoSB.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

public class TrainerServiceImpl implements TrainerService<Trainer> {
    @Autowired
    private TrainerRepository trainerRepository;

    @Override
    public Iterable<Trainer> findAll() {
        return trainerRepository.findAll();
    }

    @Override
    public Optional<Trainer> findOneById(int id) {
        return trainerRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        trainerRepository.deleteById(id);
    }

    @Override
    public Trainer update(Trainer obj) {
        return trainerRepository.save(obj);
    }

    @Override
    public Trainer save(Trainer obj) {
        return trainerRepository.save(obj);
    }
}
