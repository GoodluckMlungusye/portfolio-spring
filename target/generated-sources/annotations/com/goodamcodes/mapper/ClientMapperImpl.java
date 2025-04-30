package com.goodamcodes.mapper;

import com.goodamcodes.dto.ClientDTO;
import com.goodamcodes.model.Client;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-30T16:01:13+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public Client toClient(ClientDTO clientDTO) {
        if ( clientDTO == null ) {
            return null;
        }

        Client.ClientBuilder client = Client.builder();

        client.name( clientDTO.getName() );
        client.phoneNumber( clientDTO.getPhoneNumber() );
        client.email( clientDTO.getEmail() );
        client.subject( clientDTO.getSubject() );
        client.message( clientDTO.getMessage() );

        return client.build();
    }

    @Override
    public ClientDTO toClientDTO(Client client) {
        if ( client == null ) {
            return null;
        }

        ClientDTO clientDTO = new ClientDTO();

        clientDTO.setName( client.getName() );
        clientDTO.setPhoneNumber( client.getPhoneNumber() );
        clientDTO.setEmail( client.getEmail() );
        clientDTO.setSubject( client.getSubject() );
        clientDTO.setMessage( client.getMessage() );

        return clientDTO;
    }

    @Override
    public List<ClientDTO> toClientDTOs(List<Client> clients) {
        if ( clients == null ) {
            return null;
        }

        List<ClientDTO> list = new ArrayList<ClientDTO>( clients.size() );
        for ( Client client : clients ) {
            list.add( toClientDTO( client ) );
        }

        return list;
    }

    @Override
    public void updateClientFromDTO(ClientDTO clientDTO, Client client) {
        if ( clientDTO == null ) {
            return;
        }

        if ( clientDTO.getName() != null ) {
            client.setName( clientDTO.getName() );
        }
        if ( clientDTO.getPhoneNumber() != null ) {
            client.setPhoneNumber( clientDTO.getPhoneNumber() );
        }
        if ( clientDTO.getEmail() != null ) {
            client.setEmail( clientDTO.getEmail() );
        }
        if ( clientDTO.getSubject() != null ) {
            client.setSubject( clientDTO.getSubject() );
        }
        if ( clientDTO.getMessage() != null ) {
            client.setMessage( clientDTO.getMessage() );
        }
    }
}
