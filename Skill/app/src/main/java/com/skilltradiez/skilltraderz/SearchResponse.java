package com.skilltradiez.skilltraderz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nweninge on 11/6/15.
 */
public class SearchResponse<T> {
    private Shards _shards;
    class Shards {
        int total;
        int sucessful;
        int failed;
    }
    private Hits hits;
    class Hits {
        int total;
        ArrayList<GetResponse<T>> hits;
    }

    public List<T> getHits() {
        List<T> hitsT = new ArrayList<T>();
        for (GetResponse<T> hit : hits.hits) {
            hitsT.add(hit._source);
        }
        return hitsT;
    }
}
