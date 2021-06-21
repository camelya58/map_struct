package com.github.camelya58.map_struct.model;

import lombok.Data;

@Data
public class EmployeeDTO {

    private int employeeId;
    private String employeeName;
    private DivisionDTO division;
    private String employeeStartDt;
}
