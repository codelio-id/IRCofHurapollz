package com.jabirdeveloper.ircofhurapollz.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.jabirdeveloper.ircofhurapollz.R;
import com.jabirdeveloper.ircofhurapollz.model.UserModel;
import com.jabirdeveloper.ircofhurapollz.model.authentication.UserAuth;
import com.jabirdeveloper.ircofhurapollz.network.WPApiUrl;
import com.jabirdeveloper.ircofhurapollz.network.WPConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RegisterActivity extends AppCompatActivity {
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Build dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Silahkan menunggu...");
        builder.setCancelable(false);
        dialog = builder.create();

        // Get all input
        TextInputEditText tiUsername = findViewById(R.id.tiUsername);
        TextInputEditText tiFname = findViewById(R.id.tiFname);
        TextInputEditText tiLname = findViewById(R.id.tiLname);
        TextInputEditText tiEmail = findViewById(R.id.tiEmail);
        TextInputEditText tiPassword = findViewById(R.id.tiPassword);
        TextInputEditText tiPassword2 = findViewById(R.id.tiPassword2);

        // Get button and set listener
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable username = tiUsername.getText();
                Editable fname = tiFname.getText();
                Editable lname = tiLname.getText();
                Editable email = tiEmail.getText();
                Editable password = tiPassword.getText();
                Editable password2 = tiPassword2.getText();

                if (
                        username != null && !(username.toString().equals(""))
                                && fname != null && !(fname.toString().equals(""))
                                && lname != null && !(lname.toString().equals(""))
                                && email != null && !(email.toString().equals(""))
                                && password != null && !(password.toString().equals(""))
                                && password2 != null && !(password2.toString().equals(""))
                ) {
                    if (password.toString().equals(password2.toString())) {
                        if (checkString(password.toString())) {
                            register(
                                    username.toString(),
                                    fname.toString(),
                                    lname.toString(),
                                    email.toString(),
                                    password.toString()
                            );
                        } else {
                            tiPassword.setError("Password harus terdiri dari huruf kecil, besar, dan angka");
                            tiPassword2.setError("Password harus terdiri dari huruf kecil, besar, dan angka");
                        }
                    } else {
                        tiPassword2.setError("Password harus sama");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void register(String username, String fname, String lname, String email, String password) {
        dialog.show();
        String url = "register";
        Call<ResponseBody> register = WPConnection.getWpService().register(
                url,
                MultipartBody.Part.createFormData("user_login-2642", username),
                MultipartBody.Part.createFormData("first_name-2642", fname),
                MultipartBody.Part.createFormData("last_name-2642", lname),
                MultipartBody.Part.createFormData("user_email-2642", email),
                MultipartBody.Part.createFormData("user_password-2642", password),
                MultipartBody.Part.createFormData("confirm_user_password-2642", password),
                MultipartBody.Part.createFormData("form_id", "2642"),
                MultipartBody.Part.createFormData("timestamp", (new Date()).getTime() + ""),
                MultipartBody.Part.createFormData("_wpnonce", "aa5ae54b3b"),
                MultipartBody.Part.createFormData("_wp_http_referer", "/register/")
        );
        register.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.code() == 200) {
                    login(username, password);
                } else {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Gagal register", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Gagal register", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login(String username, String password) {
        String url = WPApiUrl.BASE_URL_LOGIN;
        Call<UserModel> login = WPConnection.getWpService().login(url, new UserAuth(username, password));
        login.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                if (response.body() != null) {

                    // Save token
                    // Get JWT token in shared prefs
                    SharedPreferences sh = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sh.edit();
                    editor.putString("token", response.body().getToken());
                    editor.putString("display_name", response.body().getUser_display_name());
                    editor.putString("email", response.body().getUser_email());
                    editor.putString("nicename", response.body().getUser_nicename());
                    editor.apply();

                    startActivity(new Intent(getApplication(), MainActivity.class));
                    finish();
                } else if (response.errorBody() != null) {
                    String msg = "";
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        msg = jObjError.getString("code");
                        if (msg.equalsIgnoreCase("[jwt_auth] invalid_username")) {
                            msg = "Gagal register, email sudah terdaftar";
                        } else if (msg.equalsIgnoreCase("[jwt_auth] incorrect_password")) {
                            msg = "Gagal register, username sudah terdaftar";
                        } else {
                            msg = "Sukses register, silahkan cek email anda untuk konfirmasi";
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplication(), MainActivity.class));
                            finish();
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Gagal register", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Gagal register", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static boolean checkString(String str) {
        char ch;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        for(int i=0;i < str.length();i++) {
            ch = str.charAt(i);
            if( Character.isDigit(ch)) {
                numberFlag = true;
            }
            else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if(numberFlag && capitalFlag && lowerCaseFlag)
                return true;
        }
        return false;
    }
}