package com.jvetter2.movieclue;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoviePojo {
    @JsonProperty("title")
    private String title;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("backdrop_path")
    private String backdrop_path;
//    @JsonProperty("vote_count")
//    private String voteCount;
//    @JsonProperty("video")
//    private String video;
//    @JsonProperty("poster_path")
//    private String posterPath;



    //
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



}
