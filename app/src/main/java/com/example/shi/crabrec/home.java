package com.example.shi.crabrec;

import android.Manifest;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fragment.RecognitionFragment;
import fragment.ResnetRecognitionFragment;
import fragment.ValidateFragment;
import model.Tab;

public class home extends AppCompatActivity {

    private List<Tab> mTabs = new ArrayList<>();
    private FragmentTabHost mTabhost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=23)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        setContentView(R.layout.activity_home);
        initTab();
    }
    private void initTab(){
        Tab tab_recognition = new Tab(R.string.tab2,R.drawable.selector_icon_home, RecognitionFragment.class);
        mTabs.add(tab_recognition);
        Tab tab_resnetRecognition = new Tab(R.string.tab3,R.drawable.selector_icon_home, ResnetRecognitionFragment.class);
        mTabs.add(tab_resnetRecognition);
        mTabhost=findViewById(R.id.tabhost);
        mTabhost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);
        for(Tab tab:mTabs){
            TabHost.TabSpec tabSpec=mTabhost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            mTabhost.addTab(tabSpec,tab.getFragment(),null);
        }
        mTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabhost.setCurrentTab(0);
    }
    private View buildIndicator(Tab tab) {
        View view= LayoutInflater.from(this).inflate(R.layout.tab_indicator,null);
        ImageView imageView=view.findViewById(R.id.icon_tab);
        TextView textView=view.findViewById(R.id.txt_indicator);
        imageView.setImageResource(tab.getIcon());
        textView.setText(tab.getTitle());
        return view;
    }
}
