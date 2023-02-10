
package Negocio;


public class EUsuarios {
    
    private int id;
    private int rolId;
    private String tipoRol;
    private String nombres;
    private String usuario;
    private String clave;
    private String token;
    private String correo;
    private String estado;

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    
    /**
     * @param rolId the rolId to set
     */
    public void setRolId(int rolId) {
        this.rolId = rolId;
    }
    
    /**
     * @return the rolId
     */
    public int getRolId() {
        return rolId;
    }
    
    public void setTipoRol(String tipo){
        this.tipoRol = tipo;
    }
    
    public String getTipoRol(){
        return tipoRol;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }
    
    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }
    
    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }
    
    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }
    
    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }
}
