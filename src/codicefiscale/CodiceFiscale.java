/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package codicefiscale;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author paolo
 */

public class CodiceFiscale {

    /**
     * @param args the command line arguments
     */
    
    /* TODO 
    
        Creare un file reader: Cambiare il path del csv. Mettere un path generico, dentro la cartella dell src. 
    
    */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        Scanner s = new Scanner(System.in);

        String nome, cognome, comunedinascita, data, codicefiscale = "";
        char sesso;

        System.out.println("Inserisci nome: ");
        nome = s.nextLine();

        System.out.println("Inserisci cognome: ");
        cognome = s.nextLine();

        System.out.println("Inserisci comune di nascita: ");
        comunedinascita = s.nextLine();

        System.out.println("Inserisci data di nascita (dd/mm/yyyy): ");
        data = s.nextLine();
        
        System.out.println("Sesso [m/f]: ");
        sesso = s.next().charAt(0);

        //metodi
        codicefiscale += CodificaCognome(cognome);
        codicefiscale += CodificaNome(nome);
        codicefiscale += AnnoNascita(data);
        codicefiscale += MeseNascita(data);
        codicefiscale += GiornoNascita(data, sesso);
        codicefiscale += ComuneNascita(comunedinascita);
        codicefiscale += CarattereDiControllo(codicefiscale);
        
