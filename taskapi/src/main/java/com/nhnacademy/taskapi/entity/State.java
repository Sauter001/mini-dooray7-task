package com.nhnacademy.taskapi.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum State {
    ACTIVE, INACTIVE, COMPLETE;

    @JsonCreator
    public static State fromString(String str){
        for (State value : State.values()) {
            if (value.name().equalsIgnoreCase(str)) {
                return value;
            }
        }
        //default
        return ACTIVE;
    }

    @JsonValue
    public String toJson(){
        return this.name().toLowerCase();
    }
}
