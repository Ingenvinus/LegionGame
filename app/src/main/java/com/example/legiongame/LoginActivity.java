package com.example.legiongame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    private GoogleSignInClient GoogleSignInClient;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "SIGN_IN";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Firebase authentication
        firebaseAuth = FirebaseAuth.getInstance();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        GoogleSignInClient = GoogleSignIn.getClient(this, gso);

        checkUser();

        // Google signIn button
        SignInButton googleButton = findViewById(R.id.sign_in_button);//.setOnClickListener();
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //begin google SignIn
                Log.d(TAG, "onClick: begin Google SignIn");
                resultLauncher.launch(new Intent(GoogleSignInClient.getSignInIntent()));
            }
        });
    }

    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){
            Log.d(TAG,"checkUser: User already signed in");
            startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
            finish();
        }
    }

    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK){
                Intent intent = result.getData();
                Log.d(TAG, "OnActivityResult: Google Singin intent result");
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);

                    assert account != null;
                    firebaseAuthWithGoogleAccount(account);
                } catch (ApiException e) {
                    // Google SignIn Failed
                    Log.d(TAG, "OnActivityResult:"+e.getMessage());
                }
            }
        }

        private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
            Log.d(TAG, "firebaseAuthWithGoogleAccount: begin firebase auth with google account");
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            firebaseAuth.signInWithCredential(credential)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Log.d(TAG, "onSuccess: Logging in succesfull");

                            // get logged in user
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            // get user info
                            String uid = firebaseUser.getUid();
                            String email = firebaseUser.getEmail();

                            // check user
                            Log.d(TAG, "onSucces: UID"+uid);
                            Log.d(TAG, "onSucces: Email"+email);

                            // check if user is new or already exists
                            if (authResult.getAdditionalUserInfo().isNewUser()){
                                //user is new - account created
                                Toast.makeText(LoginActivity.this, "Account Created\n"+email, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                // existing user - Logged in
                                Toast.makeText(LoginActivity.this, "Exisiting User\n"+email, Toast.LENGTH_SHORT).show();
                            }

                            // start main activity
                            startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: Logging in failed" +e.getMessage());
                        }
                    });
        }
    });
    
    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);

    }

    public void updateUI(GoogleSignInAccount account){
        if(account != null) {
            Intent switchActivityIntent = new Intent(this, MainMenuActivity.class);
            startActivity(switchActivityIntent);
        }
    }
}