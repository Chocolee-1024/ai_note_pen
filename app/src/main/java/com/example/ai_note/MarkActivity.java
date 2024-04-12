package com.example.ai_note;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MarkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);

        TextView textView = findViewById(R.id.textView);
        String articleText = "在清晨的晨光中，我我走進了森林。森林裡的樹樹苍翠，小鳥鳥在樹上歡快地歌唱。我我感受到了大自然的美麗，心情愉悅。突然間，我我看到了一隻小小的松鼠在樹上跳躍。我我停下腳步，靜靜地觀察著它。松鼠似乎察覺到了我的存在，轉過頭來，用它的小小眼睛瞪著我我小鳥鳥。我們相視了一會兒，然後松鼠咯咯地笑了。我我也跟著笑了起來。這是一段美好的時刻小鳥鳥，我我會永遠記得。";
        textView.setTextIsSelectable(true);
        textView.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                menu.clear();
                menu.add(0, 0, 0, "GPT");
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                TextView textView = findViewById(R.id.textView);
                SpannableString spannableString = new SpannableString(textView.getText());
                int start = textView.getSelectionStart();
                int end = textView.getSelectionEnd();
                String selectedText = textView.getText().subSequence(start, end).toString();
                switch (item.getItemId()) {
                    case 0:
                        Toast.makeText(MarkActivity.this, selectedText, Toast.LENGTH_SHORT).show();
                        break;
                }
                mode.finish();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
            }
        });

        textView.setText(articleText);
    }
}