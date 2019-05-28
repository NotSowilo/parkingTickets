/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ultimapractica.inOUT;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import ultimapractica.parking;

/**
 *
 * @author dani
 */
public class In {

    public static String choose() {
        String op;
        Scanner lec = new Scanner(System.in);
        System.out.println("Elige una opción");
        op = lec.next();
        lec.nextLine();
        return op;
    }

    public static boolean validacioMat(String mat) {
        parking park = new parking();
        boolean verify, in, OK = true;
        File f = new File("files/in");
        Scanner lector = null;
        verify = mat.matches("[0-9]{4}[A-Z-[AEIOU]]{3}");

        if (verify) {
            try {
                lector = new Scanner(f);
                lector.useDelimiter(":");
                lector.nextLine();
                while (lector.hasNextLine() && OK) {

                    in = mat.matches(lector.next());
                    if (in) {
                        lector.nextLong();
                        if (0 == lector.nextLong()) {
                            OK = false;
                        }
                    }
                    lector.nextLine();

                }
            } catch (FileNotFoundException ex) {
                System.err.println(ex.getMessage());
            }
            park.tancarRecurs(lector);
            if (OK) {
                System.out.println("Matricula introducida correctamente.");
            } else {
                System.out.println("Ya tenemos registrada esta matricula y aun no ha salido de nuestro parking. Registra la salida de esta matricula o vuelve a intentarlo.");
                verify = false;
            }
        } 

        return verify;
    }

    public static String pedirMatricula(String matricula) {
        Scanner lector = new Scanner(System.in);
        System.out.println("Introduce una matricula: ");

        matricula = lector.next();

        return matricula;
    }

    public static int pedirCodigo(int codi) {
        Scanner lector = new Scanner(System.in);
        System.out.println("Introduce un codigo que quieras: ");

        codi = lector.nextInt();

        return codi;
    }
}
