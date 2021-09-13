package com.foreverdevelopers.doctors_directory_kenya.data.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.foreverdevelopers.doctors_directory_kenya.data.DataDB;
import com.foreverdevelopers.doctors_directory_kenya.data.dao.ServiceDao;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Service;

import java.util.List;

public class ServiceViewModel extends AndroidViewModel {
    private final ServiceDao serviceDao;
    private final MutableLiveData<List<Service>> _filteredServices = new MutableLiveData<>();
    public final LiveData<List<Service>> services;
    public LiveData<List<Service>> filteredServices = this._filteredServices;
    public ServiceViewModel(@NonNull Application application) {
        super(application);
        serviceDao = DataDB.getDatabase(application).serviceDao();
        services = serviceDao.getAllServices();
    }

    public void add(Service service){ DataDB.dbWriteExecutor.execute(new Runnable() {
        @Override
        public void run() {
            serviceDao.addService(service);
        }
    });}
    public void addAll(List<Service> services){
        DataDB.dbWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                serviceDao.addAllServices(services);
            }
        });
    }
    public void delete(Service service){
        DataDB.dbWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                serviceDao.deleteService(service);
            }
        });
    }
    public void deleteAll(){
        DataDB.dbWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                serviceDao.deleteAllServices();
            }
        });
    }
    public void setFilteredServices(List<Service> services){
        this._filteredServices.postValue(services);
    }
    public LiveData<List<Service>> getAll(){ return serviceDao.getAllServices();}
    public LiveData<List<Service>> search(String searchValue){ return serviceDao.searchServices(searchValue);}
    public LiveData<Service> getByName(String service){return serviceDao.getService(service);}
}
