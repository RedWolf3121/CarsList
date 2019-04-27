package com.example.carslist.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.carslist.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeUserActivity extends AppCompatActivity {

    TextView tvUsername;
    ImageView ivUserAvatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_user);

        tvUsername = findViewById(R.id.username);
        ivUserAvatar = findViewById(R.id.useravatar);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            tvUsername.setText(firebaseUser.getDisplayName());
            Glide.with(this)
                    .load(firebaseUser.getPhotoUrl().toString())
                    .into(ivUserAvatar);


            findViewById(R.id.out).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AuthUI.getInstance()
                            .signOut(WelcomeUserActivity.this)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                public void onComplete(@NonNull Task<Void> task) {
                                    startActivity(new Intent(WelcomeUserActivity.this, MainActivity.class));
                                    finish();
                                }
                            });
                }
            });
        }
    }
}
