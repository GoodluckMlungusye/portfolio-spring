package com.goodamcodes.mapper;

import com.goodamcodes.dto.ClientDTO;
import com.goodamcodes.model.Client;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toClient(ClientDTO clientDTO);
    ClientDTO toClientDTO(Client client);
    List<ClientDTO> toClientDTOs(List<Client> clients);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateClientFromDTO(ClientDTO clientDTO,@MappingTarget Client client);
}
