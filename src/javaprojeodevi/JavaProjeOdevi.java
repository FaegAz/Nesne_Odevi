/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaprojeodevi;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JavaProjeOdevi {
    public static int hata;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        AnaSayfa.init();
        
        try {
            Database.dbCreate();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        try {
            Database.dbTableCreateTest();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        try {
            Database.dbTableCreate();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        
        try {
            Database.dbTableClear();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        
    }
    
}
