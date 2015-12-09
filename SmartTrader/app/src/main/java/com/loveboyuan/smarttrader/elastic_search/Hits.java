package com.loveboyuan.smarttrader.elastic_search;

import java.util.List;


public class Hits<T> {
    private int total;
    private float max_score;
    private List<SearchHit<T>> hits;

    public Hits() {}

    public List<SearchHit<T>> getHits() {
        return hits;
    }


    @Override
    public String toString() {
        return "Hits [total=" + total + ", max_score=" + max_score + ", hits="
                + hits + "]";
    }
}