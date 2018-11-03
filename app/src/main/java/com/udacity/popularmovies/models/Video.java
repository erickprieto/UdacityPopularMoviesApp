package com.udacity.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Video implements Parcelable {

    public static final Parcelable.Creator<Video> CREATOR = new Creator<Video>() {

        @SuppressWarnings({"unchecked"})
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        public Video[] newArray(int size) {
            return (new Video[size]);
        }

    };

    private static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    private static final String YOUTUBE_APP_URL  = "vnd.youtube:";

    private String id;

    private String languageISOCode;

    private String countryISOCode;

    private String resourceKey;

    private String name;

    private String siteProviderName;

    private Integer size;

    private String type;

    protected Video(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.languageISOCode = ((String) in.readValue((String.class.getClassLoader())));
        this.countryISOCode = ((String) in.readValue((String.class.getClassLoader())));
        this.resourceKey = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.siteProviderName = ((String) in.readValue((String.class.getClassLoader())));
        this.size = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Video() {
    }

    public Video(String id
            , String languageISOCode
            , String countryISOCode
            , String resourceKey
            , String name
            , String siteProviderName
            , Integer size
            , String type) {
        this.id = id;
        this.languageISOCode = languageISOCode;
        this.countryISOCode = countryISOCode;
        this.resourceKey = resourceKey;
        this.name = name;
        this.siteProviderName = siteProviderName;
        this.size = size;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguageISOCode() {
        return languageISOCode;
    }

    public void setLanguageISOCode(String languageISOCode) {
        this.languageISOCode = languageISOCode;
    }

    public String getCountryISOCode() {
        return countryISOCode;
    }

    public void setCountryISOCode(String countryISOCode) {
        this.countryISOCode = countryISOCode;
    }

    public String getResourceKey() {
        return resourceKey;
    }

    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSiteProviderName() {
        return siteProviderName;
    }

    public void setSiteProviderName(String siteProviderName) {
        this.siteProviderName = siteProviderName;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return resourceKey;
    }

    public String buildWebYoutubeUrl() { return YOUTUBE_BASE_URL + resourceKey; }
    public String buildAppYoutubeUrl() { return YOUTUBE_APP_URL + resourceKey; }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("languageISOCode", languageISOCode)
                .append("countryISOCode", countryISOCode)
                .append("resourceKey", resourceKey)
                .append("name", name)
                .append("siteProviderName", siteProviderName)
                .append("size", size)
                .append("type", type)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(siteProviderName)
                .append(languageISOCode)
                .append(id)
                .append(countryISOCode)
                .append(name)
                .append(type)
                .append(resourceKey)
                .append(size)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Video) == false) {
            return false;
        }
        Video rhs = ((Video) other);
        return new EqualsBuilder().append(siteProviderName, rhs.siteProviderName)
                .append(languageISOCode, rhs.languageISOCode)
                .append(id, rhs.id)
                .append(countryISOCode, rhs.countryISOCode)
                .append(name, rhs.name)
                .append(type, rhs.type)
                .append(resourceKey, rhs.resourceKey)
                .append(size, rhs.size)
                .isEquals();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(languageISOCode);
        dest.writeValue(countryISOCode);
        dest.writeValue(resourceKey);
        dest.writeValue(name);
        dest.writeValue(siteProviderName);
        dest.writeValue(size);
        dest.writeValue(type);
    }

    public int describeContents() {
        return 0;
    }


}
