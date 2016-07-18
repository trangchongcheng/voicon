package com.cheng.robotchat.voiconchat.ultils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by chientruong on 6/8/16.
 */
public class CoverBitmapToByte {

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
        return stream.toByteArray();
    }
    public static Bitmap coverImageViewToBimap(ImageView imageView){
        imageView.buildDrawingCache();
        return imageView.getDrawingCache();
    }

}
