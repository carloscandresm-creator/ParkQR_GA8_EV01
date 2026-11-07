
package com.parkqr.model;

public class Apartamento {

    private int idApartamento;
    private int torre;
    private int piso;
    private int numero;

    public Apartamento() {
    }

    public Apartamento(int idApartamento, int torre, int piso, int numero) {
        this.idApartamento = idApartamento;
        this.torre = torre;
        this.piso = piso;
        this.numero = numero;
    }

    public int getIdApartamento() {
        return idApartamento;
    }

    public void setIdApartamento(int idApartamento) {
        this.idApartamento = idApartamento;
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

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
