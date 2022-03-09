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

import java.util.List;
import com.example.test2.R;

import org.w3c.dom.Text;

public class ProductPreparedOrderAdapter extends RecyclerView.Adapter<ProductPreparedOrderAdapter.ViewHolder> {

    private Context context;
    private List<ProductFinalOrders1> productFinalOrders1List;

    public ProductPreparedOrderAdapter(Context context, List<ProductFinalOrders1> productFinalOrders1List) {
        this.context = context;
        this.productFinalOrders1List = productFinalOrders1List;
    }

    @NonNull
    @Override
    public ProductPreparedOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_preparedorder, parent, false);
        return new ProductPreparedOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductPreparedOrderAdapter.ViewHolder holder, int position) {
        final ProductFinalOrders1 productFinalOrders1 = productFinalOrders1List.get(position);
        holder.address.setText(productFinalOrders1.getAddress());
        holder.totalPrice.setText(productFinalOrders1.getGrandTotalPrice());
        final String random = productFinalOrders1.getRandomUID();
        holder.viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductPreparedOrderView.class);
                intent.putExtra("RandomUID", random);
                context.startActivity(intent);
                ((ProductPreparedOrder) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productFinalOrders1List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView address, totalPrice;
        private Button viewOrder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            address = (TextView) itemView.findViewById(R.id.customer_address);
            totalPrice = (TextView) itemView.findViewById(R.id.customer_totalprice);
            viewOrder = (Button) itemView.findViewById(R.id.View);
        }
    }
}
