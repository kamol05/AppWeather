package com.example.appweather.microservice.model;

import com.example.appweather.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
public class Agent {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String nickName;

    private String firstName;

    private String secondName;

    private String password;

    private Date loginDate;

    private Date logoutDate;

    private boolean logged;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Role role;

}
