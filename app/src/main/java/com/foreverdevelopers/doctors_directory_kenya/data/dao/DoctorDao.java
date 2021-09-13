package com.foreverdevelopers.doctors_directory_kenya.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.foreverdevelopers.doctors_directory_kenya.data.entity.Doctor;

import java.util.List;

@Dao
public interface DoctorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDoctor(Doctor doctor);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAllDoctor(List<Doctor> doctors);
    @Delete
    void deleteDoctor(Doctor doctor);
    @Query("DELETE FROM Doctor")
    void deleteAllDoctor();
    @Query("SELECT * FROM Doctor WHERE phone = :phone")
    LiveData<Doctor> getDoctor(Integer phone);
    @Query("SELECT * FROM Doctor")
    LiveData<List<Doctor>> getAllDoctor();
    @Query("SELECT * FROM Doctor WHERE doctor_name LIKE '%' || :searchValue || '%' " +
            "OR facility LIKE '%' || :searchValue || '%' " +
            "OR county LIKE '%' || :searchValue || '%' " +
            "OR phone LIKE '%' || :searchValue || '%' " +
            "OR specialty LIKE '%' || :searchValue || '%'")
    LiveData<List<Doctor>> searchDoctor(String searchValue);
}
