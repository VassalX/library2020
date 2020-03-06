package com.library2020.model;

import java.io.Serializable;

public enum OrderStatus implements Serializable {
    ON_REVIEW,
    CANCELED_BY_USER,
    CANCELED_BY_ADMIN,
    ACTIVE,
    RETURNED;

    public String toString(){
        switch(this){
            case ON_REVIEW :
                return "ON_REVIEW";
            case CANCELED_BY_USER :
                return "CANCELED_BY_USER";
            case CANCELED_BY_ADMIN :
                return "CANCELED_BY_ADMIN";
            case ACTIVE :
                return "ACTIVE";
            case RETURNED :
                return "RETURNED";
        }
        return null;
    }

    public static OrderStatus fromString(String value){
        if(value.equalsIgnoreCase(ON_REVIEW.toString()))
            return OrderStatus.ON_REVIEW;
        else if(value.equalsIgnoreCase(CANCELED_BY_USER.toString()))
            return OrderStatus.CANCELED_BY_USER;
        else if(value.equalsIgnoreCase(CANCELED_BY_ADMIN.toString()))
            return OrderStatus.CANCELED_BY_ADMIN;
        else if(value.equalsIgnoreCase(ACTIVE.toString()))
            return OrderStatus.ACTIVE;
        else if(value.equalsIgnoreCase(RETURNED.toString()))
            return OrderStatus.RETURNED;
        else
            return null;
    }
}