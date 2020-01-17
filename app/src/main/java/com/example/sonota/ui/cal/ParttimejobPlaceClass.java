package com.example.sonota.ui.cal;

public class ParttimejobPlaceClass {
    int id;
    String ParttimejobPlace;

    public ParttimejobPlaceClass(int id, String parttimejobPlace) {
        this.id = id;
        ParttimejobPlace = parttimejobPlace;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParttimejobPlace() {
        return ParttimejobPlace;
    }

    public void setParttimejobPlace(String parttimejobPlace) {
        ParttimejobPlace = parttimejobPlace;
    }
}
