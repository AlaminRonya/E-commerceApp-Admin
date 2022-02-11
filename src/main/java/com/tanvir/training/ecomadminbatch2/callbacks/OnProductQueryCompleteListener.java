package com.tanvir.training.ecomadminbatch2.callbacks;

import com.tanvir.training.ecomadminbatch2.models.ProductModel;

import java.util.List;

public interface OnProductQueryCompleteListener {
    void onProductQueryComplete(List<ProductModel> models);
}
