package com.tanvir.training.ecomadminbatch2.callbacks;

import com.tanvir.training.ecomadminbatch2.models.PurchaseModel;

import java.util.List;

public interface OnPurchaseQueryCompleteListener {
    void onPurchaseQueryComplete(List<PurchaseModel> temp);
}
