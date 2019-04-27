package com.example.carslist;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.carslist.View.ViewArticleActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {
    List<Article> list;
    DatabaseReference mRef;


    public ArticleAdapter(List<Article> list){
        this.list = list;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View Articleitem = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false);
        return new ArticleViewHolder(Articleitem);
    }

    @Override
    public void onBindViewHolder(final ArticleViewHolder holder, final int position) {
        final Article article = list.get(position);
        holder.articleContext.setText(article.context);
        holder.articleTitle.setText(article.title);
        Glide.with(holder.constraintLayout.getContext()).load(article.img).into(holder.img);


        holder.trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(article);
            }
        });
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ViewArticleActivity.class);

                intent.putExtra("id", list.get(holder.getAdapterPosition()).id);


                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        private TextView articleTitle;
        private TextView articleContext;
        ImageView trash;
        ConstraintLayout constraintLayout;
        ImageView img;
        ArticleViewHolder(View itemPost) {
            super(itemPost);
            articleTitle = itemPost.findViewById(R.id.post_title);
            articleContext = itemPost.findViewById(R.id.post_context);
            trash = itemPost.findViewById(R.id.trash);
            constraintLayout = itemPost.findViewById(R.id.id_article);
            img = itemPost.findViewById(R.id.post_image);
        }
    }

    public void remove(Article article){
        int position = list.indexOf(article);
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,list.size());
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("Articles").child(article.id).removeValue();
    }
}
