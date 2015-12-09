package com.loveboyuan.smarttrader.elastic_search;



public class SearchResponse<T> {

    private int took;
    private boolean timed_out;
    private Shard _shards;
    private Hits<T> hits;

    public SearchResponse() {}

    public Hits<T> getHits() {
        return hits;
    }

}


class Shard {
    private int total;
    private int successful;
    private int failed;

    public Shard() {}


}