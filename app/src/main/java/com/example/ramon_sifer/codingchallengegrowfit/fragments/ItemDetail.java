package com.example.ramon_sifer.codingchallengegrowfit.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ramon_sifer.codingchallengegrowfit.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */

// picasso is used for image loading

public class ItemDetail extends Fragment {


    private String name;
    private Boolean onSale;
    private String price;
    private String discountPrice;
    private View view;
    private String imageUrl;
    private String sizeValues;

    public ItemDetail() {
        // Required empty public constructor
    }
    public static ItemDetail newInstance(Bundle bundle) {
        ItemDetail fragment = new ItemDetail();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            name = getArguments().getString("nameValue");
            price =getArguments().getString("priceValue");
            onSale = getArguments().getBoolean("onSaleValue");
            imageUrl = getArguments().getString("imageValue");
            discountPrice = getArguments().getString("discountValue");
            sizeValues = getArguments().getString("sizeValue");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_item_detail, container, false);
        initView(); // all the views is intialized in this function
        return view;
    }

    private void initView() {
        TextView nameTextView =(TextView) view.findViewById(R.id.name_view_detail);

        TextView onSaleTextView =(TextView) view.findViewById(R.id.on_sale_view);

        TextView priceTextView =(TextView) view.findViewById(R.id.price_view_detail);

        TextView discountedPriceTextView =(TextView) view.findViewById(R.id.discount_view_detail);

        TextView sizeValuesTextView =(TextView) view.findViewById(R.id.size_view_detail);

        if(onSale){
            onSaleTextView.setVisibility(View.VISIBLE);
            discountedPriceTextView.setText(discountPrice);
        }


        nameTextView.setText(name);
        priceTextView.setText(price);
        sizeValuesTextView.setText(sizeValues.substring(0,sizeValues.length()-1));
        ImageView imageViewDetil = (ImageView) view.findViewById(R.id.image_detail_view);
        if(imageUrl!="")
            Picasso.with(getContext())
                    .load(imageUrl)
                    .error(getContext().getDrawable(R.drawable.default_image))
                    .into(imageViewDetil);
    }

}
