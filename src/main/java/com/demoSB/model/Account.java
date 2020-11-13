package com.demoSB.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "accounts")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Length(min = 6, max = 50)
    @Column(name = "username",unique = true)
    private String username;

    @NotNull
    @Length(min = 8, max = 50)
    @Column(name = "password")
    private String password;

    @NotNull
    @Range(min = 0, max = 1)
    @Column(name = "status")
    private int status;

    public Account() {
    }

    public Account(@NotNull @Length(min = 6, max = 50) String username,
                   @NotNull @Length(min = 8, max = 50) String password,
                   @NotNull @Range(min = 0, max = 1) int status) {
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
