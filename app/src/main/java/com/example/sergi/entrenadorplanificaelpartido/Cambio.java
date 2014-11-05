package com.example.sergi.entrenadorplanificaelpartido;

/**
 * Created by Sergi on 5/11/14.
 */
public class Cambio {
    public String entra;
    public String sale;
    public String minuto;
    public Boolean avisado;
    public Boolean escrito;

    public Cambio(String Entra, String Sale, String Minuto) {
        minuto = Minuto;
        sale = Sale;
        entra = Entra;
        avisado = false;
        escrito = false;
    }

    public Cambio (Cambio c) {
        minuto = c.getMinuto();
        sale = c.getSale();
        entra = c.getEntra();
        avisado = false;
        escrito = false;
    }

    public Boolean getAvisado() {
        return avisado;
    }

    public Boolean getEscrito() {
        return escrito;
    }

    public String getEntra() {
        return entra;
    }

    public String getSale() {
        return sale;
    }

    public String getMinuto() {
        return minuto;
    }

    public void setEntra(String entra) {
        this.entra = entra;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public void setMinuto(String minuto) {
        this.minuto = minuto;
    }

    public void setAvisado(Boolean avisado) {
        this.avisado = avisado;
    }

    public void setEscrito(Boolean escrito) {
        this.escrito = escrito;
    }
}
