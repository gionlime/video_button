package com.sd.lib.blingbutton.demo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v)
    {
        // 切换状态
        v.setSelected(!v.isSelected());
    }
}
