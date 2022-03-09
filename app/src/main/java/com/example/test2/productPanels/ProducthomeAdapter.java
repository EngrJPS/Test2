package com.example.test2.productPanels;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test2.R;


import java.util.List;

public class ProducthomeAdapter extends RecyclerView.Adapter<ProducthomeAdapter.MyViewHolder> {

    private Context context;
    private List<UpdateProductModel> updateProductModelsLists;

    public ProducthomeAdapter(Context context, List<UpdateProductModel> updateProductModels) {
        this.context = context;
        this.updateProductModelsLists = updateProductModelsLists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_menu_update_delete, parent, false);
        return new ProducthomeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProducthomeAdapter.MyViewHolder holder, int position) {

        final UpdateProductModel updateProductModels= updateProductModelsLists.get(position);
        holder.products.setText(updateProductModels.getProductName());
        updateProductModels.getRandomUID();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateDeleteProduct.class);
                intent.putExtra("updatedeletedproduct", updateProductModels.getRandomUID());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return updateProductModelsLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView products;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            products = itemView.findViewById(R.id.product_name);
        }
    }
}
