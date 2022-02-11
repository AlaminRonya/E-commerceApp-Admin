package com.tanvir.training.ecomadminbatch2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tanvir.training.ecomadminbatch2.databinding.FragmentProductDetailsBinding;
import com.tanvir.training.ecomadminbatch2.viewmodels.ProductViewModel;


public class ProductDetailsFragment extends Fragment {
    private FragmentProductDetailsBinding binding;
    private ProductViewModel productViewModel;
    private String productId;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        productId = getArguments().getString("pid");
        binding = FragmentProductDetailsBinding.inflate(inflater);
        productViewModel = new ViewModelProvider(requireActivity())
                .get(ProductViewModel.class);
        productViewModel.getProductById(productId);
        productViewModel.getPurchaseListByProductId(productId);
        productViewModel.productLiveData.observe(getViewLifecycleOwner(),
                productModel -> {
                    binding.setProduct(productModel);
                });
//        productViewModel.purchaseListLiveData
//                .observe(getViewLifecycleOwner(), purchaseModels -> {
//                    String history = "";
//                    for (PurchaseModel p : purchaseModels) {
//                        history += "Purchased on "+
//                                p.getPurchaseDate()+"\n"+
//                                "for BDT "+p.getPurchasePrice()+"\n"+
//                                "with quantity "+p.getPurchaseQuantity()+"\n\n";
//                    }
//                    binding.purchaseHistoryTV.setText(history);
//                });
//        binding.priceUpdateBtn.setOnClickListener(v -> {
//            showUpdatePriceAlertDialog();
//        });
        return binding.getRoot();
    }
}