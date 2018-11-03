package com.udacity.popularmovies.net.contracts.TO;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.udacity.popularmovies.models.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains Video information from webservice.
 * Contained on {@link PageResultVideosTO}.
 *
 * @author Erick Prieto
 * @since 2018
 */
public class VideoTO implements ModelTO, Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("iso_639_1")
    @Expose
    private String languageISOCode;

    @SerializedName("iso_3166_1")
    @Expose
    private String countryISOCode;

    @SerializedName("key")
    @Expose
    private String resourceKey;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("site")
    @Expose
    private String siteProviderName;

    @SerializedName("size")
    @Expose
    private Integer size;

    @SerializedName("type")
    @Expose
    private String type;

    public final static Parcelable.Creator<VideoTO> CREATOR = new Creator<VideoTO>() {


        @SuppressWarnings({"unchecked"})
        public VideoTO createFromParcel(Parcel in) {
            return new VideoTO(in);
        }

        public VideoTO[] newArray(int size) {
            return (new VideoTO[size]);
        }

    }
            ;

    protected VideoTO(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.languageISOCode = ((String) in.readValue((String.class.getClassLoader())));
        this.countryISOCode = ((String) in.readValue((String.class.getClassLoader())));
        this.resourceKey = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.siteProviderName = ((String) in.readValue((String.class.getClassLoader())));
        this.size = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
    }

    public VideoTO() {
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
        if ((other instanceof VideoTO) == false) {
            return false;
        }
        VideoTO rhs = ((VideoTO) other);
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

    public Video toModel() {
        return new Video(this.id
                , this.languageISOCode
                , this.countryISOCode
                , this.resourceKey
                , this.name
                , this.siteProviderName
                , this.size
                , this.type);
    }

    public static List<Video> toListModel(List<VideoTO> tos) {
        if(tos == null) { return null; }
        List<Video> result = new ArrayList<>();
        for (VideoTO to : tos) {
            result.add(to.toModel());
        }
        return result;
    }
}