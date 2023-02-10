/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class EColorTableCell extends DefaultTableCellRenderer {

    private int columnIndex;
    
    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }
    
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        switch(table.getValueAt(row, columnIndex).toString()){
            case "INACTIVO":
                setForeground(Color.RED);
                break;
                
            case "ANULADO":
                setForeground(Color.RED);
                break;
            
            default:
                setForeground(Color.BLACK);
                break;
        }
        
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        return this;
    }
}
