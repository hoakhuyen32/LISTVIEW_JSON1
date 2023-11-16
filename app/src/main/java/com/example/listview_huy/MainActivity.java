package com.example.listview_huy;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainActivity extends AppCompatActivity {
TextView textView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        new ReadJSONObject().execute("https://raw.githubusercontent.com/hoakhuyen32/JSONname/main/monhoc.json");
    }

    private void Anhxa() {
        textView = (TextView) findViewById(R.id.textView);
    }

    private class ReadJSONObject extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line ="";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                String monhoc = object.getString("Monhoc");
                String hoten = object.getString("Hoten");
                String lophoc = object.getString("Lophoc");
                String khoahoc = object.getString("Khoahoc");
                String masv = object.getString("Masinhvien");
                // Sử dụng một TextView hoặc phần tử giao diện người dùng khác thay vì Toast để hiển thị một cách tốt hơn
                textView.setText("Môn học: "+ monhoc+"\n" + "Họ và tên: " +hoten+ "\n"+ "Lớp học: "+lophoc + "\n"+"Khóa học: " + khoahoc + "\n" +"Mã sinh viên: "+ masv);
            } catch (JSONException e) {
                // Ghi log lỗi hoặc hiển thị một thông báo thân thiện với người dùng
                Log.e("Lỗi phân tích JSON", "Lỗi phân tích JSON: " + e.getMessage());
            }
        }
    }
}