/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;


public class ItemAutocomplete {
    
    //http://netbeans.apache.org/kb/docs/javaee/ecommerce/transaction.html
    //https://coderanch.com/t/685828/java/Java-Code-Netbeans-Retail-Transaction
    //https://github.com/microservices-demo/orders/blob/master/src/main/java/works/weave/socks/orders/entities/Cart.java
    //https://gist.github.com/yaswanthrajyadiki/f9b2ec5fbbc1368a54de
    
    private int id;
    private String descripcion;
    
    public ItemAutocomplete(int id, String descripcion){
        this.id = id;
        this.descripcion = descripcion;
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    @Override
    public String toString() {
        return this.descripcion;
    }
}
