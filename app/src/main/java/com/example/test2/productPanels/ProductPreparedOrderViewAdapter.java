package com.example.test2.productPanels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test2.R;
import java.util.List;

public class ProductPreparedOrderViewAdapter extends RecyclerView.Adapter<ProductPreparedOrderViewAdapter.ViewHolder> {

    private Context context;
    private List<ProductFinalOrders> productFinalOrdersList;

    public ProductPreparedOrderViewAdapter(Context context, List<ProductFinalOrders> productFinalOrdersList) {
        this.context = context;
        this.productFinalOrdersList = productFinalOrdersList;
    }

    @NonNull
    @Override
    public ProductPreparedOrderViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_preparedorderview, parent, false);
        return new ProductPreparedOrderViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductPreparedOrderViewAdapter.ViewHolder holder, int position) {

        final ProductFinalOrders productFinalOrders = productFinalOrdersList.get(position);
        holder.productName.setText(productFinalOrders.getProductName());
        holder.price.setText(productFinalOrders.getProductPrice());
        holder.totalPrice.setText(productFinalOrders.getTotalPrice());
        holder.quantity.setText(productFinalOrders.getProductQuantity());

    }

    @Override
    public int getItemCount() {
        return productFinalOrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productName, price, totalPrice, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = (TextView) itemView.findViewById(R.id.RProductName);
            price = (TextView) itemView.findViewById(R.id.RproductPrice);
            totalPrice = (TextView) itemView.findViewById(R.id.Rtotalprice);
            quantity = (TextView) itemView.findViewById(R.id.RproductQuantity);
        }
    }
}
