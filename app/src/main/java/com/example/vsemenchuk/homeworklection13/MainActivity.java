package com.example.vsemenchuk.homeworklection13;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Student>>{

    public static final int REQUEST_CODE_ADD_STUDENT_FROM_ACTIVITY = 5;
    public static final int REQUEST_CODE_EDIT_STUDENT_FROM_ACTIVITY = 6;

    ListView listView;
    FloatingActionButton btnAdd;

    DataBaseHelper dbHelper;
    ArrayAdapter<Student> studentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);
        dbHelper = new DataBaseHelper(this);

        initStudents();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student student = studentsAdapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, EditStudentActivity.class);
                intent.putExtra(MyIntentService.EXTRA_STUDENT, student);
                startActivityForResult(intent, REQUEST_CODE_EDIT_STUDENT_FROM_ACTIVITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == MyIntentService.REQUEST_CODE_INSERT_STUDENT) {
                long id = data.getLongExtra(MyIntentService.EXTRA_ID, 0);
                Toast.makeText(this, "Student add, id - " + id, Toast.LENGTH_SHORT).show();
                reloadStudents();
            } else if (requestCode == MyIntentService.REQUEST_CODE_UPDATE_STUDENT) {
                int amount = data.getIntExtra(MyIntentService.EXTRA_AMOUNT, 0);
                Toast.makeText(this, "Student updated, amount - " + amount, Toast.LENGTH_SHORT).show();
                reloadStudents();
            } else if (requestCode == MyIntentService.REQUEST_CODE_DELETE_STUDENT) {
                int amount = data.getIntExtra(MyIntentService.EXTRA_AMOUNT , 0);
                Toast.makeText(this, "Student deleted, amount - " + amount, Toast.LENGTH_SHORT).show();
                reloadStudents();
            } else if (requestCode == REQUEST_CODE_ADD_STUDENT_FROM_ACTIVITY) {
                Student student = data.getParcelableExtra(MyIntentService.EXTRA_STUDENT);
                insertStudent(student);
            } else if (requestCode == REQUEST_CODE_EDIT_STUDENT_FROM_ACTIVITY) {
                int action = data.getIntExtra(EditStudentActivity.EXTRA_ACTION_EDIT_ACTIVITY, 0);
                Student student = data.getParcelableExtra(MyIntentService.EXTRA_STUDENT);
                if (action == EditStudentActivity.ACTION_EDIT) {
                    updateStudent(student);
                } else if (action == EditStudentActivity.ACTION_DELETE) {
                    deleteStudent(student);
                }
            }
        }
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, AddStudentActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_STUDENT_FROM_ACTIVITY);
    }

    private void initStudents() {
        getSupportLoaderManager().initLoader(0, null, this);
    }

    private void reloadStudents() {
        getSupportLoaderManager().restartLoader(0, null, this);
    }

    private void insertStudent(Student student) {
        MyIntentService.insertStudent(this, student);
    }

    private void updateStudent(Student student) {
        MyIntentService.updateStudent(this, student);
    }

    private void deleteStudent(Student student) {
        MyIntentService.deleteStudent(this, student);
    }

    private void setAdapter(ArrayList<Student> students) {
        studentsAdapter = new ArrayAdapter<>(this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            students
        );
        listView.setAdapter(studentsAdapter);
    }

    @NonNull
    @Override
    public Loader<ArrayList<Student>> onCreateLoader(int id, @Nullable Bundle args) {
        return new StudentsLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Student>> loader, ArrayList<Student> data) {
        setAdapter(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Student>> loader) {

    }
}
