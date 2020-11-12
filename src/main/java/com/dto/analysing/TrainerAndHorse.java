package com.dto.analysing;

import com.demoSB.model.Horse;
import com.demoSB.model.Trainer;

import java.util.Set;

public class TrainerAndHorse {
    private Trainer trainer;
    private Set<Horse> horseSet;

    public TrainerAndHorse() {
    }

    public TrainerAndHorse(Trainer trainer, Set<Horse> horseSet) {
        this.trainer = trainer;
        this.horseSet = horseSet;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Set<Horse> getHorseSet() {
        return horseSet;
    }

    public void setHorseSet(Set<Horse> horseSet) {
        this.horseSet = horseSet;
    }
}
