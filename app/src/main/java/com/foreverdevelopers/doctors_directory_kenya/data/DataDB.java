package com.foreverdevelopers.doctors_directory_kenya.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.foreverdevelopers.doctors_directory_kenya.data.dao.CountyDao;
import com.foreverdevelopers.doctors_directory_kenya.data.dao.DoctorDao;
import com.foreverdevelopers.doctors_directory_kenya.data.dao.FacilityDao;
import com.foreverdevelopers.doctors_directory_kenya.data.dao.ServiceDao;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.County;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Doctor;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Facility;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Service;
import com.foreverdevelopers.doctors_directory_kenya.util.Converter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {
            County.class,
            Doctor.class,
            Facility.class,
            Service.class
        },
        version = 1,
        exportSchema = true)
@TypeConverters({Converter.class})
public abstract class DataDB extends RoomDatabase {
    public abstract CountyDao countyDao();
    public abstract DoctorDao doctorDao();
    public abstract FacilityDao facilityDao();
    public abstract ServiceDao serviceDao();

    static final int NUMBER_OF_THREADS = 4;
    static volatile private DataDB MAIN_INSTANCE;
    public static final ExecutorService dbWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static final Callback dataDbCallback  = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            dbWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    clearDb(MAIN_INSTANCE);
                }
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }

        @Override
        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
            super.onDestructiveMigration(db);
        }
    };

    static final Migration MIGRATION_0_1 = new Migration(0, 1) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };
    public static DataDB getDatabase(Context context) {
        synchronized(DataDB.class) {
            MAIN_INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DataDB.class, "mdkt-db")
                    .enableMultiInstanceInvalidation()
                    //.addMigrations(MIGRATION_0_1)
                    //.addCallback(dataDbCallback)
                    .fallbackToDestructiveMigrationOnDowngrade()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return MAIN_INSTANCE;
    }
    public static void clearDb(DataDB dataDB) {
        dataDB.countyDao().deleteAllCounties();
        dataDB.facilityDao().deleteAllFacility();
        dataDB.serviceDao().deleteAllServices();
        dataDB.doctorDao().deleteAllDoctor();
    }
}
