package com.foreverdevelopers.m_daktari.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Service {
    @PrimaryKey @NonNull
    @ColumnInfo(name = "service_name")
    public String name;
}