        System.out.println(codicefiscale);
        writeCode(codicefiscale);
        
        
//        System.out.println(CarattereDiControllo(codicefiscale));

    }
    
    /*
    
    public static String pathCSV() throws FileNotFoundException {
        
        String desktopPath = System.getProperty("user.home") + "/Desktop";
        String fileName = "pathCSV.txt";
        String filePath = desktopPath + fileName;
        
        File file = new File(filePath);
        
        if (file.exists()) {
            
            try {
                
                Scanner scanner = new Scanner(file);
                if (scanner.hasNextLine()) {
                    
                    String pathCSVString = scanner.nextLine();
                    if (!pathCSVString.isEmpty()) {
                        
                        return pathCSVString;
                        
                    } else {
                        
                        System.out.println("Il file esiste ma e' vuoto");
                        pathCSVString = getInputFromUser();
                        writeToFile(filePath, pathCSVString);
                        
                    }
                    
                } else {
                    
                    System.out.println("Il file esiste ma e' vuoto");
                    String pathCSVString = getInputFromUser();
                    writeToFile(filePath, pathCSVString);
                    
                }
                
            } catch (IOException e) {
                
                e.printStackTrace();
                
            }
            
        } else {
            
            System.out.println("Il file non esiste. Creazione in corso...");
            String pathCSVString = getInputFromUser();
            writeToFile(filePath, pathCSVString);
            
        }
        
        return null;
        
    }
    
    */
    
    public static String getInputFromUser() {
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci il contenuto da scrivere nel file:");
        return scanner.nextLine();
        
    }
    
    public static void writeToFile(String filePath, String content) {
        
        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(content);
            writer.close();
            System.out.println("Contenuto scritto nel file con successo.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public static String CodificaCognome(String cognome){

        cognome = cognome.toLowerCase();
        String cognomecodificato = "";
        char consonanti[] = {'b', 'c', 'd', 'f', 'g', 'h', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'z','j', 'k', 'w', 'x', 'y'};
        int contatore = 0;


        for(int i = 0; i < cognome.length() && cognomecodificato.length() < 3; i++) {

            char carattereatt = cognome.charAt(i);

            for (int j = 0; j < consonanti.length; j++) {

                if (carattereatt == consonanti[j]) {

                    cognomecodificato += carattereatt;
                    contatore++;

                }

            }

        }



        if (cognomecodificato.length() < 3) {

            char vocali[] = {'a', 'e', 'i', 'o', 'u'};

            for (int i = 0; i < cognome.length(); i++) {

                char carattereatt = cognome.charAt(i);

                for (int j = 0; j < vocali.length; j++) {

                    if (carattereatt == vocali[j]) {

                        cognomecodificato += vocali[i];

                    }

                }

            }

        }

        if (cognomecodificato.length() < 3) {

            for (int i = cognomecodificato.length(); i < 3; i++) {

                cognomecodificato += "X";

            }

        }

        return cognomecodificato.toUpperCase();

    }


    public static String CodificaNome(String nome) {

        nome = nome.toLowerCase();
        String nomecodificato = "";
        char consonanti[] = {'b', 'c', 'd', 'f', 'g', 'h', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'z','j', 'k', 'w', 'x', 'y'};
        int contatore = 0;
        char consonantiu[] = new char[10];


        //controllo consonanti e aggiunte in un array di caratteri
        for (int i = 0; i < nome.length(); i++) {

            char carattereatt = nome.charAt(i);

            for (int j = 0; j < consonanti.length; j++) {

                if (carattereatt == consonanti[j]) {

                    consonantiu[contatore] = carattereatt;
                    contatore++;
                    break;

                }

            }

            if (contatore >=4) {

                break;

            }


        }

        if (contatore >= 4) {

            char nomecodchar;

            nomecodchar = consonantiu[0];
            nomecodificato += String.valueOf(nomecodchar);
            nomecodchar = consonantiu[2];
            nomecodificato += String.valueOf(nomecodchar);
            nomecodchar = consonantiu[3];
            nomecodificato += String.valueOf(nomecodchar);
            
            
//            nomecodificato += consonantiu[0] + consonantiu[2] + consonantiu[3];

        } else {

            for (int i = 0; i < contatore; i++) {

                nomecodificato += consonantiu[i];

            }

        }

        if (nomecodificato.length() < 3) {

            char vocali[] = {'a', 'e', 'i', 'o', 'u'};

            for (int i = 0; i < nome.length(); i++) {

                char carattereatt = nome.charAt(i);

                for (int j = 0; j < vocali.length; j++) {

                    if (carattereatt == vocali[j]) {

                        nomecodificato += vocali[j];

                    }

                }

                if (nomecodificato.length() == 3) {

                    break;

                }

            }

        }

        if (nomecodificato.length() < 3) {

            for (int i = nomecodificato.length(); i < 3; i ++) {

                nomecodificato += "X";

            }

        }

        return nomecodificato.toUpperCase();

    }

    public static String AnnoNascita(String data) {

        String anno = "";

        anno += data.charAt(8);
        anno += data.charAt(9);


        return anno;
    }

    public static String MeseNascita(String data){

        data = data.toLowerCase();
        String dataf = "";
        dataf += data.charAt(3);
        dataf += data.charAt(4);
        String mese = "";

        String mesenum[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        String mesechar[] = {"A", "B", "C", "D", "E", "H", "L", "M", "P", "R", "S", "T"};


        for (int i = 0; i < 12; i++) {

            if (dataf.equals(mesenum[i])) {

                mese += mesechar[i];
                break;

            }

        }

        return mese;
    }

    public static String GiornoNascita(String data, char sesso) {

        data = data.toLowerCase();
        String dataf = "";
        dataf += data.charAt(0);
        dataf += data.charAt(1);

        if (sesso == 'm') {

            return dataf;

        } else if (sesso == 'f') {

            int datafi = Integer.parseInt(dataf);
            datafi += 40;

            String datafii = "";
            datafii = Integer.toString(datafi);
            return datafii;
        }


        return null;

    }

    public static String ComuneNascita(String comune) throws FileNotFoundException {
        
        String filePath = "C:\\Users\\paolo\\Desktop\\Codice-Fiscale\\src\\CodiciComune.csv";
        
        
        File file = new File(filePath);
        
        try {
            
            Scanner scanner = new Scanner(file);
            
            while (scanner.hasNextLine()) {
            
                String line = scanner.nextLine();
                
                String col[] = line.split(",");
                
                if (col.length >= 2) {
                
                    String primaColonna = col[0];
                    String secondaColonna = col[1];
                    
                    if (primaColonna.equalsIgnoreCase(comune)) {
                    
                        return secondaColonna.toUpperCase();
                    
                    }
                    
                }
                
            }
            
            scanner.close();
            
        } catch (FileNotFoundException e) {
        
            System.out.println("File non trovato: " + e.getMessage());
            
        }

//        String comuni[] = {"Acquaviva delle fonti", "Conversano", "Castellana Grotte", "Bari"};
//        String comunicod[] = {"A048", "C975", "C134", "A662"};
//
//        for (int i = 0; i < comuni.length; i++) {
//
//            if (comune.toLowerCase().equals(comuni[i].toLowerCase())) {
//
//                return comunicod[i];
//
//            }
//
//        }
//        return null;
        return null;

    }
    public static String CarattereDiControllo(String codicefiscale){

        codicefiscale = codicefiscale.toLowerCase();

        char caratteri[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        char caratteriDiControllo[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        int caratteriDispari[] = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23};
        int caratteriPari[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9,0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
        String codicePari = "";
        String codiceDispari = "";
        int somma = 0;
        String codicedicontrollo = "";

        for (int i = 0; i < codicefiscale.length(); i++) {

            if (i % 2 == 0) {

                codiceDispari += codicefiscale.charAt(i);


            } else {

                codicePari += codicefiscale.charAt(i);

            }

        }

        //somma pari
        
        for (int i = 0; i < codicePari.length(); i++) {
        
            char carattereattuale = codicePari.charAt((i));
            
            for (int  j = 0; j < caratteri.length; j++) {
            
                if (caratteri[j] == carattereattuale) {
                
                    somma = somma + caratteriPari[j]; 
                
                }
            
            }
        
        }
        
        for (int i = 0; i < codiceDispari.length(); i++) {
        
            char carattereattuale = codiceDispari.charAt(i);
            
            for (int j = 0; j < caratteri.length; j++) {
            
                if (caratteri[j] == carattereattuale) {
                
                    somma = somma + caratteriDispari[j];
                
                }
            
            }
        
        }
        
        int resto = somma % 26;
        
        codicedicontrollo = String.valueOf(caratteriDiControllo[resto]);
        
//        System.out.println("Pari: " + codicePari);
//        System.out.println("Dispari: " + codiceDispari);
        
        return codicedicontrollo.toUpperCase();
        
    } 
    
    public static void writeCode(String codicefiscale) throws IOException {
        
        String desktopPath = System.getProperty("user.home") + "/Desktop";
        String FilePath = desktopPath + "/Codice-Fiscale.txt";
        
        File desktop = new File(desktopPath);
        
        
        
        if (desktop.exists() && desktop.isDirectory()) {
        
            File newFile = new File(desktop, "Codice-Fiscale.txt");
            
            try {
                
                if (newFile.createNewFile()) {
                
                    System.out.println("File creato sul desktop");
                    
                } else {
                    
                    System.out.println("Il file esiste gia sul desktop!");
                    
                }
                
            } catch (IOException e) {
                        
                    System.out.println("Error");
                    e.printStackTrace();
                    
            }
            
            try {
                
                FileWriter writer = new FileWriter(FilePath, true);
                
                writer.write("\n" + codicefiscale);
                writer.close();
                System.out.println("Codice inserito nel file sul desktop!");
                
            } catch (IOException e) {
                
                System.out.println("Errore nella scrittura del codice");
                e.printStackTrace();
                
            }
        }
    }
}
