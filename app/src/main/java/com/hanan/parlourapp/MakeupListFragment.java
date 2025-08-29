package com.hanan.parlourapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MakeupListFragment extends Fragment {

    private static final String ARG_CATEGORY = "category"; // "female" or "bridal"

    public static MakeupListFragment newInstance(String category) {
        Bundle b = new Bundle();
        b.putString(ARG_CATEGORY, category);
        MakeupListFragment f = new MakeupListFragment();
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_makeup_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView rv = view.findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setHasFixedSize(true);
        rv.setItemAnimator(null); // smoother scroll

        String category = getArguments() != null ? getArguments().getString(ARG_CATEGORY, "female") : "female";

        ArrayList<Makeup> items = new ArrayList<>();
        if ("regular".equalsIgnoreCase(category)) {
            // Female makeup styles (sample PKR prices)
            items.add(new Makeup("m1","Party Glam","Evening party makeup", 4000, R.drawable.makeup));
            items.add(new Makeup("m2","Casual Day Look","Soft natural look", 2500, R.drawable.makeup));
            items.add(new Makeup("m3","Smokey Eyes","Bold eye makeup", 5000, R.drawable.makeup));
            items.add(new Makeup("m4","HD Makeup","Camera-ready flawless skin", 6000, R.drawable.makeup));
        } else {
            // Bridal makeup styles
            items.add(new Makeup("b1","Nikkah Soft Look","Elegant nikkah makeup", 12000, R.drawable.makeup));
            items.add(new Makeup("b2","Mehndi Glow","Colorful mehndi style", 10000, R.drawable.makeup));
            items.add(new Makeup("b3","Baraat Glam","Full bridal glam", 18000, R.drawable.makeup));
            items.add(new Makeup("b4","Walima Reception","Soft elegant walima makeup", 15000, R.drawable.makeup));
        }

        MakeupAdapter adapter = new MakeupAdapter(items, item -> {
            // Open booking screen
            Intent i = new Intent(requireContext(), BookingActivity.class);
            i.putExtra("style_id", item.getId());
            i.putExtra("style_name", item.getName());
            i.putExtra("style_price", item.getPricePkr());
            startActivity(i);
        });

        rv.setAdapter(adapter);
    }
}
