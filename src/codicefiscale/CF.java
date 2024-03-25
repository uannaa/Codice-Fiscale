/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codicefiscale;

import java.util.Scanner;

/**
 *
 * @author uanna
 */
public class CF {
    
    public static String nome, cognome, data, comune, sesso;
    
    public CF() {
    
        DataInput();
    
    }
    
    public CF(String nome, String cognome, String data, String comune, String sesso) {
        
        this.nome = nome;
        this.cognome = cognome;
        this.data = data;
        this.comune = comune;
        this.sesso = sesso;
        
    }
    
    public static void DataInput() {

        
        Scanner s = new Scanner(System.in);
        
            
        System.out.println("Inserisci nome: ");
        CF.nome = s.nextLine();
        
        System.out.println("Inserisci cognome: ");
        CF.cognome = s.nextLine();
        
        System.out.println("Inserisci data di nascita: (dd/mm/yyyy)");
        CF.data = s.nextLine();
        
        System.out.println("Inserisci comune di nascita: ");
        CF.comune = s.nextLine();
        
        System.out.println("Inserisci sesso (m/f): ");
        CF.sesso = s.nextLine();
        
    }
    
    
    
}
