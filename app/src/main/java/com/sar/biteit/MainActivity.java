package com.sar.biteit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private AppCompatImageView sign_in_facebook;
    CallbackManager callbackManager;
    private LoginButton loginButton;
    private static final String EMAIL = "email";
    private AppCompatTextView forgot_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        sign_in_facebook = findViewById(R.id.sign_in_facebook);
        loginButton = findViewById(R.id.login_button);
        forgot_password = findViewById(R.id.forgot_password);

        boolean loggedOut = AccessToken.getCurrentAccessToken() == null;


        if (!loggedOut) {
//            Toast.makeText(getApplicationContext(), "Username is: " + Profile.getCurrentProfile().getName(),Toast.LENGTH_SHORT).show();
            Log.d("TAG", "Username is: " + Profile.getCurrentProfile().getName());
            getUserProfile(AccessToken.getCurrentAccessToken());
        }
        callbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions(Arrays.asList(EMAIL, "public_profile"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
                Log.d("API123", loggedIn + " ??");
                Toast.makeText(getApplicationContext(), "Logged in",Toast.LENGTH_SHORT).show();
                getUserProfile(AccessToken.getCurrentAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getApplicationContext(), " not Logged in",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(getApplicationContext(), "error Logged in" + exception.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("API111",  exception.getMessage()+ " ??");
                printKeyHash();
            }
        });


        sign_in_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", object.toString());
                        try {
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String email = object.getString("email");
                            String id = object.getString("id");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                            Toast.makeText(getApplicationContext(), "Name: " + first_name + " " + last_name
                                    +"\nEmail:  " + email
                                    +"\nID: "+id,Toast.LENGTH_LONG).show();

//                            forgot_password.setText("First Name: " + first_name + "\nLast Name: " + last_name);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

        Intent intent = new Intent(MainActivity.this, home.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    private void printKeyHash() {
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.sar.biteit", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("KeyHash:", e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("KeyHash:", e.toString());
        }
    }
}