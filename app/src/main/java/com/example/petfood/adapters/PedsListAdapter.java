package com.example.petfood.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.petfood.R;
import com.example.petfood.model.PetsModel;

import java.util.List;

public class PedsListAdapter extends RecyclerView.Adapter<PedsListAdapter.MyViewHolder> {

   private List<PetsModel>petsModelList;
    private PetListClickListener clickListener;
   public PedsListAdapter(List<PetsModel>petsModelList,PetListClickListener clickListener){
       this.petsModelList=petsModelList;
       this.clickListener=clickListener;

   }

    public void updateData(List<PetsModel>petsModelList){
        this.petsModelList=petsModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PedsListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row , parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedsListAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.petName.setText(petsModelList.get(position).getName());
        holder.petAddress.setText("Address: "+petsModelList.get(position).getAddress());
        holder.petHours.setText("Today's hours: "+petsModelList.get(position).getHours().getTodayHours());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(petsModelList.get(position));
            }
        });

        Glide.with(holder.thumbImage)
                .load(petsModelList.get(position).getImage())
                .into(holder.thumbImage);

    }

    @Override
    public int getItemCount() {
        return petsModelList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView petName;
        TextView petAddress;
        TextView petHours;
        ImageView thumbImage;

        public MyViewHolder(View view){
            super(view);

            petName = view.findViewById(R.id.petName);
            petAddress = view.findViewById(R.id.petAddress);
            petHours = view.findViewById(R.id.petHours);
            thumbImage = view.findViewById(R.id.thumbImage);

        }
    }

    public interface PetListClickListener{
       public void onItemClick(PetsModel petsModel);
    }

}
