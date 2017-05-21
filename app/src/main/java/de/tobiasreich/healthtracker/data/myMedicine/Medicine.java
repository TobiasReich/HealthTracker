package de.tobiasreich.healthtracker.data.myMedicine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by T on 21.05.2017. */
public class Medicine {

    public int id;
    public String name;
    public String description;

    // constructors
    public Medicine() {
    }

    public Medicine(String name) {
        this.name = name;
    }

    public Medicine(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // setter
    public void setId(int id) {
        this.id = id;
    }

    public void setMedicineName(String name) {
        this.name = name;
    }

    public void setMedicineDescription(String description) {
        this.description = description;
    }

    // getter
    public int getId() {
        return this.id;
    }

    public String getMedicineName() {
        return this.name;
    }

    public String getDescription() {
        return description;
    }

    public static List<Medicine> getRandomMedicine(){
        ArrayList<Medicine> meds = new ArrayList<>();

        for (int i = 0; i< 100; i++){
            Medicine m = new Medicine();
            m.name = "Medicine " + i;
            m.description = "Tiene partes de " + Math.random();
            meds.add(m);
        }

        return meds;
    }

}
