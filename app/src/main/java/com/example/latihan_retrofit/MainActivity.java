package com.example.latihan_retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.latihan_retrofit.databinding.ActivityMainBinding;
import com.example.latihan_retrofit.models.Repo;
import com.example.latihan_retrofit.services.GithubService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private GithubService service;
    private ActivityMainBinding binding;
    TextView alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GithubService.class);

        alert = (TextView) findViewById(R.id.alert);

    }

    public void handleSend(View view){
        Call<List<Repo>> repos = service.ListRepos(binding.getUsername());
        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                if(response.isSuccessful()){
                    binding.setRepo(response.body().get(0));
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Log.e("Error", t.getMessage());
                alert.setVisibility(View.VISIBLE);
                progress.dismiss();
            }
        });
    }
}