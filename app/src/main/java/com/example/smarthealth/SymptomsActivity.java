package com.example.smarthealth;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SymptomsActivity extends AppCompatActivity {
    private EditText[] symptomInputs;
    private TextView resultTextView;
    private APIHandler apiHandler;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_symptoms);

        // Initialize APIHandler
        apiHandler = new APIHandler(this);

        // Initialize symptom inputs
        symptomInputs = new EditText[7];
        symptomInputs[0] = findViewById(R.id.Symptom_1);
        symptomInputs[1] = findViewById(R.id.Symptom_2);
        symptomInputs[2] = findViewById(R.id.Symptom_3);
        symptomInputs[3] = findViewById(R.id.Symptom_4);
        symptomInputs[4] = findViewById(R.id.Symptom_5);
        symptomInputs[5] = findViewById(R.id.Symptom_6);
        symptomInputs[6] = findViewById(R.id.Symptom_7);

        Button predictButton = findViewById(R.id.predict);
        resultTextView = findViewById(R.id.result);

        // Retrieve name, age, and gender from the intent
        final String name;
        final String age;
        final String gender;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            age = extras.getString("age");
            gender = extras.getString("gender");
        } else {
            name = "";
            age = "";
            gender = "";
        }

        predictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] symptoms = new String[7];
                for (int i = 0; i < 7; i++) {
                    symptoms[i] = symptomInputs[i].getText().toString();
                }

                apiHandler.predictDisease(symptoms, new APIHandler.VolleyCallback() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onSuccess(String disease) {
                        String message = "Predicted Disease: " + disease + "\n";
                        message += "Name: " + name + "\n";
                        message += "Age: " + age + "\n";
                        message += "Gender: " + gender;

                        resultTextView.setText(message);
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onError() {
                        resultTextView.setText("Error occurred during prediction.");
                    }
                });
            }
        });
    }
}

