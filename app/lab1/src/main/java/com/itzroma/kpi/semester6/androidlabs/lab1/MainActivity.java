package com.itzroma.kpi.semester6.androidlabs.lab1;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText passwordInputET;

    private RadioGroup visibilityOptionsRG;

    private Button okButton;

    private TextView retrievedPassTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passwordInputET = findViewById(R.id.password_input);
        visibilityOptionsRG = findViewById(R.id.visibility_options);
        okButton = findViewById(R.id.ok_button);
        retrievedPassTV = findViewById(R.id.retrieved_pass);

        // i do this because placeholder in string are displaying by default
        setRetrievedPassTVText("");

        visibilityOptionsRG.setOnCheckedChangeListener((radioGroup, i) -> {
            int cursorStart = passwordInputET.getSelectionStart();
            int cursorEnd = passwordInputET.getSelectionEnd();

            if (i == R.id.hide_rb) {
                passwordInputET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else if (i == R.id.show_rb) {
                passwordInputET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
            }

            passwordInputET.setSelection(cursorStart, cursorEnd);
        });

        okButton.setOnClickListener(v -> setRetrievedPassTVText(passwordInputET.getText().toString()));
    }

    private void setRetrievedPassTVText(String text) {
        retrievedPassTV.setText(getResources().getString(R.string.retrieved_pass, text));
    }
}