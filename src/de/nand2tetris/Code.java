package de.nand2tetris;

/**
 * Die Code-Klasse dient ausschließlich dem Übersetzen von Assembler-Befehlen in ihr entsprechendes
 * Binärcode-Äquivalent.
 * Wird eine ungültige C-Anweisung erkannt, so wird dies der aufrufenden Klasse durch eine Ausnahme mitgeteilt.
 * @author Silvan Habenicht
 * */

public class Code {

    public static String dest(String string){

        if(string == null)       return "000";
        if(string.equals("M"))   return "001";
        if(string.equals("D"))   return "010";
        if(string.equals("MD"))  return "011";
        if(string.equals("A"))   return "100";
        if(string.equals("AM"))  return "101";
        if(string.equals("AD"))  return "110";
        if(string.equals("AMD")) return "111";

        return "000";
    }

    public static String comp(String string) throws Exception {

        if(string.equals("0"))   return "0101010";
        if(string.equals("1"))   return "0111111";
        if(string.equals("-1"))  return "0111010";
        if(string.equals("D"))   return "0001100";
        if(string.equals("A"))   return "0110000";
        if(string.equals("!D"))  return "0001101";
        if(string.equals("!A"))  return "0110001";
        if(string.equals("-D"))  return "0001111";
        if(string.equals("-A"))  return "0110011";
        if(string.equals("D+1")) return "0011111";
        if(string.equals("A+1")) return "0110111";
        if(string.equals("D-1")) return "0001110";
        if(string.equals("A-1")) return "0110010";
        if(string.equals("D+A")) return "0000010";
        if(string.equals("D-A")) return "0010011";
        if(string.equals("A-D")) return "0000111";
        if(string.equals("D&A")) return "0000000";
        if(string.equals("D|A")) return "0010101";
        if(string.equals("M"))   return "1110000";
        if(string.equals("!M"))  return "1110001";
        if(string.equals("-M"))  return "1110011";
        if(string.equals("M+1")) return "1110111";
        if(string.equals("M-1")) return "1110010";
        if(string.equals("D+M")) return "1000010";
        if(string.equals("D-M")) return "1010011";
        if(string.equals("M-D")) return "1000111";
        if(string.equals("D&M")) return "1000000";
        if(string.equals("D|M")) return "1010101";

        throw new Exception("Ungültige C-Anweisung gefunden");
    }

    public static String jump(String string){

        if(string == null)       return "000";
        if(string.equals("JGT")) return "001";
        if(string.equals("JEQ")) return "010";
        if(string.equals("JGE")) return "011";
        if(string.equals("JLT")) return "100";
        if(string.equals("JNE")) return "101";
        if(string.equals("JLE")) return "110";
        if(string.equals("JMP")) return "111";

        return "000";
    }

}
