package com.acun.submission4.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie implements Parcelable {

    private Integer id;
    private String title;
    private String release;
    private String overview;
    private String poster;
    private String score;
    private String language;
    private String popularity;

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.release = in.readString();
        this.overview = in.readString();
        this.poster = in.readString();
        this.score = in.readString();
        this.language = in.readString();
        this.popularity = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


    public Movie() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
       }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public Movie (JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.title = object.getString("title");
            this.release = object.getString("release_date");
            this.overview = object.getString("overview");
            String poster_path = object.getString("poster_path");
            this.poster = "https://image.tmdb.org/t/p/w185"+poster_path;
            this.score = object.getString("vote_average");
            this.language = object.getString("original_language");
            this.popularity = object.getString("popularity");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.title);
        parcel.writeString(this.release);
        parcel.writeString(this.overview);
        parcel.writeString(this.poster);
        parcel.writeString(this.score);
        parcel.writeString(this.language);
        parcel.writeString(this.popularity);
    }
}
