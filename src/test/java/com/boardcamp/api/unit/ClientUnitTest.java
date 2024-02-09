package com.boardcamp.api.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.boardcamp.api.errors.ClientNotFoundException;
import com.boardcamp.api.errors.ExistingCpfExceptionError;
import com.boardcamp.api.models.ClientModel;
import com.boardcamp.api.repositories.ClientRepository;
import com.boardcamp.api.services.ClientService;

class ClientUnitTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        reset(clientRepository);
    }

    @Test
    void testGetClientById_success() {
        ClientModel mockClient = new ClientModel();
        mockClient.setId(1L);
        mockClient.setName("Kaio");
        mockClient.setCpf("00000000000");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(mockClient));

        ClientModel retrievedClient = clientService.getClientById(1L);

        assertEquals(mockClient, retrievedClient);
    }

    @Test
    void testGetClientById_notFound() {
        when(clientRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> clientService.getClientById(2L));
    }

    @Test
    void testCreateClient_success() {
        ClientModel clientData = new ClientModel();
        clientData.setName("Kaio");
        clientData.setCpf("00000000000");

        when(clientRepository.save(clientData)).thenReturn(clientData);

        ClientModel createdClient = clientService.createClient(clientData);

        assertEquals(clientData, createdClient);
    }

    @Test
    void testCreateClient_existingCpf() {
        ClientModel clientData = new ClientModel();
        clientData.setName("Kaio");
        clientData.setCpf("00000000000");

        when(clientRepository.existsByCpf(clientData.getCpf())).thenReturn(true);

        assertThrows(ExistingCpfExceptionError.class, () -> clientService.createClient(clientData));
    }

    @Test
    void testCreateClient_invalidData() {
        ClientModel clientData = new ClientModel();
        clientData.setCpf("cpf inválido");

        assertThrows(IllegalArgumentException.class, () -> clientService.createClient(clientData));
    }
}
