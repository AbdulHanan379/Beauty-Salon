package com.hanan.parlourapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class FacialAdapter extends RecyclerView.Adapter<FacialAdapter.VH> {

    public interface OnBookClickListener {
        void onBookClick(Facial item);
    }

    private final List<Facial> data;
    private final OnBookClickListener listener;
    private final NumberFormat currency = NumberFormat.getNumberInstance(new Locale("en", "PK"));

    public FacialAdapter(List<Facial> data, OnBookClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_facial, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Facial item = data.get(position);
        h.title.setText(item.getName());
        h.desc.setText(item.getDescription());
        h.price.setText("PKR " + currency.format(item.getPricePkr()));
        h.image.setImageResource(item.getImageRes());
        h.book.setOnClickListener(v -> {
            if (listener != null) listener.onBookClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, desc, price;
        Button book;

        VH(@NonNull View v) {
            super(v);
            image = v.findViewById(R.id.ivImage);
            title = v.findViewById(R.id.tvTitle);
            desc = v.findViewById(R.id.tvDesc);
            price = v.findViewById(R.id.tvPrice);
            book = v.findViewById(R.id.btnBook);
        }
    }
}
