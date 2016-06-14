/***********************************************
 * Camilo Ruiz Casanova - 1324486
 * Andres Felipe Polanco - 1324539
 * Universidad del Valle
 **********************************************/
package Modelo;

import java.util.ArrayList;

/**
 *
 * @author camilo
 */
public class Nodo 
{
    private boolean meta1; // booleanos de metas encontradas
    private boolean meta2;
    private boolean meta3;
    private int posMeta1; // guardan desde que elemento del arreglo del camino empieza cada meta
    private int posMeta2;
    private int x;
    private int y;
    private double costo;
    private ArrayList<int[]> camino;
    private ArrayList<int[]> tortugas;
    private int turnosBonus;
    private int[] anterior;
    private boolean evitar;
    private double heuristica;
    private double fn;

    //Este constructor se usa para la busqueda por costo uniforme, amplitud y profundidad, pues no tienen en cuenta la heuristica
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
    
    //Este constructor se usa para la busqueda por costo uniforme, amplitud y profundidad, pues no tienen en cuenta la heuristica
    public Nodo(int x, int y, Nodo padre, double costo, int[] tortuga, boolean evitar)
    {
        //Se inicializan las variables
        this.x = x;
        this.y = y;
        this.costo = costo;
        camino = new ArrayList<>();
        tortugas = new ArrayList<>();
        turnosBonus = 4;
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
    
    //Este contructor es usado para la busqueda por A* y avara pues se tiene en cuenta la heuristica
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
        this.evitar = evitar;
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

    //Este contructor es usado para la busqueda por A* y avara pues se tiene en cuenta la heuristica
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
        turnosBonus = 4;
        this.evitar = evitar;
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
        return getTurnosBonus() > 0;
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

    /**
     * @return the meta1
     */
    public boolean isMeta1() {
        return meta1;
    }

    /**
     * @param meta1 the meta1 to set
     */
    public void setMeta1(boolean meta1) {
        this.meta1 = meta1;
    }

    /**
     * @return the meta2
     */
    public boolean isMeta2() {
        return meta2;
    }

    /**
     * @param meta2 the meta2 to set
     */
    public void setMeta2(boolean meta2) {
        this.meta2 = meta2;
    }

    /**
     * @return the meta3
     */
    public boolean isMeta3() {
        return meta3;
    }

    /**
     * @param meta3 the meta3 to set
     */
    public void setMeta3(boolean meta3) {
        this.meta3 = meta3;
    }

    /**
     * @return the posMeta1
     */
    public int getPosMeta1() {
        return posMeta1;
    }

    /**
     * @param posMeta1 the posMeta1 to set
     */
    public void setPosMeta1(int posMeta1) {
        this.posMeta1 = posMeta1;
    }

    /**
     * @return the posMeta2
     */
    public int getPosMeta2() {
        return posMeta2;
    }

    /**
     * @param posMeta2 the posMeta2 to set
     */
    public void setPosMeta2(int posMeta2) {
        this.posMeta2 = posMeta2;
    }

    /**
     * @return the tortugas
     */
    public ArrayList<int[]> getTortugas() {
        return tortugas;
    }

    /**
     * @param tortugas the tortugas to set
     */
    public void setTortugas(ArrayList<int[]> tortugas) {
        this.tortugas = tortugas;
    }

    /**
     * @return the turnosBonus
     */
    public int getTurnosBonus() {
        return turnosBonus;
    }

    /**
     * @param turnosBonus the turnosBonus to set
     */
    public void setTurnosBonus(int turnosBonus) {
        this.turnosBonus = turnosBonus;
    }

    /**
     * @return the anterior
     */
    public int[] getAnterior() {
        return anterior;
    }

    /**
     * @param anterior the anterior to set
     */
    public void setAnterior(int[] anterior) {
        this.anterior = anterior;
    }

    /**
     * @return the evitar
     */
    public boolean isEvitar() {
        return evitar;
    }

    /**
     * @param evitar the evitar to set
     */
    public void setEvitar(boolean evitar) {
        this.evitar = evitar;
    }
}
