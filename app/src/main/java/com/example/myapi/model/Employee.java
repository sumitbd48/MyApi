package com.example.myapi.model;

import com.google.gson.annotations.SerializedName;

public class Employee {

    private int id;
    @SerializedName("name")
    private String employee_name;

    @SerializedName("salary")
    private String employee_salary;

    @SerializedName("age")
    private String employee_age;

    public Employee(int id, String employee_name, String employee_salary, String employee_age) {
        this.id = id;
        this.employee_name = employee_name;
        this.employee_salary = employee_salary;
        this.employee_age = employee_age;
    }

    public int getId() {
        return id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public String getEmployee_salary() {
        return employee_salary;
    }

    public String getEmployee_age() {
        return employee_age;
    }
}
