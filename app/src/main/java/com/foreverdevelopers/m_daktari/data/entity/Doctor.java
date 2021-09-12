package com.foreverdevelopers.m_daktari.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Doctor {
    @PrimaryKey
    @ColumnInfo(name = "phone")
    public Integer phone;
    @ColumnInfo(name = "id")
    public String id;
    @ColumnInfo(name = "doctor_name")
    public String name;
    @ColumnInfo(name = "county")
    public String county;
    @ColumnInfo(name = "photo")
    public String profilePhoto;
    @ColumnInfo(name = "facility")
    public String facility;
    @ColumnInfo(name = "email")
    public String email;
    @ColumnInfo(name = "specialty")
    public List<String> specialty;
}
