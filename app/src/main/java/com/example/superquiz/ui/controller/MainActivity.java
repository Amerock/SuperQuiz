package com.example.superquiz.ui.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.superquiz.R;
import com.example.superquiz.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TextView mGreetingTextView;
    private EditText mNameEditText;
    private Button mPlayButton;
    private static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    private static final String SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO";
    private static final String SHARED_PREF_USER_INFO_NAME = "SHARED_PREF_USER_INFO_NAME";
    private static final String SHARED_PREF_USER_INFO_SCORE = "SHARED_PREF_USER_INFO_SCORE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mGreetingTextView = findViewById(R.id.main_textview_greeting);
        mNameEditText = findViewById(R.id.main_edittext_name);
        mPlayButton = findViewById(R.id.main_button_play);
        mPlayButton.setEnabled(false);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE);
        String firstName = sharedPreferences.getString(SHARED_PREF_USER_INFO_NAME, null);
        int AncienScore = sharedPreferences.getInt(SHARED_PREF_USER_INFO_SCORE, 0);



        if (firstName != null) {
            mNameEditText.setText(firstName);
            mNameEditText.setSelection(firstName.length());
        }


        if (firstName != null && AncienScore > 0) {
            String message = getString(R.string.welcome_back_with_score, firstName, AncienScore);
            mGreetingTextView.setText(message);
        } else {
            mGreetingTextView.setText(getString(R.string.Bienvenue));
        }

        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPlayButton.setEnabled(!s.toString().isEmpty());
            }
        });
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The user just clicked
                getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)
                        .edit()
                        .putString(SHARED_PREF_USER_INFO_NAME, mNameEditText.getText().toString())
                        .apply();

                startActivityForResult(new Intent(MainActivity.this, GameActivity.class), GAME_ACTIVITY_REQUEST_CODE);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode && data != null) {
            // Fetch the score from the Intent
            int score = data.getIntExtra(GameActivity.BUNDLE_STATE_SCORE, 0);

            getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)
                    .edit()
                    .putInt(SHARED_PREF_USER_INFO_SCORE, score)
                    .apply();

            greetUser();
        }
    }
    private void greetUser() {
        String firstName = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getString(SHARED_PREF_USER_INFO_NAME, null);
        int score = getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE).getInt(SHARED_PREF_USER_INFO_SCORE, -1);

        if (firstName != null) {
            if (score != -1) {
                mGreetingTextView.setText(getString(R.string.welcome_back_with_score, firstName, score));
            }

            mNameEditText.setText(firstName);
        }
    }

}