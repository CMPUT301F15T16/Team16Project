package com.loveboyuan.smarttrader.elastic_search;


public class SimpleSearchCommand {
    private SimpleSearchQuery query;

    public SimpleSearchCommand(String query) {
        super();
        this.query = new SimpleSearchQuery(query);
    }


    static class SimpleSearchQuery {
        private SimpleSearchQueryString queryString;

        public SimpleSearchQuery(String query) {
            super();
            this.queryString = new SimpleSearchQueryString(query);
        }


        static class SimpleSearchQueryString {
            private String query;

            public SimpleSearchQueryString(String query) {
                super();
                this.query = query;
            }

        }
    }
}
