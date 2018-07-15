package com.example.nehagupta.github;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText username;
    TextView name;
    ImageView image;
    TextView repos;
    TextView followers;
    public static String nm;
    ProgressBar progressBar;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.username);
        name=findViewById(R.id.name);
        image=findViewById(R.id.image);
        repos=findViewById(R.id.repos);
        layout=findViewById(R.id.layout);
        followers=findViewById(R.id.followers);
        progressBar=findViewById(R.id.progressBar);
    }
    public void fetchData(View view){
        layout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        nm=username.getText().toString();
        Retrofit.Builder builder=new Retrofit.Builder().baseUrl("https://api.github.com/users/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit=builder.build();
        UserService service=retrofit.create(UserService.class);
        Call<UserResponse> call=service.getDetails(nm);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() < 400) {
                    UserResponse userResponse = response.body();
                    name.setText(userResponse.name);
                    Picasso.get().load(userResponse.avatar_url).into(image);
                    repos.setText(userResponse.public_repos + "");
                    followers.setText(userResponse.followers + "");
                } else {
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
                layout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }
    public void view_repos(View view)
    {
        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtra("type","repos");
        startActivity(intent);

    }
    public void view_followers(View view)
    {
        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtra("type","followers");
        startActivityForResult(intent,90);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==4)
        {
            layout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            final String nm=data.getStringExtra("name");
            Retrofit.Builder builder=new Retrofit.Builder().baseUrl("https://api.github.com/users/").addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit=builder.build();
            UserService service=retrofit.create(UserService.class);
            Call<UserResponse> call=service.getDetails(nm);
            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if (response.code() < 400) {
                        UserResponse userResponse = response.body();
                        name.setText(userResponse.name);
                        username.setText(nm);
                        Picasso.get().load(userResponse.avatar_url).into(image);
                        repos.setText(userResponse.public_repos + "");
                        followers.setText(userResponse.followers + "");
                    } else {
                        Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_LONG).show();
                    }
                    layout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {

                }
            });
        }
    }
}
