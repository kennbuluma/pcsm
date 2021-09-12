package com.foreverdevelopers.m_daktari.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.foreverdevelopers.m_daktari.data.dao.CountyDao;
import com.foreverdevelopers.m_daktari.data.dao.DoctorDao;
import com.foreverdevelopers.m_daktari.data.dao.FacilityDao;
import com.foreverdevelopers.m_daktari.data.dao.ServiceDao;
import com.foreverdevelopers.m_daktari.data.entity.County;
import com.foreverdevelopers.m_daktari.data.entity.Doctor;
import com.foreverdevelopers.m_daktari.data.entity.Facility;
import com.foreverdevelopers.m_daktari.data.entity.Service;
import com.foreverdevelopers.m_daktari.util.Converter;

@Database(entities = {
        County.class,
        Doctor.class,
        Facility.class,
        Service.class
}, version = 1)
@TypeConverters({Converter.class})
public abstract class DataDB extends RoomDatabase {
    public abstract CountyDao countyDao();
    public abstract DoctorDao doctorDao();
    public abstract FacilityDao facilityDao();
    public abstract ServiceDao serviceDao();
}
