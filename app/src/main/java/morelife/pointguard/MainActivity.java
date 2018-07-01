package morelife.pointguard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button nGoogleBtn;
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient nGoogleApiClient;
    private FirebaseAuth nAuth;
    private static final String TAG = "MAIN_ACTIVITY";
    private FirebaseAuth.AuthStateListener nAuthListener;

    CircularProgressButton hy, hy2;
    private TextView Name, Email, nid, larry;
    String selectedPhoto;
    Uri imageUri;
    Bitmap bitmap;
    RelativeLayout goog;
    Animation rotate;

    public static final String hello = "hellol";
    public static final String Namess = "hello world";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (TextView) findViewById(R.id.name);
        Email = (TextView) findViewById(R.id.email);
        nid = (TextView) findViewById(R.id.nid);
        larry = (TextView) findViewById(R.id.larry);


        nAuth = FirebaseAuth.getInstance();
        nAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(MainActivity.this, Schools.class));
                    Intent intent = new Intent(MainActivity.this, Schools.class);


                    String lolo = Name.getText().toString();
                    String lolos = Email.getText().toString();
                    String loloss = nid.getText().toString();
                    String lolosss = larry.getText().toString();

                    intent.putExtra("nm",lolo);
                    intent.putExtra("em",lolos);
                    intent.putExtra("id",loloss);
                    intent.putExtra("larry",lolosss);

                    startActivity(intent);

                }

            }
        };



        //nGoogleBtn = (Button) findViewById(R.id.googleBtn1);


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)

                //fetch default_web_client_id and put it in my phpmyadmin UID table.
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        nGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(MainActivity.this, "You Got an Error", Toast.LENGTH_LONG).show();

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        hy = (CircularProgressButton) findViewById(R.id.hy);
        hy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hy.startAnimation();
                signIn();

            }
        });


//        Uri uri;
//        String stringUri;
//        uri = Uri.parse(stringUri);




//        textView = (TextView) findViewById(R.id.textView7);
//
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent StartIntent = new Intent(getApplicationContext(), Terms.class);
//                startActivity(StartIntent);
//
//            }
//        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        nAuth.addAuthStateListener(nAuthListener);



    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(nGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            //Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                String name = account.getDisplayName();
                String email = account.getEmail();
                String Id = account.getId();
                Uri Photo = account.getPhotoUrl();

                String stringUri;
                stringUri = Photo.toString();
                Log.d(TAG, stringUri);

                SharedPreferences sh = getSharedPreferences(hello , 0);
                SharedPreferences.Editor edit = sh.edit();
                edit.putString("nmm",name);
                edit.putString("nmma",email);
                edit.putString("nmmaa",stringUri);

                Name.setText(name);
                Email.setText(email);
                larry.setText(stringUri);
                nid.setText(Id);

                edit.commit();






            } else {
                Log.d(TAG, String.valueOf(result));
                Toast.makeText(MainActivity.this, "Sorry Authentication Failed...",
                        Toast.LENGTH_SHORT).show();
                hy.stopAnimation();
                Intent intent = new Intent(MainActivity.this,MainActivity.class);

                startActivity(intent);
                //GoogleSignIn failed, Update Contacts.Intents.UI appropriately
                // ...
            }
        }


    }




    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {


        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        nAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = nAuth.getCurrentUser();



                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Sorry Authentication Failed..",
                                    Toast.LENGTH_SHORT).show();
//
                            hy.stopAnimation();
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);

                            startActivity(intent);

                        }

                        // ...
                    }

                    private void updateUI(FirebaseUser user) {


                    }
                });


    }


}
