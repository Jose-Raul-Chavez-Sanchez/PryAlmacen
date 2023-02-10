/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;


public class EProductos {
    
    private int id;
    private ELaboratorios laboratorio;
    private ECategorias categoria;
    private EMarcas marca;
    private EUnidadesMedida undMedida;
    private String descripcion;
    private int stockMinimo;
    private int stockMaximo;
    private int stock;
    private String fechaVto;
    private double precioCompra;
    private double utilidad;
    private double precioVenta;
    private String estado;
    
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
     * @return the laborarotio
     */
    public ELaboratorios getLaboratorio() {
        return laboratorio;
    }

    /**
     * @param laboratorio the laborarotio to set
     */
    public void setLaboratorio(ELaboratorios laboratorio) {
        this.laboratorio = laboratorio;
    }

    /**
     * @return the categoria
     */
    public ECategorias getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(ECategorias categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the marca
     */
    public EMarcas getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(EMarcas marca) {
        this.marca = marca;
    }

    /**
     * @return the undMedida
     */
    public EUnidadesMedida getUndMedida() {
        return undMedida;
    }

    /**
     * @param undMedida the undMedida to set
     */
    public void setUndMedida(EUnidadesMedida undMedida) {
        this.undMedida = undMedida;
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

    /**
     * @return the stockMinimo
     */
    public int getStockMinimo() {
        return stockMinimo;
    }

    /**
     * @param stockMinimo the stockMinimo to set
     */
    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    /**
     * @return the stockMaximo
     */
    public int getStockMaximo() {
        return stockMaximo;
    }

    /**
     * @param stockMaximo the stockMaximo to set
     */
    public void setStockMaximo(int stockMaximo) {
        this.stockMaximo = stockMaximo;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the fechaVto
     */
    public String getFechaVto() {
        return fechaVto;
    }

    /**
     * @param fechaVto the fechaVto to set
     */
    public void setFechaVto(String fechaVto) {
        this.fechaVto = fechaVto;
    }

    /**
     * @return the precioCompra
     */
    public double getPrecioCompra() {
        return precioCompra;
    }

    /**
     * @param precioCompra the precioCompra to set
     */
    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    /**
     * @return the utilidad
     */
    public double getUtilidad() {
        return utilidad;
    }

    /**
     * @param utilidad the utilidad to set
     */
    public void setUtilidad(double utilidad) {
        this.utilidad = utilidad;
    }

    /**
     * @return the precioVenta
     */
    public double getPrecioVenta() {
        return precioVenta;
    }

    /**
     * @param precioVenta the precioVenta to set
     */
    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
