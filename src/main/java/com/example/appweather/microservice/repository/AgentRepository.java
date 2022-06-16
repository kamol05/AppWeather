package com.example.appweather.microservice.repository;


import com.example.appweather.microservice.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> getByNickName(String nickname);

    Optional<Agent> findById(Long id);
}
