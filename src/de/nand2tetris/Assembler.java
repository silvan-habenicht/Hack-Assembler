package de.nand2tetris;

import java.io.*;

import static de.nand2tetris.CommandType.*;

/**
 * Über die Assembler-Klasse wird die Main-Methode aufgerufen.
 * Sie beendet das Programm mit einer Rückmeldung, falls keine Datei als Kommandozeilenargument übergeben wurde.
 * Die zu assemblierende Datei mit der Endung .asm sollte sich im Projektordner „Assembler“ befinden (Beispieldateien
 * sind dort bereits abgelegt). Andernfalls muss ein Pfad mit angegeben werden.
 * Wurde ein Dateipfad bzw. eine Datei übergeben, erzeugt die Main-Methode einen Parser, der die .asm-Datei
 * ausliest und verarbeitet. Mit Hilfe der Methoden aus der Klasse Parser wird sodann der entsprechende Binärcode
 * in eine neue Datei mit der Endung .hack geschrieben. Im Falle eines ungültigen Programmbefehls, wirft das Programm
 * eine IOException unter Angabe der entsprechenden Programmcodezeile (ohne Leer- bzw. Kommentarzeilen).
 * @author Silvan Habenicht
 * */

public class Assembler {

    public static void main(String[] args) throws IOException {

        if(args.length == 0){
            System.out.println("Es wurde keine Datei bzw. kein Dateipfad übergeben.");
            System.exit(0);
        }

        Parser parser = new Parser(new File(args[0]));

        FileOutputStream fileOutputStream = new FileOutputStream(
                new File(args[0].replaceAll(".asm",".hack")));

        BufferedWriter bufferedWriter = new BufferedWriter(
                new OutputStreamWriter(fileOutputStream));

        while(parser.hasMoreCommands()){
            String string;
            CommandType ct = parser.commandType();
            if(ct == A_COMMAND) {
                string = parser.binarySymbol();
            }
            else if(ct == C_COMMAND)
                try {
                    string = "111" + Code.comp(parser.comp()) + Code.dest(parser.dest()) + Code.jump(parser.jump());
                } catch (Exception e){throw new IOException("Assemblerfehler in der "
                        + parser.getCurrentPosition() + ". Anweisung.");}
            else {
                parser.advance();
                continue;
            }

            parser.advance();
            bufferedWriter.write(string);
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        System.out.println("Die Übersetzung war erfolgreich.");


    }
}