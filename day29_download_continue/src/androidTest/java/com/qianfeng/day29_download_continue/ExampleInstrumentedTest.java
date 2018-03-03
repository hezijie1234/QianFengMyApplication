package com.qianfeng.day29_download_continue;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.qianfeng.day29_download_continue.dao.DownloadDAO;
import com.qianfeng.day29_download_continue.dao.DownloadDAOImpl;
import com.qianfeng.day29_download_continue.entities.DownloadInfo;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "xxx";

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.qianfeng.day29_download_continue", appContext.getPackageName());
    }


    @Test
    public void testAdd(){
        int n = 1 + 1;
        assertEquals(2,n);
    }

    @Test
    public void testDownloadDAO(){
        DownloadDAO dao = new DownloadDAOImpl(InstrumentationRegistry.getTargetContext());
        DownloadInfo info = new DownloadInfo(0,"test","test_url",0,0);
        dao.save(info);
        DownloadInfo result = dao.query("test_url");
        Log.i(TAG, "save--->query: "+result);
        dao.updateLength("test_url",8888);
        dao.updateProgress("test_url",5555);
        result = dao.query("test_url");
        Log.i(TAG, "update--->query: "+result);
        dao.delete("test_url");
        result = dao.query("test_url");
        Log.i(TAG, "delete--->query: "+result);
        dao.close();
    }
}
