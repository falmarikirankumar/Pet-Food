package com.example.petfood;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.petfood.adapters.PedsListAdapter;
import com.example.petfood.model.PetsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PedsListAdapter.PetListClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Pets List");

        List<PetsModel> petsModelList = getPetsData();
        initRecyclerView(petsModelList);

    }

    private void initRecyclerView(List<PetsModel> petsModelList){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PedsListAdapter adapter = new PedsListAdapter(petsModelList,this);
        recyclerView.setAdapter(adapter);
    }

    private List<PetsModel> getPetsData(){
       InputStream is = getResources().openRawResource(R.raw.petslist);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try{
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while((n=reader.read(buffer))!= -1){
                writer.write(buffer, 0,n);
            }
        }catch (Exception e){

        }

        String jsonStr = writer.toString();
        Gson gson = new Gson();
        PetsModel[] petsModels= gson.fromJson(jsonStr, PetsModel[].class);
        List<PetsModel> petsList = Arrays.asList(petsModels);

        return petsList;

    }

    @Override
    public void onItemClick(PetsModel petsModel) {
        Intent intent=new Intent(MainActivity.this,PetMenuActivity.class);
        intent.putExtra("PetsModel", petsModel);
        startActivity(intent);
    }

}