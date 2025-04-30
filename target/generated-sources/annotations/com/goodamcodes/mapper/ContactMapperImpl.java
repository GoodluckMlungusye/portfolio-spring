package com.goodamcodes.mapper;

import com.goodamcodes.dto.ContactDTO;
import com.goodamcodes.model.Contact;
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
public class ContactMapperImpl implements ContactMapper {

    @Override
    public Contact toContact(ContactDTO contactDTO) {
        if ( contactDTO == null ) {
            return null;
        }

        Contact.ContactBuilder contact = Contact.builder();

        contact.medium( contactDTO.getMedium() );
        contact.contactLink( contactDTO.getContactLink() );

        return contact.build();
    }

    @Override
    public ContactDTO toContactDTO(Contact contact) {
        if ( contact == null ) {
            return null;
        }

        ContactDTO contactDTO = new ContactDTO();

        contactDTO.setMedium( contact.getMedium() );
        contactDTO.setContactLink( contact.getContactLink() );

        return contactDTO;
    }

    @Override
    public List<ContactDTO> toContactDTOs(List<Contact> contacts) {
        if ( contacts == null ) {
            return null;
        }

        List<ContactDTO> list = new ArrayList<ContactDTO>( contacts.size() );
        for ( Contact contact : contacts ) {
            list.add( toContactDTO( contact ) );
        }

        return list;
    }

    @Override
    public void updateContactFromDTO(ContactDTO contactDTO, Contact contact) {
        if ( contactDTO == null ) {
            return;
        }

        if ( contactDTO.getMedium() != null ) {
            contact.setMedium( contactDTO.getMedium() );
        }
        if ( contactDTO.getContactLink() != null ) {
            contact.setContactLink( contactDTO.getContactLink() );
        }
    }
}
