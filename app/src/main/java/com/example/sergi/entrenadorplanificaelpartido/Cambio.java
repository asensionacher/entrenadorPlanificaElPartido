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
    public String pos;
    public Boolean notificado;

    public Cambio(String Entra, String Sale, String Minuto, String Pos) {
        minuto = Minuto;
        sale = Sale;
        entra = Entra;
        pos = Pos;
        avisado = false;
        escrito = false;
        notificado = false;
    }

    public Cambio (Cambio c) {
        minuto = c.getMinuto();
        sale = c.getSale();
        entra = c.getEntra();
        pos = c.getPos();
        avisado = false;
        escrito = false;
        notificado = false;
    }

    public void setNotificado(Boolean notificado) {
        this.notificado = notificado;
    }

    public Boolean getNotificado() {

        return notificado;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getPos() {

        return pos;
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
