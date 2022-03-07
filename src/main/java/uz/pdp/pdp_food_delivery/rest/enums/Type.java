package uz.pdp.pdp_food_delivery.rest.enums;

import lombok.Getter;

@Getter
public enum Type {

    COMPLAIN //normmas degani esli cho
    , ADVICE // maslahat ma'nosida
    , THANKS;

    public static Type getByName(String typ) {
        for (Type type : values()) {
            if (type.name().equalsIgnoreCase(typ)) return type;
        }
        return ADVICE;
    }

}
