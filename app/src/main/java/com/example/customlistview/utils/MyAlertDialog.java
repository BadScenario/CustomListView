package com.example.customlistview.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.customlistview.controllers.Removable;
import com.example.customlistview.controllers.Updatable;
import com.example.customlistview.models.Product;

public class MyAlertDialog extends DialogFragment {

    private Removable removable;
    private Updatable updatable;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Removable) {
            removable = (Removable) context;
        }

        if (context instanceof Updatable) {
            updatable = (Updatable) context;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Product product = (Product) requireArguments().getSerializable("product");

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        return builder
                .setTitle("Выберите опцию")
                .setPositiveButton("Удалить", ((dialog, which) -> {
                    if (removable != null) {
                        removable.remove(product);
                    }
                }))
                .setNeutralButton("Редактировать", ((dialog, which) -> {
                    if (updatable != null) {
                        updatable.getUpdate(product);
                    }
                }))
                .setNegativeButton("Отмена", null)
                .create();
    }
}
