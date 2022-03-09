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

public class ProductOrdertobePreparedViewAdapter extends RecyclerView.Adapter<ProductOrdertobePreparedViewAdapter.ViewHolder> {

    private Context context;
    private List<ProductWaitingOrders> productWaitingOrdersList;

    public ProductOrdertobePreparedViewAdapter(Context context, List<ProductWaitingOrders> productWaitingOrdersList) {
        this.context = context;
        this.productWaitingOrdersList = productWaitingOrdersList;
    }

    @NonNull
    @Override
    public ProductOrdertobePreparedViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_orderstobeprepared_view, parent, false);
        return new ProductOrdertobePreparedViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductOrdertobePreparedViewAdapter.ViewHolder holder, int position) {
        final ProductWaitingOrders productWaitingOrders = productWaitingOrdersList.get(position);
        holder.productName.setText(productWaitingOrders.getProductId());
        holder.price.setText(productWaitingOrders.getProductPrice());
        holder.qty.setText(productWaitingOrders.getProductQuantity());
        holder.totalPrice.setText(productWaitingOrders.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return productWaitingOrdersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productName, price, totalPrice, qty;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = (TextView) itemView.findViewById(R.id.PN);
            price = (TextView) itemView.findViewById(R.id.Pprice);
            totalPrice = (TextView) itemView.findViewById(R.id.Pqty);
            qty = (TextView) itemView.findViewById(R.id.Tprice);
        }
    }
}
