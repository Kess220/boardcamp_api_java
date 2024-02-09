package com.boardcamp.api.integration;

import com.boardcamp.api.models.ClientModel;
import com.boardcamp.api.repositories.ClientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClientRepository clientRepository;

    @AfterEach
    void cleanDataBase() {
        clientRepository.deleteAll();
    }

    @SuppressWarnings("null")
    @Test
    void testCreateClient() {
        ClientModel clientData = new ClientModel();
        clientData.setName("Kaio");
        clientData.setCpf("00000000000");

        ResponseEntity<ClientModel> response = restTemplate.postForEntity("/customers", clientData, ClientModel.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(clientData.getName(), response.getBody().getName());
        assertEquals(clientData.getCpf(), response.getBody().getCpf());

        ClientModel savedClient = clientRepository.findById(response.getBody().getId()).orElse(null);
        assertNotNull(savedClient);
        assertEquals(clientData.getName(), savedClient.getName());
        assertEquals(clientData.getCpf(), savedClient.getCpf());
    }

    @Test
    void testCreateClientWithDuplicateCPF() {
        ClientModel clientData1 = new ClientModel();
        clientData1.setName("Kaio");
        clientData1.setCpf("00000000000");

        ResponseEntity<ClientModel> response1 = restTemplate.postForEntity("/customers", clientData1,
                ClientModel.class);

        assertEquals(HttpStatus.CREATED, response1.getStatusCode());

        ClientModel clientData2 = new ClientModel();
        clientData2.setName("Another Name");
        clientData2.setCpf("00000000000");

        ResponseEntity<Void> response2 = restTemplate.postForEntity("/customers", clientData2,
                Void.class);

        assertEquals(HttpStatus.CONFLICT, response2.getStatusCode());
    }

    @SuppressWarnings("null")
    @Test
    void testGetClientById() {
        ClientModel clientData = new ClientModel();
        clientData.setName("Kaio");
        clientData.setCpf("00000000000");

        ResponseEntity<ClientModel> createResponse = restTemplate.postForEntity("/customers", clientData,
                ClientModel.class);
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());

        Long clientId = createResponse.getBody().getId();
        assertNotNull(clientId);

        ResponseEntity<ClientModel> getResponse = restTemplate.getForEntity("/customers/" + clientId,
                ClientModel.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());

        ClientModel retrievedClient = getResponse.getBody();
        assertNotNull(retrievedClient);
        assertEquals(clientData.getName(), retrievedClient.getName());
        assertEquals(clientData.getCpf(), retrievedClient.getCpf());
    }

}
