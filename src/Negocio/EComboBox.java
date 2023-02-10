/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

public class EComboBox {
    
    private int id;
    private String descripcion;
    
    public EComboBox(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
 
    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    @Override
    public String toString() {
        return descripcion;
    }
}
