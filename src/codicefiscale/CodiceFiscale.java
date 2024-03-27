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
        
        
        /*
        
        Scanner s = new Scanner(System.in);

        String nome, cognome, comunedinascita, data, codicefiscale = "";
        char sesso;

        System.out.println("Inserisci nome: ");
        nome = s.nextLine();

        System.out.println("Inserisci cognome: ");
        cognome = s.nextLine();

        System.out.println("Inserisci comune di nascita: ");
        comunedinascita = s.nextLine();
        comunedinascita = checkComune(comunedinascita);

        System.out.println("Inserisci data di nascita (dd/mm/yyyy): ");
        data = s.nextLine();
        data = checkData(data);
        
        System.out.println("Sesso [m/f]: ");
        sesso = s.next().charAt(0);

        //Metodi per calcolo
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
    
    public static String checkComune(String comune) {
        
        boolean exists = false;
        String destinationPath = downloadFileMain();
        
        String filePath = destinationPath;
        
                File file = new File(filePath);
        
                
        while (exists == false) {
            
            exists = false;
            
            try {

                Scanner scanner = new Scanner(file);

                while (scanner.hasNextLine()) {

                    String line = scanner.nextLine();

                    String col[] = line.split(",");

                    if (col.length >= 2) {

                        String primaColonna = col[0];
                        String secondaColonna = col[1];

                        if (primaColonna.equalsIgnoreCase(comune)) {

                            exists = true;

                        }

                    }

                }

                scanner.close();

            } catch (FileNotFoundException e) {

                System.out.println("File non trovato: " + e.getMessage());

            }
            
            if (exists == false) {
                
                Scanner s = new Scanner(System.in);
                System.out.println("Il comune che hai inserito non esiste!\nReinseriscilo: ");
                comune = s.nextLine();
                
            }
        }
        
        return comune;
        
    }
    
    public static String checkData(String data) {
        
        boolean exists = false;
        

        
        while (exists == false) {
            
            String giorno = data.substring(0,2);
            String mese = data.substring(3,5);
            String anno = data.substring(6);
        
            exists = true;
            
            if (giorno.charAt(0) == '0') {

                giorno = data.substring(1,2);

            }

            if (mese.charAt(0) == '0') {

                mese = data.substring(4,5);

            }

            if (anno.charAt(0) == '0') {

                System.out.println("Error!");
                exists = false;

            } 
            

            int giornonum = Integer.parseInt(giorno);
            int mesenum = Integer.parseInt(mese);
//            int annonum = Integer.parseInt(anno);

            if (giornonum <= 0) {

                System.out.println("Il giorno non puo essere minore o uguale a 0");
                exists = false;

            } 
            
            if (mesenum > 12) {
                
                System.out.println("I mesi sono solo 12");
                exists = false;
                
            }

            if (mesenum == 2 && giornonum > 29) {

                System.out.println("Il mese di febbraio comprende solo 29 giorni");
                exists = false;

            } 

            //11 4 6 9 (30) // 1 3 5 7 8 10 12

            if ((mesenum == 11 || mesenum == 4 || mesenum == 6 || mesenum == 9) && giornonum > 30) {

                System.out.println("Il mese che hai inserito non ha piu di 30 giorni.");
                exists = false;

            } 

            if ((mesenum == 1 || mesenum == 3 || mesenum == 5 || mesenum == 7 || mesenum == 8 || mesenum == 10 || mesenum == 12) && giornonum > 31) {

                System.out.println("Genio! Nessun mese ha piu di 31 giorni.");
                exists = false;

            } 

            if (mesenum < 1) {

                System.out.println("Mese < 1");
                exists = false;

            }
            
            if (giornonum < 1) {

                System.out.println("Giorno < 1");
                exists = false;

            }
            
            if (exists == false) {
                
                Scanner s = new Scanner(System.in);
                System.out.println("La data e' sbagliata.\nInserisci data di nascita (dd/mm/yyyy): ");
                data = s.nextLine();
                
                
            }
            
            
        }
        
        return data;
    }
    
    
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
    
    private static String downloadFileMain() {
        
        String fileUrl = "https://www.dropbox.com/scl/fi/5t6yyn175czj0aacinlve/CodiciComune.csv?rlkey=d3m1zdff3y8haid7ldy3iq6yk&dl=1";
        String fileName = "CodiciComune.csv";
        String destinationPath = System.getProperty("user.home") + File.separator + fileName;
        
        File file = new File(destinationPath);
        
        
        if (!file.exists()) {
            
            try {

                System.out.println("Il file con i codici non esiste sul tuo pc. Download in corso...");
                downloadFile(fileUrl, destinationPath);

                if (file.exists()) {

                    System.out.println("File scaricato con successo.");

                } else {

                    System.out.println("Error");

                }

            } catch (IOException e) {

                e.printStackTrace();

            }
            
        }
        
        return destinationPath;
        
    }
    
    private static void downloadFile(String fileUrl, String destinationPath) throws IOException {
        URL url = new URL(fileUrl);
        try (BufferedInputStream in = new BufferedInputStream(url.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(destinationPath)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        }
    }

    public static String ComuneNascita(String comune) throws FileNotFoundException {
        
        
        downloadFileMain();
        String destinationPath = downloadFileMain();
        
        String filePath = destinationPath;
        
        
        
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

        */

    }
}
