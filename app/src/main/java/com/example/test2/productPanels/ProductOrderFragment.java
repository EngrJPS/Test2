package com.example.test2.productPanels;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test2.R;

public class ProductOrderFragment extends Fragment {

    private TextView orderPrepare, preparedOrder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_orders, null);
        getActivity().setTitle("New Orders");
        orderPrepare = (TextView) v.findViewById(R.id.ordertobe);
        preparedOrder = (TextView) v.findViewById(R.id.prepareorder);
        orderPrepare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(),ProductOrdertobePrepared.class);
                startActivity(i);
            }
        });

        preparedOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),ProductPreparedOrder.class);
                startActivity(intent);
            }
        });
        return v;
    }
}
