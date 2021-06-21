package com.github.camelya58.map_struct.service;

import com.github.camelya58.map_struct.model.Division;
import com.github.camelya58.map_struct.model.DivisionDTO;
import com.github.camelya58.map_struct.model.Employee;
import com.github.camelya58.map_struct.model.EmployeeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * In case when naming different fields names use @Mappings and @Mapping
 * to describe which fields need to be the same.
 */
@Mapper
public interface EmployeeMapper {

    @Mappings({
            @Mapping(target = "employeeId", source = "entity.id"),
            @Mapping(target = "employeeName", source = "entity.name"),
            @Mapping(target="employeeStartDt", source = "entity.startDt",
                    dateFormat = "dd-MM-yyyy HH:mm:ss")
    })
    EmployeeDTO employeeToEmployeeDTO(Employee entity);

    @Mappings({
            @Mapping(target="id", source="dto.employeeId"),
            @Mapping(target="name", source="dto.employeeName"),
            @Mapping(target="startDt", source="dto.employeeStartDt",
                    dateFormat="dd-MM-yyyy HH:mm:ss")
    })
    Employee employeeDTOtoEmployee(EmployeeDTO dto);

    DivisionDTO divisionToDivisionDTO(Division division);

    Division divisionDTOToDivision(DivisionDTO dto);
}
