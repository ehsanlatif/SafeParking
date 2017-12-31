package com.example.ehsan.safeparking;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

public class Intermediate extends AppCompatActivity implements OnFragmentInteractionListener {
    Fragment selectedFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_white_36dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Button button=(Button) findViewById(R.id.appCompatButton);
        if(Login.isLoggedIn)
        button.setVisibility(View.GONE);

        selectedFragment = MapFragment.newInstance(null,null);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, selectedFragment);
        transaction.commit();
    }

    public void SignupButtonPressed(View view) {
        Intent intent = new Intent(this, Login.class);
        intent.putExtra("intent", "signup");
        finish();
        startActivity(intent);
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
