package com.gradecalculatorapp;

import android.os.Bundle;
import android.widget.Toast;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gradecalculatorapp.viewmodel.CourseViewModel;

public class MainActivity extends AppCompatActivity {
    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        handlerThread = new HandlerThread("BackgroundThread");
        handlerThread.start();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(navView, navController);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        navView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                navController.navigate(R.id.navigation_home);
                return true;
            } else if (itemId == R.id.navigation_clear) {
                showClearConfirmationDialog();
                return true;
            }
            return false;
        });
    }
    private void showClearConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Clear All Courses")
                .setMessage("Are you sure you want to clear all courses?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    courseViewModel.clearAllCourses();
                    Toast.makeText(MainActivity.this, "All courses cleared", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }


    private HandlerThread handlerThread;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quitSafely();
    }


}

