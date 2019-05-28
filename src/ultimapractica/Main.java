/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ultimapractica;

import ultimapractica.inOUT.In;

/**
 *
 * @author dani
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        menu();
    }

    public static void menu() {
        parking park = new parking();

        System.out.println("==============================");
        System.out.println("\t    MENU\t");
        System.out.println("\t1. AFEGIR ENTRADA");
        System.out.println("\t2. REGISTRAR SORTIDA");
        System.out.println("\t3. CREAR TIQUET");
        System.out.println("\t4. CERCAR PER CODI");
        System.out.println("\t5. LLISTAR  ESTACIONATS");
        System.out.println("\t6. SORTIR");
        System.out.println("==============================");

        switch (In.choose()) {
            case "1":
                park.afegirEntrada();
                menu();
                break;
            case "2":
                park.registrarSortida();
                menu();
                break;
            case "3":
                park.crearTicket();
                menu();
                break;
            case "4":
                park.cercarCodi();
                menu();
                break;
            case "5":
                park.llistaEstacionat();
                menu();
                break;
            case "6":
                System.out.println("Seleccionado salir, bye!");
                break;
            default:
                System.out.println("Opcion incorrecta, vuelve a intentarlo");
                menu();
                break;
        }
    }

}
