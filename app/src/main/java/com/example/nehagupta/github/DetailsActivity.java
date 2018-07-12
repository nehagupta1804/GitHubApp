package com.example.nehagupta.github;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity {

    ListView listview;
    ArrayList<String> titles=new ArrayList<>();
    ArrayAdapter<String> adapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        listview=findViewById(R.id.listview);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,titles);
        listview.setAdapter(adapter);
        Intent intent =getIntent();
        String check=intent.getStringExtra("type");
        Retrofit.Builder builder=new Retrofit.Builder().baseUrl("https://api.github.com/users/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        UserService service=retrofit.create(UserService.class);
        if(check.equals("repos"))
        {
            Call<ArrayList<Repos>> call=service.getRepoDetails(MainActivity.nm);
            call.enqueue(new Callback<ArrayList<Repos>>() {
                @Override
                public void onResponse(Call<ArrayList<Repos>> call, Response<ArrayList<Repos>> response) {
                    ArrayList<Repos> listResponse = response.body();
                    titles.clear();
                    for(int i=0;i<listResponse.size();i++)
                    {
                        String name=listResponse.get(i).name;
                        titles.add(name);
                    }
                    progressBar.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ArrayList<Repos>> call, Throwable t) {

                }
            });

        }
        else if(check.equals("followers"))
        {
            Call<ArrayList<Followers>> call=service.getFollowersDetails(MainActivity.nm);
            call.enqueue(new Callback<ArrayList<Followers>>() {
                @Override
                public void onResponse(Call<ArrayList<Followers>> call, Response<ArrayList<Followers>> response) {
                    ArrayList<Followers> listResponse = response.body();
                    titles.clear();
                    for(int i=0;i<listResponse.size();i++)
                    {
                        String name=listResponse.get(i).login;
                        titles.add(name);
                    }
                    progressBar.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ArrayList<Followers>> call, Throwable t) {

                }
            });

        }
    }
}
