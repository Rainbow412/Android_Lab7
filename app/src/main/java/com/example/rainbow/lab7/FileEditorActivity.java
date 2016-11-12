package com.example.rainbow.lab7;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileEditorActivity extends AppCompatActivity {

    private String FILENAME = "MyFile.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_editor);

        final EditText file_edit = (EditText)findViewById(R.id.file_edit);
        Button save = (Button)findViewById(R.id.save_button);
        Button load = (Button)findViewById(R.id.load_button);
        Button clear_file = (Button)findViewById(R.id.clear_file);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //按下save按钮后把输入框的内容保存到内部文件中
                try(FileOutputStream fileOutputStream = openFileOutput(FILENAME,MODE_PRIVATE)){
                    String text = file_edit.getText().toString();
                    fileOutputStream.write(text.getBytes());
                    fileOutputStream.close();
                    Toast.makeText(FileEditorActivity.this, "Saved successfully.",
                            Toast.LENGTH_LONG).show();
                    Log.d("TAG", "Successfully saved");

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("TAG", "Fail to save");
                }
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //按下load按钮后把内部文件内容显示在输入框中
                try(FileInputStream fileInputStream = openFileInput(FILENAME)){
                    byte[] contents = new byte[fileInputStream.available()];
                    fileInputStream.read(contents);
                    file_edit.setText(new String(contents)); //一定要new String！！
                    Toast.makeText(FileEditorActivity.this, "Load successfully.",
                            Toast.LENGTH_LONG).show();
                    Log.d("TAG", "Successfully load");
                } catch (FileNotFoundException e) {
                    //不存在该内部文件
                    Toast.makeText(FileEditorActivity.this, "Fail to load file.",
                            Toast.LENGTH_LONG).show();
                    Log.d("TAG", "Fail to load file");
                } catch (IOException e) {
                    Log.d("TAG", "Fail to read");
                }
            }
        });

        //按下clear按钮清空输入框内容
        clear_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file_edit.setText("");
            }
        });


    }
}
