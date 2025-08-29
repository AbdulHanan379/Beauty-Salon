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

public class HairStyleAdapter extends RecyclerView.Adapter<HairStyleAdapter.VH> {

    public interface OnBookClickListener {
        void onBookClick(HairStyle item);
    }

    private final List<HairStyle> data;
    private final OnBookClickListener listener;
    private final NumberFormat currency = NumberFormat.getNumberInstance(new Locale("en", "PK"));

    public HairStyleAdapter(List<HairStyle> data, OnBookClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hairstyle, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        HairStyle item = data.get(position);
        holder.title.setText(item.getName());
        holder.desc.setText(item.getDescription());
        holder.price.setText("PKR " + currency.format(item.getPricePkr()));
        holder.image.setImageResource(item.getImageRes());

        holder.book.setOnClickListener(v -> {
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
