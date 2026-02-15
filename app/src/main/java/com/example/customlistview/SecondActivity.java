package com.example.customlistview;

import android.app.ComponentCaller;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.customlistview.adapter.ListAdapter;
import com.example.customlistview.controllers.Removable;
import com.example.customlistview.controllers.Updatable;
import com.example.customlistview.databinding.ActivitySecondBinding;
import com.example.customlistview.models.Product;
import com.example.customlistview.utils.MyAlertDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity implements Removable, Updatable {

    private ActivitySecondBinding binding;
    public static List<Product> productList = new ArrayList<>();
    public static final int GALLERY_REQUEST = 123;
    private Uri photoUri = null;
    private Integer item = null;
    private Product product = null;
    private ListAdapter listAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (productList == null) {
            productList = new ArrayList<>();
        }

        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarSecondTB);
        getSupportActionBar().setTitle("МАГАЗИН ПРОДУКТОВ");

        binding.editImageIV.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_REQUEST);
        });

        binding.addBTN.setOnClickListener(v -> {
            String productName = binding.nameProductET.getText().toString();
            String costProduct = binding.costProductET.getText().toString();
            String descriptionProduct = binding.descriptionET.getText().toString();
            String imageProduct = String.valueOf(photoUri);

            product = new Product(productName, costProduct, imageProduct, descriptionProduct);

            productList.add(product);

            listAdapter = new ListAdapter(this, productList);

            binding.listViewLV.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();

            binding.nameProductET.setText("");
            binding.costProductET.setText("");
            binding.descriptionET.setText("");
            binding.editImageIV.setImageResource(R.drawable.ic_stat_name);
        });

        binding.listViewLV.setOnItemClickListener(((parent, view, position, id) -> {
            item = position;
            Product selectedProduct = productList.get(position);
            MyAlertDialog dialog = new MyAlertDialog();
            Bundle args = new Bundle();
            args.putSerializable("product", selectedProduct);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "custom");
        }));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (listAdapter != null) {
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            photoUri = data.getData();
        }
        binding.editImageIV.setImageURI(photoUri);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.back).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.exit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void remove(Product product) {
        productList.remove(product);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void getUpdate(Product product) {
        Intent intent = new Intent(this, DetailsActivity.class);
        //intent.putExtra("product", product);
        intent.putExtra("position", item);
        startActivity(intent);
    }
}