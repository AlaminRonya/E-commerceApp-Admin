package com.tanvir.training.ecomadminbatch2.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tanvir.training.ecomadminbatch2.callbacks.OnCategoryQueryCompleteListener;
import com.tanvir.training.ecomadminbatch2.callbacks.OnProductQueryCompleteListener;
import com.tanvir.training.ecomadminbatch2.models.ProductModel;
import com.tanvir.training.ecomadminbatch2.models.PurchaseModel;
import com.tanvir.training.ecomadminbatch2.repos.AdminRepository;

import java.util.List;

public class ProductViewModel extends ViewModel {
    private final AdminRepository repository = new AdminRepository();

    public MutableLiveData<List<String>> categoryListLiveData = new MutableLiveData<>();
    public MutableLiveData<List<ProductModel>> productListLiveData = new MutableLiveData<>();
    public MutableLiveData<List<PurchaseModel>> purchaseListLiveData = new MutableLiveData<>();
    public MutableLiveData<ProductModel> productLiveData = new MutableLiveData<>();
    public ProductViewModel(){
        getCategories();
        getAllProducts();
    }
    public void addProduct(ProductModel productModel, PurchaseModel purchaseModel) {
        repository.addNewProduct(productModel, purchaseModel);
    }

    private void getCategories(){
        repository.getAllCategories(new OnCategoryQueryCompleteListener() {
            @Override
            public void onCategoryQueryComplete(List<String> items) {
                categoryListLiveData.postValue(items);
            }
        });
    }
    private void getAllProducts(){
        repository.getAllProducts(new OnProductQueryCompleteListener() {
            @Override
            public void onProductQueryComplete(List<ProductModel> models) {
                productListLiveData.postValue(models);
            }
        });
    }
    public void getProductsByCategory(String category) {
        repository.getAllProductsByCategory(category, items ->
                productListLiveData.postValue(items));
    }

    
    

    public void getPurchaseListByProductId(String productId) {
    }
    public void getProductById(String productId) {
        repository.getProductByProductId(productId, productModel -> {
            productLiveData.postValue(productModel);
        });
    }



}
