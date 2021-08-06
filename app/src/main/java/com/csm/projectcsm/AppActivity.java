package com.csm.projectcsm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.app.Activity;
import android.os.Bundle;

public class AppActivity extends AppCompatActivity {

    private NavController navController;
    private AppViewModel appViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_app);
        navController = navHostFragment.getNavController();
        appViewModel.setNavController(navController);
    }
}