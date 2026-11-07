package com.parkqr.model;

import java.sql.Timestamp;

public class Cobro {

    private int idCobro;
    private int idMovimiento;
    private double valor;
    private String motivo;
    private Timestamp fechaCobro;
    private Timestamp fechaVencimiento;
    private String estado;
    private Timestamp fechaPago;
    private int idUsuarioRecibe;
    private int torre;
    private int piso;
    private int numeroApartamento;
    private String nombreUsuarioRecibe;

    public int getIdCobro() {
        return idCobro;
    }

    public void setIdCobro(int idCobro) {
        this.idCobro = idCobro;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Timestamp getFechaCobro() {
        return fechaCobro;
    }

    public void setFechaCobro(Timestamp fechaCobro) {
        this.fechaCobro = fechaCobro;
    }

    public Timestamp getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Timestamp fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Timestamp fechaPago) {
        this.fechaPago = fechaPago;
    }

    public int getIdUsuarioRecibe() {
        return idUsuarioRecibe;
    }

    public void setIdUsuarioRecibe(int idUsuarioRecibe) {
        this.idUsuarioRecibe = idUsuarioRecibe;
    }
    public int getTorre() {
        return torre;
    }

    public void setTorre(int torre) {
        this.torre = torre;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public int getNumeroApartamento() {
        return numeroApartamento;
    }

    public void setNumeroApartamento(int numeroApartamento) {
        this.numeroApartamento = numeroApartamento;
    }

    public String getNombreUsuarioRecibe() {
        return nombreUsuarioRecibe;
    }

    public void setNombreUsuarioRecibe(String nombreUsuarioRecibe) {
        this.nombreUsuarioRecibe = nombreUsuarioRecibe;
    }
}