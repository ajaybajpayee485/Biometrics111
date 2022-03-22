package com.example.biometrics;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class DiaryCreateEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_create_entry);

        Toolbar toolbar=(Toolbar) findViewById(R.id.tb_createDiary);
        toolbar.setNavigationOnClickListener(v->exit());

        Button button =(Button) findViewById(R.id.diaryInputEnter);
        button.setOnClickListener(view -> {
            AlertDialog dialog=createEntryDialog();
        });
    }

    private AlertDialog createEntryDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(context:DiaryCreateEntry.this);
        builder.setMessage("Save Entry");

        builder.setPositiveButton(text:"Yes",(dialog,which)->{
            TextInputEditText inputTitle=findViewById(R.id.diaryInputTitle);
            String title=inputTitle.getText().toString();

            TextInputEditText inputText = findViewById(R.id.diaryInputText);
            String text=inputText.getText().toString();
            Entry entry=Entry.createDiaryEntry(DiaryCreateEntry.this,title,text);
            DiaryEntryObHelper dbHelper= new DiaryEntryObHelper(DiaryCreateEntry.this);
            dbHelper.addEntry(entry);
            dbHelper.close();
            exit();



        });

        builder.setNegativeButton("Cancel",((dialog,which)->{

        });
       AlertDialog dialog=builder.create();
       return dialog;
    }
    private void exit(){
        Intent intent =new Intent(DiaryCreateEntry.this,DiaryActivity.class);
        startActivity(intent);
        DiaryCreateEntry.this.finish();
    }
}