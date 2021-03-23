package com.sar.biteit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sar.biteit.databinding.ActivityViewBindingExampleBinding;

public class viewBindingExample extends AppCompatActivity {

    private ActivityViewBindingExampleBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_binding_example);

        binding = ActivityViewBindingExampleBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.signInFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Binding Facebook Button", Toast.LENGTH_SHORT).show();
            }
        });

        binding.signInGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Binding Gmail Button", Toast.LENGTH_SHORT).show();
            }
        });
    }
}