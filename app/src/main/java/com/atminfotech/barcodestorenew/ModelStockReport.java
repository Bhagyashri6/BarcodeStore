package com.atminfotech.barcodestorenew;

public class ModelStockReport {

    private String purDate;
    private String brand;
    private String name;
    private String qty;
    private String values;

    public ModelStockReport(String Date, String brand, String name, String qty, String values) {

        this.purDate = Date;
        this.brand = brand;
        this.qty = qty;
        this.name = name;
        if(values.equalsIgnoreCase("anytype{}") || values.equalsIgnoreCase(""))
            this.values="-";
                    else if(Integer.valueOf(values)<=0){this.values="Expired";}
        else this.values = values;

    }

    public String getPurDate() {return purDate;    }

    public void setPurDate(String purDate) {
        this.purDate = purDate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return purDate+" "+brand+" "+name;
    }
}

