/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import java.time.LocalDateTime;


public class EMovimientos {
    
    private int id;
    private ETipoMovimiento tipoMovimiento;
    private EUsuarios usuario;
    private String fechaRegistro;
    private String serie;
    private String numero;
    private String observaciones;
    private LocalDateTime fechaAnulado;
    private String motivoAnulado;
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
     * @return the tipoMovimiento
     */
    public ETipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    /**
     * @param tipoMovimiento the tipoMovimiento to set
     */
    public void setTipoMovimiento(ETipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    /**
     * @return the usuario
     */
    public EUsuarios getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(EUsuarios usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the fechaRegistro
     */
    public String getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @param fechaRegistro the fechaRegistro to set
     */
    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @return the serie
     */
    public String getSerie() {
        return serie;
    }

    /**
     * @param serie the serie to set
     */
    public void setSerie(String serie) {
        this.serie = serie;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    /**
     * @return the fechaAnulado
     */
    public LocalDateTime getFechaAnulado() {
        return fechaAnulado;
    }

    /**
     * @param fechaAnulado the fechaAnulado to set
     */
    public void setFechaAnulado(LocalDateTime fechaAnulado) {
        this.fechaAnulado = fechaAnulado;
    }

    /**
     * @return the motivoAnulado
     */
    public String getMotivoAnulado() {
        return motivoAnulado;
    }

    /**
     * @param motivoAnulado the motivoAnulado to set
     */
    public void setMotivoAnulado(String motivoAnulado) {
        this.motivoAnulado = motivoAnulado;
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
