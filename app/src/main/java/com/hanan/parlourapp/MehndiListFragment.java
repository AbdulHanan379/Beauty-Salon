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

public class MehndiListFragment extends Fragment {

    private static final String ARG_CATEGORY = "category"; // "female" or "bridal"

    public static MehndiListFragment newInstance(String category) {
        Bundle b = new Bundle();
        b.putString(ARG_CATEGORY, category);
        MehndiListFragment f = new MehndiListFragment();
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mehndi_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView rv = view.findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setHasFixedSize(true);
        rv.setItemAnimator(null); // smoother scroll

        String category = getArguments() != null ? getArguments().getString(ARG_CATEGORY, "female") : "female";

        ArrayList<Mehndi> items = new ArrayList<>();
        if ("regular".equalsIgnoreCase(category)) {
            // Female mehndi designs
            items.add(new Mehndi("m1","Simple Floral","Elegant floral hand design", 1500, R.drawable.mehndi));
            items.add(new Mehndi("m2","Arabic Style","Trendy Arabic patterns", 2000, R.drawable.mehndi));
            items.add(new Mehndi("m3","Party Mehndi","Quick party style design", 2500, R.drawable.mehndi));
            items.add(new Mehndi("m4","Half Hand","Minimal half-hand design", 1000, R.drawable.mehndi));
        } else {
            // Bridal mehndi designs
            items.add(new Mehndi("b1","Full Bridal","Traditional full hand & feet", 8000, R.drawable.mehndi));
            items.add(new Mehndi("b2","Heavy Bridal","Detailed bridal patterns", 10000, R.drawable.mehndi));
            items.add(new Mehndi("b3","Rajasthani Style","Intricate Indian style", 12000, R.drawable.mehndi));
            items.add(new Mehndi("b4","Pakistani Style","Classic Pakistani bridal look", 9000, R.drawable.mehndi));
        }

        MehndiAdapter adapter = new MehndiAdapter(items, item -> {
            // Open booking screen with selected mehndi service
            Intent i = new Intent(requireContext(), BookingActivity.class);
            i.putExtra("style_id", item.getId());
            i.putExtra("style_name", item.getName());
            i.putExtra("style_price", item.getPricePkr());
            startActivity(i);
        });

        rv.setAdapter(adapter);
    }
}
