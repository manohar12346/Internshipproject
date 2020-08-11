package com.example.internshipproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager pager;
    TabLayout tab;
    Toolbar bar;
    DrawerLayout layout;
    NavigationView navig;
    FragmentManager manager;
    FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager=findViewById(R.id.viewpager);
        tab=findViewById(R.id.tablayout);
        navig=findViewById(R.id.navigation);
        layout=findViewById(R.id.drawer);


        bar=findViewById(R.id.toolbar);
        pager.setAdapter(new Adapter (getSupportFragmentManager(),Adapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,tab.getTabCount()));
        tab.setupWithViewPager(pager);
        setSupportActionBar(bar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,layout,bar,0,0);
        toggle.syncState();
        layout.addDrawerListener(toggle);
        navig.setNavigationItemSelectedListener(this);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    class Adapter extends FragmentPagerAdapter {
        public Adapter(@NonNull FragmentManager fm, int behaviorResumeOnlyCurrentFragment, int tabCount) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new birthday() ;
                case 1:
                    return new Anniversaries() ;
                case 2:
                    return new others() ;

            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case 0:
                    return "Birthdays" ;
                case 1:
                    return " Anniversaries" ;
                case 2:
                    return  "others" ;

            }
            return super.getPageTitle(position);


        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        manager=getSupportFragmentManager();
        transaction=manager.beginTransaction();
        switch(item.getItemId()){
            case R.id.settings:
                Intent inten =new Intent(this,settings.class);
                startActivity(inten);
                break;

            case R.id.ome:
                Intent intent =new Intent(this,MainActivity.class);
                startActivity(intent);

        }
        return false;
    }

}
