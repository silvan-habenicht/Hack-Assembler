package de.nand2tetris;

import java.util.Hashtable;

/**
 * Die SymbolTable-Klasse ermöglicht die rasche Übersetzung von Programmvariablen in ihre entsprechende Registerposition
 * und von Sprungstellen-Verweisen auf ihre entsprechende Programmzeile. Der Konstruktor fügt zu Beginn einige
 * vordefinierte Variablen in die Tabelle hinzu, damit sie auf Anhieb berücksichtigt werden können.
 * @author Silvan Habenicht
 * */

public class SymbolTable {

    private Hashtable <String, Integer> hashtable = new Hashtable<>();

    SymbolTable(){
        hashtable.put("SP",0);
        hashtable.put("LCL",1);
        hashtable.put("ARG",2);
        hashtable.put("THIS",3);
        hashtable.put("THAT",4);
        hashtable.put("SCREEN",16384);
        hashtable.put("KBD",24576);
        hashtable.put("R0",0);
        hashtable.put("R1",1);
        hashtable.put("R2",2);
        hashtable.put("R3",3);
        hashtable.put("R4",4);
        hashtable.put("R5",5);
        hashtable.put("R6",6);
        hashtable.put("R7",7);
        hashtable.put("R8",8);
        hashtable.put("R9",9);
        hashtable.put("R10",10);
        hashtable.put("R11",11);
        hashtable.put("R12",12);
        hashtable.put("R13",13);
        hashtable.put("R14",14);
        hashtable.put("R15",15);
    }

    public void addEntry(String symbol, int address){
        hashtable.put(symbol,address);
    }

    public boolean contains(String symbol){
        return hashtable.containsKey(symbol);
    }

    public int GetAddress(String symbol){
        return hashtable.get(symbol);
    }

}
