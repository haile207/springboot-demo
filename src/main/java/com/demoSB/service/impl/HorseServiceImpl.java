package com.demoSB.service.impl;

import com.demoSB.model.Horse;
import com.demoSB.repository.HorseRepository;
import com.demoSB.service.HorseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

public class HorseServiceImpl implements HorseService {
    @Autowired
    private HorseRepository horseRepository;

    @Override
    public Iterable<Horse> findAll() {
        return horseRepository.findAll();
    }

    @Override
    public Optional<Horse> findOneById(int id) {
        return horseRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        horseRepository.deleteById(id);
    }

    @Override
    public Horse update(Horse obj) {
        return horseRepository.save(obj);
    }

    @Override
    public Horse save(Horse obj) {
        return horseRepository.save(obj);
    }
}
