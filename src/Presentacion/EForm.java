/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;


public class EForm {
    
    private IFormActive caller;
    
    public void setCaller(IFormActive caller) {
        this.caller = caller;
    }
    
    public IFormActive getCaller() {
        return caller;
    }
}
