package com.home.vlas.sqlite.Helper;

public class Tag {
    int id;
    String tag_name;

    public Tag() {}

    public Tag(String tag_name) {
        this.tag_name = tag_name;
    }

    public Tag(int id, String tag_name) {
        this.id = id;
        this.tag_name = tag_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTagName(String tag_name) {
        this.tag_name = tag_name;
    }

    public int getId() {
        return this.id;
    }

    public String getTagName() {
        return this.tag_name;
    }
}
