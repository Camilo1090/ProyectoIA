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
    public boolean meta1; // booleanos de metas encontradas
    public boolean meta2;
    public boolean meta3;
    public int posMeta1; // guardan desde que elemento del arreglo del camino empieza cada meta
    public int posMeta2;
    private int x;
    private int y;
    private double costo;
    private ArrayList<int[]> camino;
    public ArrayList<int[]> tortugas;
    public int turnosBonus;
    public int[] anterior;
    public boolean evitar;
    private double heuristica;
    private double fn;

    //Este constructor se usa para la busqueda por costo uniforme pues no tiene en cuenta la heuristica
    public Nodo(int x, int y, Nodo padre, double costo, boolean evitar)
    {
        //Se inicializan las variables
        this.x = x;
        this.y = y;
        this.costo = costo;
        camino = new ArrayList<>();
        tortugas = new ArrayList<>();
        this.evitar = evitar;
        /*Esta condicion se usa para añadir el camino del padre al camino del nuevo nodo creado
        * si el padre es nulo entonces solo se agrega la posicion actual al camino*/
        if (padre != null)
        {
            camino.addAll(padre.getCamino());
            camino.add(new int[]{x,y});
            meta1 = padre.meta1;
            meta2 = padre.meta2;
            meta3 = padre.meta3;
            posMeta1 = padre.posMeta1;
            posMeta2 = padre.posMeta2;
            turnosBonus = padre.turnosBonus - 1;
            tortugas.addAll(padre.tortugas);
            anterior = new int[]{padre.getX(), padre.getY()};
        }
        else 
        {
            camino.add(new int[]{x,y});
            meta1 = false;
            meta2 = false;
            meta3 = false;
            posMeta1 = 0;
            posMeta2 = 0;
            turnosBonus = 0;
            anterior = new int[2];
        }
    }
    
    //Este constructor se usa para la busqueda por costo uniforme pues no tiene en cuenta la heuristica
    public Nodo(int x, int y, Nodo padre, double costo, int[] tortuga, boolean evitar)
    {
        //Se inicializan las variables
        this.x = x;
        this.y = y;
        this.costo = costo;
        camino = new ArrayList<>();
        tortugas = new ArrayList<>();
        turnosBonus = 3;
        this.evitar = evitar;
        /*Esta condicion se usa para añadir el camino del padre al camino del nuevo nodo creado
        * si el padre es nulo entonces solo se agrega la posicion actual al camino*/
        if (padre != null)
        {
            camino.addAll(padre.getCamino());
            camino.add(new int[]{x,y});
            meta1 = padre.meta1;
            meta2 = padre.meta2;
            meta3 = padre.meta3;
            posMeta1 = padre.posMeta1;
            posMeta2 = padre.posMeta2;
            tortugas.addAll(padre.tortugas);
            tortugas.add(tortuga);
            anterior = new int[]{padre.getX(), padre.getY()};
        }
        else 
        {
            camino.add(new int[]{x,y});
            meta1 = false;
            meta2 = false;
            meta3 = false;  
            posMeta1 = 0;
            posMeta2 = 0;          
            tortugas.add(tortuga);
            anterior = new int[2];
        }
    }
    
    //Este contructor es usado para la busqueda por A* pues se tiene en cuenta la heuristica
    public Nodo(int x, int y, Nodo padre, double costo, boolean evitar, double heuristica)
    {
        //Se inicializan las variables
        this.x = x;
        this.y = y;
        this.costo = costo;
        this.heuristica = heuristica;
        this.fn = this.costo + this.heuristica;
        camino = new ArrayList<>();
        tortugas = new ArrayList<>();
        /*Esta condicion se usa para añadir el camino del padre al camino del nuevo nodo creado
        * si el padre es nulo entonces solo se agrega la posicion actual al camino*/
        if (padre != null)
        {
            camino.addAll(padre.getCamino());
            camino.add(new int[]{x, y});
            meta1 = padre.meta1;
            meta2 = padre.meta2;
            meta3 = padre.meta3;
            posMeta1 = padre.posMeta1;
            posMeta2 = padre.posMeta2;
            turnosBonus = padre.turnosBonus - 1;
            tortugas.addAll(padre.tortugas);
            anterior = new int[]{padre.getX(), padre.getY()};
        }
        else 
        {
            camino.add(new int[]{x, y});
            meta1 = false;
            meta2 = false;
            meta3 = false;
            posMeta1 = 0;
            posMeta2 = 0;
            turnosBonus = 0;
            anterior = new int[2];
        }
    }

    //Este contructor es usado para la busqueda por A* pues se tiene en cuenta la heuristica
    public Nodo(int x, int y, Nodo padre, double costo, int[] tortuga, boolean evitar, double heuristica)
    {
        //Se inicializan las variables
        this.x = x;
        this.y = y;
        this.costo = costo;
        this.heuristica = heuristica;
        this.fn = this.costo + this.heuristica;
        camino = new ArrayList<>();
        tortugas = new ArrayList<>();
        turnosBonus = 3;
        /*Esta condicion se usa para añadir el camino del padre al camino del nuevo nodo creado
        * si el padre es nulo entonces solo se agrega la posicion actual al camino*/
        if (padre != null)
        {
            camino.addAll(padre.getCamino());
            camino.add(new int[]{x, y});
            meta1 = padre.meta1;
            meta2 = padre.meta2;
            meta3 = padre.meta3;
            posMeta1 = padre.posMeta1;
            posMeta2 = padre.posMeta2;
            tortugas.addAll(padre.tortugas);
            tortugas.add(tortuga);
            anterior = new int[]{padre.getX(), padre.getY()};
        }
        else 
        {
            camino.add(new int[]{x, y});
            meta1 = false;
            meta2 = false;
            meta3 = false;
            posMeta1 = 0;
            posMeta2 = 0;
            anterior = new int[2];
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
        return turnosBonus > 0;
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
