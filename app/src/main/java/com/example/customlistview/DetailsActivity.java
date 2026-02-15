package com.example.customlistview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.customlistview.databinding.ActivityDetailsBinding;
import com.example.customlistview.models.Product;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private ActivityDetailsBinding binding;
    public static final int GALLERY_REQUEST_UPDATE = 321;
    private Uri photoUriUpdate = null;
    private String imageProductUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarDetailsTB);
        getSupportActionBar().setTitle("МАГАЗИН ПРОДУКТОВ");

        Integer item = getIntent().getIntExtra("position", 0);
        Product product = SecondActivity.productList.get(item);


        String name = product.getName();
        String price = product.getPrice();
        String description = product.getDescription();
        Uri photo = Uri.parse(product.getPhoto());

        binding.nameProductET.setText(name);
        binding.costProductET.setText(price);
        binding.descriptionET.setText(description);
        binding.editImageIV.setImageURI(photo);

        binding.editImageIV.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_REQUEST_UPDATE);
        });

        binding.saveBTN.setOnClickListener(v -> {

            if (photoUriUpdate != null) {
                imageProductUpdate = String.valueOf(photoUriUpdate);
            } else {
                imageProductUpdate = product.getPhoto();
            }

            Product newProduct = new Product(binding.nameProductET.getText().toString(),
                    binding.costProductET.getText().toString(),
                    imageProductUpdate,
                    binding.descriptionET.getText().toString());

            if (item != null) {
                swap(item, newProduct, SecondActivity.productList);
            }

            finish();
        });
    }

    private void swap(int item, Product product, List<Product> products) {
        products.add(item + 1, product);
        products.remove(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_UPDATE && resultCode == RESULT_OK) {
            photoUriUpdate = data.getData();
        }
        binding.editImageIV.setImageURI(photoUriUpdate);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.exit) {
            finishAffinity();
            Toast.makeText(this, "Программа завершена", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.back) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}