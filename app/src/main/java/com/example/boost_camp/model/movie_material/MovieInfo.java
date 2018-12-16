package com.example.boost_camp.model.movie_material;


import android.databinding.Observable;
import android.databinding.ObservableInt;

import com.example.boost_camp.model.movie_material.movie_material.Items;

import java.util.ArrayList;

/**
 * Created by park on 2018-12-07.
 */

public class MovieInfo {

    //public final ObservableInt observableInt=new ObservableInt();

    private ArrayList<Items>items;

    private int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<Items> getItems() {
        return items;
    }

    public void setItems(ArrayList<Items> items) {
        this.items = items;
    }

}
