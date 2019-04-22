package com.example.kjorge.agenda.Activity;


import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.example.kjorge.agenda.Fragment.FragmentMain;
import com.example.kjorge.agenda.InterfaceHelp.InterfaceHelp;
import com.example.kjorge.agenda.R;

public class MainActivity extends AppCompatActivity implements InterfaceHelp, TabLayout.OnTabSelectedListener {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FindView();
        OnClick();

        String valorRetorno = (String) getIntent().getSerializableExtra("valor");


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Agendamento");

        tabLayout.addTab(tabLayout.newTab().setText("Agendamento"));
        tabLayout.addTab(tabLayout.newTab().setText("Historico"));
        tabLayout.addOnTabSelectedListener(this);
        viewPager.setAdapter(new FragmentMain(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        if (valorRetorno != null) {
            Log.e("passou","passou com o retorno");
            FragmentTransaction frag = getSupportFragmentManager().beginTransaction();
            viewPager.setCurrentItem(1);
            frag.commit();
        } else {
            Log.e("passou","passou else");

        }
    }

    @Override
    public void FindView() {
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
    }

    @Override
    public void OnClick() {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
