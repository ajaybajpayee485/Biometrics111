package com.example.biometrics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class DiaryActivity extends AppCompatActivity {

    List<Entry> entries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        Toolbar diaryToolbar=findViewById(R.id.diary_toolbar);
        RecyclerView recyclerView=findViewById(R.id.recycleView_entries);
        CalendarView calendarView=findViewById(R.id.calendarView);
        calendarView.setVisibility(calendarView.GONE);

        DiaryEntryObHelper dbHelper=new DiaryEntryObHelper(this);
        entries=dbHelper.getAllEntries();
        DiaryAdapter adapter=new DiaryAdapter(entries);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager=new linearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        TabLayout tabLayout=findViewById(R.id.tab_entries);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position =tab.getPosition();
                switch(position){
                    case 0:
                    calendarView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    break;
                    case 1:
                        recyclerView.setVisibility(View.GONE);
                        calendarView.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        TextView testText=findViewById(R.id.datetext);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int Year, int month, int YearofMonth) {
                month=month+1;
                testText.setText(""+YearofMonth+"."+month+"."+Year);

            }
        });
        @Override
           public boolean onCreateOptionsMenu(Menu menu ){
            getMenuInflater().inflate(R..menu.menu,menu);
            return true;

        }
        @Override
                public boolean onOptionItemSelected(MenuItem item) {
            switch (item.getitemId()){
                case R.id.menuAction_newEntry:
                    Intent intent =new Intent(DiaryActivity.this,DiaryCreateEntry.class);
                    startActivity(intent);
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        }

    }
}