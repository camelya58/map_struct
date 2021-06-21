package com.github.camelya58.map_struct.service;

import com.github.camelya58.map_struct.model.Person;
import com.github.camelya58.map_struct.model.PersonDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonMapperIntegrationTest {

    @Test
    public void personToPersonDTO() {
        Person person = new Person();
        person.setName("John");
        PersonDTO dto = PersonMapper.INSTANCE.personToPersonDTO(person);

        assertEquals(person.getName(), dto.getName());
        assertNull(person.getId());
        assertNotNull(dto.getId());
    }

    @Test
    public void personDTOToPerson() {
        PersonDTO dto = new PersonDTO();
        dto.setName("John");
        Person person = PersonMapper.INSTANCE.personDTOtoPerson(dto);

        assertEquals(dto.getName(), person.getName());
        assertNotNull(person.getId());
        assertNull(dto.getId());
    }
}
