package com.wosloveslife.drawabletrain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FrameLayout flContainer = (FrameLayout) findViewById(R.id.fl_container);

        final EraseAnimDrawable drawable = new EraseAnimDrawable(this);
        drawable.setDuration(700);
        drawable.setBounds(0, 0, 500, 500);
        flContainer.setForeground(drawable);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawable.start();
            }
        });

        findViewById(R.id.resetBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawable.reset();
            }
        });

    }
}
