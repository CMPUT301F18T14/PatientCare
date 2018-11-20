package com.example.mukha.picmymedcode.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.android.picmymedphotohandler.GalleryActivity;
import com.example.android.picmymedphotohandler.SlideshowActivity;
import com.example.mukha.picmymedcode.Controller.PicMyMedApplication;
import com.example.mukha.picmymedcode.Model.Patient;
import com.example.mukha.picmymedcode.Model.Problem;
import com.example.mukha.picmymedcode.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class RecordActivity extends AppCompatActivity{
    private static final String FILENAME = "file.sav";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManage;
    public ArrayList<Problem> problemArrayList;
    int position;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_activity_test_scroll);

        Patient user = (Patient)PicMyMedApplication.getLoggedInUser();
        problemArrayList = user.getProblemList();
        //loadFromFile();
        manageRecyclerview();
        position = getIntent().getIntExtra("key",0);
        String name = problemArrayList.get(position).getTitle();
        getSupportActionBar().setTitle(name);

        Button addRecordButton = findViewById(R.id.record_save_button);
        addRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordActivity.this,AddRecordActivity.class);
                intent.putExtra("key",position);

                startActivity(intent);
            }
        });

        ImageButton galleryButton = findViewById(R.id.gallery_button);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(RecordActivity.this,GalleryActivity.class);
                startActivity(galleryIntent);
            }
        });

        ImageButton slideshowButton = findViewById(R.id.slideshow_button);
        slideshowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(RecordActivity.this,SlideshowActivity.class);
                startActivity(galleryIntent);
            }
        });
    }

    public void manageRecyclerview(){
        mRecyclerView = findViewById(R.id.record_recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManage = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManage);
        mAdapter = new RecordAdapter(problemArrayList.get(position).getRecordList());
        mRecyclerView.setAdapter(mAdapter);
    }

    protected void onStart() {
        // TODO Auto-generated method stub

        super.onStart();
        Patient user = (Patient)PicMyMedApplication.getLoggedInUser();
        problemArrayList = user.getProblemList();


        //loadFromFile();
        mAdapter = new RecordAdapter(problemArrayList.get(position).getRecordList());
        mRecyclerView.setAdapter(mAdapter);

    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            Gson gson = new Gson();
            Type typeListProblem = new TypeToken<ArrayList<Problem>>() {
            }.getType();
            problemArrayList = gson.fromJson(reader, typeListProblem);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);

            Gson gson = new Gson();
            gson.toJson(problemArrayList,osw);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
