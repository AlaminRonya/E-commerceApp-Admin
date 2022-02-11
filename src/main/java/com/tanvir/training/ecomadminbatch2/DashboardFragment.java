package com.tanvir.training.ecomadminbatch2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tanvir.training.ecomadminbatch2.adaptes.DashbordAdapter;
import com.tanvir.training.ecomadminbatch2.callbacks.OnDashboardItemSelectedListener;
import com.tanvir.training.ecomadminbatch2.databinding.FragmentDashboardBinding;
import com.tanvir.training.ecomadminbatch2.models.DashboardItem;
import com.tanvir.training.ecomadminbatch2.utils.Constants;
import com.tanvir.training.ecomadminbatch2.viewmodels.LoginViewModel;

public class DashboardFragment extends Fragment implements OnDashboardItemSelectedListener {
    private FragmentDashboardBinding binding;
    private LoginViewModel loginViewModel;
    public DashboardFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater);
        loginViewModel = new ViewModelProvider(requireActivity())
                .get(LoginViewModel.class);
        loginViewModel.getStateLiveData()
                .observe(getViewLifecycleOwner(), authState -> {
                    if (authState == LoginViewModel.AuthState.UNAUTHENTICATED) {
                        Navigation.findNavController(container)
                                .navigate(R.id.action_dashboardFragment_to_loginFragment);
                    }
                });

        final DashbordAdapter adapter = new DashbordAdapter(this, DashboardItem.getDashboardItems());
        final GridLayoutManager glm = new GridLayoutManager(getActivity(), 2);
        binding.dashboardRV.setLayoutManager(glm);
        binding.dashboardRV.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onDashboardItemSelect(String item) {
        switch (item) {
            case Constants.Item.ADD_PRODUCT:
                Navigation.findNavController(getActivity(), R.id.fragmentContainerView)
                        .navigate(R.id.action_dashboardFragment_to_newProductFragment);
                break;
            case Constants.Item.VIEW_PRODUCT:
                Navigation.findNavController(getActivity(), R.id.fragmentContainerView)
                        .navigate(R.id.action_dashboardFragment_to_productListFragment);
                break;
        }
    }
}