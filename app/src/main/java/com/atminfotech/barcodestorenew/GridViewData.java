package com.atminfotech.barcodestorenew;

/**
 * Created by ABC on 08/12/2017.
 */

class GridViewData {
    String pk;
    String design;
    String image;
    String name;
    String rate;
    String CompanyName;
    String Class1;
    String position;
    String width;
    String type;
    String activity;

    public GridViewData( String pk,String image, String name, String rate,String CompanyName,String Class1,String position) {
        this.pk = pk;
        this.image = image;
        this.name = name;
        this.rate = rate;
        this.CompanyName = CompanyName;
        this.Class1 = Class1;
        this.position =position;
    }

    public String getClass1() {
        return Class1;
    }

    public void setClass1(String class1) {
        Class1 = class1;
    }

    public GridViewData() {

    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getColor() {
        return CompanyName;
    }

    public void setColor(String color) {
        this.CompanyName = color;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
