package com.github.camelya58.map_struct.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CarDTO {

    private int id;
    private String name;
    private FuelType fuelType;
}
