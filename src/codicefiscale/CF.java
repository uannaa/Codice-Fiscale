/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codicefiscale;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author uanna
 */
public class CF {
    
    public static String nome, cognome, data, comune;
    public static char sesso;
     
    public CF(){} 
    
    public CF(Utente utente){
        
        CF.nome = utente.getNome();
        
        CF.cognome = utente.getCognome();
        
        CF.data = utente.getData();
        CF.data = checkData(data);
        
        CF.comune = utente.getComune();
        CF.comune = checkComune(comune);
        
        CF.sesso = utente.getSesso();
        CF.sesso = checkSesso(sesso);
        
    }
    
    public CF(String nome, String cognome, String data, String comune, char sesso) {
        
        this.nome = nome;
        this.cognome = cognome;
        this.data = data;
        this.comune = comune;
        this.sesso = sesso;
        
    }
    
    public String getCodice() throws FileNotFoundException {
        
        String codicefiscale = "";
        
        codicefiscale += CodificaCognome(CF.cognome);
        codicefiscale += CodificaNome(CF.nome);
        codicefiscale += AnnoNascita(CF.data);
        codicefiscale += MeseNascita(CF.data);
        codicefiscale += GiornoNascita(CF.data, CF.sesso);
        codicefiscale += CodificaComuneNascita(CF.comune);
        codicefiscale += CarattereDiControllo(codicefiscale);
        
        return codicefiscale;
        
    }
    
    public void DataInput(){
        
        Scanner s = new Scanner(System.in);
        
            
        System.out.println("Inserisci nome: ");
        CF.nome = s.nextLine();
        
        System.out.println("Inserisci cognome: ");
        CF.cognome = s.nextLine();
        
        System.out.println("Inserisci data di nascita: (dd/mm/yyyy)");
        CF.data = s.nextLine();
        data = checkData(data);
        
        System.out.println("Inserisci comune di nascita: ");
        CF.comune = s.nextLine();
        comune = checkComune(comune);
        
        System.out.println("Inserisci sesso (m/f): ");
        CF.sesso = s.next().charAt(0);
        
    }
    
    public void printCode() throws FileNotFoundException {
        
        CF codice = new CF();
        
        String codicef = codice.getCodice();
        
        System.out.println(codicef);
    }
    
    public void saveCodice(boolean save) throws FileNotFoundException, IOException {
        
        if (save == true) {
        
            CF codice = new CF();
        
            String codicef = codice.getCodice();
        
            writeCode(codicef);
        
        }
        
    }
    
    public void saveCodice() throws FileNotFoundException, IOException {
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Vuoi salvare questo codice fiscale su un file di testo sul desktop? (Y/N): ");
        String select = scanner.nextLine();
        
        if (select.equalsIgnoreCase("y")) {
        
            CF codice = new CF();
        
            String codicef = codice.getCodice();
        
            writeCode(codicef);
        
        } else if (select.equalsIgnoreCase("n")) {
            
            System.out.println("Ok!");
            
        } else {
            
            System.err.println("Errore!");
            
        }
        
    }
    //METODI PRIVATI
    
    //Metodi di conversione
    private static int[] ConversioneDataInt(String data) {
        
        String giorno;
        String mese;
        String anno;
        
        if (data.length() == 10) {
        
            giorno = data.substring(0,2);
            mese = data.substring(3,5);
            anno = data.substring(6);

            if (giorno.charAt(0) == '0') {

                giorno = data.substring(1,2);

            }

            if (mese.charAt(0) == '0') {

                mese = data.substring(4,5);

            }
        
            int giornonum = Integer.parseInt(giorno);
            int mesenum = Integer.parseInt(mese);
            int annonum = Integer.parseInt(anno);
            
            return new int[]{giornonum, mesenum, annonum};
            
        } else {
            
            System.out.println("Error!");
            
        }
        
        
        return null;
    }
    
    private static String[] ConversioneDataString(String data) {
        
        String giorno = data.substring(0,2);
        String mese = data.substring(3,5);
        String anno = data.substring(6);
        
        return new String[] {giorno, mese, anno};
        
    }
    
    //Metodi di controllo
    private static boolean isBisestile(String data) {
        
        boolean isBisestile = false;

        int dataconvertita[] = ConversioneDataInt(data);
        int giornonum = dataconvertita[0];
        int mesenum = dataconvertita[1];
        int annonum = dataconvertita[2];
        
        if ((annonum - 1904) % 4 == 0)  {
            
            isBisestile = true;
            
        } else {
            
            isBisestile = false;
            
        }
        
        return isBisestile;
        
    }
    
