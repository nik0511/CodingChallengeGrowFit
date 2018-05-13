package com.example.ramon_sifer.codingchallengegrowfit.modelclasses;

import java.util.ArrayList;

/**
 * Created by ramon_sifer on 13/5/18.
 */

//comparable interface to sort the list wrt discount price i.e most discount is the last item on list
public class ListArray implements Comparable<ListArray>{
    private String name;
    private String style;
    private String code_color;
    private String regular_price;
    private String actual_price;
    private String image;
    private String discount_percentage;
    private Boolean on_sale;
    private ArrayList <SizeArray> sizes;

    public ListArray(String name, String style, String code_color, String regular_price, String actual_price, String image, String discount_percentage, Boolean on_sale, ArrayList<SizeArray> sizes) {
        this.name = name;
        this.style = style;
        this.code_color = code_color;
        this.regular_price = regular_price;
        this.actual_price = actual_price;
        this.image = image;
        this.discount_percentage = discount_percentage;
        this.on_sale = on_sale;
        this.sizes = sizes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getCode_color() {
        return code_color;
    }

    public void setCode_color(String code_color) {
        this.code_color = code_color;
    }

    public String getRegular_price() {
        return regular_price;
    }

    public void setRegular_price(String regular_price) {
        this.regular_price = regular_price;
    }

    public String getActual_price() {
        return actual_price;
    }

    public void setActual_price(String actual_price) {
        this.actual_price = actual_price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getOn_sale() {
        return on_sale;
    }

    public void setOn_sale(Boolean on_sale) {
        this.on_sale = on_sale;
    }

    public ArrayList<SizeArray> getSizes() {
        return sizes;
    }

    public void setSizes(ArrayList<SizeArray> sizes) {
        this.sizes = sizes;
    }

    public String getDiscount_percentage() {
        return discount_percentage;
    }

    public void setDiscount_percentage(String discount_percentage) {
        this.discount_percentage = discount_percentage;
    }


    //function which compares the arraylist
    @Override
    public int compareTo(ListArray array) {

        int compareDiscount = array.getDiscount_percentage().equals("")?0:Integer.valueOf(array.getDiscount_percentage().split("%")[0]);
        int compareFrom =this.getDiscount_percentage().equals("")?0:Integer.valueOf(this.getDiscount_percentage().split("%")[0]);
        return compareFrom -compareDiscount;
    }
}
