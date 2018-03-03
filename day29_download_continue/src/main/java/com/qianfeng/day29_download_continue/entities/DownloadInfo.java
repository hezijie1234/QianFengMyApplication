package com.qianfeng.day29_download_continue.entities;

import java.io.Serializable;

/**
 * 下载信息实体类
 * Created by xray on 17/1/12.
 */

public class DownloadInfo implements Serializable{

    private int id;
    private String fileName;
    private String url;
    private long progress;
    private long length;

    public DownloadInfo() {
    }

    public DownloadInfo(int id, String fileName, String url, long progress, long length) {
        this.id = id;
        this.fileName = fileName;
        this.url = url;
        this.progress = progress;
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "DownloadInfo{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", url='" + url + '\'' +
                ", progress=" + progress +
                ", length=" + length +
                '}';
    }
}
