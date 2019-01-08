package de.nand2tetris;

import java.io.*;
import java.util.ArrayList;

import static de.nand2tetris.CommandType.*;

/**
 * Die Parser-Klasse übernimmt die wesentliche Arbeit des Assemblers. Sie liest eine ihr übergebene Datei aus, entfernt
 * alle Kommentare und Leerzeichen und sammelt alle relevanten Programmzeilen in einer ArrayList. Diese Liste wird
 * anschließend zwei weitere Male durchlaufen, um Programmsprünge und Variablen berücksichtigen zu können. Sie werden
 * dabei einer Symboltabelle hinzugefügt.
 * @author Silvan Habenicht
 * */

public class Parser {

    private ArrayList<String> list = new ArrayList<>();  // Liste mit den Programmcode-Zeilen
    private int currentPosition = 0;                     // Aktuelle Programmcode-Zeile
    private String currentCommand;                       // Aktuelle Programmcode-Anweisung
    private SymbolTable symbolTable = new SymbolTable(); // Symbol-Tabelle für Variablen/Sprüng
    private int instructionCounter = 0;                  // Anzahl der Anweisungen im Programmcode
    private int ramCounter = 16;                         // Position des nächsten freien Registers

    Parser(File file) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file)
                )
        );

        String line;
        while((line = bufferedReader.readLine()) != null){
            line = line.replaceAll("\\s","");
            if(!line.isEmpty() && !line.startsWith("//"))
                if(line.contains("//"))
                    list.add(line.substring(0,line.indexOf("//")));
                else
                    list.add(line);
        }
        bufferedReader.close();

        currentCommand = list.get(currentPosition);
        fillSymbolTable1();
        fillSymbolTable2();
    }

    // Untersucht den Programmcode auf Sprünge, zählt die Anzahl an Programmanweisungen und ordnet den Sprüngen die
    // etsprechende Programmzeile zu.
    private void fillSymbolTable1() {
        instructionCounter = 0;
        while(hasMoreCommands()){
            if(commandType() == A_COMMAND)
                instructionCounter++;
            else if(commandType() == C_COMMAND)
                instructionCounter++;
            else
                symbolTable.addEntry(symbol(),instructionCounter);
            advance();
        }
        currentPosition = 0;
        currentCommand = list.get(0);
    }

    // Fügt übrige Variablennamen mit einem entsprechenden Register der Symboltabelle hinzu.
    private void fillSymbolTable2() {

        while(hasMoreCommands()){
            if(commandType() == A_COMMAND){
                try{Integer.parseInt(symbol());} catch (NumberFormatException e){
                if(!symbolTable.contains(symbol()))
                    symbolTable.addEntry(symbol(),ramCounter++);}
            }
        advance();
        }
        currentPosition = 0;
        currentCommand = list.get(0);
    }

    // Liefert false genau dann,  wenn der Parser in der letzten Programmzeile angelangt ist.
    public boolean hasMoreCommands() {
        return list.size() > currentPosition;
    }

    // Lässt den Parser zur nächsten Programmzeile übergehen.
    public void advance(){
        assert hasMoreCommands();
        currentPosition += 1;
        if(hasMoreCommands())
            currentCommand = list.get(currentPosition);
    }

    // Gibt Auskunft über den Befehlstyp der aktuellen Anweisung (A_COMMAND/C_COMMAND/L_COMMAND).
    public CommandType commandType(){

        if(currentCommand.startsWith("@"))
            return A_COMMAND;

        if(currentCommand.startsWith("("))
            return L_COMMAND;

        return C_COMMAND;
    }

    // Gibt den reinen Variablennamen aus dem aktuellen A_ bzw. L_COMMAND als String zurück.
    private String symbol(){
        assert commandType() != C_COMMAND;

        return currentCommand
                .replace("@","")
                .replace("(","")
                .replace(")","");
    }

    // Gibt den dest-Wert (Speicherregister) einer C-Anweisung als String zurück. Z.B. „D“ in „D=D+M“ oder „D;JGT “.
    public String dest(){
        assert commandType() == C_COMMAND;
        if(currentCommand.contains("="))
            return currentCommand.substring(0,currentCommand.indexOf("="));
        else
            return null;

    }

    // Gibt den comp-Wert (Rechenoperation) einer C-Anweisung als String zurück. Z.B. „D+M“ in „D=D+M“.
    public String comp(){
        assert commandType() == C_COMMAND;
        if(currentCommand.contains("=")) {
            return currentCommand.substring(currentCommand.indexOf("=")+1);
        }
        if(currentCommand.contains(";"))
            return currentCommand.substring(0,currentCommand.indexOf(";"));

        return null;
    }

    // Gibt den jmp-Wert (Sprungbedingung) einer C-Anweisung als String zurück. Z.B. „JGT“ in „D;JGT“
    public String jump(){
        assert commandType() == C_COMMAND;
        if(currentCommand.contains(";"))
            return currentCommand.substring(currentCommand.indexOf(";")+1);

        return null;
    }

    // Gibt die Aktuelle Programmzeilennummer des Parsers zurück.
    public int getCurrentPosition(){
        return currentPosition;
    }

    // Gibt den entprechenden 16-Bit Binärcode-Befehl zur aktuellen A-Anweisung zurück.
    public String binarySymbol(){
        assert commandType() == A_COMMAND;
        if(symbolTable.contains(symbol()))
            return Integer.toBinaryString(0x10000 | symbolTable.GetAddress(symbol())).substring(1);
        else
            return Integer.toBinaryString(0x10000 | Integer.parseInt(symbol())).substring(1);
    }

}