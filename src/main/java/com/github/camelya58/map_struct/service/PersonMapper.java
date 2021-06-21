package com.github.camelya58.map_struct.service;

import com.github.camelya58.map_struct.model.Person;
import com.github.camelya58.map_struct.model.PersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * In case when the value of the source field can be null
 * we can set the default value to the target field using @Mapping.
 * <p>
 * Moreover we need to instantiate PersonMapper instance which we will use to invoke
 * the conversion methods.
 */
@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "id", source = "entity.id",
            defaultExpression = "java(java.util.UUID.randomUUID().toString())")
    PersonDTO personToPersonDTO(Person entity);

    @Mapping(target = "id", source = "dto.id",
            defaultExpression = "java(java.util.UUID.randomUUID().toString())")
    Person personDTOtoPerson(PersonDTO dto);

}
