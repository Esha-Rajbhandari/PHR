package com.example.esha.personalhealthrecord.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.esha.personalhealthrecord.POJO.CardContent;
import com.example.esha.personalhealthrecord.R;

import java.util.ArrayList;

public class CardContentAdapter extends RecyclerView.Adapter<CardContentAdapter.CardViewHolder> {

    private Context mContext;
    private ArrayList<CardContent> mCardContentList;

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
        return new CardViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.linearLayout.setId(mCardContentList.get(position).getId());
        holder.imgHealthResource.setImageResource(mCardContentList.get(position).getImgResource());
        holder.txtHealthResource.setText(mCardContentList.get(position).getText());
    }

    public CardContentAdapter(Context mContext, ArrayList<CardContent> mCardContentList) {
        this.mContext = mContext;
        this.mCardContentList = mCardContentList;
    }

    @Override
    public int getItemCount() {

        return mCardContentList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgHealthResource;
        private TextView txtHealthResource;
        private LinearLayout linearLayout;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHealthResource = itemView.findViewById(R.id.cardview_image);
            txtHealthResource = itemView.findViewById(R.id.cardview_text);
            linearLayout = itemView.findViewById(R.id.linear_layout);
        }
    }
}
