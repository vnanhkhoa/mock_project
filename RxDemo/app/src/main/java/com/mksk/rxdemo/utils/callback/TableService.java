package com.mksk.rxdemo.utils.callback;

public class TableService {

    private String a;

    public void getDataFromDb(String testParameter) {
        if (a != null) {
            a = "";
        }
        a = testParameter;
    }

}
