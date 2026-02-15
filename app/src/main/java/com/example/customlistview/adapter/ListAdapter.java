package com.example.customlistview.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.customlistview.R;
import com.example.customlistview.models.Product;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Product> {
    public ListAdapter(@NonNull Context context, @NonNull List<Product> objects) {
        super(context, R.layout.list_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        Product product = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);

        }

        ImageView imageView = view.findViewById(R.id.imageIV);
        TextView productNameTV = view.findViewById(R.id.nameProductTV);
        TextView costProductTV = view.findViewById(R.id.costProductTV);
        TextView descriptionProduct = view.findViewById(R.id.descriptionET);

        imageView.setImageURI(Uri.parse(product.getPhoto()));
        productNameTV.setText(product.getName());
        costProductTV.setText(product.getPrice());
        descriptionProduct.setText(product.getDescription());

        return view;
    }
}