    private static String checkComune(String comune) {
        
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

                System.err.println("File non trovato: " + e.getMessage());

            }
            
            if (exists == false) {
                
                Scanner s = new Scanner(System.in);
                System.out.println("Il comune che hai inserito non esiste!\nReinseriscilo: ");
                comune = s.nextLine();
                
            }
        }
        
        return comune;
        
    }
    
    private static String checkData(String data) {
        
        boolean exists = false;
        boolean isBisestile = isBisestile(data);
        
        while (exists == false) {
        
            int dataconvertita[] = ConversioneDataInt(data);
            int giornonum = dataconvertita[0];
            int mesenum = dataconvertita[1];
            int annonum = dataconvertita[2];

            String dataconvertitas[] = ConversioneDataString(data);
            String giorno = dataconvertitas[0];
            String mese = dataconvertitas[1];
            String anno = dataconvertitas[2];
            
            exists = true;

            if (anno.charAt(0) == '0') {

                System.err.println("Error!");
                exists = false;

            }

            if (giornonum <= 0) {

                System.err.println("Il giorno non puo essere minore o uguale a 0");
                exists = false;

            }
            
            if (mesenum > 12) {
                
                System.err.println("I mesi sono solo 12");
                exists = false;
                
            }

            if (mesenum == 2 && giornonum > 29) {

                System.err.println("Il mese di febbraio comprende solo 29 giorni");
                exists = false;

            }
            
            //anno bisestile
            if (mesenum == 2 && giornonum == 29) {
                
                if (!isBisestile == true) {
                    
                    System.err.println("Il " + annonum + " non e' bisestile.");
                    exists = false;
                    
                }
                
            }

            //11 4 6 9 (30) // 1 3 5 7 8 10 12

            if ((mesenum == 11 || mesenum == 4 || mesenum == 6 || mesenum == 9) && giornonum > 30) {

                System.err.println("Il mese che hai inserito non ha piu di 30 giorni.");
                exists = false;

            } 

            if ((mesenum == 1 || mesenum == 3 || mesenum == 5 || mesenum == 7 || mesenum == 8 || mesenum == 10 || mesenum == 12) && giornonum > 31) {

                System.err.println("Genio! Nessun mese ha piu di 31 giorni.");
                exists = false;

            } 

            if (mesenum < 1) {

                System.err.println("Mese < 1");
                exists = false;

            }
            
            if (giornonum < 1) {

                System.err.println("Giorno < 1");
                exists = false;

            }
            
            if (data.length() < 10) {
                
                System.err.println("Data troppo corta!");
                exists = false;
                
            }
            
            if (exists == false) {
                
                Scanner s = new Scanner(System.in);
                System.out.println("Reinserisci data di nascita (dd/mm/yyyy): ");
                data = s.nextLine();
                
            }
            
            
        }
        
        return data;
    }
    
    private static char checkSesso(char sesso) {
        
        boolean exists = false;
        Scanner scanner = new Scanner(System.in);
        
        
        while (exists == false) {
            
            exists = true;
        
            if (sesso != 'm' || sesso != 'f') {
                
                System.err.println("Il sesso puo essere M o F");
                exists = false;
            
            }
            
            if (exists == false) {
                
                System.out.println("Reiserisci: ");
                sesso = scanner.next().charAt(0);
                
            }
            
        }
        
        return sesso;
        
    }
    
    //Metodi di codifica
    private static String CodificaNome(String nome) {

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
    
    private static String CodificaCognome(String cognome){

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
    
    private static String CarattereDiControllo(String codicefiscale){

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
    
    private static String CodificaComuneNascita(String comune) throws FileNotFoundException {
        
        
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
    
    
    //Codifica delle nascita
    private static String GiornoNascita(String data, char sesso) {

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
    
    private static String MeseNascita(String data){

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
    
    private static String AnnoNascita(String data) {

        String anno = "";

        anno += data.charAt(8);
        anno += data.charAt(9);


        return anno;
    }

    //Metodi aggiuntivi
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
    
    private static String getInputFromUser() {
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci il contenuto da scrivere nel file:");
        return scanner.nextLine();
        
    }
    
    private static void writeCode(String codicefiscale) throws IOException {
        
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