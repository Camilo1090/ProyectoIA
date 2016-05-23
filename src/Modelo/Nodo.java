/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author camilo
 */
public class Nodo 
{
    public int[][] matriz;
    public boolean meta1;
    public boolean meta2;
    public boolean meta3;
    private int x;
    private int y;
    private double costo;
    private ArrayList<int[]> camino;
    private boolean bonus;
    private double heuristica;
    private double fn;

    //Este constructor se usa para la busqueda por costo uniforme pues no tiene en cuenta la heuristica
    public Nodo(int[][] matriz, int x, int y, Nodo padre, double costo, boolean bonus)
    {
        //Se inicializan las variables
        this.matriz = matriz;
        this.x = x;
        this.y = y;
        this.costo = costo;
        this.bonus = bonus;
        camino = new ArrayList<>();
        /*Esta condicion se usa para añadir el camino del padre al camino del nuevo nodo creado
        * si el padre es nulo entonces solo se agrega la posicion actual al camino*/
        if (padre != null)
        {
            camino.addAll(padre.getCamino());
            camino.add(new int[]{x,y});
            meta1 = padre.meta1;
            meta2 = padre.meta2;
            meta3 = padre.meta3;
        }
        else 
        {
            camino.add(new int[]{x,y});
            meta1 = false;
            meta2 = false;
            meta3 = false;
        }
    }

    //Este contructor es usado para la busqueda por A* pues se tiene en cuenta la heuristica
    public Nodo(int[][] matriz, int x, int y, Nodo padre, double costo, boolean bonus, int heuristica)
    {
        //Se inicializan las variables
        this.matriz = matriz;
        this.x = x;
        this.y = y;
        this.costo = costo;
        this.bonus = bonus;
        this.heuristica = heuristica;
        this.fn = this.costo + this.heuristica;
        camino = new ArrayList<>();
        /*Esta condicion se usa para añadir el camino del padre al camino del nuevo nodo creado
        * si el padre es nulo entonces solo se agrega la posicion actual al camino*/
        if (padre != null)
        {
            camino.addAll(padre.getCamino());
            camino.add(new int[]{x, y});
        }
        else 
        {
            camino.add(new int[]{x, y});
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public ArrayList<int[]> getCamino() {
        return camino;
    }

    public void setCamino(ArrayList<int[]> camino) {
        this.camino = camino;
    }

    public boolean isBonus() {
        return bonus;
    }

    public void setBonus(boolean isBonus) {
        this.bonus = isBonus;
    }

    public double getHeuristica() {
        return heuristica;
    }

    public void setHeuristica(double heuristica) {
        this.heuristica = heuristica;
    }

    public double getFn() {
        return fn;
    }

    public void setFn(double fn) {
        this.fn = fn;
    }
}