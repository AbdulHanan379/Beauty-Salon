package com.hanan.parlourapp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PromoSliderAdapter extends RecyclerView.Adapter<PromoSliderAdapter.PromoViewHolder> {

    private final List<PromoItem> promoList;

    public PromoSliderAdapter(List<PromoItem> promoList) {
        this.promoList = promoList;
    }

    @NonNull
    @Override
    public PromoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_promo_slide, parent, false);
        return new PromoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoViewHolder holder, int position) {
        PromoItem item = promoList.get(position);
        holder.textView.setText(item.getText());
        holder.imageView.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return promoList.size();
    }

    public static class PromoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public PromoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.promoImage);
            textView = itemView.findViewById(R.id.promoText);
        }
    }
}
