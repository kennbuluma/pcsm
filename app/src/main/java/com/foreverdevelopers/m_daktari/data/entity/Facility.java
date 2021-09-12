package com.foreverdevelopers.m_daktari.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Facility {
    @PrimaryKey @NonNull
    @ColumnInfo(name = "facility_name")
    public String name;
}
