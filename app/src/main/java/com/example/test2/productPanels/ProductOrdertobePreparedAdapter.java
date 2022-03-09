package com.example.test2.productPanels;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test2.R;

import java.util.List;

public class ProductOrdertobePreparedAdapter extends RecyclerView.Adapter<ProductOrdertobePreparedAdapter.ViewHolder> {
    private Context context;
    private List<ProductWaitingOrders1> productWaitingOrders1List;

    public ProductOrdertobePreparedAdapter(Context context, List<ProductWaitingOrders1> productWaitingOrders1List) {
        this.context = context;
        this.productWaitingOrders1List = productWaitingOrders1List;
    }

    @NonNull
    @Override
    public ProductOrdertobePreparedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_ordertobeprepared, parent, false);
        return new ProductOrdertobePreparedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductOrdertobePreparedAdapter.ViewHolder holder, int position) {
        final ProductWaitingOrders1 productWaitingOrders1 = productWaitingOrders1List.get(position);
        holder.Address.setText(productWaitingOrders1.getAddress());
        holder.totalPrice.setText("Total Php: " + productWaitingOrders1.getGrandTotalPrice());
        final String random = productWaitingOrders1.getRandomUID();
        holder.viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductOrdertobePrepared.class);
                intent.putExtra("RandomUID", random);
                context.startActivity(intent);
                ((ProductOrdertobePrepared) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Address, totalPrice;
        private Button viewOrder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Address = itemView.findViewById(R.id.cust_address);
            totalPrice = itemView.findViewById(R.id.Grandtotalprice);
            viewOrder = itemView.findViewById(R.id.View_order);
        }
    }
}
