package de.tobiasreich.healthtracker.data.prescription;

public class MedicinePrescriptionObject {

    public String name = "Item " + ((int) (Math.random() * 100));
    public double value = Math.random();

    public long medicineID; // Id of the medicine
    public long timestamp;  // When to take the medicine
    public int amount;      // How many of the medicine to take
    public String info;     // Info text like "Drink water" or "After lunch"
    public int intervall;   // 0 for don't repeat


    public MedicinePrescriptionObject(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }
}
