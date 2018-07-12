package com.example.nehagupta.github;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("{name}")
    Call<UserResponse> getDetails(@Path("name") String name );

    @GET("{name}/repos")
    Call<ArrayList<Repos>> getRepoDetails(@Path("name") String name );

    @GET("{name}/followers")
    Call<ArrayList<Followers>> getFollowersDetails(@Path("name") String name );



}
