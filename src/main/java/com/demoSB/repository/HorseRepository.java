package com.demoSB.repository;

import com.demoSB.model.Horse;
import org.springframework.data.repository.CrudRepository;

public interface HorseRepository extends CrudRepository<Horse, Integer> {
}
