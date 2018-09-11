package com.momo.engks.dalil;

public class Model_List_Item {

    private String title , image, desc;

    public Model_List_Item(String title, String desc , String image) {

        this.title = title;
        this.image = image;
        this.desc = desc;
    }

    public Model_List_Item(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {

        this.desc = desc;
    }
}
