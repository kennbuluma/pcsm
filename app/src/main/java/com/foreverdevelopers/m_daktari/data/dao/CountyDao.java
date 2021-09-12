package com.foreverdevelopers.m_daktari.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.foreverdevelopers.m_daktari.data.entity.County;

import java.util.List;

@Dao
public interface CountyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addCounty(County county);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAllCounties(List<County> counties);
    @Delete
    void deleteCounty(County county);
    @Query("DELETE FROM County")
    void deleteAllCounties();
    @Query("SELECT * FROM County WHERE county_name = :county")
    LiveData<County> getCounty(String county);
    @Query("SELECT * FROM County")
    LiveData<List<County>> getAllCounties();
    @Query("SELECT * FROM County WHERE county_name LIKE '%' || :searchValue || '%'")
    LiveData<List<County>> searchCounties(String searchValue);
}
