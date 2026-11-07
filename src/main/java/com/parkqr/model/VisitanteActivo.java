
package com.parkqr.model;

import java.sql.Timestamp;

public class VisitanteActivo {

    private String nombre;
    private String placa;
    private String tipoVehiculo;
    private String marca;
    private String color;

    private int torre;
    private int piso;
    private int numeroApartamento;

    private Timestamp fechaEntrada;
    private long minutosDentro;

    private double totalCobrado;
    private double cobroPendiente;
    private String estadoCobro;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public Timestamp getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Timestamp fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public long getMinutosDentro() {
        return minutosDentro;
    }

    public void setMinutosDentro(long minutosDentro) {
        this.minutosDentro = minutosDentro;
    }

    public double getTotalCobrado() {
        return totalCobrado;
    }

    public void setTotalCobrado(double totalCobrado) {
        this.totalCobrado = totalCobrado;
    }

    public String getEstadoCobro() {
        return estadoCobro;
    }

    public double getCobroPendiente() {
        return cobroPendiente;
    }

    public void setCobroPendiente(double cobroPendiente) {
        this.cobroPendiente = cobroPendiente;
    }

    public void setEstadoCobro(String estadoCobro) {
        this.estadoCobro = estadoCobro;
    }
}
