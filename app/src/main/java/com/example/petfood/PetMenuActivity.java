package com.example.petfood;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
//import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petfood.adapters.MenuListAdapter;
import com.example.petfood.model.Menu;
import com.example.petfood.model.PetsModel;

import java.util.ArrayList;
import java.util.List;

public class PetMenuActivity extends AppCompatActivity implements MenuListAdapter.MenuListClickListener{
    private List<Menu> menuList=null;
    private MenuListAdapter menuListAdapter;
    private List<Menu> itemsInCartList;
    private int totalItemInCart = 0;
    private TextView buttonCheckout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_menu);


        PetsModel petsModel =getIntent().getParcelableExtra("PetsModel");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(petsModel.getName());
        actionBar.setSubtitle(petsModel.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(true);


        menuList=petsModel.getMenus();
        initRecyclerView();

        buttonCheckout = findViewById(R.id.buttonCheckout);
        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemsInCartList != null && itemsInCartList.size() <= 0){
                    Toast.makeText(PetMenuActivity.this, "Please add some item in cart.", Toast.LENGTH_LONG).show();
                    return;
                }
                petsModel.setMenus(itemsInCartList);
                Intent i = new Intent(PetMenuActivity.this,PlaceYourOrderActivity.class);
                i.putExtra("PetsModel", petsModel);
                startActivityForResult(i,1000);

            }
        });


    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        menuListAdapter = new MenuListAdapter(menuList,this);
        recyclerView.setAdapter(menuListAdapter);

    }


    @Override
    public void onAddToCartClick(Menu menu) {
        if(itemsInCartList == null){
            itemsInCartList = new ArrayList<>();

        }
        itemsInCartList.add(menu);
        totalItemInCart=0;

        for(Menu m : itemsInCartList){
            totalItemInCart=totalItemInCart+m.getTotalInCart();
        }
        buttonCheckout.setText("Checkout ("+ totalItemInCart+") items");
    }

    @Override
    public void onUpdateCartClick(Menu menu) {
        if(itemsInCartList.contains(menu)){
            int index = itemsInCartList.indexOf(menu);
            itemsInCartList.remove(index);
            itemsInCartList.add(index, menu);

            totalItemInCart=0;

            for(Menu m : itemsInCartList){
                totalItemInCart=totalItemInCart+m.getTotalInCart();
            }
            buttonCheckout.setText("Checkout ("+ totalItemInCart+") items");

        }
    }

    @Override
    public void onRemoveFromCartClick(Menu menu) {
        if(itemsInCartList.contains(menu)){
            itemsInCartList.remove(menu);

            totalItemInCart=0;

            for(Menu m : itemsInCartList){
                totalItemInCart=totalItemInCart+m.getTotalInCart();
            }
            buttonCheckout.setText("Checkout ("+ totalItemInCart+") items");

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == Activity.RESULT_OK ){
            //
            finish();
        }
    }
}