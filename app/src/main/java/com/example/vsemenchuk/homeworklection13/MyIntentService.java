package com.example.vsemenchuk.homeworklection13;

import android.app.Activity;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


public class MyIntentService extends IntentService {

    public static final String EXTRA_STUDENT = "com.example.vsemenchuk.homeworklection12.extra.STUDENT";
    public static final String EXTRA_PENDING_INTENT = "com.example.vsemenchuk.homeworklection12.extra.PENDING_INTENT";
    public static final String EXTRA_ID = "com.example.vsemenchuk.homeworklection12.extra.ID";
    public static final String EXTRA_AMOUNT = "com.example.vsemenchuk.homeworklection12.extra.AMOUNT";

    public static final String ACTION_INSERT_STUDENT = "com.example.vsemenchuk.homeworklection12.INSERT_STUDENT";
    public static final String ACTION_UPDATE_STUDENT = "com.example.vsemenchuk.homeworklection12.UPDATE_STUDENT";
    public static final String ACTION_DELETE_STUDENT = "com.example.vsemenchuk.homeworklection12.DELETE_STUDENT";

    public static final int REQUEST_CODE_INSERT_STUDENT = 1;
    public static final int REQUEST_CODE_UPDATE_STUDENT = 2;
    public static final int REQUEST_CODE_DELETE_STUDENT = 3;

    public MyIntentService() {
        super("MyIntentService");
    }

    public static void insertStudent(Context context, Student student) {
        Intent intent = new Intent(context, MyIntentService.class);
        PendingIntent pendingIntent = ((AppCompatActivity) context).createPendingResult(
                REQUEST_CODE_INSERT_STUDENT, intent, 0);
        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
        intent.putExtra(EXTRA_STUDENT, student);
        intent.setAction(ACTION_INSERT_STUDENT);

        context.startService(intent);
    }

    public static void updateStudent(Context context, Student student) {
        Intent intent = new Intent(context, MyIntentService.class);
        PendingIntent pendingIntent = ((AppCompatActivity) context).createPendingResult(
                REQUEST_CODE_UPDATE_STUDENT, intent, 0);
        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
        intent.putExtra(EXTRA_STUDENT, student);
        intent.setAction(ACTION_UPDATE_STUDENT);

        context.startService(intent);
    }

    public static void deleteStudent(Context context, Student student) {
        Intent intent = new Intent(context, MyIntentService.class);
        PendingIntent pendingIntent = ((AppCompatActivity) context).createPendingResult(
                REQUEST_CODE_DELETE_STUDENT, intent, 0);
        intent.putExtra(EXTRA_PENDING_INTENT, pendingIntent);
        intent.putExtra(EXTRA_STUDENT, student);
        intent.setAction(ACTION_DELETE_STUDENT);

        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            PendingIntent pendingIntent = intent.getParcelableExtra(EXTRA_PENDING_INTENT);

            DataBaseHelper dbHelper = new DataBaseHelper(this);
            Intent result = new Intent();

            if (action.equals(ACTION_INSERT_STUDENT)) {
                Student student = intent.getParcelableExtra(EXTRA_STUDENT);
                long id = dbHelper.insertStudent(student);
                result.putExtra(EXTRA_ID, id);
            } else if (action.equals(ACTION_UPDATE_STUDENT)) {
                Student student = intent.getParcelableExtra(EXTRA_STUDENT);
                int amount = dbHelper.updateStudent(student);
                result.putExtra(EXTRA_AMOUNT, amount);
            } else if (action.equals(ACTION_DELETE_STUDENT)) {
                Student student = intent.getParcelableExtra(EXTRA_STUDENT);
                int amount = dbHelper.deleteStudent(student);
                result.putExtra(EXTRA_AMOUNT, amount);
            }

            if (pendingIntent != null) {
                try {
                    pendingIntent.send(this, Activity.RESULT_OK, result);
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
