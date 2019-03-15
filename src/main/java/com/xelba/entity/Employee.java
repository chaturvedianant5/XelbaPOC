package com.xelba.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "empid")
    private int empId;

    @Column(name = "empname")
    private String empName;

    @Column(name = "empaddress")
    private String empAddress;

    public Employee() {
    }

    public Employee(int id, String name, String address) {
        this.setEmpId(id);
        this.setEmpName(name);
        this.setEmpAddress(address);
    }

    /**
     * @return the empId
     */
    public int getEmpId() {
        return empId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmpId(int empId) {
        this.empId = empId;
    }

    /**
     * @return the empName
     */
    public String getEmpName() {
        return empName;
    }

    /**
     * @param empName the empName to set
     */
    public void setEmpName(String empName) {
        this.empName = empName;
    }

    /**
     * @return the empAddress
     */
    public String getEmpAddress() {
        return empAddress;
    }

    /**
     * @param empAddress the empAddress to set
     */
    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    @Override
    public String toString() {
        return "Employee details: "
                + "\nName: " + this.getEmpName()
                + "\nAddress: " + this.getEmpAddress()
                + "\nEmployee ID: " + this.getEmpId();
    }

    

}