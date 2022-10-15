package com.example.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.miniproject.Model.EventWithData;
import com.example.miniproject.Model.UserModel;
import com.example.miniproject.adapter.AlumniAdapter;
import com.example.miniproject.adapter.PageAdapter;
import com.example.miniproject.adapter.SearchViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Events extends AppCompatActivity {

    private TabLayout tab_layout;
    private ViewPager2 viewPager2;
    private PageAdapter pageAdapter;
    private FloatingActionButton float_btn;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private ArrayList<UserModel> list;
    private SearchViewAdapter searchViewAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_main);

        tab_layout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager_2);
        float_btn = findViewById(R.id.float_btn);
        searchView = findViewById(R.id.searchView);
        pageAdapter = new PageAdapter(this);
        viewPager2.setAdapter(pageAdapter);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return false;
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");
        Query query = databaseReference.orderByChild("userType").equalTo("Alumni");
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        searchViewAdapter = new SearchViewAdapter(Events.this,  list);
        recyclerView.setAdapter(searchViewAdapter);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    list.add(userModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tab_layout.getTabAt(position).select();
            }
        });

        float_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),EditEvent.class));
            }
        });

    }

    private void filterList(String text) {
        ArrayList<UserModel> filtered = new ArrayList<>();
        if (TextUtils.isEmpty(text)){
            recyclerView.setVisibility(View.GONE);
        }else{
            for (UserModel userModel : list){
                if (userModel.getUserName().toLowerCase().contains(text.toLowerCase())){
                    filtered.add(userModel);
                }
                if (filtered.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                }else{
                    recyclerView.setVisibility(View.VISIBLE);
                    searchViewAdapter.setfiltered(filtered);
                }
            }
        }
    }

}
