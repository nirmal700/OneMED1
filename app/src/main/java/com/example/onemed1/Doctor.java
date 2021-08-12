package com.example.onemed1;

import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private String oid;
    private String name;
    private String specification;
    private String qualifications;
    private String description;
    private String password;
    private List<Boolean> slotToday = new java.util.ArrayList<>();
    private List<Boolean> slotTomorrow = new ArrayList<>();

    public Doctor(String oid, String name, String specification, String qualifications, String description, String password, List<Boolean> slotToday, List<Boolean> slotTomorrow) {
        this.oid = oid;
        this.name = name;
        this.specification = specification;
        this.qualifications = qualifications;
        this.description = description;
        this.password = password;
        this.slotToday = slotToday;
        this.slotTomorrow = slotTomorrow;
    }

    public Doctor() {
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Boolean> getSlotToday() {
        return slotToday;
    }

    public void setSlotToday(List<Boolean> slotToday) {
        this.slotToday = slotToday;
    }

    public List<Boolean> getSlotTomorrow() {
        return slotTomorrow;
    }

    public void setSlotTomorrow(List<Boolean> slotTomorrow) {
        this.slotTomorrow = slotTomorrow;
    }
}
