package com.atminfotech.barcodestorenew;

import java.util.ArrayList;

/**
 * Created by ABC on 08/12/2017.
 */

class ListInfoData {
    public static ArrayList<GridViewData> listD = new ArrayList<>();


    public static ArrayList<GridViewData> getList() {
        return listD;
    }


    public static ArrayList<GridViewData> listDHorizontalList = new ArrayList<>();


    public static ArrayList<GridViewData> getListDHorizontalList() {
        return listDHorizontalList;
    }


    public static void setListDHorizontalList( String pk,String image, String name, String rate,String CompanyName,String Class1,String position){
        GridViewData data = new GridViewData(pk,image,name,rate,CompanyName,Class1,position);
        listDHorizontalList.add(data);

    }
}
