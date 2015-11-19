package com.skilltradiez.skilltraderz;

import java.util.List;

/**
 * Created by nweninge on 11/6/15.
 */
public class SkillSearchResponse {
    private int took;
    private boolean timed_out;
    private Shards _shards;
    class Shards {
        int total;
        int sucessful;
        int failed;
    }
    public Hits hits;
    class Hits {
        int total;
        float max_score;
        List<Hit> hits;
    }
    class Hit {
        String _index;
        String _type;
        String _id;
        float _score;
        Skill _source;
    }
}
