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

public class FacialListFragment extends Fragment {

    private static final String ARG_CATEGORY = "category"; // "basic" or "premium"

    public static FacialListFragment newInstance(String category) {
        Bundle b = new Bundle();
        b.putString(ARG_CATEGORY, category);
        FacialListFragment f = new FacialListFragment();
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_facial_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView rv = view.findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setHasFixedSize(true);
        rv.setItemAnimator(null);

        String category = getArguments() != null ? getArguments().getString(ARG_CATEGORY, "basic") : "basic";

        ArrayList<Facial> items = new ArrayList<>();
        if ("Bridal".equalsIgnoreCase(category)) {
            items.add(new Facial("fa1", "Fruit Facial", "Natural glow with fruit extracts", 2500, R.drawable.facial));
            items.add(new Facial("fa2", "Herbal Facial", "Relaxing herbal treatment", 3000, R.drawable.facial));
        } else {
            items.add(new Facial("fp1", "Gold Facial", "Luxury glow with 24K gold pack", 8000, R.drawable.facial));
            items.add(new Facial("fp2", "Diamond Facial", "Premium brightening treatment", 10000, R.drawable.facial));
        }

        FacialAdapter adapter = new FacialAdapter(items, item -> {
            Intent i = new Intent(requireContext(), BookingActivity.class);
            i.putExtra("facial_id", item.getId());
            i.putExtra("facial_name", item.getName());
            i.putExtra("facial_price", item.getPricePkr());
            startActivity(i);
        });

        rv.setAdapter(adapter);
    }
}
