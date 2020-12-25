package com.docker.extended.admin.controller;

import com.docker.extended.admin.domain.Command;
import com.docker.extended.admin.domain.Endpoint;
import com.docker.extended.admin.repository.CommandRepository;
import com.docker.extended.admin.repository.EndpointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/command")
@Validated
public class CommandsController {

    public final CommandRepository commandRepository;
    public final EndpointRepository endpointRepository;

    @GetMapping("/get_all")
    public Iterable<Command> getCommands() { return commandRepository.findAll(); }

    @PostMapping("/create")
    public ResponseEntity<Void> CreateCommand(@RequestParam String content) {
        commandRepository.save(new Command(content));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> DeleteCommand(@RequestParam String commandId) {
        Command command = commandRepository.findById(UUID.fromString(commandId)).get();
        commandRepository.delete(command);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get_endpoints")
    public Iterable<Endpoint> getEndpoints(@RequestParam String commandId) {
        Command command = commandRepository.findById(UUID.fromString(commandId)).get();
        return command.getEndpoints();
    }

    @PostMapping("/add_endpoint")
    public ResponseEntity<Void> AddEndpoint(@RequestParam String commandId, @RequestParam String content, @RequestParam boolean optional) {
        Command command = commandRepository.findById(UUID.fromString(commandId)).get();
        Endpoint endpoint = new Endpoint(content, optional);
        endpointRepository.save(endpoint);
        command.getEndpoints().add(endpoint);
        commandRepository.save(command);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/rm_endpoint")
    public ResponseEntity<Void> RmEndpoint(@RequestParam String commandId, @RequestParam String endpointId) {
        Command command = commandRepository.findById(UUID.fromString(commandId)).get();
        Endpoint endpoint = endpointRepository.findById(UUID.fromString(endpointId)).get();
        command.getEndpoints().remove(endpoint);
        endpointRepository.delete(endpoint);
        commandRepository.save(command);
        return ResponseEntity.ok().build();
    }
}
