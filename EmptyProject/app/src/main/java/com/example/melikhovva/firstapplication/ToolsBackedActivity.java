package com.example.melikhovva.firstapplication;

import android.support.v7.app.AppCompatActivity;

public class ToolsBackedActivity extends AppCompatActivity {

    protected final ApplicationToolsProvider applicationToolsProvider;

    public ToolsBackedActivity() {
        applicationToolsProvider = Application.getInstance().getApplicationToolsProvider();
    }
}