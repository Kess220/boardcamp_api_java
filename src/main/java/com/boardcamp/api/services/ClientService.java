package com.boardcamp.api.services;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.boardcamp.api.errors.ClientNotFoundException;
import com.boardcamp.api.errors.CpfInvalidError;
import com.boardcamp.api.errors.ExistingCpfExceptionError;
import com.boardcamp.api.models.ClientModel;
import com.boardcamp.api.repositories.ClientRepository;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientModel getClientById(@NonNull Long clientId) {
        Optional<ClientModel> optionalClient = clientRepository.findById(clientId);

        if (optionalClient.isPresent()) {
            return optionalClient.get();
        } else {
            throw new ClientNotFoundException("Client not found with this ID");
        }
    }

    @Transactional
    public ClientModel createClient(@Valid ClientModel clientData) {
        validateClientData(clientData);

        if (clientRepository.existsByCpf(clientData.getCpf())) {
            throw new ExistingCpfExceptionError(clientData.getCpf());
        }

        return clientRepository.save(clientData);
    }

    private void validateClientData(ClientModel clientData) {
        if (clientData == null || StringUtils.isEmpty(clientData.getName()) ||
                StringUtils.isEmpty(clientData.getCpf()) || !isValidCpf(clientData.getCpf())) {
            throw new CpfInvalidError("Invalid, empty, or null CPF");
        }
    }

    private boolean isValidCpf(String cpf) {
        String cpfPattern = "^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11})$";
        return Pattern.matches(cpfPattern, cpf);
    }

}
