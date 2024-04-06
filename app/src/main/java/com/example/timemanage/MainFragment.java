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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
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
        Spinner week_select = root.findViewById(R.id.spinner);
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

        week_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                int week = switch (selectedItem) {
                    case "周一" -> 1;
                    case "周二" -> 2;
                    case "周三" -> 3;
                    case "周四" -> 4;
                    case "周五" -> 5;
                    case "周六" -> 6;
                    case "周日" -> 7;
                    default -> 1;
                };
                Fragment fragment = new ShowCourseFragment(week);
                FragmentManager fragmentManager = getChildFragmentManager();;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_layout_2,fragment);
                fragmentTransaction.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        EditText editText = view.findViewById(R.id.editText);
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
                String t1 = time_spinner_1.getSelectedItem().toString();
                String t2 = time_spinner_2.getSelectedItem().toString();
                if((editText.getText() == null) || (Integer.parseInt(t1) >= Integer.parseInt(t2)) ){
                    Toast.makeText(mainContext, "添加失败，格式错误", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }else{
                    Cursor cursor = db.query("Course", null, null, null, null, null, null);
                    int count = cursor.getCount();
                    int week = switch (week_spinner.getSelectedItem().toString()) {
                        case "周一" -> 1;
                        case "周二" -> 2;
                        case "周三" -> 3;
                        case "周四" -> 4;
                        case "周五" -> 5;
                        case "周六" -> 6;
                        case "周日" -> 7;
                        default -> 1;
                    };
                    ContentValues values = new ContentValues();
                    values.put("id", count+1);
                    values.put("name", String.valueOf(editText.getText()));
                    values.put("startTime", Integer.parseInt(t1));
                    values.put("endTime", Integer.parseInt(t2));
                    values.put("week",week);
                    db.insert("Course",null,values);
//                String name ="";
//                //Cursor cursor = db.query("Course", null, null, null, null, null, null);
//                while (cursor.moveToNext()){
//                    name = cursor.getString(cursor.getColumnIndex("name"));
//                }
                    Toast.makeText(mainContext, String.valueOf(count), Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            }
        });
        builder.show();
    }
}