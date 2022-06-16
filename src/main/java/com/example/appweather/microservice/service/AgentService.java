package com.example.appweather.microservice.service;

import com.example.appweather.microservice.model.Agent;
import com.example.appweather.microservice.repository.AgentRepository;
import com.example.appweather.model.Role;
import com.example.appweather.response.BaseResponse;
import com.example.appweather.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class AgentService implements BaseService {
    AgentRepository agentRepository;
    public AgentService(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    public BaseResponse add(Agent agent) {
        Optional<Agent> optional = agentRepository.getByNickName(agent.getNickName());
        if (optional.isPresent()){
            return EXIST(agent);
        }
        String password = PasswordUtil.getPasswordFromSalt(agent.getNickName());
        if (password == null){
            log.error("Cant generate Password");
            return SPECIAL(agent, "Cant generate Password", 500, false);
        }
        agent.setRole(Role.AGENT);
        agent.setPassword(password);
        agentRepository.save(agent);
        log.info("Agent Successfully added");
        return SUCCESS_ADDED(agent);

    }
    public BaseResponse login(Agent candidate) {
        Optional<Agent> optional = agentRepository.getByNickName(candidate.getNickName());
        if (!optional.isPresent()){
            log.warn("Agent does not exist");
            return SPECIAL(candidate, "Agent does not exist", -777, false);
        }
        Agent agent = optional.get();
        if (agent.isLogged() || !checkPassword(agent, candidate.getPassword())){
            log.warn("Wrong Password or already logged!!");
            return SPECIAL(candidate, "Wrong Password or already logged!!", -777, false);
        }
        agent.setLogged(true);
        agent.setLoginDate(new Date());
        agent.setRole(Role.AGENT);
        agentRepository.save(agent);
        log.info("Login Successful");
        return SPECIAL(agent, "Successfully", 200, true);
    }
    public BaseResponse logout(Agent candidate) {
        Optional<Agent> optional = agentRepository.getByNickName(candidate.getNickName());
        if (!optional.isPresent()){
            log.warn("Agent does not exist");
            return SPECIAL(candidate, "Agent does not exist", -777, false);
        }
        Agent agent = optional.get();
        agent.setLogged(false);
        agent.setLogoutDate(new Date());
        agentRepository.save(agent);
        log.info("logout Successful");
        return SPECIAL(agent, "Successful", 200, true);
    }

    public boolean checkPassword(Agent agent, String password) {
        String candidate = PasswordUtil.get_SHA_256_SecurePassword(password, agent.getNickName());
        return candidate.equals(agent.getPassword());
    }

    public Agent getByNickName(String nickname){
        Optional<Agent> optional =  agentRepository.getByNickName(nickname);
        return optional.orElse(null);
    }

    public BaseResponse getAll() {
        return SPECIAL(agentRepository.findAll(), "SUCCES", 200, true);
    }

    public BaseResponse getById(Long id) {
        return SPECIAL(agentRepository.getById(id), "Ok", 200, true);
    }

    public BaseResponse change(Agent agent) {
        return SPECIAL(agentRepository.save(agent), "Ok", 200, true);
    }

    public BaseResponse deleteById(Long id) {
        Optional<Agent> optional = agentRepository.findById(id);
        if (optional.isPresent()){
            log.info("delete Successful");
            return SPECIAL(optional.get(), "Ok", 200, true);
        }
        log.warn("Agent does not exist");
        return SPECIAL(null, "Agent not found", 200, true);
    }

}
