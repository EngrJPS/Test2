package com.example.test2.productPanels;

import com.example.test2.R;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProducerOrderProductAdapter extends RecyclerView.Adapter<ProducerOrderProductAdapter.ViewHolder> {

    private Context context;
    private List<ProductPendingOrders> productPendingOrdersList;

    public ProducerOrderProductAdapter(Context context, List<ProductPendingOrders> productPendingOrdersList) {
        this.context = context;
        this.productPendingOrdersList = productPendingOrdersList;
    }

    @NonNull
    @Override
    public ProducerOrderProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.producer_order_products,parent, false);
        return new ProducerOrderProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProducerOrderProductAdapter.ViewHolder holder, int position) {

        final ProductPendingOrders productPendingOrders = productPendingOrdersList.get(position);
        holder.productName.setText(productPendingOrders.getProductName());
        holder.quantity.setText(productPendingOrders.getProductQty());
        holder.price.setText("Price: Php " + productPendingOrders.getProductPrice());
        holder.totalPrice.setText("Total: Php " + productPendingOrders.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return productPendingOrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productName, price, totalPrice, quantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.PN);
            price = itemView.findViewById(R.id.PR);
            totalPrice = itemView.findViewById(R.id.TR);
            quantity = itemView.findViewById(R.id.QY);
        }
    }
}
