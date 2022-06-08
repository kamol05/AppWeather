package com.example.appweather.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "userDb")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String login;

    private String password;

    @OneToOne(fetch = FetchType.EAGER)
    private Country country;

    @Enumerated(EnumType.STRING)
    private Role role;


}
