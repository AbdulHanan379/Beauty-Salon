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
public class HairStyleListFragment extends Fragment {

    private static final String ARG_CATEGORY = "category"; // "female" or "bridal"

    public static HairStyleListFragment newInstance(String category) {
        Bundle b = new Bundle();
        b.putString(ARG_CATEGORY, category);
        HairStyleListFragment f = new HairStyleListFragment();
        f.setArguments(b);
        return f;
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hairstyle_list, container, false);
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView rv = view.findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setHasFixedSize(true);
        rv.setItemAnimator(null); // keeps scroll ultra-smooth

        String category = getArguments() != null ? getArguments().getString(ARG_CATEGORY, "female") : "female";

        ArrayList<HairStyle> items = new ArrayList<>();
        if ("regular".equalsIgnoreCase(category)) {
            // Female styles (sample prices in PKR)
            items.add(new HairStyle("f1","Soft Curls","Loose curls for everyday glam", 1800, R.drawable.hair));
            items.add(new HairStyle("f2","Blow-dry","Smooth & voluminous finish", 1200, R.drawable.hair));
            items.add(new HairStyle("f3","Casual Updo","Elegant quick updo", 2500, R.drawable.hair));
            items.add(new HairStyle("f4","Straight Ironing","Silky straight look", 1500, R.drawable.hair));
        } else {
            // Bridal styles (sample prices in PKR)
            items.add(new HairStyle("b1","Traditional Bridal Bun","Classic embellished bun", 12000, R.drawable.hair));
            items.add(new HairStyle("b2","Mehndi Floral Style","Soft curls with floral work", 8000, R.drawable.hair));
            items.add(new HairStyle("b3","Walima Hollywood Waves","Timeless waves", 10000, R.drawable.hair));
            items.add(new HairStyle("b4","Nikkah Soft Updo","Graceful romantic updo", 9000, R.drawable.hair));
        }

        HairStyleAdapter adapter = new HairStyleAdapter(items, item -> {
            // Go to booking screen with selected item
            Intent i = new Intent(requireContext(), BookingActivity.class);
            i.putExtra("style_id", item.getId());
            i.putExtra("style_name", item.getName());
            i.putExtra("style_price", item.getPricePkr());
            startActivity(i);
        });

        rv.setAdapter(adapter);
    }
}
