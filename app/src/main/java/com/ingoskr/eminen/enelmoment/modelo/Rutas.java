package com.ingoskr.eminen.enelmoment.modelo;

/**
 * Created by emine on 25/08/2016.
 */
public class Rutas {

    private String id;
    private String color;

    public Rutas(String id, String color) {
        this.id = id;
        this.color = color;
    }





    @Override
    public String toString() {
        return "Rutas{" +
                "id='" + id + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


}
