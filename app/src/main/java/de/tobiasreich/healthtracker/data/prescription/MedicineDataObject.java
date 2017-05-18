package de.tobiasreich.healthtracker.data.prescription;

public class MedicineDataObject {

    public String name = "Item " + ((int) (Math.random() * 100));

    public double value = Math.random();

    public MedicineDataObject(String name, double value) {
        this.name = name;
        this.value = value;
    }
}
