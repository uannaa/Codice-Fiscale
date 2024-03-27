/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codicefiscale;

import java.util.Scanner;

/**
 *
 * @author paolo
 */
public class Utente {
    
    private static String nome, cognome, data, comune;
    private static char sesso;
    
    public Utente(){}
    public Utente(String nome, String cognome, String data, String comune, char sesso) {
        
        this.nome = nome;
        this.cognome = cognome;
        this.data = data;
        this.comune = comune;
        this.sesso = sesso;
        
    }
    
    public void setUtente(String nome, String cognome, String data, String comune, char sesso) {
        
        this.nome = nome;
        this.cognome = cognome;
        this.data = data;
        this.comune = comune;
        this.sesso = sesso;
        
    }
    
    public void setInputUtente() {
        
        Scanner s = new Scanner(System.in);
        
        System.out.println("Inserisci nome: ");
        this.nome = s.nextLine();
        
        System.out.println("Inserisci cognome: ");
        this.cognome = s.nextLine();
        
        System.out.println("Inserisci data di nascita: (dd/mm/yyyy)");
        this.data = s.nextLine();
        
        System.out.println("Inserisci comune di nascita: ");
        this.comune = s.nextLine();
        
        System.out.println("Inserisci sesso (m/f): ");
        this.sesso = s.next().charAt(0);
        
    }
    
    public String getNome() {
        
        return nome;
        
    }
    
    public String getCognome() {
        
        return cognome;
        
    }
    
    public String getData() {
        
        return data;
        
    }
    
    public String getComune() {
        
        return comune;
        
    }
    
    public char getSesso() {
        
        return sesso;     
             
    }
        
}
