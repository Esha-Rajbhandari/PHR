package com.example.esha.personalhealthrecord.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.esha.personalhealthrecord.POJO.News;
import com.example.esha.personalhealthrecord.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class NewsAdapter extends FirestoreRecyclerAdapter<News, NewsAdapter.NewsHolder> {

    private OnItemClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public NewsAdapter(@NonNull FirestoreRecyclerOptions<News> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NewsHolder holder, int position, @NonNull News model) {
        String news = model.getNews_body();
        holder.newsTitle.setText(model.getNews_title());
        holder.newsDesc.setText(news.substring(0,50)+ "...");
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View reportView =  LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_layout, viewGroup,false);
        return new NewsHolder(reportView);
    }

    public class NewsHolder extends RecyclerView.ViewHolder{
        private TextView newsTitle;
        private TextView newsDesc;
        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.news_title_view);
            newsDesc = itemView.findViewById(R.id.news_desc_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(NewsAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
}
