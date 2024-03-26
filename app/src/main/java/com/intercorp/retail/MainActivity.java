package com.intercorp.retail;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etSurname, etAge, etDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.et_name);
        etSurname = findViewById(R.id.et_surname);
        etAge = findViewById(R.id.et_age);
        etDate = findViewById(R.id.et_date);
        Button btnSave = findViewById(R.id.btn_save);

        etDate.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                    (view, year, monthOfYear, dayOfMonth) -> etDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year), mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String surname = etSurname.getText().toString();
            String age = etAge.getText().toString();
            String date = etDate.getText().toString();

            // Inicializa Firebase
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("users");

            // Obtiene el usuario actual
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String userId = user.getUid();

                // Crea un mapa con los datos del usuario
                Map<String, Object> userData = new HashMap<>();
                userData.put("name", name);
                userData.put("surname", surname);
                userData.put("age", age);
                userData.put("date", date);

                // Guarda los datos en Firebase
                myRef.child(userId).setValue(userData);
            }
        });
    }
}
