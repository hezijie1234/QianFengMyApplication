package day23downappbyservice.myapplication.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hezijie on 2017/1/3.
 */
public class BitmapZip {

    public static Bitmap bitmapZip(byte[] bytes,int destWidth,int destHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        int height = options.outHeight;
        int width = options.outWidth;
        int sampleSize = 1;
        while((height/sampleSize >= destHeight) || (width / sampleSize >= destWidth)){
            sampleSize *= 2;
        }
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inSampleSize = sampleSize;
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        return bitmap;
    }

    public static byte[] stream2ByteArray(InputStream input){

        try(
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ){
            int len = 0;
            byte[] bytes = new byte[1024];
            while((len = input.read(bytes))!=-1){
                baos.write(bytes,0,len);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
