package com.foreverdevelopers.m_daktari.data.entity;

import java.util.List;

public class Doctor {
    public String id, name, county, profilePhoto, facility;
    public List<String> specialty;
    public Integer phone;

    public Doctor(){}
    public Doctor(String id, String name, String county,
                  String profilePhoto, String facility,
                  Integer phone, List<String> specialty){
        this.id = id;
        this.name = name;
        this.county = county;
        this.profilePhoto = profilePhoto;
        this.facility = facility;
        this.phone = phone;
        this.specialty = specialty;
    }
}
