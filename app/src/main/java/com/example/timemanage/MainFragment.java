package com.example.timemanage;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context mainContext;

    private View root;
    private Spinner spinner;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        androidx.appcompat.widget.Toolbar toolbar = root.findViewById(R.id.toolbar);
        mainContext = getActivity();
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.add:
                        createAddDialog();
                        break;
                }
                return false;
            }
        });
        return root;
    }

    public void createAddDialog() {
        //创建 一个提示对话框的构造者对象
        AlertDialog.Builder builder = new AlertDialog.Builder(mainContext);
        builder.setTitle("添加课程");//设置弹出对话框的标题
        //builder.setIcon(R.drawable.ic_launcher);//设置弹出对话框的图标
        builder.setCancelable(false);//能否被取消


        View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_add_dialog, null);
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mainContext, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,arrayList);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(mainContext, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,arrayList2);
        week_spinner.setAdapter(arrayAdapter);
        time_spinner_1.setAdapter(arrayAdapter2);
        time_spinner_2.setAdapter(arrayAdapter2);

        builder.setView(view);
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mainContext, "取消操作", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = new DatabaseHelper(mainContext,"Course.db",null,1)
                        .getWritableDatabase();
//                ContentValues values = new ContentValues();
//                // 开始组装第一条数据
//                values.put("id", 1);
//                values.put("name", "数据结构");
//                values.put("startTime", 1);
//                values.put("endTime", 2);
//                values.put("week",1);
//                db.insert("Course",null,values);
                String name ="";
                Cursor cursor = db.query("Course", null, null, null, null, null, null);
                while (cursor.moveToNext()){
                    name = cursor.getString(cursor.getColumnIndex("name"));
                }



                Toast.makeText(mainContext, name, Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        builder.show();
    }
}