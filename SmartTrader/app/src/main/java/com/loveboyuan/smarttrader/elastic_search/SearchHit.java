package com.loveboyuan.smarttrader.elastic_search;


public class SearchHit<T> {
    private String _index;
    private String _type;
    private String _id;
    private String _version;
    private boolean found;
    private T _source;

    public SearchHit() {

    }

    public T getSource() {
        return _source;
    }


    @Override
    public String toString() {
        return "SimpleElasticSearchResponse [_index=" + _index + ", _type="
                + _type + ", _id=" + _id + ", _version=" + _version
                + ", found=" + found + ", _source=" + _source + "]";
    }


}
