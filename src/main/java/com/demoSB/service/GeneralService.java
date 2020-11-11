package com.demoSB.service;

import java.util.Optional;

public interface GeneralService<T> {
    Iterable<T> findAll();
    Optional<T> findOneById(int id);
    void deleteById(int id);
    T update(T obj);
    T save(T obj);
}
