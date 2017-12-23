package com.atminfotech.barcodestorenew;

class AppData {

    private String SalesBillNo,LRNumber,Date,TransportName,TotalMeters,TotalAmount;
    private String imageId;

    private static final AppData ourInstance = new AppData();

    static AppData getInstance() {
        return ourInstance;
    }

    private AppData() {
        super();
    }

    public AppData(String SalesBillNo,String LRNumber,String Date,String TransportName,String TotalMeters,String TotalAmount,String imageId){
        super();
        this.SalesBillNo = SalesBillNo;
        this.LRNumber = LRNumber;
        this.Date = Date;
        this.TransportName = TransportName;
        this.TotalMeters = TotalMeters;
        this.TotalAmount = TotalAmount;
        this.imageId =imageId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setSalesBillNo(String SalesBillNo){
        this.SalesBillNo = SalesBillNo;
    }

    public String getSalesBillNo(){
        return SalesBillNo;
    }

    public void setLRNumber(String LRNumber){
        this.LRNumber = LRNumber;
    }

    public String getLRNumber(){
        return LRNumber;
    }

    public void setDate(String Date){
        this.Date = Date;
    }

    public String getDate(){
        return Date;
    }

    public void setTransportName(String TransportName){
        this.TransportName = TransportName;
    }

    public String getTransportName(){
        return TransportName;
    }

    public void setTotalMeters(String TotalMeters){
        this.TotalMeters = TotalMeters;
    }

    public String getTotalMeters(){
        return TotalMeters;
    }

    public void setTotalAmount(String TotalAmount){
        this.TotalAmount = TotalAmount;
    }

    public String getTotalAmount(){
        return TotalAmount;
    }

}
