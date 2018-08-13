package com.example.vsemenchuk.homeworklection13;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditStudentActivity extends AppCompatActivity {

    public static final String EXTRA_ACTION_EDIT_ACTIVITY = "com.example.vsemenchuk.homeworklection12.extra.ACTION_EDIT_ACTIVITY";
    public static final int ACTION_EDIT = 1;
    public static final int ACTION_DELETE = 2;

    EditText etFirstNameEdit, etLastNameEdit, etAgeEdit;
    Button btnSaveStudentEdit, btnDeleteStudent;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        etFirstNameEdit = findViewById(R.id.etFirstNameEdit);
        etLastNameEdit = findViewById(R.id.etLastNameEdit);
        etAgeEdit = findViewById(R.id.etAgeEdit);

        btnSaveStudentEdit = findViewById(R.id.btnSaveStudentEdit);
        btnDeleteStudent = findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        student = intent.getParcelableExtra(MyIntentService.EXTRA_STUDENT);

        etFirstNameEdit.setText(student.getFirstName());
        etLastNameEdit.setText(student.getLastName());
        etAgeEdit.setText(String.valueOf(student.getAge()));

        btnSaveStudentEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student.setFirstName(etFirstNameEdit.getText().toString());
                student.setLastName(etLastNameEdit.getText().toString());
                student.setAge(Integer.valueOf(etAgeEdit.getText().toString()));

                Intent result = new Intent();
                result.putExtra(MyIntentService.EXTRA_STUDENT, student);
                result.putExtra(EXTRA_ACTION_EDIT_ACTIVITY, ACTION_EDIT);
                setResult(RESULT_OK, result);
                finish();
            }
        });

        btnDeleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                student.setFirstName(etFirstNameEdit.getText().toString());
                student.setLastName(etLastNameEdit.getText().toString());
                student.setAge(Integer.valueOf(etAgeEdit.getText().toString()));

                Intent result = new Intent();
                result.putExtra(MyIntentService.EXTRA_STUDENT, student);
                result.putExtra(EXTRA_ACTION_EDIT_ACTIVITY, ACTION_DELETE);
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }
}
