package com.example.esha.personalhealthrecord.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.esha.personalhealthrecord.R;

import java.util.ArrayList;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder>{
    private RecyclerView recyclerView;
    private ArrayList<String> items = new ArrayList<>();
    private ArrayList<String> url = new ArrayList<>();
    private Context mContext;

    public FileAdapter(RecyclerView recyclerView, ArrayList<String> items, ArrayList<String> url, Context mContext) {
        this.recyclerView = recyclerView;
        this.items = items;
        this.url = url;
        this.mContext = mContext;
    }
//update file of the user
    public void updateFile(String fileName, String urls){
        Log.i("ooo", "updateFile: "+fileName+"   "+urls);
        items.add(fileName+".pdf");
        url.add(urls);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_file_item,viewGroup,false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder fileViewHolder, int i) {
        fileViewHolder.fileTextView.setText(items.get(i));
        Log.i("ooo", "onBindViewHolder: "+items.get(i));
    }
//gets the total data count
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class FileViewHolder extends RecyclerView.ViewHolder{

        private TextView fileTextView;

        public FileViewHolder(@NonNull View itemView) {
            super(itemView);
            fileTextView = itemView.findViewById(R.id.tv_file_url);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = recyclerView.getChildLayoutPosition(v);
                    Intent intent = new Intent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setType(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url.get(position)));
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
