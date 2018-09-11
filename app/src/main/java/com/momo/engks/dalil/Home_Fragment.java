package com.momo.engks.dalil;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.roger.catloadinglibrary.CatLoadingView;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Fragment extends Fragment {

    private FirebaseRecyclerAdapter<Model_List_Item, ViewHolder> adapter;
    private RecyclerView recyclerView;
    private Context mContext;

    public Home_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_, container, false);
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#1976D2"));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "جاري تحديث البيانات..", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart() {
        super.onStart();
        try {
            final CatLoadingView catLoadingView = new CatLoadingView();
            catLoadingView.setCanceledOnTouchOutside(false);
            catLoadingView.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "");

            Query query = FirebaseDatabase.getInstance().getReference().child("Data").limitToFirst(20);

            FirebaseRecyclerOptions<Model_List_Item> options = new FirebaseRecyclerOptions.Builder<Model_List_Item>()
                    .setQuery(query, Model_List_Item.class).build();


            adapter = new FirebaseRecyclerAdapter<Model_List_Item, ViewHolder>
                    (

                            options
                    ) {
                @Override
                protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Model_List_Item model) {
                    catLoadingView.dismiss();
                    holder.setDetails(getActivity(), model.getTitle(), model.getDesc(), model.getImage());

                }

                @NonNull
                @Override
                public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

                    return new ViewHolder(view);
                }
            };

            recyclerView.setAdapter(adapter);

            adapter.startListening();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search");
        TextView searchText = (TextView)
                searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        Typeface myCustomFont = Typeface.createFromAsset(getActivity().getAssets(), "CairoBold.ttf");
        searchText.setTypeface(myCustomFont);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                firebasesearch(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebasesearch(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void firebasesearch(String searchtext) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("جارى البحث..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Data");
        Query querysearched = databaseReference.orderByChild("title").startAt(searchtext).endAt(searchtext + "\uf8ff");
        FirebaseRecyclerOptions<Model_List_Item> options = new FirebaseRecyclerOptions.Builder<Model_List_Item>()
                .setQuery(querysearched, Model_List_Item.class).build();


        adapter = new FirebaseRecyclerAdapter<Model_List_Item, ViewHolder>
                (

                        options
                ) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Model_List_Item model) {
                progressDialog.dismiss();
                holder.setDetails(getActivity(), model.getTitle(), model.getDesc(), model.getImage());

            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

                return new ViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }


}
