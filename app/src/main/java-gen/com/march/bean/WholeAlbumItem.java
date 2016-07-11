package com.march.bean;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "WHOLE_ALBUM_ITEM".
 */
public class WholeAlbumItem extends Album  {

    private String album_link;
    private String key_words;
    private String album_cover;
    private String album_desc;
    private String time_stamp;
    private Boolean isFavorite;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public WholeAlbumItem() {
    }

    public WholeAlbumItem(String album_link, String key_words, String album_cover, String album_desc, String time_stamp, Boolean isFavorite) {
        this.album_link = album_link;
        this.key_words = key_words;
        this.album_cover = album_cover;
        this.album_desc = album_desc;
        this.time_stamp = time_stamp;
        this.isFavorite = isFavorite;
    }

    public String getAlbum_link() {
        return album_link;
    }

    public void setAlbum_link(String album_link) {
        this.album_link = album_link;
    }

    public String getKey_words() {
        return key_words;
    }

    public void setKey_words(String key_words) {
        this.key_words = key_words;
    }

    public String getAlbum_cover() {
        return album_cover;
    }

    public void setAlbum_cover(String album_cover) {
        this.album_cover = album_cover;
    }

    public String getAlbum_desc() {
        return album_desc;
    }

    public void setAlbum_desc(String album_desc) {
        this.album_desc = album_desc;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
