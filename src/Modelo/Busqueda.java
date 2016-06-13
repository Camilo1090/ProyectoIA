/***********************************************
 * Camilo Ruiz Casanova - 1324486
 * Andres Felipe Polanco - 1324539
 * Universidad del Valle
 **********************************************/
package Modelo;



/**
 *
 * @author camilo
 */
public class Busqueda 
{
    protected int[][] matriz;
    protected int nodosExpandidos;
    protected int nodosCreados;
    protected int profundidad;
    protected double factorRamificacion;
    protected Nodo nodoMeta;

    protected int iniX;
    protected int iniY;
    protected int endX;
    protected int endY;

    public Busqueda() 
    {
        //Se inicializan las variables
        nodoMeta = null;
        nodosCreados = 0;
        nodosExpandidos = 0;
        profundidad = 0;
        factorRamificacion = 0;
        nodoMeta = new Nodo(getIniX(), getIniY(), null, 0, true);
    }

    //Metodo encargado de retornar true si la posicion es valida
    public boolean posicionValida(int posX, int posY)
    {
        boolean valido = true;
        
        try 
        {
            if (getMatriz()[posX][posY] == 1)
            {
                valido = false;
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            valido = false;
        }
        
        return valido;
    }

    //Metodo encargado de comprobar si es un nodo meta
    public boolean isGoal1(Nodo nodo)
    {
        boolean isGoal = false;
        
        if (posicionValida(nodo.getX(), nodo.getY()))
        {
            if (getMatriz()[nodo.getX()][nodo.getY()] == 7)
            {
                isGoal = true;
            }
        }
        
        return isGoal;
    }
    
    //Metodo encargado de comprobar si es un nodo meta
    public boolean isGoal2(Nodo nodo)
    {
        boolean isGoal = false;
        
        if (posicionValida(nodo.getX(), nodo.getY()))
        {
            if (getMatriz()[nodo.getX()][nodo.getY()] == 6)
            {
                isGoal = true;
            }
        }
        
        return isGoal;
    }
    
    //Metodo encargado de comprobar si es un nodo meta
    public boolean isGoal3(Nodo nodo)
    {
        boolean isGoal = false;
        
        if (posicionValida(nodo.getX(), nodo.getY()))
        {
            if (getMatriz()[nodo.getX()][nodo.getY()] == 5)
            {
                isGoal = true;
            }
        }
        
        return isGoal;
    }
    
    //Metodo encargado de comprobar si es un nodo tortuga
    public boolean isTurtle(Nodo nodo)
    {
        boolean isTurtle = false;
        
        if (posicionValida(nodo.getX(), nodo.getY()))
        {
            if (getMatriz()[nodo.getX()][nodo.getY()] == 4)
            {
                isTurtle = true;
            }
        }
        
        return isTurtle;
    }
    
    //Metodo encargado de comprobar si es un nodo tortuga
    public boolean isTurtle(int[] pos)
    {
        boolean isTurtle = false;
        
        if (posicionValida(pos[0], pos[1]))
        {
            if (getMatriz()[pos[0]][pos[1]] == 4)
            {
                isTurtle = true;
            }
        }
        
        return isTurtle;
    }
    
    //Metodo encargado de comprobar si es un nodo aquaman
    public boolean isAquaman(Nodo nodo)
    {
        boolean isAquaman = false;
        
        if (posicionValida(nodo.getX(), nodo.getY()))
        {
            if (getMatriz()[nodo.getX()][nodo.getY()] == 8)
            {
                isAquaman = true;
            }
        }
        
        return isAquaman;
    }

    /*Metodo encargado de calcular el factor de ramificacion segun la profundidad y los nodos expandidos
    * Esta se calcula segun la formula dada por el profesor:
    * profundidad = p
    * sumatoria desde i = 0 hasta p de b^i es igual a (b^(profundidad+1) - 1)/( 1)*/
    public double calcularFactorRamificacion(int profudidad, int nodos)
    {
        double factRam;
        factRam = Math.pow((nodos + 1.0), 1.0 / (profudidad + 1.0));
        factRam = Math.round(factRam*10000.0)/10000.0; //Redondea el numero a 4 cifras significativas
        return factRam;
    }

    //Metodo de encargado de Actualizar la profundidad para calcular la mayor profundidad
    public void actualizarProfundidad(int profundidad)
    {
        if (profundidad > this.getProfundidad())
        {
            this.setProfundidad(profundidad);
        }
    }

    //Metodo ecargado de calcular los costos en una posicion dada
    public double calcularCosto(int posX, int posY, Nodo nodo, double costo, boolean isBonus)
    {
        double c = 0;
        
        switch (getMatriz()[posX][posY])
        {
            case 0:
                c = 1;
                break;
            // Espacio libre
            case 2:
                c = 1;
                break;
            // Tiburon
            case 3:
                c = 10;
                break;
            // Tortuga como espacio libre
            case 4:
                c = 1;
                break;
            // Dory como espacio libre
            case 5:
                c = 1;
                break;
            // Marlin como espacion libre
            case 6:
                c = 1;
                break;
            // Nemo como espacio libre
            case 7:
                c = 1;
                break;
            case 8:
                return Double.MAX_VALUE;
            default:
                break;
        }
        
        if (isBonus)
            c /= 2;
        
        costo += c;
        
        return costo;
    }

    public int getNodosExpandidos() {
        return nodosExpandidos;
    }

    public void setNodosExpandidos(int nodosExpandidos) {
        this.nodosExpandidos = nodosExpandidos;
    }

    public int getNodosCreados() {
        return nodosCreados;
    }

    public void setNodosCreados(int nodosCreados) {
        this.nodosCreados = nodosCreados;
    }

    public int getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }

    public double getFactorRamificacion() {
        return factorRamificacion;
    }

    public void setFactorRamificacion(double factorRamificacion) {
        this.factorRamificacion = factorRamificacion;
    }

    public Nodo getNodoMeta() {
        return nodoMeta;
    }

    public void setNodoMeta(Nodo nodoMeta) {
        this.nodoMeta = nodoMeta;
    }

    /**
     * @return the matriz
     */
    public int[][] getMatriz() {
        return matriz;
    }

    /**
     * @param matriz the matriz to set
     */
    public void setMatriz(int[][] matriz) {
        this.matriz = matriz;
    }

    /**
     * @return the iniX
     */
    public int getIniX() {
        return iniX;
    }

    /**
     * @param iniX the iniX to set
     */
    public void setIniX(int iniX) {
        this.iniX = iniX;
    }

    /**
     * @return the iniY
     */
    public int getIniY() {
        return iniY;
    }

    /**
     * @param iniY the iniY to set
     */
    public void setIniY(int iniY) {
        this.iniY = iniY;
    }

    /**
     * @return the endX
     */
    public int getEndX() {
        return endX;
    }

    /**
     * @param endX the endX to set
     */
    public void setEndX(int endX) {
        this.endX = endX;
    }

    /**
     * @return the endY
     */
    public int getEndY() {
        return endY;
    }

    /**
     * @param endY the endY to set
     */
    public void setEndY(int endY) {
        this.endY = endY;
    }
}
