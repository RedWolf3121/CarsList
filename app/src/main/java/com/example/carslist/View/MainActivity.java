package com.example.carslist.View;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.carslist.Article;
import com.example.carslist.ArticleAdapter;
import com.example.carslist.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    List<Article> ArticleList;
    DatabaseReference mRef;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.article_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArticleList = new ArrayList<>();


        mRef = FirebaseDatabase.getInstance().getReference().child("Artiles");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArticleList.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Article article = dataSnapshot1.getValue(Article.class);
                    ArticleList.add(article);
                }
                ArticleAdapter articleAdapter = new ArticleAdapter(ArticleList);
                recyclerView.setAdapter(articleAdapter);
                articleAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "No se ha cargado correctamente!", Toast.LENGTH_SHORT).show();
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewArticleActivity.class);
                startActivity(intent);
            }
        });
    }
}
