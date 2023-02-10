/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;
import Negocio.EProductos;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java.util.LinkedHashMap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;

public class ExportExcelUtil {
    
    public static String[] alignText;
    
    /**
        * Crear título de tabla
    */
    public static void createCaption(HSSFWorkbook wb, HSSFSheet sheet, String headString, int col) {
        HSSFRow row = sheet.createRow (0); // Crear fila de hoja de cálculo Excel
        HSSFCell cell = row.createCell ((short) 0); // Crea la celda de la fila especificada en la hoja de cálculo de Excel
        row.setHeight ((short) 1000); // Establecer la altura

        cell.setCellType (CellType.STRING); // Defina la celda como un tipo de cadena
        cell.setCellValue(new HSSFRichTextString(headString));

        sheet.addMergedRegion (new CellRangeAddress (0, 0, 0, col)); // Especifica el área de fusión del título

	// Definir formato de celda, agregar estilo de tabla de celda y agregar al libro de trabajo
	HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment (HorizontalAlignment.CENTER); // Especifique la alineación central de la celda
        cellStyle.setVerticalAlignment (VerticalAlignment.CENTER); // Especifique la alineación vertical de la celda
        cellStyle.setWrapText (true); // Especificar ajuste de celda

        // Establecer la fuente de la celda
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName ("Microsoft Yahei");
        font.setFontHeightInPoints ((short) 16); // tamaño de fuente

        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
    }
    
    /**
     * Crear encabezado de tabla
    */
    public static void createThead(HSSFWorkbook wb, HSSFSheet sheet, String[] thead, int[] sheetWidth) {
        HSSFRow row1 = sheet.createRow(1);
        row1.setHeight((short) 600);
                
        // Establecer el contenido del encabezado
        for (int i = 0; i < thead.length; i++) {           
            HSSFCell cell1 = row1.createCell(i);
            cell1.setCellType(CellType.STRING);
            cell1.setCellValue(new HSSFRichTextString(thead[i]));
            cell1.setCellStyle(createCellStyle(wb, alignText[i], true));
        }

        // Establecer el ancho de cada columna
        for (int i = 0; i < sheetWidth.length; i++) {
            sheet.setColumnWidth(i, sheetWidth[i]);
        }
    }
    
    /**
        Rellene los datos
     * @param wb
    */
    public static void createBody(HSSFWorkbook wb, HSSFSheet sheet, List<LinkedHashMap<String, String>> result) {   
        // Insertar datos
        for (int i = 0; i < result.size(); i++) {
            HSSFRow row = sheet.createRow(i + 2);
            row.setHeight ((short) 400); // Establecer la altura
            HSSFCell cell = null;
            int j = 0;
            for (String key : (result.get(i).keySet())) {
                cell = row.createCell(j);
                cell.setCellType(createCellType(result.get(i).get(key)));
                cell.setCellValue(new HSSFRichTextString(result.get(i).get(key)));
                cell.setCellStyle(createCellStyle(wb, alignText[j], false));
                j++;
            }
        }
    }
    
    private static HSSFCellStyle createCellStyle(HSSFWorkbook workBook, String align, boolean isHead) {
        // Definir formato de celda, agregar estilo de tabla de celda y agregar al libro de trabajo
        HSSFCellStyle cellStyle = workBook.createCellStyle();
        cellStyle.setWrapText(true);
        
        Sheet sheet = workBook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        Row row = sheet.getRow(lastRowNum);
        
        // Fuente de la celda
        HSSFFont font = workBook.createFont();
        if(isHead){
            font.setBold(true);
        }
        font.setFontName(" ");
        font.setFontHeightInPoints((short) 10);

        for (Cell cell : row) {
            String cellValue = "";
            switch (cell.getCellType()) {
                case NUMERIC:
                    cellValue = cell.getNumericCellValue() + "";
                    break;
                case STRING:
                    cellValue = cell.getStringCellValue();
            }
            
            if(cellValue.equals("INACTIVO") || cellValue.equals("ANULADO")){
                font.setColor(XSSFFont.COLOR_RED);
            }else{
                font.setColor(XSSFFont.COLOR_NORMAL);
            }
        }
        
        cellStyle.setFont(font);
        
        switch (align) {
            case "R":
                cellStyle.setAlignment(HorizontalAlignment.RIGHT);
                break;
            case "C":
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                break;
            default:
                cellStyle.setAlignment(HorizontalAlignment.LEFT);
                break;
        }
        
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setWrapText(true);
        
        if(isHead){
            cellStyle.setFillForegroundColor (IndexedColors.GREY_25_PERCENT.index); // Establecer el color de fondo
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setRightBorderColor (IndexedColors.BLACK.index); // Establecer el color del borde derecho
        }
        
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight (BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        
        return cellStyle;
    }
    
    private static CellType createCellType(String value){
        for (char c: value.toCharArray()) {
            if (Character.isDigit(c)) {
                return CellType.NUMERIC;
            }
        }
        
        return CellType.STRING;
    }
}
