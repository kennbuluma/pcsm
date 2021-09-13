package com.foreverdevelopers.doctors_directory_kenya.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class County {
    @PrimaryKey @NonNull
    @ColumnInfo(name = "county_name")
    public String name;
}
