package com.example.ehsan.safeparking;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity implements OnFragmentInteractionListener {

    public static FragmentManager fragmentManager;
    public static boolean isLoggedIn=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fragmentManager=getSupportFragmentManager();
        String str=getIntent().getStringExtra("intent");
        if(str!=null && str.equals("login"))
        {
            Login_Fragment login_fragment=new Login_Fragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.frameContainer, login_fragment)
                    .commit();
        }else
        {
            SignUp_Fragment signUp__fragment=new SignUp_Fragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameContainer, signUp__fragment);
            transaction.commit();
        }
    }
    protected void replaceLoginFragment() {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer, new Login_Fragment(),
                        Utils.Login_Fragment).commit();
    }
    public void close(View view) {
        onBackPressed();

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
