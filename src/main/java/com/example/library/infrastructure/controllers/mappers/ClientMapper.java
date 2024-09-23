package com.example.library.infrastructure.controllers.mappers;

import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public Client toClient(ClientDTO clientDTO) {
        return new Client(
                null,
                clientDTO.name(),
                clientDTO.document(),
                clientDTO.type()
        );
    }

    public ClientDTO toDTO(Client client) {
        return new ClientDTO(
                client.name(),
                client.document(),
                client.type()
        );
    }
}
