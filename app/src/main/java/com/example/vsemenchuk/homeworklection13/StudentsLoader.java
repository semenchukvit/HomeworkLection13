package com.example.vsemenchuk.homeworklection13;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

public class StudentsLoader extends AsyncTaskLoader<ArrayList<Student>> {

    private DataBaseHelper dbHelper;

    public StudentsLoader(@NonNull Context context) {
        super(context);
        this.dbHelper = new DataBaseHelper(context);
    }

    @Nullable
    @Override
    public ArrayList<Student> loadInBackground() {
        return dbHelper.getStudents();
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
