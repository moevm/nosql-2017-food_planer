package com.letiproject.foodplanner.app.domain;


public class Ingredient {

    private String name;
    private int amount;
    private String units;

    public static class CommonIngredient {
        private int amount;
        private String units;

        public CommonIngredient(int amount, String units) {
            this.amount = amount;
            this.units = units;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getUnits() {
            return units;
        }

        public void setUnits(String units) {
            this.units = units;
        }
    }

    public Ingredient(String name, int amount, String units) {
        this.name = name;
        this.amount = amount;
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
