package com.scene.sceneandroiddemo.entity;

import java.io.Serializable;

/**
 * Created by scene on 16/01/26.
 */
public class TypeInfo implements Serializable {
    int id;
    String type;

    public TypeInfo() {
    }

    public TypeInfo(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
