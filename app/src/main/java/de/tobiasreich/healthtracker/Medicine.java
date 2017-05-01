package de.tobiasreich.healthtracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by T on 01.05.2017.
 */

public class Medicine {

    public String title;
    public String description;




    public static List<Medicine> getRandomMedicine(){
        ArrayList<Medicine> meds = new ArrayList<>();

        for (int i = 0; i< 100; i++){
            Medicine m = new Medicine();
            m.title = "Medicine " + i;
            m.description = "Tiene partes de " + Math.random();
            meds.add(m);
        }

        return meds;
    }

}
