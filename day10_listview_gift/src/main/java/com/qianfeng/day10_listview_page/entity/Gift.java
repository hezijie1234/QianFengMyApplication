package com.qianfeng.day10_listview_page.entity;

/**
 * Created by xray on 16/12/1.
 */

public class Gift {

    private long id;
    private String iconurl;
    private String giftname;
    private int number;
    private String gname;
    private String addtime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public String getGiftname() {
        return giftname;
    }

    public void setGiftname(String giftname) {
        this.giftname = giftname;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "Gift{" +
                "id=" + id +
                ", iconurl='" + iconurl + '\'' +
                ", giftname='" + giftname + '\'' +
                ", number=" + number +
                ", gname='" + gname + '\'' +
                ", addtime='" + addtime + '\'' +
                '}';
    }
}
