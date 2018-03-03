package com.qianfeng.day29_download_continue.dao;

import com.qianfeng.day29_download_continue.entities.DownloadInfo;

/**
 * 下载信息的访问接口
 * Created by xray on 17/1/12.
 */

public interface DownloadDAO {

    /**
     * 保存下载信息
     * @param downloadInfo
     */
    void save(DownloadInfo downloadInfo);

    /**
     * 通过URL查询下载信息
     * @param url
     * @return
     */
    DownloadInfo query(String url);

    /**
     * 更新下载进度
     * @param url
     * @param progress
     */
    void updateProgress(String url,long progress);

    /**
     * 更新文件长度
     * @param url
     * @param length
     */
    void updateLength(String url,long length);

    /**
     * 删除下载信息
     * @param url
     */
    void delete(String url);

    /**
     * 关闭数据库
     */
    void close();
}
