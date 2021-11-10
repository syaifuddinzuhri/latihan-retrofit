package com.example.latihan_retrofit.services;

import com.example.latihan_retrofit.models.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {

    @GET("users/{user}/repos")
    Call<List<Repo>> ListRepos(@Path("user") String user);
}
