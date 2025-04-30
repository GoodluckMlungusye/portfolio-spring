package com.goodamcodes.mapper;

import com.goodamcodes.dto.ContactDTO;
import com.goodamcodes.model.Contact;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    Contact toContact(ContactDTO contactDTO);
    ContactDTO toContactDTO(Contact contact);
    List<ContactDTO> toContactDTOs(List<Contact> contacts);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateContactFromDTO(ContactDTO contactDTO, @MappingTarget Contact contact);

}
