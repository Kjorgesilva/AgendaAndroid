package com.example.kjorge.agenda.Fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.kjorge.agenda.Activity.EditaActivity;
import com.example.kjorge.agenda.Model.Agendamento;

import java.util.ArrayList;
import java.util.List;

public class FragmentMain extends FragmentStatePagerAdapter {

    private Fragment fragment;


    public FragmentMain(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int i) {


        switch (i) {
            case 0:
                fragment = new Fragment_Agendamento();
                break;
            case 1:
                fragment = new Fragment_Historico();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

}
