package com.wiprotestapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private TextView tv_transportinfo;
    private TextView tv_red;
    private TextView tv_blue;
    private TextView tv_green;
    private LinearLayout ll_bottom;
    private TextView tv_itemdisplay;
    MyPageAdapter pageAdapter;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        tv_transportinfo = (TextView) findViewById(R.id.tv_transportinfo);
        tv_itemdisplay = (TextView)findViewById(R.id.tv_itemdisplay);
        tv_red = (TextView)findViewById(R.id.tv_red);
        tv_red.setOnClickListener(this);
        tv_blue = (TextView)findViewById(R.id.tv_blue);
        tv_blue.setOnClickListener(this);
        tv_green = (TextView)findViewById(R.id.tv_green);
        tv_green.setOnClickListener(this);
        ll_bottom = (LinearLayout)findViewById(R.id.ll_bottom);

        List<Fragment> fragments = getFragments();
        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        pager =
                (ViewPager)findViewById(R.id.viewPager);
        pager.setAdapter(pageAdapter);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    pager.setBackgroundColor(getResources().getColor(R.color.red));
                }else if(position==1){
                    pager.setBackgroundColor(getResources().getColor(R.color.blue));
                }else if(position==2){
                    pager.setBackgroundColor(getResources().getColor(R.color.green));
                }else if(position==3){
                    pager.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                Toast.makeText(MainActivity.this,"Fragment: "+(position+1),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(MyFragment.newInstance("Fragment 1"));
        fragmentList.add(MyFragment.newInstance("Fragment 2"));
        fragmentList.add(MyFragment.newInstance("Fragment 3"));
        fragmentList.add(MyFragment.newInstance("Fragment 4"));
        return fragmentList;
    }

    class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;
        public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }
        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }
        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id) {

            case R.id.tv_item1:
                tv_itemdisplay.setText(getResources().getString(R.string.item1));
                break;
            case R.id.tv_item2:
                tv_itemdisplay.setText(getResources().getString(R.string.item2));
                break;
            case R.id.tv_item3:
                tv_itemdisplay.setText(getResources().getString(R.string.item3));
                break;
            case R.id.tv_item4:
                tv_itemdisplay.setText(getResources().getString(R.string.item4));
                break;
            case R.id.tv_item5:
                tv_itemdisplay.setText(getResources().getString(R.string.item5));
                break;
            case R.id.tv_red:
                ll_bottom.setBackgroundColor(getResources().getColor(R.color.red));
                break;
            case R.id.tv_blue:
                ll_bottom.setBackgroundColor(getResources().getColor(R.color.blue));
                break;
            case R.id.tv_green:
                ll_bottom.setBackgroundColor(getResources().getColor(R.color.green));
                break;
            case R.id.tv_transportinfo:
                Intent transportInfoIntent = new Intent(MainActivity.this, TransportInfoActivity.class);
                startActivity(transportInfoIntent);
                finish();

                break;
            default:
                break;
        }

    }
}
