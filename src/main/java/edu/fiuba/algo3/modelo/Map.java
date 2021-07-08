package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.HashMap;

public class Map {
    private HashMap<String, ArrayList<String>> countries;
    public Map(){
        countries = new HashMap<String, ArrayList<String>>();
        ArrayList<String> borderingCanada = new ArrayList<>();
        borderingCanada.add("Terranova");
        borderingCanada.add("Oregon");
        borderingCanada.add("New York");
        borderingCanada.add("Island");
        countries.put("Canada", borderingCanada);
        ArrayList<String> borderingAlaska = new ArrayList<>();
        borderingAlaska.add("Oregon");
        countries.put("Alaska", borderingAlaska);
        ArrayList<String> borderingOregon = new ArrayList<>();
        borderingOregon.add("Alaska");
        borderingOregon.add("Canada");
        borderingOregon.add("New York");
        borderingOregon.add("Alaska");
        countries.put("Oregon", borderingOregon);
        ArrayList<String> borderingTerranova = new ArrayList<>();
        borderingTerranova.add("New York");
        countries.put("Terranova", borderingTerranova);
        ArrayList<String> borderingCalifornia = new ArrayList<>();
        borderingCalifornia.add("New York");
        borderingCalifornia.add("Oregon");
        borderingCalifornia.add("Mexico");
        countries.put("California", borderingCalifornia);
        ArrayList<String> borderingNewYork = new ArrayList<>();
        borderingNewYork.add("Terranova");
        borderingNewYork.add("Canada");
        borderingNewYork.add("Oregon");
        borderingNewYork.add("California");
        borderingNewYork.add("Island");
        countries.put("New York", borderingNewYork);
        ArrayList<String> borderingIsland = new ArrayList<>();
        borderingIsland.add("Canada");
        borderingIsland.add("New York");
        countries.put("Island", borderingIsland);
        ArrayList<String> borderingMexico = new ArrayList<>();
        borderingMexico.add("California");
        countries.put("Mexico", borderingMexico);
        ArrayList<String> borderingChina = new ArrayList<>();
        borderingChina.add("Mexico");
        borderingChina.add("France");
        countries.put("China", borderingChina);
        ArrayList<String> borderingFrance = new ArrayList<>();
        borderingFrance.add("China");
        countries.put("France", borderingFrance);
    }

    public boolean validateBorderingCountry(String attackingCountry, String defendingCountry) {
        return countries.get(attackingCountry).contains(defendingCountry);
    }
}

