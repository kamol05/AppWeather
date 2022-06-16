package com.example.appweather.microservice.controller;

import com.example.appweather.microservice.model.Agent;
import com.example.appweather.microservice.service.AgentService;
import com.example.appweather.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/agents")
public class AgentsController {
    AgentService agentService;
    public AgentsController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> add(@RequestBody Agent agent) {
        log.info("Adding new Agent with nickname:" + agent.getNickName());
        BaseResponse response = agentService.add(agent);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(){
        log.info("Getting All Agents");
        BaseResponse response = agentService.getAll();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        log.info("Getting Agent with id" + id);
        BaseResponse response = agentService.getById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/change")
    public ResponseEntity<?> change(@RequestBody Agent agent){
        log.info("Changing Agent with nickname" + agent.getNickName());
        BaseResponse response = agentService.change(agent);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        log.info("Deleting Agent with id" + id);
        BaseResponse response = agentService.deleteById(id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
