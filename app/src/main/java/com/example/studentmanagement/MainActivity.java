package com.example.studentmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    EditText txt1; // text sur name
    EditText txt2; // text mid name
    EditText txt3; // text lastname
    EditText txt4; // text day
    EditText txt5; // text month
    EditText txt6; // text year
    EditText txt7; // text class
    Button btn; // Button search
    Button btn1; // Button birthday
    String autocomplextext = "";
    String[] items = {"Lý", "Hóa", "Sinh", "Toán", "Tin", "CLC"}; // Danh sách các hệ chuyên
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapteritem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Xác định ô chứa tên hệ chuyên
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        // Danh sách các hệ chuyên sẽ được hiển thị dưới dạng list
        adapteritem = new ArrayAdapter<>(this, R.layout.list, items);

        autoCompleteTextView.setAdapter(adapteritem);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Khi người dùng chọn 1 hệ chuyên nào đó thì lấy tên hệ đấy
                autocomplextext = adapterView.getItemAtPosition(i).toString();
            }
        });
        // Xác định các nút, edittext
        btn1 = findViewById(R.id.button1);
        btn = findViewById(R.id.button);
        txt1 = findViewById(R.id.editext1);
        txt2 = findViewById(R.id.editext2);
        txt3 = findViewById(R.id.editext3);
        txt4 = findViewById(R.id.editext4);
        txt5 = findViewById(R.id.editext5);
        txt6 = findViewById(R.id.editext6);
        txt7 = findViewById(R.id.editext7);
        // Khi người dùng nhất search thì truyên vô dữ liệu vô class Task với các tham số từ edittext
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sur = txt1.getText().toString();
                String day = txt2.getText().toString();
                String mid = txt3.getText().toString();
                String month = txt4.getText().toString();
                String last = txt5.getText().toString();
                String year = txt6.getText().toString();
                String clas = txt7.getText().toString();
                Data1 data = new Data1(sur, mid, last, day, month, year, clas, autocomplextext);
                new Task(MainActivity.this).execute(data);
            }
        });
        // Khi ngườ dùng bấm vô Birthday thì truyền vô file task với tham số là ngày hôm nay
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String day;
                String month;
                Calendar calendar = Calendar.getInstance();
                TimeZone timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
                calendar.setTimeZone(timeZone);
                int day1 = calendar.get(Calendar.DAY_OF_MONTH);
                int month1 = calendar.get(Calendar.MONTH) + 1; // Month starts from 0
                if (day1 < 10) {
                    day = "0" + String.valueOf(day1);
                } else {
                    day = String.valueOf(day1);
                }
                if (month1 < 10) {
                    month = "0" + String.valueOf(month1);
                } else {
                    month = String.valueOf(month1);
                }
                Data1 data = new Data1("", "", "", day, month, "", "", "");
                new Task(MainActivity.this).execute(data);
            }
        });
    }
    private class Task extends AsyncTask<Data1, Void, LinkedList<String>> {
        ProgressDialog progressDialog;
        private final Context context;
        public Task(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Getting Result");
            progressDialog.setMessage("Working...");
            progressDialog.show();
        }

        @Override
        protected LinkedList<String> doInBackground(Data1... data1s) {
            // Lấy data đầu vì length của cái này chỉ là 1
            Data1 data = data1s[0];
            // Lấy kết quả sau khi class Data xử lí xong dữ liệu
            return data.getResult();
        }

        @Override
        protected void onPostExecute(LinkedList<String> strings) {
            super.onPostExecute(strings);
            progressDialog.dismiss();
            // Truyền vô activity2 arraylist
            Intent i = new Intent(getApplicationContext(), Activity2.class);
            ArrayList<String> list = new ArrayList<>(strings);
            i.putExtra("list", list);
            startActivity(i);
        }
    }
}
