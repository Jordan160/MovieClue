package com.jvetter2.movieclue;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

//@JsonRootName("results")
public class MovieResponse {
    @JsonProperty("results")
    public List<MoviePojo> results;

    public List<MoviePojo> getResults() {
        return results;
    }

    public void setResults(List<MoviePojo> results) {
        this.results = results;
    }
}



//
//                    "popularity": 97.861,
//                    "vote_count": 84,
//                    "video": false,
//                    "poster_path": "\/5MSDwUcqnGodFTvtlLiLKK0XKS.jpg",
//                    "id": 521034,
//                    "adult": false,
//                    "backdrop_path": "\/8PK4X8U3C79ilzIjNTkTgjmc4js.jpg",
//                    "original_language": "en",
//                    "original_title": "The Secret Garden",
//                    "genre_ids": [
//                    18,
//                    14,
//                    10751
//                    ],
//                    "title": "The Secret Garden",
//                    "vote_average": 7.4,
//                    "overview": "Mary Lennox is born in India to wealthy British parents who never wanted her. When her parents suddenly die, she is sent back to England to live with her uncle. She meets her sickly cousin, and the two children find a wondrous secret garden lost in the grounds of Misselthwaite Manor.",
//                    "release_date": "2020-07-08"
