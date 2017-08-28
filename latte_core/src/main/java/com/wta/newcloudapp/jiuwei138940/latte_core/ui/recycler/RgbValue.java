package com.wta.newcloudapp.jiuwei138940.latte_core.ui.recycler;

import com.google.auto.value.AutoValue;


@AutoValue
public abstract class RgbValue {
    public abstract int red();

    public abstract int green();

    public abstract int blue();

    public static RgbValue create(int red,int green,int blue){
        return new AutoValue_RgbValue(red, green, blue);
    }
}

