package com.atminfotech.barcodestorenew;

import java.util.ArrayList;
import java.util.List;

class ListModel {

    public  static List<RowItem> list = new ArrayList<>();
    /*public static ArrayList<String> getname(){
        return
    }*/

    static void setList(String imageUri, String title, String description, String Rate,String Amount,int Qty){
        RowItem item = new RowItem(imageUri, title, description,Qty,Rate,Amount);
        list.add(item);
    }

    public static List<RowItem> getList(){
        return list;
    }
}
