package com.atminfotech.barcodestorenew;

public class Beanclass {
    private String image1;
    private String title1;
    private String date1;

    public Beanclass(String image, String title, String date) {
        this.image1 = image;
        this.title1 = title;
        this.date1 = date;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }
}
