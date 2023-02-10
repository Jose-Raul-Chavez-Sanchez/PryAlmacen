/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;
public class EFormCart {
    
    private IFormCart caller;
    
    public void setCaller(IFormCart caller) {
        this.caller = caller;
    }
    
    public IFormCart getCaller() {
        return caller;
    }
}
