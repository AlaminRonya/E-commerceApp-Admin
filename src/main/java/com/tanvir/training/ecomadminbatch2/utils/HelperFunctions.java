package com.tanvir.training.ecomadminbatch2.utils;

import android.content.Context;
import android.widget.Toast;

public class HelperFunctions {
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
