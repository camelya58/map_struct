package com.github.camelya58.map_struct.service;

import com.github.camelya58.map_struct.model.*;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * In case when the source can have different types and we want to convert it to target
 * depends on that difference we need to write the methods which can be invoked before and after mapping
 * using @BeforeMapping and @AfterMapping.
 * <p>
 * The conversion itself will be automatic.
 */
@Mapper
public abstract class CarMapper {

    @BeforeMapping
    protected void enrichDTOWithFuelType(
            Car car, @MappingTarget CarDTO dto) {

        if (car instanceof ElectricCar) {
            dto.setFuelType(FuelType.ELECTRIC);
        }
        if (car instanceof BioDieselCar) {
            dto.setFuelType(FuelType.BIO_DIESEL);
        }
    }

    @AfterMapping
    protected void convertNameToUpperCase(@MappingTarget CarDTO dto) {
        dto.setName(dto.getName().toUpperCase());
    }

    public abstract CarDTO toCarDTO(Car car);
}
