package com.example.vsemenchuk.homeworklection13;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddStudentActivity extends AppCompatActivity {

    EditText etFirstNameAdd, etLastNameAdd, etAgeAdd;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        etFirstNameAdd = findViewById(R.id.etFirstNameAdd);
        etLastNameAdd = findViewById(R.id.etLastNameAdd);
        etAgeAdd = findViewById(R.id.etAgeAdd);

        btnSave = findViewById(R.id.btnSaveStudentAdd);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = etFirstNameAdd.getText().toString();
                String lastName = etLastNameAdd.getText().toString();
                int age = Integer.valueOf(etAgeAdd.getText().toString());

                Student student = new Student(firstName, lastName, age);

                Intent result = new Intent();
                result.putExtra(MyIntentService.EXTRA_STUDENT, student);
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }
}
