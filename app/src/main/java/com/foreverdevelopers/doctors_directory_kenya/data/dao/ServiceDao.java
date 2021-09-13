package com.foreverdevelopers.doctors_directory_kenya.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.foreverdevelopers.doctors_directory_kenya.data.entity.Service;

import java.util.List;

@Dao
public interface ServiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addService(Service service);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAllServices(List<Service> service);
    @Delete
    void deleteService(Service service);
    @Query("DELETE FROM Service")
    void deleteAllServices();
    @Query("SELECT * FROM Service WHERE service_name = :service")
    LiveData<Service> getService(String service);
    @Query("SELECT * FROM Service")
    LiveData<List<Service>> getAllServices();
    @Query("SELECT * FROM Service WHERE service_name LIKE '%' || :searchValue || '%'")
    LiveData<List<Service>> searchServices(String searchValue);
}
