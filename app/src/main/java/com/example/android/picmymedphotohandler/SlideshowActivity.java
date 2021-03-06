/*
 * SlideshowActivity
 *
 * 1.2
 *
 * November 16, 2018
 *
 * Copyright 2018 CMPUT301F18T14. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.picmymedphotohandler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;

import com.example.picmymedcode.Controller.PicMyMedApplication;
import com.example.picmymedcode.Model.Patient;
import com.example.picmymedcode.Model.Photo;
import com.example.picmymedcode.Model.Record;
import com.example.picmymedcode.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * SlideshowActivity performs actions on the database and the SlideShowAdapter settings
 * to run a seamless slide show of the photos stored in the device.
 *
 * @author  Md Touhidul (Apu) Islam
 * @version 1.2, 02/12/18
 * @since   1.1
 *
 * Ideas were combined from
 * 1. https://www.youtube.com/watch?v=0U61HP7ZipE
 * 2. https://www.youtube.com/watch?v=DenAOzzxiFY
 * 3. https://github.com/ongakuer/CircleIndicator
 * 4. https://dubedout.eu/2016/09/13/viewpager-basics/
 * Used in: SlideshowActivity.java, SlideShowAdapter.java
 */

public class SlideshowActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private ViewPager viewPager;

    private SlideShowAdapter adapter;

    private CircleIndicator indicator;

    private Handler handler;

    private Runnable runnable;

    private Timer timer;

    private ArrayList<GalleryCells> galleryCells;

    private final int DELAY_TIME = 4000;        // The delay time for the handler

    private final int PERIOD_TIME = 4000;       // The period time for the handler

    private int problemIndex;

    /**
     * Method loads activity state
     *
     * @param savedInstanceState    Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);

        problemIndex = getIntent().getIntExtra("problemIndex", 0);
        Patient user = (Patient)PicMyMedApplication.getLoggedInUser();

        // Load the image files
        //loadingImageFiles = new LoadingImageFiles(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES));

//        toolbar = (Toolbar) findViewById(R.id.toolbar_id);
//        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewPager_id);

        indicator = (CircleIndicator) findViewById(R.id.circleIndicator_id);

        galleryCells = preparedDataFromRecordList(user.getProblemList().get(problemIndex).getRecordList());

        adapter = new SlideShowAdapter(galleryCells,this);

        viewPager.setAdapter(adapter);

        indicator.setViewPager(viewPager);

        /**
         * Method initializes the handler
         */
        handler = new Handler();

        /**
         * Method initializes the runnable task the handler will handle
         */
        runnable = new Runnable() {
            @Override
            public void run() {
                // Getting the current item the pager is on
                int currentItem = viewPager.getCurrentItem();

                if (currentItem == adapter.getCount() - 1){
                    /* If the pager is on the last item, bring to the first item */
                    currentItem = 0;
                    viewPager.setCurrentItem(currentItem, true);
                } else{
                    /* If the pager is not on the last item, take it to the next item*/
                    currentItem ++;
                    viewPager.setCurrentItem(currentItem, true);
                }

            }
        };

        /**
         * Method initializes the timer
         */
        timer = new Timer();

        /**
         * Method schedules the task
         */
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, DELAY_TIME, PERIOD_TIME);
    }

    /**
     * This method performs operation on the data
     * to make it viewable under the defined adapter setting.
     *
     * @return      ArrayList of GalleryCells containing modified data for adapter compatibility
     */
    private ArrayList<GalleryCells> preparedDataFromRecordList(ArrayList<Record> records) {
        ArrayList<GalleryCells> galleryCellsArrayList = new ArrayList<>();
        for (Record record : records) {
            galleryCellsArrayList.addAll(preparedDataFromRecord(record));
        }
        return galleryCellsArrayList;
    }

    /**
     * This method performs operation on the data
     * to make it viewable under the defined adapter setting.
     *
     * @return      ArrayList of GalleryCells containing modified data for adapter compatibility
     */
    private ArrayList<GalleryCells> preparedDataFromRecord(Record record) {
        ArrayList<GalleryCells> galleryCellsArrayList = new ArrayList<>();
        galleryCellsArrayList = preparedData(record.getPhotoList());
        return galleryCellsArrayList;
    }

    /**
     * This method performs operation on the data
     * to make it viewable under the defined adapter setting.
     *
     * @return      ArrayList of GalleryCells containing modified data for adapter compatibility
     */
    private ArrayList<GalleryCells> preparedData(ArrayList<Photo> photos) {
        ArrayList<GalleryCells> galleryCellsArrayList = new ArrayList<>();
        byte[] decodedString;
        Bitmap decodedByte;

        for (Photo photo : photos) {
            GalleryCells galleryCells = new GalleryCells();
            decodedString = Base64.decode(photo.getBase64EncodedString(), Base64.DEFAULT);
            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            galleryCells.setBitmap(decodedByte);
            galleryCellsArrayList.add(galleryCells);
        }

        return galleryCellsArrayList;
    }
}
