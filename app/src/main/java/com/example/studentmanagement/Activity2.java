package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

public class Activity2 extends AppCompatActivity {
    Button btn; // Button back
    ListView view; // List danh sách tên học sinh
    TextView txt; // Ô kết quả

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listname);
        view = findViewById(R.id.listview);
        btn = findViewById(R.id.button2);
        txt = findViewById(R.id.textView);
        Intent i = getIntent();
        // Lấy arraylist từ file mainactivity truyền vô
        ArrayList<String> list = i.getStringArrayListExtra("list");
        if (!list.isEmpty()) {
            ArrayAdapter apdater = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
            view.setAdapter(apdater);
            txt.setText("Tìm thấy " + list.size() + " kết quả");
        } else {
            txt.setText("Không tìm thấy kết quả nào");
        }
        // Click vô button này thì về menu chính
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i1);
            }
        });
    }
}