package com.example.ayabeltran.firstproject;

/**
 * Created by Lorenzo11 on 09/03/2018.
 */

public class ImgRepo {

    private int id;
    private String imgname;
    private String desc;
    private String imgstring;
    private String vidstring;


    public ImgRepo(String imgname, String desc, String imgstring, String vidstring) {
        this.imgname = imgname;
        this.desc = desc;
        this.imgstring = imgstring;
        this.vidstring = vidstring;
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

    public String getVidstring() {return vidstring;}
}