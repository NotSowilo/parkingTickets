/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ultimapractica;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import ultimapractica.inOUT.In;

/**
 *
 * @author dani
 */
public class parking {

    final double MILITOMIN = 60000;
    final double PREUMIN = 0.05;
    File f = new File("files/in");

    public void afegirEntrada() {
        String matr = "";
        long timeIN;
        Calendar cal = Calendar.getInstance();
        timeIN = cal.getTimeInMillis();
        matr = In.pedirMatricula(matr);
        if (In.validacioMat(matr)) {
            introducirDato(matr, timeIN, 0, 0, getCodi());
        } else {
            System.out.println("Matricula incorrecta.");
            afegirEntrada();
        }

    }

    public int getCodi() {

        Scanner lector = null;
        int codi = 0;

        try {
            lector = new Scanner(f);
            lector.useDelimiter(":");
            lector.nextLine();
            while (lector.hasNextLine()) {

                lector.next();
                lector.nextLong();
                lector.nextLong();
                lector.next();
                codi = lector.nextInt();
                lector.nextLine();
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        tancarRecurs(lector);
        codi++;
        return codi;
    }

    public void introducirDato(String dato, long timeIN, long timeOUT, double price, int cod) {

        FileWriter fw = null;

        try {
            fw = new FileWriter(f, true);
            fw.write(dato + ":" + timeIN + ":" + timeOUT + ":" + price + ":" + cod + ":" + "\r\n");
        } catch (IOException ex) {
            System.err.println("ERROR D'ESCRIPTURA " + ex.getMessage());
        }
        tancarRecurs(fw);
    }

    public void tancarRecurs(Closeable recurs) {
        try {
            recurs.close();
        } catch (IOException ex) {
            System.err.println("ERROR TANCANT RECURS: " + ex.getMessage());
        }
    }

    public void replaceSelected(String mat, long timeIn, long timeOut, double imp, int codi) {
        try {
            // input the file content to the StringBuffer "input"
            BufferedReader file = new BufferedReader(new FileReader("files/in"));
            StringBuilder inputBuffer = new StringBuilder();
            String line;

            while ((line = file.readLine()) != null) {
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            tancarRecurs(file);

            String inputStr = inputBuffer.toString();
            System.out.println(inputStr);
            // logic to replace lines in the string (could use regex here to be generic)
            inputStr = inputStr.replace(mat + ":" + timeIn + ":0:0.0:" + codi + ":", mat + ":" + timeIn + ":" + timeOut + ":" + imp + ":" + codi + ":");
            System.out.println(inputStr);
            // write the new string with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream("files/in");
            fileOut.write(inputStr.getBytes());
            tancarRecurs(fileOut);

        } catch (IOException e) {
            System.out.println("Problem reading file.");
        }

    }

    public double setImport(long timeIn, long timeOut) {
        double time;

        time = timeOut - timeIn;

        time /= MILITOMIN;
        time *= PREUMIN;
        return time;
    }

    public void registrarSortida() {
        int codi = 0, co;
        String mat = "";
        long timeIn = 0, timeOut;
        double imp = 0;
        codi = In.pedirCodigo(codi);
        Calendar cal = Calendar.getInstance();
        timeOut = cal.getTimeInMillis();
        boolean OK = true;
        Scanner lector = null;

        try {
            lector = new Scanner(f);
            lector.useDelimiter(":");
            lector.nextLine();
            while (lector.hasNextLine() && OK) {
                mat = lector.next();
                timeIn = lector.nextLong();
                lector.nextLong();
                imp = lector.nextDouble();
                co = lector.nextInt();
                if (co == codi) {

                    OK = false;

                }
                lector.nextLine();

            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        tancarRecurs(lector);
        imp = setImport(timeIn, timeOut);
        replaceSelected(mat, timeIn, timeOut, imp, codi);
    }

    public void crearTicket() {
        Calendar cal = Calendar.getInstance();
        Scanner lector = null;
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd/M/yyyy");
        boolean OK = true;
        int codi = 0, co = 1;
        String mat = "";
        long timeIn = 0, timeOut = 0;
        double imp = 0;
        codi = In.pedirCodigo(codi);
        try {
            lector = new Scanner(f);
            lector.useDelimiter(":");
            lector.nextLine();
            while (lector.hasNextLine() && OK) {
                mat = lector.next();
                timeIn = lector.nextLong();
                timeOut = lector.nextLong();
                imp = lector.nextDouble();
                co = lector.nextInt();
                if (co == codi) {

                    OK = false;

                }
                lector.nextLine();

            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
        dataFormat.applyPattern("dd/MM/yyyy hh:mm:ss");
        System.out.println("");
        System.out.println("CODI: " + co);
        System.out.println("MATRICULA: " + mat);
        System.out.println("DATA-HORA ENTRADA: " + dataFormat.format(timeIn));
        if (timeOut == 0) {
            System.out.println("DATA-HORA SORTIDA: " + timeOut);
        } else {
            System.out.println("DATA-HORA SORTIDA: " + dataFormat.format(timeOut));
        }
        System.out.println("TEMPS ESTACIONAT: " + (timeOut - timeIn) / MILITOMIN);
        System.out.println("PREU PER MINUT: " + PREUMIN);
        System.out.printf("IMPORT: %.2f\n", imp);

    }

    public void llistaEstacionat() {

        Scanner lector = null;
        String mat;
        double imp;
        int codi, count = 0;
        long timeIn, timeOut;
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd/M/yyyy");
        dataFormat.applyPattern("dd/MM/yyyy hh:mm:ss");
        System.out.println("MATRICULA    FECHA ENTRADA     FECHA SALIDA   IMPORTE  CODI");
        try {
            lector = new Scanner(f);
            lector.useDelimiter(":");
            lector.nextLine();
            while (lector.hasNextLine()) {
                count++;
                mat = lector.next();
                timeIn = lector.nextLong();
                timeOut = lector.nextLong();
                imp = lector.nextDouble();
                codi = lector.nextInt();
                lector.nextLine();
                if (0 == timeOut) {
                    System.out.println(mat + "   " + dataFormat.format(timeIn) + "       " + timeOut + "    \t" + imp + "\t" + codi);
                }
            }

        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        }

        tancarRecurs(lector);
        if (count == 0) {
            System.out.println("No tenemos ningun vehiculo estacionado, vuelve a intentarlo más tarde.");
        }
    }

    public void cercarCodi() {
        int codi = 0, co = 1, count = 0;
        Scanner lector = null;
        String mat = "";
        long timeIn = 0, timeOut = 0;
        double imp = 0;
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd/M/yyyy");
        dataFormat.applyPattern("dd/MM/yyyy hh:mm:ss");
        codi = In.pedirCodigo(codi);
        try {
            lector = new Scanner(f);
            lector.useDelimiter(":");
            lector.nextLine();
            while (lector.hasNextLine()) {
                mat = lector.next();
                timeIn = lector.nextLong();
                timeOut = lector.nextLong();
                imp = lector.nextDouble();
                co = lector.nextInt();
                if (co == codi) {
                    count++;
                    System.out.println("");
                    System.out.println("MATRICULA: " + mat);
                    System.out.println("DATA-HORA SORTIDA: " + dataFormat.format(timeIn));
                    System.out.println("DATA-HORA ENTRADA: " + dataFormat.format(timeOut));
                    System.out.printf("IMPORT: %.2f\n", imp);
                    System.out.println("CODI: " + co);
                }
                lector.nextLine();
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        }

        tancarRecurs(lector);
        if (count == 0) {
            System.out.println("No hay ningun registro de este codigo, vuelve a intentarlo.");
        }
    }
}
