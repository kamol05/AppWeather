package com.example.appweather.service;

import com.example.appweather.model.Country;
import com.example.appweather.model.Role;
import com.example.appweather.model.User;
import com.example.appweather.repository.CountryRepository;
import com.example.appweather.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class InitialSetup implements ApplicationListener<ContextRefreshedEvent> {
    private final PasswordEncoder bcryptEncoder;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    public InitialSetup(PasswordEncoder bcryptEncoder, UserRepository userRepository, CountryRepository countryRepository) {
        this.bcryptEncoder = bcryptEncoder;
        this.userRepository = userRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Country uzb = new Country();
        uzb.setName("UZBEKISTAN");
        uzb.setLatitude(41.311081);
        uzb.setLongitude(69.240562);

        User admin = new User();
        admin.setLogin("SuperAdmin");
        admin.setPassword(bcryptEncoder.encode("SuperAdmin"));
        admin.setRole(Role.ADMIN);
        admin.setCountry(uzb);

        User user = new User();
        user.setLogin("user");
        user.setPassword(bcryptEncoder.encode("user"));
        user.setRole(Role.USER);
        user.setCountry(uzb);

        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword(bcryptEncoder.encode("user"));
        user1.setRole(Role.USER);
        user1.setCountry(uzb);

        User user2 = new User();
        user2.setLogin("user2");
        user2.setPassword(bcryptEncoder.encode("user"));
        user2.setRole(Role.USER);
        user2.setCountry(uzb);

        countryRepository.save(uzb);
        userRepository.save(admin);
        userRepository.save(user);
        userRepository.save(user1);
        userRepository.save(user2);

    }

}
