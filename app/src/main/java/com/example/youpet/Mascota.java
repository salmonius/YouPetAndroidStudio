package com.example.youpet;

public class Mascota {
    private int id,usuarioId;
    private String nombre,tipo,fecha,tamano,sexo,castrado,sociabilidad;
    private byte[] imagen;

    public Mascota(int id, int usuarioId, String nombre, String tipo, String fecha, String tamano, String sexo, String castrado, String sociabilidad, byte[] imagen) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.tipo = tipo;
        this.fecha = fecha;
        this.tamano = tamano;
        this.sexo = sexo;
        this.castrado = castrado;
        this.sociabilidad = sociabilidad;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCastrado() {
        return castrado;
    }

    public void setCastrado(String castrado) {
        this.castrado = castrado;
    }

    public String getSociabilidad() {
        return sociabilidad;
    }

    public void setSociabilidad(String sociabilidad) {
        this.sociabilidad = sociabilidad;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
