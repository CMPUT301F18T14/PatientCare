/*
 * newUsernameActivity
 *
 * Copyright (C) 2018 CMPUT301F18T14. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.example.picmymedcode.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.picmymedcode.Controller.PicMyMedController;
import com.example.picmymedcode.Controller.Utility;
import com.example.picmymedcode.Model.User;
import com.example.picmymedcode.R;
import com.example.picmymedcode.Model.CareProvider;
import com.example.picmymedcode.Model.Patient;

import static java.lang.String.valueOf;

/**
 * SignUpActivity extends AppCompatActivity to create an activity for a new user to sign up
 *
 * @author  Umer, Apu, Ian, Shawna, Eenna, Debra
 * @version 1.1, 16/11/18
 * @since   1.1
 */
public class SignUpActivity extends AppCompatActivity {

    EditText enteredUsername;
    EditText enteredEmail;
    EditText enteredPhone;
    Button signUpBtn;
    RadioGroup radioGroupUserProfile;
    RadioButton radioButtonUserProfileType;
    private static final Integer MIN_USER_ID_LENGTH = 8;


    /**
     * Method initializes SignUpActivity
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup_activity);

        enteredUsername = (EditText) findViewById(R.id.enteredUID);
        enteredEmail = (EditText) findViewById(R.id.enteredEmail);
        enteredPhone = (EditText) findViewById(R.id.enteredPhone);
        signUpBtn = (Button) findViewById(R.id.signUpButton);
        radioGroupUserProfile = (RadioGroup) findViewById(R.id.rg_userprofile);
        signUpBtn.setOnClickListener(signUpOnClickListener);


    }

    /**
     * OnClickListener for signUpButton separated
     */
    private View.OnClickListener signUpOnClickListener = new View.OnClickListener() {
        /**
         * Method handles user clicking the sign up button
         *
         * @param v View
         */
        @Override
        public void onClick(View v) {
            signUpLogic();
        }
    };

    /**
     * Handles the logic for creating a user
     * Verifies that fields are filled out and username does not exist
     * Before creating a new user
     */
    private void signUpLogic() {

        String username = enteredUsername.getText().toString();
        String email = enteredEmail.getText().toString();
        String phoneNumber = enteredPhone.getText().toString();
        User user = null;


        if (username.length() < MIN_USER_ID_LENGTH) {
            Utility.toastMessage(getApplicationContext(), String.format("User ID should be at least %s characters!", String.valueOf(MIN_USER_ID_LENGTH)));

        } else if (email.length() == 0) {
            Utility.toastMessage(getApplicationContext(), "Email cannot be empty!");

        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Utility.toastMessage(getApplicationContext(), "Invalid email address!");

        } else if (phoneNumber.length() == 0) {
            Utility.toastMessage(getApplicationContext(),"Phone number cannot be empty!");

        } else if (!phoneNumber.matches("^[+]?[0-9]{10,13}$")) {
            Utility.toastMessage(getApplicationContext(),"Phone number is invalid!");

        } else if (radioButtonUserProfileType == null) {
            Utility.toastMessage(getApplicationContext(), "Please select a user profile from the options listed!");

        } else {
            try {
                if (radioButtonUserProfileType.getId() == R.id.rb_patient) {
                    user = new Patient(username, email, phoneNumber);

                } else if (radioButtonUserProfileType.getId() == R.id.rb_careprovider) {
                    user = new CareProvider(username, email, phoneNumber);
                }

                if (PicMyMedController.checkValidUser(user) != 1 && user != null) {
                    Utility.toastMessage(getApplicationContext(), "Username already exists, please try another one.");
                    return;

                } else {
                    PicMyMedController.createUser(user);
                    Utility.toastMessage(getApplicationContext(), "Account successfully created. Please login.");
                    Intent logInScreenIntent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(logInScreenIntent);
                    finish();

                }
            } catch (Exception e) {
                Log.i("DEBUG SignUpActivity", e.getMessage());
                Utility.toastMessage(getApplicationContext(), e.getMessage());
            }
        }
    }

    /**
     * Method identifies the radio button that was clicked
     *
     * @param v
     */
    public void userProfileClick (View v) {

        int radioButtonID = radioGroupUserProfile.getCheckedRadioButtonId();
        radioButtonUserProfileType = (RadioButton)findViewById(radioButtonID);
    }
}