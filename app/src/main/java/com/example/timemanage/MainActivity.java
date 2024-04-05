package com.example.timemanage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    private Fragment fragment_main;
    private Fragment fragment_lock;
    private Fragment fragment_statistic;
    public void createAddDialog() {
        //创建 一个提示对话框的构造者对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加课程");//设置弹出对话框的标题
        //builder.setIcon(R.drawable.ic_launcher);//设置弹出对话框的图标
        builder.setCancelable(false);//能否被取消


        View view = LayoutInflater.from(this).inflate(R.layout.activity_add_dialog, null);
        Spinner week_spinner = (Spinner) view.findViewById(R.id.week_spinner);
        Spinner time_spinner_1 = (Spinner) view.findViewById(R.id.time_spinner_1);
        Spinner time_spinner_2 = (Spinner) view.findViewById(R.id.time_spinner_2);
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayList2 = new ArrayList<>();
        arrayList.add("周一");
        arrayList.add("周二");
        arrayList.add("周三");
        arrayList.add("周四");
        arrayList.add("周五");
        arrayList.add("周六");
        arrayList.add("周日");
        arrayList2.add("1");arrayList2.add("2");arrayList2.add("3");arrayList2.add("4");arrayList2.add("5");
        arrayList2.add("6");arrayList2.add("7");arrayList2.add("8");arrayList2.add("9");arrayList2.add("10");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,arrayList);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,arrayList2);
        week_spinner.setAdapter(arrayAdapter);
        time_spinner_1.setAdapter(arrayAdapter2);
        time_spinner_2.setAdapter(arrayAdapter2);

        builder.setView(view);
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "那你还要不要再吃点吧", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "那你就吃一点吧", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        builder.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //case android.R.id.home:
            //mDrawerLayout.openDrawer(GravityCompat.START);
            //break;
            case R.id.add:
                createAddDialog();
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment_main = new MainFragment();
        fragment_lock = new LockFragment();
        fragment_statistic = new StatisticFragment();
        fragmentTransaction.add(R.id.fragment_layout,fragment_main);
        fragmentTransaction.commit();
        BottomNavigationView bottomNavigationItemView = findViewById(R.id.bottomnavigation);
        bottomNavigationItemView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (menuItem.getItemId()){
                    case R.id.item_tab1:
                        fragmentTransaction.replace(R.id.fragment_layout,  fragment_main);
                        break;
                    case R.id.item_tab2:
                        fragmentTransaction.replace(R.id.fragment_layout, fragment_lock);
                        //跳转计时
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.commit();
                        break;
                    case R.id.item_tab3:
                        fragmentTransaction.replace(R.id.fragment_layout, fragment_statistic);
                        break;
                }
                fragmentTransaction.commit();
                return true;
            }
        });

    }

}