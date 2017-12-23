package com.atminfotech.barcodestorenew;

public class RowItem {
    private String imageId;
    private String title;
    private String desc;
    private String Rate;
    private String Amount;
    private int qty;

    public RowItem(String imageId, String title, String desc, int qty, String Rate, String Amount) {
        this.imageId = imageId;
        this.title = title;
        this.desc = desc;
        this.qty = qty;
        this.Rate = Rate;
        this.Amount = Amount;
    }

    public String getImageId() {
        return imageId;
    }
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getQty() {
        return qty;
    }
    public void setQty(int qty) {
        this.qty = qty;
    }
    public String getRate() {
        return Rate;
    }
    public void setRate(String Rate) {
        this.Rate = Rate;
    }
    public String getAmount() {
        return Amount;
    }
    public void setAmount(String Amount) {
        this.Amount = Amount;
    }
    @Override
    public String toString() {
        return title + "\n" + desc;
    }
}