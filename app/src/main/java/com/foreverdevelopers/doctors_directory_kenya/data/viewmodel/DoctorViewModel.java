package com.foreverdevelopers.doctors_directory_kenya.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.foreverdevelopers.doctors_directory_kenya.data.DataDB;
import com.foreverdevelopers.doctors_directory_kenya.data.dao.DoctorDao;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Doctor;

import java.util.List;

public class DoctorViewModel extends AndroidViewModel {
    private final DoctorDao doctorDao;
    private final MutableLiveData<List<Doctor>> _filteredDoctors = new MutableLiveData<>();
    private final MutableLiveData<Doctor> _doctor = new MutableLiveData<>();
    public final LiveData<List<Doctor>> doctors;
    public LiveData<List<Doctor>> filteredDoctors = this._filteredDoctors;

    public DoctorViewModel(@NonNull Application application) {
        super(application);
        doctorDao = DataDB.getDatabase(application).doctorDao();
        doctors = doctorDao.getAllDoctor();
    }

    public void add(Doctor Doctor){ DataDB.dbWriteExecutor.execute(new Runnable() {
        @Override
        public void run() {
            doctorDao.addDoctor(Doctor);
        }
    });}
    public void addAll(List<Doctor> Doctors){
        DataDB.dbWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                doctorDao.addAllDoctor(Doctors);
            }
        });
    }
    public void delete(Doctor Doctor){
        DataDB.dbWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                doctorDao.deleteDoctor(Doctor);
            }
        });
    }
    public void deleteAll(){
        DataDB.dbWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                doctorDao.deleteAllDoctor();
            }
        });
    }
    public void setFilteredDoctors(List<Doctor> doctors){
        this._filteredDoctors.postValue(doctors);
    }
    public void setDoctor(Doctor doctor){
        this._doctor.postValue(doctor);
    }

    public LiveData<List<Doctor>> getAll(){ return doctorDao.getAllDoctor();}
    public LiveData<List<Doctor>> search(String searchValue){ return doctorDao.searchDoctor(searchValue);}
    public LiveData<Doctor> getByPhone(Integer doctor){return doctorDao.getDoctor(doctor);}
    public LiveData<Doctor> getDoctor = _doctor;
}
