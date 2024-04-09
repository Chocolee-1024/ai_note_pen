package com.example.ai_note;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private MyCanvasView myCanvasView;
    private Button clear_button;

    private Button go_mark;
    private Button strokeWidth;
    private TextView yellow_button;
    private TextView red_button;
    private TextView blue_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myCanvasView = findViewById(R.id.myCanvasView);

        yellow_button = findViewById(R.id.yellow_pen);
        red_button = findViewById(R.id.red_pen);
        blue_button = findViewById(R.id.blue_pen);
        clear_button = findViewById(R.id.clear_button);
        go_mark = findViewById(R.id.go_mark);
        strokeWidth = findViewById(R.id.Stroke_width_button);
        go_mark.setOnClickListener(v -> {
            Intent intent = new Intent(this, MarkActivity.class);
            startActivity(intent);
        });
//        strokeWidth.setOnClickListener(v -> {
//            myCanvasView.setStrokeWidth();
//        });
//        clear_button.setOnClickListener(v -> {
//            myCanvasView.clear();
//        });
//        yellow_button.setOnClickListener(v -> {
//            myCanvasView.setColor(0xFFFFFF99);
//        });
//
//        red_button.setOnClickListener(v -> {
//            myCanvasView.setColor(0xFFFF4081);
//        });
//
//        blue_button.setOnClickListener(v -> {
//            myCanvasView.setColor(0xFF7DF9FF);
//        });

    }
}