/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package codicefiscale;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Date;  
import java.util.Scanner;

/**
 *
 * @author paolo
 */

public class CodiceFiscale {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        Utente utente = new Utente();
        
        utente.getInput();
        
        CF code = new CF(utente);
        
        code.getCodice();
       
        code.printCode();
        code.saveCodice();
        
    }
}
