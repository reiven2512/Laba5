package com.example.prak_3;

import android.graphics.Bitmap;

public class Info {
    private String name;
    private String description;
    private int id;
    private int quantity;
    private boolean switcher;
    public Info(String name, String description, int id, int quantity)
    {
        this.name = name;
        this.description = description;
        this.id = id;
        this.quantity = quantity;
        this.switcher = true;
    }

    public String getName()
    {
        return name;
    }
    public String getDescription()
    {
        return description;
    }
    public int getId()
    {
        return id;
    }
    public int getQuantity() { return quantity; }
    public boolean getSwitcher() { return switcher; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setSwitcher(boolean switcher) { this.switcher = switcher; }
}
