package com.example.app1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.app1.Fragment.HomeFragment;
import com.example.app1.Fragment.NotificationFragment;
import com.example.app1.Fragment.PostDetailsFragment;
import com.example.app1.Fragment.ProfileFragment;
import com.example.app1.Fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment selectFragment = null;

    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.bottomNavigationView = findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnNavigationItemReselectedListener(navigationItemSelectListener);
        frameLayout = findViewById(R.id.fragment_container);
        Bundle intent = getIntent().getExtras();

        if (intent != null) {

            String publisher = intent.getString("publisherid");
            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();

            editor.putString("profileid", publisher);
            editor.apply();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }


        bottomNavigationView.setOnItemSelectedListener(Item -> {
            Integer id = Item.getItemId();
            if (id == R.id.nav_home1) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new HomeFragment());
                transaction.commit();

            }
            if (id == R.id.nav_search) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new SearchFragment());
                transaction.commit();
            }
            if (id == R.id.nav_add) {

                selectFragment = null;
                startActivity(new Intent(MainActivity.this, PostActivity.class));

//                FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragment_container, new PostDetailsFragment());
//                transaction.commit();
            }
            if (id == R.id.nav_heart) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new NotificationFragment());
                transaction.commit();
            }
                if (id == R.id.nav_profile) {
                    SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                  editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                  editor.apply();
                  selectFragment= new ProfileFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, selectFragment);
                    transaction.commit();
                }
            if(selectFragment!=null){
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectFragment).commit();
                    }
            return true;
        });

//    }

//    private BottomNavigationView.OnNavigationItemReselectedListener navigationItemSelectListener=
//            new BottomNavigationView.OnNavigationItemReselectedListener() {
//
//                @Override
//                public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
//                    switch (menuItem.getItemId()){
////                        case R.id:

//                        case R.id.nav_search:
//                            selectFragment=new SearchFragment();
//                            break;
//                        case R.id.nav_add:
//                            selectFragment=null;
//                            startActivity(new Intent(MainActivity.this,PostActivity.class));
//                            break;
//                        case R.id.nav_heart:
//                            selectFragment= new NotificationFragment();
//                            break;
//                        case R.id.nav_profile:
//                            SharedPreferences.Editor editor = getSharedPreferences("PREFS",MODE_PRIVATE).edit();
//                            editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
//                            editor.apply();
//                            selectFragment= new ProfileFragment();
//                            break;
//                    }
//                    if(selectFragment!=null){
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectFragment).commit();
//                    }
//                }
//            };
    }

}