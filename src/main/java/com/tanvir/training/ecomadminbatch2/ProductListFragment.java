package com.tanvir.training.ecomadminbatch2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tanvir.training.ecomadminbatch2.adaptes.ProductAdapter;
import com.tanvir.training.ecomadminbatch2.callbacks.OnProductItemClickListener;
import com.tanvir.training.ecomadminbatch2.databinding.FragmentProductListBinding;
import com.tanvir.training.ecomadminbatch2.viewmodels.ProductViewModel;


public class ProductListFragment extends Fragment implements OnProductItemClickListener {
    private FragmentProductListBinding binding;
    private ProductViewModel productViewModel;

    public ProductListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductListBinding.inflate(inflater);
        productViewModel = new ViewModelProvider(requireActivity())
                .get(ProductViewModel.class);
        final ProductAdapter adapter = new ProductAdapter(this);
        binding.productRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.productRV.setAdapter(adapter);
        productViewModel.productListLiveData.observe(getViewLifecycleOwner(),
                productList -> {
                    Toast.makeText(getActivity(), "Value : "+productList.size(), Toast.LENGTH_SHORT).show();
                    adapter.submitList(productList);
                });
        return binding.getRoot();
    }

    @Override
    public void onProductItemClicked(String productId) {
        final Bundle bundle = new Bundle();
        bundle.putString("pid", productId);
        Navigation.findNavController(getActivity(), R.id.fragmentContainerView)
                .navigate(R.id.action_productListFragment_to_productDetailsFragment, bundle);
    }
}