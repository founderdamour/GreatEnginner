package com.yzz.great.util.exception;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yzz.great.App;
import com.yzz.great.R;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ShowExceptionActivity extends AppCompatActivity {

    private TextView showExceptionTV;

    public static void showException(Throwable throwable) {
        App applicationContext = App.getInstance();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        throwable.printStackTrace(new PrintStream(byteArrayOutputStream));
        String msg = new String(byteArrayOutputStream.toByteArray());

        Intent intent = new Intent(applicationContext, ShowExceptionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("msg", msg);
        applicationContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_exception);
        showExceptionTV = findViewById(R.id.showExceptionTV);
        handlerIntent(getIntent());
    }

    private void handlerIntent(Intent intent) {
        String msg = intent.getStringExtra("msg");
        if (msg != null)
            showExceptionTV.append(msg);
    }

}
