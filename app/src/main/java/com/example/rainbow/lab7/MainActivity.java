package com.example.rainbow.lab7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText pd = (EditText)findViewById(R.id.pd);
        final EditText cf_pd = (EditText)findViewById(R.id.confirm_pd);
        Button ok = (Button)findViewById(R.id.ok_button);
        Button clear_login = (Button)findViewById(R.id.clear_login);

        final SharedPreferences sharedPref = getSharedPreferences("MY_KEY", MODE_PRIVATE);
        final String key = sharedPref.getString("KEY_SCORE", "n");
        Log.d("key", key);
        //首次打开该应用，没有设置过密码
        if(key.equals("n")){
            pd.setHint("New Password");
            cf_pd.setHint("Confirm Password");
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //password为空
                    if(TextUtils.isEmpty(pd.getText().toString())){
                        Toast.makeText(MainActivity.this, "Password cannot be empty.",
                                Toast.LENGTH_LONG).show();
                    }
                    //再次输入的password与第一次输入的不符合
                    else if(!cf_pd.getText().toString().equals(pd.getText().toString())){
                        Toast.makeText(MainActivity.this, "Password Mismatch.",
                                Toast.LENGTH_LONG).show();
                    }
                    //再次输入的password与第一次输入的一致
                    // 记录到SharedPreferences中，并实现跳转
                    else{
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("KEY_SCORE", pd.getText().toString());
                        editor.commit();

                        Intent intent = new Intent(MainActivity.this, FileEditorActivity.class);
                        startActivity(intent);
                    }
                }
            });
            //按下clear按钮后清空2个输入框
            clear_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pd.setText("");
                    cf_pd.setText("");
                }
            });
        }
        else{
            cf_pd.setVisibility(GONE); //不可见并且不占用空间
            pd.setHint("Password");
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //输入的密码与记录的密码一致，实现跳转
                    if(pd.getText().toString().equals(key)){
                        Intent intent = new Intent(MainActivity.this, FileEditorActivity.class);
                        startActivity(intent);
                    }
                    //输入的密码与记录的密码不一致
                    else{
                        Toast.makeText(MainActivity.this, "Invalid Password.",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
            //按下clear按钮后清空输入框
            clear_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pd.setText("");
                }
            });
        }
    }
}
