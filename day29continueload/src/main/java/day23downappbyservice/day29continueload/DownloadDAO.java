package day23downappbyservice.day29continueload;

import day23downappbyservice.day29continueload.entities.DownLoadInfo;

/**
 * Created by hezijie on 2017/1/12.
 */
public interface DownloadDAO {

    void add(DownLoadInfo info);

    void delete(String url);

    DownLoadInfo query(String url);

    void updateProgress(String url,long progress);

    void updateLength(String url,long length);

    void close();
}
