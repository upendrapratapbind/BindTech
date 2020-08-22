package com.elearning.bindtech.utils;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;

import com.elearning.bindtech.DbHandler;
import com.elearning.bindtech.models.OnSliderItem;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DownloadOnBoardingScreensService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    OnBoardingScreenData onBoardingScreenData;

    public DownloadOnBoardingScreensService() {
        super("DownloadOnboardingScreensService");
    }


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadOnBoardingScreensService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String data = intent.getStringExtra("data");
        if (data != null && !data.isEmpty()) {
            onBoardingScreenData = new Gson().fromJson(data, OnBoardingScreenData.class);
            List<OnSliderItem> onBoardItemList = onBoardingScreenData.getImageData();
            if (onBoardItemList != null && onBoardItemList.size() != 0) {
                int size = onBoardItemList.size();
                OnBoardingScreenSyncDAO.getInstance().insertLastUpdatedOn(onBoardingScreenData.getLastUpdatedOn(), DbHandler.getWritableDb(this));
                OnSliderScreensDAO.getInstance().deleteTableData(DbHandler.getWritableDb(this));
                deleteDirectoryContent();
                for (int j = 0; j < size; j++) {
                    OnSliderItem onBoardItem = onBoardItemList.get(j);
                    saveImage(onBoardItem);
                }
            }
        }


    }

    private void deleteDirectoryContent() {
        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/ROOTS");
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    new File(dir, children[i]).delete();
                }
            }
            createNoMediaFile();

        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
    }

    private void createNoMediaFile() {
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/ROOTS");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, ".nomedia");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveImage(OnSliderItem onBoardItem) {
        String filepath = "";
        try {

            URL url = new URL(onBoardItem.getImagePath());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.connect();

            // urlConnection.connect();
            //File SDCardRoot = Environment.getExternalStorageDirectory().getAbsoluteFile()+"ROOTS";
            File SDCardRoot = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/ROOTS");
            if (!SDCardRoot.exists()) {
                SDCardRoot.mkdirs();
            }


            String filename = "screen_" + Utils.getDateTime() + ".png";
            Log.i("Local filename:", "" + filename + ".png");

            File file = new File(SDCardRoot, filename);
            if (file.createNewFile()) {
                file.createNewFile();
            }
            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = urlConnection.getInputStream();
            int totalSize = urlConnection.getContentLength();
            int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                Log.i("Progress:", "downloadedSize:" + downloadedSize + "totalSize:" + totalSize);
            }
            fileOutput.close();
            if (downloadedSize == totalSize) {
                filepath = file.getAbsolutePath();
                onBoardItem.setImagePath(filepath);
                OnSliderScreensDAO.getInstance().insert(onBoardItem, DbHandler.getWritableDb(this));

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            filepath = null;
            e.printStackTrace();
        }
        Log.i("filepath:", " " + filepath);
        //return filepath;

    }
}
