package com.example.myfitnesslad;

public class Meal {

    private int calories;
    private int carbs;
    private int fat;
    private int protein;

    public Meal (int calories, int carbs, int fat, int protein){
        this.calories = calories;
        this.carbs = carbs;
        this.fat = fat;
        this.protein = protein;
    }

    // Calories getters and setters
    public void setCalories(int calories2){ this.calories = calories2; }
    public int getCalories(){ return this.calories; }


    // Carbs getters and setters
    public void setCarbs(int carbs2){
        this.carbs = carbs2;
    }
    public int getCarbs(){
        return this.carbs;
    }

    // Fat getters and setters
    public void setFat(int fat2){
        this.fat = fat2;
    }
    public int getFat(){
        return this.fat;
    }

    // Protein getters and setters
    public void setProtein(int protein2){
        this.protein = protein2;
    }
    public int getProtein(){
        return this.protein;
    }

    //Calorie Calculation from carbs, fats and protein (in grams)
    public int calculateCalories() {
        return (carbs*4)+(protein*4)+(fat*9);
    }

    //TODO: when person class is implemented have this function have a gender parameter
    public boolean ocCalories(int totalCalories)
    {
        if (totalCalories > 2250){
            return true;
        }
        return false;
    }
    public boolean ocCarbs(int totalCarbs)
    {
        if (totalCarbs > 350){
            return true;
        }
        return false;
    }
    public boolean ocFats(int totalFats)
    {
        if (totalFats > 80){
            return true;
        }
        return false;
    }
    public boolean ocProtein(int totalProtein)
    {
        if (totalProtein > 60){
            return true;
        }
        return false;
    }
}
