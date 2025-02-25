package Puzzle8;

import java.util.ArrayList;

public class Nodo {
    int[][] estado;
    ArrayList<Nodo> hijos;
    Nodo padre;
    int costo;
    
    public Nodo(int[][] estado, int costo) {
        this.estado = estado;
        this.costo = costo;
        this.hijos = new ArrayList<>();
        this.padre = null;
    }
    
    public int[][] getEstado() {
        return estado;
    }
    
    public void setEstado(int[][] estado) {
        this.estado = estado;
    }
    
    public ArrayList<Nodo> getHijos() {
        return hijos;
    }
    
    public void setHijos(ArrayList<Nodo> hijos) {
        this.hijos = hijos;
    }
    
    public Nodo getPadre() {
        return padre;
    }
    
    public void setPadre(Nodo padre) {
        this.padre = padre;
    }
    
    public int getCosto() {
        return costo;
    }
    
    public void setCosto(int costo) {
        this.costo = costo;
    }
}
