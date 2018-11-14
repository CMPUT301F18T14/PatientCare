package com.example.mukha.picmymedcode.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.Button;

import com.example.mukha.picmymedcode.Model.Problem;
import com.example.mukha.picmymedcode.Model.ProblemList;
import com.example.mukha.picmymedcode.R;

import java.util.Date;

public class ProblemActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManage;

    public Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.problem_activity);

        ProblemList problemList = new ProblemList();
        Problem problem = null;
        try {
            problem = new Problem(date,"Problem","Description");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        problemList.addProblem(problem);
        Problem problem1 = null;
        try {
            problem1 = new Problem(date,"Problem1","Description1");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        problemList.addProblem(problem1);

        mRecyclerView = (RecyclerView) findViewById(R.id.problem_recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManage = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManage);
        mAdapter = new ProblemAdapter(problemList.problemList);
        mRecyclerView.setAdapter(mAdapter);


        Button addproblembutton = (Button) findViewById(R.id.add_problem_button);
        addproblembutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent problemIntent = new Intent(ProblemActivity.this,AddProblemActivity.class);
                startActivity(problemIntent);
            }
        });



    }

}
