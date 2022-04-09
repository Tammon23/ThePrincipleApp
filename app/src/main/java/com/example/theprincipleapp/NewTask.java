package com.example.theprincipleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.theprincipleapp.db.Task;
import com.example.theprincipleapp.db.TaskTypeEnum;
import com.example.theprincipleapp.db.UserDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewTask extends AppCompatActivity {

    Button btnSubmit, btnCancel;
    EditText editTextDescription, editTextName, editTextLocation, editTextOpenDateDate, editTextOpenDateTime, editTextDueDateDate, editTextDueDateTime;
    Spinner spinnerTaskType;

    String description, name, location;
    TaskTypeEnum taskType;

    final Calendar openDateCalendar = Calendar.getInstance();
    final Calendar dueDateCalendar = Calendar.getInstance();

    Date openDate, dueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        // temp
        UserDatabase.createDatabase(getApplicationContext());

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please select a valid task type.");
        AlertDialog dialog = alertDialogBuilder.create();

        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);

        editTextDescription = findViewById(R.id.editTextMultilineDescription);
        editTextName = findViewById(R.id.editTextName);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextOpenDateDate = findViewById(R.id.editTextOpenDateDate);
        editTextOpenDateTime = findViewById(R.id.editTextOpenDateTime);
        editTextDueDateDate = findViewById(R.id.editTextDueDateDate);
        editTextDueDateTime = findViewById(R.id.editTextDueDateTime);

        spinnerTaskType = (Spinner) findViewById(R.id.spinnerTaskType);
        spinnerTaskType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TaskTypeEnum.values()));

        btnSubmit.setOnClickListener(view -> {
            int spinnerSelectedPosition = spinnerTaskType.getLastVisiblePosition();

            if (spinnerSelectedPosition == 0){
                dialog.show();
            } else {
                location = editTextLocation.getText().toString();
                name = editTextName.getText().toString();
                description = editTextDescription.getText().toString();
                taskType = TaskTypeEnum.values()[spinnerSelectedPosition];

                openDate = openDateCalendar.getTime();
                dueDate = dueDateCalendar.getTime();

                // openDateCalendar and dueDateCalendar has dates,
                // (defaults to today of non filled entries)


                Task task = new Task();
                task.location = location;
                task.name = name;
                task.description = description;
                task.type = taskType;
                task.open = openDate;
                task.due = dueDate;

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        //TODO: Save New Task to database

                        // UserDatabase.UDB.taskDao().insert(task);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Toast.makeText(getApplicationContext(),"Task successfully added", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });


                finish();
            }
        });

        btnCancel.setOnClickListener(view -> {
            finish();
        });


        // -------------------------- Listeners for choosing open date and time --------------------------
        DatePickerDialog.OnDateSetListener openDateDate = (view, year, month, day) -> {
            openDateCalendar.set(Calendar.YEAR, year);
            openDateCalendar.set(Calendar.MONTH,month);
            openDateCalendar.set(Calendar.DAY_OF_MONTH,day);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
            editTextOpenDateDate.setText(dateFormat.format(openDateCalendar.getTime()));
        };

        TimePickerDialog.OnTimeSetListener openDateTime = (timePicker, i, i1) -> {
            openDateCalendar.set(Calendar.MINUTE, timePicker.getMinute());
            openDateCalendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            editTextOpenDateTime.setText(dateFormat.format(openDateCalendar.getTime()));
        };

        editTextOpenDateDate.setOnClickListener(view -> new DatePickerDialog(this, openDateDate, openDateCalendar.get(Calendar.YEAR), openDateCalendar.get(Calendar.MONTH), openDateCalendar.get(Calendar.DAY_OF_MONTH)).show());

        editTextOpenDateTime.setOnClickListener(view -> new TimePickerDialog(this, openDateTime, openDateCalendar.get(Calendar.MINUTE), openDateCalendar.get(Calendar.HOUR), false).show());
        // -------------------------- END Listeners for choosing open date and time --------------------------


        // -------------------------- Listeners for choosing due date and time --------------------------
        DatePickerDialog.OnDateSetListener dueDateDate = (view, year, month, day) -> {
            dueDateCalendar.set(Calendar.YEAR, year);
            dueDateCalendar.set(Calendar.MONTH,month);
            dueDateCalendar.set(Calendar.DAY_OF_MONTH,day);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
            editTextDueDateDate.setText(dateFormat.format(dueDateCalendar.getTime()));
        };

        TimePickerDialog.OnTimeSetListener dueDateTime = (timePicker, i, i1) -> {
            dueDateCalendar.set(Calendar.MINUTE, timePicker.getMinute());
            dueDateCalendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            editTextDueDateTime.setText(dateFormat.format(dueDateCalendar.getTime()));
        };

        editTextDueDateDate.setOnClickListener(view -> new DatePickerDialog(this, dueDateDate, dueDateCalendar.get(Calendar.YEAR), dueDateCalendar.get(Calendar.MONTH), dueDateCalendar.get(Calendar.DAY_OF_MONTH)).show());

        editTextDueDateTime.setOnClickListener(view -> new TimePickerDialog(this, dueDateTime, dueDateCalendar.get(Calendar.MINUTE), dueDateCalendar.get(Calendar.HOUR), false).show());
        // END -------------------------- Listeners for choosing due date and time --------------------------

    }
}
