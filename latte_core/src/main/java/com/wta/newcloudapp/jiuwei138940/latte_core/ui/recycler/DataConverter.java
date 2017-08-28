package com.wta.newcloudapp.jiuwei138940.latte_core.ui.recycler;

import java.util.ArrayList;



public abstract class DataConverter {
    protected final ArrayList<MultipleItemEmity> ENTITIES = new ArrayList<>();
    private String mJsonData = null;

    public abstract ArrayList<MultipleItemEmity> convert();

    public DataConverter setJsonData(String json){
        this.mJsonData = json;
        return this;
    }

    protected String getJsonData(){
        if (mJsonData == null || mJsonData.isEmpty()){
            throw new NullPointerException("Data is NULL!");
        }
        return mJsonData;
    }
}
