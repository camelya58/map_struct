package com.github.camelya58.map_struct.service;

import com.github.camelya58.map_struct.model.Division;
import com.github.camelya58.map_struct.model.DivisionDTO;
import com.github.camelya58.map_struct.model.Employee;
import com.github.camelya58.map_struct.model.EmployeeDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeMapperIntegrationTest {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private final EmployeeMapper mapper =
            Mappers.getMapper(EmployeeMapper.class);

    @Test
    public void givenEmployeeToEmployeeDTO() {
        Employee entity = new Employee();
        entity.setName("John");
        entity.setId(1);
        entity.setDivision(new Division(1, "Division1"));
        EmployeeDTO dto = mapper.employeeToEmployeeDTO(entity);

        assertEquals(entity.getName(), dto.getEmployeeName());
        assertEquals(entity.getId(), dto.getEmployeeId());
        assertEquals(entity.getDivision().getId(), dto.getDivision().getId());
        assertEquals(entity.getDivision().getName(), dto.getDivision().getName());
    }

    @Test
    public void givenEmployeeDTOtoEmployee() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeName("John");
        dto.setEmployeeId(1);
        dto.setDivision(new DivisionDTO(1, "Division1"));
        Employee entity = mapper.employeeDTOtoEmployee(dto);

        assertEquals(entity.getName(), dto.getEmployeeName());
        assertEquals(entity.getId(), dto.getEmployeeId());
        assertEquals(entity.getDivision().getId(), dto.getDivision().getId());
        assertEquals(entity.getDivision().getName(), dto.getDivision().getName());
    }

    @Test
    public void givenEmpStartDtMappingToEmpDTO() throws ParseException {
        Employee entity = new Employee();
        entity.setStartDt(new Date());
        EmployeeDTO dto = mapper.employeeToEmployeeDTO(entity);

        assertEquals(entity.getStartDt().toString(), DATE_FORMAT.parse(dto.getEmployeeStartDt()).toString());
    }

    @Test
    public void givenEmpDTOStartDtMappingToEmp() throws ParseException {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeStartDt("13-05-2021 01:00:00");
        Employee entity = mapper.employeeDTOtoEmployee(dto);

        assertEquals(entity.getStartDt().toString(), DATE_FORMAT.parse(dto.getEmployeeStartDt()).toString());

    }
}
