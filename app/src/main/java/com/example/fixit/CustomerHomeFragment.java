package com.example.fixit;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.example.fixit.R;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CustomerHomeFragment extends Fragment {
    private ViewPager viewPager;
    private LinearLayout customIndicatorLayout;
    private int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3};
    private int currentPage = 0;
    ArrayList<CategoryModel> categoryarray = new ArrayList<>();
    ArrayList<ServiceProviderInfo> serviceProviderInfoArrayList = new ArrayList<>();
    RecyclerView categoryRecycler;

    public CustomerHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_customer_home, container, false);

        viewPager = root.findViewById(R.id.viewPager);
        customIndicatorLayout = root.findViewById(R.id.custom_indicator);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryRecycler = view.findViewById(R.id.categoriesRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        categoryRecycler.setLayoutManager(layoutManager);
        categoryarray.add(new CategoryModel("Car Service", R.drawable.services_medicinal));
        categoryarray.add(new CategoryModel("Gardening Service", R.drawable.services_medicinal));
        categoryarray.add(new CategoryModel("Maid Service", R.drawable.services_medicinal));
        categoryarray.add(new CategoryModel("Plumbing Service", R.drawable.services_medicinal));
        categoryarray.add(new CategoryModel("Electrician Service", R.drawable.services_medicinal));

        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(categoryarray,getContext());
        categoryRecycler.setAdapter(categoriesAdapter);


        RecyclerView serviceproviderinfo = view.findViewById(R.id.serviceproviderlist);
        serviceproviderinfo.setLayoutManager(new LinearLayoutManager(getContext()));

        serviceProviderInfoArrayList.add(new ServiceProviderInfo("Ayush Soni", "Electrician", "6353805504", "Nadiad", "4.7", R.drawable.profile_icon));
        serviceProviderInfoArrayList.add(new ServiceProviderInfo("Ayush Soni", "Electrician", "6353805504", "Nadiad", "4.7", R.drawable.profile_icon));
        serviceProviderInfoArrayList.add(new ServiceProviderInfo("Ayush Soni", "Electrician", "6353805504", "Nadiad", "4.7", R.drawable.profile_icon));
        serviceProviderInfoArrayList.add(new ServiceProviderInfo("Ayush Soni", "Electrician", "6353805504", "Nadiad", "4.7", R.drawable.profile_icon));


        ServiceProviderInfoAdapter adapter = new ServiceProviderInfoAdapter(getContext(),serviceProviderInfoArrayList);
        serviceproviderinfo.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Setup the slider here instead of in onViewCreated
        setupSlider();
    }

    private void setupSlider() {
        // Ensure that the fragment is added and context is not null
        if (!isAdded() || getContext() == null) {
            return;
        }

        ArrayList<Integer> imageList = new ArrayList<>();
        for (int image : images) {
            imageList.add(image);
        }

        // Ensure context is attached before creating the adapter
        CustomerHomeViewPagerAdapter adapter = new CustomerHomeViewPagerAdapter(getContext(), imageList);
        viewPager.setAdapter(adapter);
        createCustomIndicator(imageList.size());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                updateCustomIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        // Auto-slide functionality
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask update = new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> {
                    if (currentPage == images.length - 1) {
                        currentPage = 0;
                    } else {
                        currentPage++;
                    }
                    viewPager.setCurrentItem(currentPage, true);
                });
            }
        };
        timer.schedule(update, 4000, 4000);
    }


    private void createCustomIndicator(int count) {
        ImageView[] dots = new ImageView[count];
        customIndicatorLayout.removeAllViews(); // Clear any previous dots

        for (int i = 0; i < count; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.indicator_inactive, null));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            customIndicatorLayout.addView(dots[i], params);
        }
        // Set the first dot to active
        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.indicator_active, null));
    }

    private void updateCustomIndicator(int position) {

        if (!isAdded() || getContext() == null) {
            return;  // Exit if the fragment is not attached
        }
        int count = customIndicatorLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            ImageView imageView = (ImageView) customIndicatorLayout.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.indicator_active, null));
            } else {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.indicator_inactive, null));
            }
        }
    }
}
