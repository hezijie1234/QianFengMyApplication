package com.qianfeng.day10_listview_page.utils;

import android.os.AsyncTask;


import com.qianfeng.day10_listview_page.entity.Gift;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xray on 16/12/1.
 */

public class JSONUtils {

    public interface OnTextTaskListener{

        void onTextComplete(String text);
        void onTextFailed();
    }

    private OnTextTaskListener mListener;

    class TextTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream inputStream = conn.getInputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                StringBuilder sb = new StringBuilder();
                while((len = inputStream.read(buffer)) != -1){
                    sb.append(new String(buffer,0,len));
                }
                inputStream.close();
                return sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String text) {
            if(mListener == null){
                return;
            }
            if(text != null && text.length() > 0){
                mListener.onTextComplete(text);
            }else{
                mListener.onTextFailed();
            }
        }
    }

    public void readText(String url,OnTextTaskListener listener){
        this.mListener = listener;
        new TextTask().execute(url);
    }

    public List<Gift> getGiftList(String json){
        List<Gift> gifts = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray("list");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Gift gift = new Gift();
                gift.setId(obj.getLong("id"));
                gift.setIconurl(obj.getString("iconurl"));
                gift.setGname(obj.getString("gname"));
                gift.setGiftname(obj.getString("giftname"));
                gift.setAddtime(obj.getString("addtime"));
                gift.setNumber(obj.getInt("number"));
                gifts.add(gift);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return gifts;
    }

}
