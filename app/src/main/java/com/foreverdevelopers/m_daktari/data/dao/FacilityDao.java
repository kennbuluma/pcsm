package com.foreverdevelopers.m_daktari.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.foreverdevelopers.m_daktari.data.entity.Facility;

import java.util.List;

@Dao
public interface FacilityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFacility(Facility facility);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAllFacility(List<Facility> facilities);
    @Delete
    void deleteFacility(Facility facility);
    @Query("DELETE FROM Facility")
    void deleteAllFacility();
    @Query("SELECT * FROM Facility WHERE facility_name =:facility")
    LiveData<Facility> getFacility(String facility);
    @Query("SELECT * FROM Facility")
    LiveData<List<Facility>> getAllFacility();
    @Query("SELECT * FROM Facility WHERE facility_name LIKE '%' || :searchValue || '%'")
    LiveData<List<Facility>> searchFacility(String searchValue);
}
