package day23downappbyservice.day29continueload;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import day23downappbyservice.day29continueload.entities.DownLoadInfo;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */

public class ApplicationTest extends ApplicationTestCase<Application> {

    public static final String TAG="111";
    public ApplicationTest() {
        super(Application.class);
    }

    public void testDownDB(){
        DownloadDAOImpl dao = new DownloadDAOImpl(getContext());
        DownLoadInfo info = new DownLoadInfo(0,"url",0,"file1",0);
        dao.add(info);
        DownLoadInfo info1 = dao.query("url");
        Log.e(TAG, "testDownDB: "+info1.toString() );
        dao.delete("url");
        DownLoadInfo info2 = dao.query("url");
        Log.e(TAG, "testDownDB: "+info2 );
    }
}