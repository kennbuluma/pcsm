package com.foreverdevelopers.doctors_directory_kenya.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.foreverdevelopers.doctors_directory_kenya.data.DataDB;
import com.foreverdevelopers.doctors_directory_kenya.data.dao.FacilityDao;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Facility;

import java.util.List;

public class FacilityViewModel extends AndroidViewModel {
    private final FacilityDao facilityDao;
    private final MutableLiveData<List<Facility>> _fitleredFacilities = new MutableLiveData<>();
    public final LiveData<List<Facility>> facilities;
    public final LiveData<List<Facility>> filteredFacilities = this._fitleredFacilities;

    public FacilityViewModel(@NonNull Application application) {
        super(application);
        facilityDao = DataDB.getDatabase(application).facilityDao();
        facilities = facilityDao.getAllFacility();
    }

    public void add(Facility facility){ DataDB.dbWriteExecutor.execute(new Runnable() {
        @Override
        public void run() {
            facilityDao.addFacility(facility);
        }
    });}
    public void addAll(List<Facility> facilities){
        DataDB.dbWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                facilityDao.addAllFacility(facilities);
            }
        });
    }
    public void delete(Facility facility){
        DataDB.dbWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                facilityDao.deleteFacility(facility);
            }
        });
    }
    public void deleteAll(){
        DataDB.dbWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                facilityDao.deleteAllFacility();
            }
        });
    }
    public void setFilteredFacilities(List<Facility> facilities){
        this._fitleredFacilities.postValue(facilities);
    }

    public LiveData<List<Facility>> getAll(){ return facilityDao.getAllFacility();}
    public LiveData<List<Facility>> search(String searchValue){ return facilityDao.searchFacility(searchValue);}
    public LiveData<Facility> getByName(String facility){return facilityDao.getFacility(facility);}
}
