package com.github.camelya58.map_struct.service;

import com.github.camelya58.map_struct.model.*;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarMapperIntegrationTest {

    private final CarMapper mapper =
            Mappers.getMapper(CarMapper.class);

    @Test
    public void givenElectricCarToCarDTO() {
        Car entity = new ElectricCar();
        entity.setId(1);
        entity.setName("ElectricCar");
        CarDTO dto = mapper.toCarDTO(entity);
        mapper.enrichDTOWithFuelType(entity, dto);
        mapper.convertNameToUpperCase(dto);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName().toUpperCase(),
                dto.getName());
        assertEquals(FuelType.ELECTRIC, dto.getFuelType());
    }

    @Test
    public void givenBioDieselCarToCarDTO() {
        Car entity = new BioDieselCar();
        entity.setId(1);
        entity.setName("BioDieselCar");
        CarDTO dto = mapper.toCarDTO(entity);
        mapper.enrichDTOWithFuelType(entity, dto);
        mapper.convertNameToUpperCase(dto);

        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getName().toUpperCase(),
                dto.getName());
        assertEquals(FuelType.BIO_DIESEL, dto.getFuelType());
    }

}
