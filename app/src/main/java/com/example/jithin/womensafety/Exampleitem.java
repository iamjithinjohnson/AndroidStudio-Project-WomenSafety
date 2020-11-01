package com.example.jithin.womensafety;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Exampleitem  {
    private String mLine1;
    private String mLine2;
    private String mLine3;


    public Exampleitem(String line1, String line2, String line3) {
        mLine1 = line1;
        mLine2 = line2;
        mLine3 = line3;
    }

    public String getLine1() {
        return mLine1;
    }

    public String getLine2() {
        return mLine2;
    }
    public String getLine3() {
        return mLine3;
    }
}
