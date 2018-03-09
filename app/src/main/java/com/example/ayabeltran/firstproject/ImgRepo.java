package com.example.ayabeltran.firstproject;

/**
 * Created by Lorenzo11 on 09/03/2018.
 */

public class ImgRepo {

    private int id;
    private String imgname,
            desc,
            imgstring;


    public ImgRepo(String imgname, String desc, String imgstring) {
        this.imgname = imgname;
        this.desc = desc;
        this.imgstring = imgstring;
    }

    public int getId() {
        return id;
    }

    public String getImgname() {
        return imgname;
    }

    public String getDesc() {
        return desc;
    }

    public String getImgstring() {
        return imgstring;
    }
}