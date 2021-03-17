package com.sar.biteit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

public class home extends AppCompatActivity {
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mDialog = new ProgressDialog(home.this);
        mDialog.setMessage("Please wait...");
        mDialog.show();

        initRecyclerViewMenu();

    }

    private void initRecyclerViewMenu(){
        RecyclerView recyclerViewBuyerPosts = findViewById(R.id.recyclerViewMenu);
        LinearLayoutManager layoutManager5 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewBuyerPosts.setLayoutManager(layoutManager5);

        recyclerViewAdapterMenu adapter = new recyclerViewAdapterMenu(this,null);
        recyclerViewBuyerPosts.setItemAnimator( new DefaultItemAnimator());
        recyclerViewBuyerPosts.setAdapter(adapter);
        mDialog.dismiss();
    }
}