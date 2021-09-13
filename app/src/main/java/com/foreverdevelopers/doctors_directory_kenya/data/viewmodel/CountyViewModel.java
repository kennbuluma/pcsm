package com.foreverdevelopers.doctors_directory_kenya.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.foreverdevelopers.doctors_directory_kenya.data.DataDB;
import com.foreverdevelopers.doctors_directory_kenya.data.dao.CountyDao;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.County;

import java.util.List;

public class CountyViewModel extends AndroidViewModel {
    private final CountyDao countyDao;
    private final MutableLiveData<List<County>> _filteredCounties = new MutableLiveData<>();
    public final LiveData<List<County>> counties;
    public final LiveData<List<County>> filteredCounties = this._filteredCounties;

    public CountyViewModel(@NonNull Application application) {
        super(application);
        countyDao = DataDB.getDatabase(application).countyDao();
        counties = countyDao.getAllCounties();
    }

    public void add(County county){ DataDB.dbWriteExecutor.execute(new Runnable() {
        @Override
        public void run() {
            countyDao.addCounty(county);
        }
    });}
    public void addAll(List<County> counties){
        DataDB.dbWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                countyDao.addAllCounties(counties);
            }
        });
    }
    public void delete(County county){
        DataDB.dbWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                countyDao.deleteCounty(county);
            }
        });
    }
    public void deleteAll(){
        DataDB.dbWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                countyDao.deleteAllCounties();
            }
        });
    }
    public void setFilteredCounties(List<County> counties){
        this._filteredCounties.postValue(counties);
    }

    public LiveData<List<County>> getAll(){ return countyDao.getAllCounties();}
    public LiveData<List<County>> search(String searchValue){ return countyDao.searchCounties(searchValue);}
    public LiveData<County> getByName(String county){return countyDao.getCounty(county);}
}
