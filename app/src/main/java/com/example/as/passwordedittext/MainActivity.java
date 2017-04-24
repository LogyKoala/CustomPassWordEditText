package com.example.as.passwordedittext;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.pw_layout, null);

        Dialog dialog = new Dialog(this);
        dialog.setContentView(layout);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.dial_style);
        window.setGravity(Gravity.BOTTOM);
        int width =WindowManager.LayoutParams.MATCH_PARENT;
        int height =WindowManager.LayoutParams.WRAP_CONTENT;
        WindowManager.LayoutParams lp = window.getAttributes(); // 获取对话框当前的参数值
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();
        CustomerKeyboard customerKeyboard = (CustomerKeyboard) layout.findViewById(R.id.keyboard_View);
        final PassWordEditText pw_EditText = (PassWordEditText)layout. findViewById(R.id.pw_EditText);
        customerKeyboard.setOnCustomerKeyboardClickListener(new CustomerKeyboard.CustomerKeyboardClickListener() {
            @Override
            public void click(String pw) {
                pw_EditText.addPW(pw);

            }

            @Override
            public void remove() {
                pw_EditText.removePW();
            }
        });

        pw_EditText.setOnPWCommitListener(new PassWordEditText.PWCommitListener() {
            @Override
            public void commit_PW(String pw) {
                Toast.makeText(MainActivity.this, pw, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
