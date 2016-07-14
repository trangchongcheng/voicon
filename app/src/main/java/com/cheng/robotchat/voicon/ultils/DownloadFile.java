package com.cheng.robotchat.voicon.ultils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by chientruong on 5/30/16.
 */
public class DownloadFile {

    public static void downloadImagesToSdCard(String downloadUrl, String imageName) {
        FileOutputStream fos;
        InputStream inputStream = null;

        try {
            URL url = new URL(downloadUrl);
            /* making a directory in sdcard */
            String sdCard = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(sdCard, "VoiconDownload");

            /* if specified not exist create new */
            if (!myDir.exists()) {
                myDir.mkdir();
                Log.v("", "inside mkdir");
            }

            /* checks the file and if it already exist delete */
            String fname = imageName;
            File file = new File(myDir, fname);
            Log.d("file===========path", "" + file);
            if (file.exists())
                file.delete();

            /* Open a connection */
            URLConnection ucon = url.openConnection();

            HttpURLConnection httpConn = (HttpURLConnection) ucon;
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            inputStream = httpConn.getInputStream();

            /*
             * if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
             * inputStream = httpConn.getInputStream(); }
             */

            fos = new FileOutputStream(file);
            // int totalSize = httpConn.getContentLength();
            // int downloadedSize = 0;
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fos.write(buffer, 0, bufferLength);
                // downloadedSize += bufferLength;
                // Log.i("Progress:", "downloadedSize:" + downloadedSize +
                // "totalSize:" + totalSize);
            }
            inputStream.close();
            fos.close();
            Log.d("test", "Image Saved in sdcard..");
        } catch (IOException io) {
            inputStream = null;
            fos = null;
            io.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }
}
