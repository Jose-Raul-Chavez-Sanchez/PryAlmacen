/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;
import Negocio.ERoles;

public class Module {

    public static final String titleMessage = "Sistema de Almacén";
    public static int userId;
    public static String userName;
    public static String status;
    public static String tipoRol;
    
    public static ERoles rol = new ERoles();
    public static int id;
    public static EForm formActive = new EForm();
    public static EFormCart itemCart = new EFormCart();
    public static boolean editCart;
    
    public static String serieNI = "NI01";
    public static String serieNS = "NS01";
    
    /****** CONFIGURACION DE CORREOS ***********/
    public static String serverHost = "smtp.gmail.com";
    public static String serverPort = "587";
    public static String userNameMail = "joseraulchavezsanchez12345678@gmail.com";
    public static String passwordMail = "ufnlpoxqfcsrscpj";
    
    public boolean isInteger(String numero){
        try{
            Integer.valueOf(numero);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
}
