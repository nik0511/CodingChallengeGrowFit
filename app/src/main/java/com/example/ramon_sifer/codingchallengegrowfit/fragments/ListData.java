package com.example.ramon_sifer.codingchallengegrowfit.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ramon_sifer.codingchallengegrowfit.R;
import com.example.ramon_sifer.codingchallengegrowfit.customclasses.ApiClient;
import com.example.ramon_sifer.codingchallengegrowfit.customclasses.NetworkStatus;
import com.example.ramon_sifer.codingchallengegrowfit.interfaces.ApiCall;
import com.example.ramon_sifer.codingchallengegrowfit.modelclasses.ListArray;
import com.example.ramon_sifer.codingchallengegrowfit.modelclasses.ListDataResponse;
import com.example.ramon_sifer.codingchallengegrowfit.modelclasses.SizeArray;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


//picasso is used for image loading

public class ListData extends Fragment {

    //instantiation
    private View view;
    private NetworkStatus network_status;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ListAdapter adapter;
    private EditText searchByNameText;
    private ImageView searchButton;
    private ArrayList<ListArray> listDataResponses;
    private CheckBox checkBoxFilter;
    private ProgressBar dialog;
    private ArrayList<ListArray> filteredDataByDiscount;

    // end of instantiation

    public ListData() {
        // Required empty public constructor
    }

    public static ListData newInstance() {
        ListData fragment = new ListData();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list_data, container, false);

        initView();// all the views are initialized in this function
        return view;
    }

    private void initView() {

        recyclerView = (RecyclerView) view.findViewById(R.id.list_view);
        searchByNameText = (EditText) view.findViewById(R.id.search_text);
        searchButton = (ImageView) view.findViewById(R.id.search_button);
        checkBoxFilter = (CheckBox) view.findViewById(R.id.checkbox);
        dialog = (ProgressBar) view.findViewById(R.id.progress);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // it checks the connectivity of the app to the internet
        network_status = new NetworkStatus(getContext());

        //client used for API requests i.e Retrofit
        Retrofit retrofit = ApiClient.getClient();
        ApiCall call = retrofit.create(ApiCall.class);
        dialog.setVisibility(View.VISIBLE);
        if (network_status.isConnectingToInternet()) {
            Call<ListDataResponse> data = call.getData();
            data.enqueue(new Callback<ListDataResponse>() {
                @Override
                public void onResponse(Call<ListDataResponse> call, Response<ListDataResponse> response) {
                    listDataResponses = response.body().products;
                    Collections.sort(listDataResponses);
                    adapter = new ListAdapter(listDataResponses);
                    recyclerView.setAdapter(adapter);
                    dialog.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ListDataResponse> call, Throwable t) {
                    dialog.setVisibility(View.GONE);
                    Log.d("error", t.getMessage());
                }
            });
        }

        // searching is done by on text change listener with the help of this function
        searchByNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String searchValue = s.toString();
                ArrayList<ListArray> filteredData = new ArrayList<ListArray>();
                ArrayList<ListArray> dataResponses;

                // this if checks if the shown list is filtered by discounts or not and searches in that list itself
                if(checkBoxFilter.isChecked()){
                    dataResponses = filteredDataByDiscount;
                }else{
                    dataResponses = listDataResponses;
                }
                if (dataResponses != null) {
                    for (int i = 0; i < dataResponses.size(); i++) {
                        boolean searchValueMatches = dataResponses.get(i).getName().contains(searchValue) || dataResponses.get(i).getName().contains(searchValue.toUpperCase());
                        if (searchValueMatches)
                            filteredData.add(dataResponses.get(i));
                    }
                    //sorts the data in ascending order of discount
                    Collections.sort(filteredData);
                    adapter = new ListAdapter(filteredData);
                    recyclerView.setAdapter(adapter);
                }

            }
        });

        // checkbox filter is to show only the discounted products
        checkBoxFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxFilter.isChecked()) {
                    filteredDataByDiscount = new ArrayList<ListArray>();
                    for (int i = 0; i < listDataResponses.size(); i++) {
                        if (listDataResponses.get(i).getOn_sale())
                            filteredDataByDiscount.add(listDataResponses.get(i));
                    }
                    Collections.sort(filteredDataByDiscount);
                    adapter = new ListAdapter(filteredDataByDiscount);
                    recyclerView.setAdapter(adapter);

                } else {
                    Collections.sort(listDataResponses);
                    adapter = new ListAdapter(listDataResponses);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        //the following part of the data is commented as it searches with the help of button and can be activated if needed

//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String searchValue = searchByNameText.getText().toString();
//                ArrayList<ListArray> filteredData = new ArrayList<ListArray>();
//                for(int i =0;i<listDataResponses.size();i++){
//                    boolean a = listDataResponses.get(i).getName().contains(searchValue)||listDataResponses.get(i).getName().contains(searchValue.toUpperCase());
//                    if(a)
//                        filteredData.add(listDataResponses.get(i));
//                }
//                adapter = new ListAdapter(filteredData);
//                recyclerView.setAdapter(adapter);
//            }
//        });

    }


    // adapter for the recycler view which builds the list
    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

        private final ArrayList<ListArray> myList;

        ListAdapter(ArrayList<ListArray> myList) {
            this.myList = myList;
        }

        private View listView;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_items, parent, false);
            return new ViewHolder(listView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setContactInfo(myList.get(position));
        }


        @Override
        public int getItemCount() {
            return myList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView name;
            private TextView price;
            private ImageView image;


            public ViewHolder(final View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                price = (TextView) itemView.findViewById(R.id.price);
                image = (ImageView) itemView.findViewById(R.id.list_image);

            }

            public void setContactInfo(final ListArray listData) {

                name.setText(listData.getName());
                price.setText(listData.getActual_price());
                if (listData.getImage() != "")
                    Picasso.with(getContext())
                            .load(listData.getImage())
                            .error(getContext().getDrawable(R.drawable.default_image))
                            .into(image);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //gets the position of the clicked child item
                        final int position = recyclerView.getChildAdapterPosition(itemView);
                        ArrayList<SizeArray> sizes = adapter.myList.get(position).getSizes();
                        String sizeFinalValue = "";

                        for (int i = 0; i < sizes.size(); i++) {
                            if (sizes.get(i).getAvailable())
                                sizeFinalValue = sizeFinalValue + sizes.get(i).getSize() + ",";
                        }

                        //bundle is used to transfer the item's data to the detail fragment so that the request is not done again
                        Bundle bundle = new Bundle();
                        bundle.putString("nameValue", adapter.myList.get(position).getName());
                        bundle.putString("discountValue", adapter.myList.get(position).getActual_price());
                        bundle.putString("imageValue", adapter.myList.get(position).getImage());
                        bundle.putBoolean("onSaleValue", adapter.myList.get(position).getOn_sale());
                        bundle.putString("priceValue", adapter.myList.get(position).getRegular_price());
                        bundle.putString("sizeValue", sizeFinalValue);

                        android.support.v4.app.FragmentManager manager = getFragmentManager();
                        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();

                        Fragment listFragment = manager.findFragmentByTag("ListFragment");
                        if (listFragment != null) {
                            transaction.hide(listFragment);
                            transaction.add(R.id.fragment_switch, ItemDetail.newInstance(bundle)).addToBackStack("mystack");
                            transaction.setTransition(transaction.TRANSIT_FRAGMENT_OPEN);
                            transaction.commit();

                        }
                    }
                });
            }
        }
    }
}
