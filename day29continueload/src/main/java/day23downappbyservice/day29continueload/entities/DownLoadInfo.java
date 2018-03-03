package day23downappbyservice.day29continueload.entities;

import java.io.Serializable;

/**
 * Created by hezijie on 2017/1/12.
 */
public class DownLoadInfo implements Serializable{

    private int id;
    private String url;
    private long progress;
    private String fileName;
    private long length;

    public DownLoadInfo(String url, long progress, String fileName, long length) {
        this.url = url;
        this.progress = progress;
        this.fileName = fileName;
        this.length = length;
    }

    @Override
    public String toString() {
        return "DownLoadInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", progress=" + progress +
                ", fileName='" + fileName + '\'' +
                ", length=" + length +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DownLoadInfo that = (DownLoadInfo) o;

        if (id != that.id) return false;
        if (progress != that.progress) return false;
        if (length != that.length) return false;
        if (!url.equals(that.url)) return false;
        return fileName.equals(that.fileName);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + url.hashCode();
        result = 31 * result + (int) (progress ^ (progress >>> 32));
        result = 31 * result + fileName.hashCode();
        result = 31 * result + (int) (length ^ (length >>> 32));
        return result;
    }

    public DownLoadInfo(int id, String url, long progress, String fileName, long length) {

        this.id = id;
        this.url = url;
        this.progress = progress;
        this.fileName = fileName;
        this.length = length;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
