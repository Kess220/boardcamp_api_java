package com.boardcamp.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.models.ClientModel;
import com.boardcamp.api.services.ClientService;

@RestController
@RequestMapping("/customers")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping()
    public ResponseEntity<ClientModel> createClientController(@RequestBody ClientModel clientData) {
        ClientModel newCliente = clientService.createClient(clientData);
        return new ResponseEntity<>(newCliente, HttpStatus.CREATED);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientModel> getClientByIdController(@PathVariable @NonNull Long clientId) {
        ClientModel client = clientService.getClientById(clientId);
        return ResponseEntity.ok(client);
    }
}
