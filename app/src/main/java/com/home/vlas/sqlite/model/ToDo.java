package com.home.vlas.sqlite.model;

public class ToDo {
    int id;
    String note;
    int status;
    String created_at;

    public ToDo() {
    }

    public ToDo(String note, int status) {
        this.note = note;
        this.status = status;
    }

    public ToDo(int id, String note, int status) {
        this.id = id;
        this.note = note;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
