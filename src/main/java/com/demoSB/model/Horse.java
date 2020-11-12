package com.demoSB.model;

import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "horses")
public class Horse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Length(min = 6, max = 50)
    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_horse_trainer"))
    private Trainer trainer;

    private LocalDateTime foaled;

    public Horse() {
    }

    public Horse(@NotNull @Length(min = 6, max = 50) String name,
                 Trainer trainer,
                 LocalDateTime foaled) {
        this.name = name;
        this.trainer = trainer;
        this.foaled = foaled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public LocalDateTime getFoaled() {
        return foaled;
    }

    public void setFoaled(LocalDateTime foaled) {
        this.foaled = foaled;
    }
}
