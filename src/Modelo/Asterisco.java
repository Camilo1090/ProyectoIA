/***********************************************
 * Camilo Ruiz Casanova - 1324486
 * Andres Felipe Polanco - 1324539
 * Universidad del Valle
 **********************************************/
package Modelo;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *
 * @author camilo
 */
public class Asterisco extends Busqueda
{
    /*Para los valores de la typeHeuristic asumiremos que 1 es la primera typeHeuristic y para
    cualquier otro valor la segunda typeHeuristic*/
    private PriorityQueue<Nodo> priorityQueue;
    private int tipoHeuristica;
    private int[] posMeta1;
    private int[] posMeta2;
    private int[] posMeta3;

    public Asterisco(int[][] matriz, int iniX, int iniY, int[] posMeta1, int[] posMeta2, int[] posMeta3, int tipoHeuristica)
    {
        //Se inicializan las variables
        this.matriz = matriz;
        this.iniX = iniX;
        this.iniY = iniY;
        this.posMeta1 = posMeta1;
        this.posMeta2 = posMeta2;
        this.posMeta3 = posMeta3;
        this.tipoHeuristica = tipoHeuristica;
        //Se inicializa la cola de prioridad con el formato de ordenamiento
        PQsort pqs = new PQsort();
        priorityQueue = new PriorityQueue<>(pqs);
        //Se añade el primer nodo a la cola de prioridad que en este caso seria el inicio
        if (tipoHeuristica == 1)
        {
            //Se añade el primer nodo a la cola de prioridad
            priorityQueue.offer(new Nodo(iniX, iniY, null, 0, true, calcularHeuristica1(this.getIniX(), this.getIniY(), posMeta1)));
        }
        else
        {
            //Se añade el primer nodo a la cola de prioridad
            priorityQueue.offer(new Nodo(iniX, iniY, null, 0, true, calcularHeuristica2(this.getIniX(), this.getIniY(), posMeta1)));
        }
    }

    /*Metodo encargado de realizar la busqueda, esta comienza y no para hasta que
    * encuentre la meta o la cola de prioridad quede vacia, que en este caso,
    * no se encontraria la meta*/
    public void busqueda()
    {
        boolean fin = false; //Variable que comprueba si a terminado
        int c = 0;
        
        while (!fin && getPriorityQueue().size() > 0)
        {
            Nodo nodo = getPriorityQueue().poll(); //Saca y remueve el nodo que se va a expandir
            //Se usa para hallar cual es la maxima profundidad
            actualizarProfundidad(nodo.getCamino().size() - 1); //Se le resta un 1 de el nodo raiz

            if (isGoal1(nodo) && !nodo.isMeta1())
            {
                //nodoMeta = nodo;
                //fin = true;
                nodo.setMeta1(true);
                nodo.setEvitar(false);
                //nodosExpandidos++;
                System.out.println("-----------------------------------------META1");
            }
            else if (isGoal2(nodo) && nodo.isMeta1() && !nodo.isMeta2())
            {
                //nodoMeta = nodo;
                //fin = true;
                nodo.setMeta2(true);
                nodo.setEvitar(false);
                //nodosExpandidos++;
                System.out.println("-----------------------------------------META2");
            }
            else if (isGoal3(nodo) && nodo.isMeta1() && nodo.isMeta2() && !nodo.isMeta3())
            {
                setNodoMeta(nodo);
                nodo.setMeta3(true);
                nodo.setEvitar(false);
                fin = true;
                setNodosExpandidos(getNodosExpandidos() + 1);
                System.out.println("-----------------------------------------META3");
            }
            
            if (!isAquaman(nodo) && !fin) // se comprueba que no sea un nodo aquaman o que no haya terminado ya la busqueda 
            { 
                expandir(nodo, 1);
                expandir(nodo, 2);
                expandir(nodo, 3);
                expandir(nodo, 4);
                setNodosExpandidos(getNodosExpandidos() + 1);
            }
            
            System.out.println(c);
            c++; 
        }
        //Se caulcula cual es el factor de ramificacion una vez a encontrado la meta
        setFactorRamificacion(calcularFactorRamificacion(getProfundidad(), getNodosCreados()));
    }

    // Metodo encargado de expandir un nodo en un operador determinado
    public void expandir(Nodo nodo, int operador)
    {
        int x;
        int y;
        
        switch (operador)
        {
            //Arriba
            case 1:
                x = nodo.getX() - 1;
                y = nodo.getY();
                break;
            //Derecha
            case 2:
                x = nodo.getX();
                y = nodo.getY() + 1;
                break;
            //Abajo
            case 3:
                x = nodo.getX() + 1;
                y = nodo.getY();
                break;
            //Izquierda
            case 4:
                x = nodo.getX();
                y = nodo.getY() - 1;
                break;
            default:
                x = nodo.getX();
                y = nodo.getY();
                break;
        }
        
        // Esta condicion comprueba que no se este devolviendo
        boolean seguir = true;
        if (nodo.getAnterior()[0] == x && nodo.getAnterior()[1] == y && nodo.isEvitar())
        {
            seguir = false;
        }
        
        if (posicionValida(x, y) && seguir)
        {            
            boolean bonus = isTurtle(nodo);
            
            // determina si ya se ha usado la tortuga
            for (int[] tortuga : nodo.getTortugas()) 
            {
                if (tortuga[0] == nodo.getX() && tortuga[1] == nodo.getY())
                {
                    bonus = false;
                    break;
                }
            }
            
            double costo = calcularCosto(x, y, nodo, nodo.getCosto(), (nodo.isBonus() || bonus));
            double heuristica = 0;
            
            if (getTipoHeuristica() == 1)
            {
                if (!nodo.isMeta1())
                {
                    heuristica = calcularHeuristica1(x, y, getPosMeta1());
                }
                else if (nodo.isMeta1() && !nodo.isMeta2())
                {
                    heuristica = calcularHeuristica1(x, y, getPosMeta2());
                }
                else if (nodo.isMeta1() && nodo.isMeta2() && !nodo.isMeta3())
                {
                    heuristica = calcularHeuristica1(x, y, getPosMeta3());
                }
            }
            else 
            {
                if (!nodo.isMeta1())
                {
                    heuristica = calcularHeuristica2(x, y, getPosMeta1());
                }
                else if (nodo.isMeta1() && !nodo.isMeta2())
                {
                    heuristica = calcularHeuristica2(x, y, getPosMeta2());
                }
                else if (nodo.isMeta1() && nodo.isMeta2() && !nodo.isMeta3())
                {
                    heuristica = calcularHeuristica2(x, y, getPosMeta3());
                }
            }
            
            //Se añade el nuevo nodo a la cola de prioridad
            if (bonus)
            {
                getPriorityQueue().offer(new Nodo(x, y, nodo, costo, new int[]{nodo.getX(), nodo.getY()}, true, heuristica));
            }
            else
            {
                getPriorityQueue().offer(new Nodo(x, y, nodo, costo, true, heuristica));
            }
            
            setNodosCreados(getNodosCreados() + 1);
        }
    }
    
    // Heuristica distancia en linea recta * 0.5
    public final double calcularHeuristica1(int posx, int posy, int[] meta)
    {
        double heuristica;
        double a = posx - meta[0];
        double b = posy - meta[1];
        heuristica = Math.sqrt((a*a) + (b*b)) * (0.5);
        
        return heuristica;
    }
    
    // Heuristica distancia en L * 0.5
    public final double calcularHeuristica2(int posx, int posy, int[] meta)
    {
        double heuristica;
        heuristica = calcularManhattan(posx, posy, meta) * (0.5);
        return heuristica;
    }

    // Metodo encargado de retornar la distancia en L
    public final int calcularManhattan(int posx, int posy, int[] meta)
    {
        int distanciaL;
        int distanciaX = Math.abs(posx - meta[0]);
        int distanciaY = Math.abs(posy - meta[1]);        
        distanciaL = distanciaX + distanciaY;

        return distanciaL;
    }

    /**
     * @return the priorityQueue
     */
    public PriorityQueue<Nodo> getPriorityQueue() {
        return priorityQueue;
    }

    /**
     * @param priorityQueue the priorityQueue to set
     */
    public void setPriorityQueue(PriorityQueue<Nodo> priorityQueue) {
        this.priorityQueue = priorityQueue;
    }

    /**
     * @return the tipoHeuristica
     */
    public int getTipoHeuristica() {
        return tipoHeuristica;
    }

    /**
     * @param tipoHeuristica the tipoHeuristica to set
     */
    public void setTipoHeuristica(int tipoHeuristica) {
        this.tipoHeuristica = tipoHeuristica;
    }

    /**
     * @return the posMeta1
     */
    public int[] getPosMeta1() {
        return posMeta1;
    }

    /**
     * @param posMeta1 the posMeta1 to set
     */
    public void setPosMeta1(int[] posMeta1) {
        this.posMeta1 = posMeta1;
    }

    /**
     * @return the posMeta2
     */
    public int[] getPosMeta2() {
        return posMeta2;
    }

    /**
     * @param posMeta2 the posMeta2 to set
     */
    public void setPosMeta2(int[] posMeta2) {
        this.posMeta2 = posMeta2;
    }

    /**
     * @return the posMeta3
     */
    public int[] getPosMeta3() {
        return posMeta3;
    }

    /**
     * @param posMeta3 the posMeta3 to set
     */
    public void setPosMeta3(int[] posMeta3) {
        this.posMeta3 = posMeta3;
    }

    //Subclase encargada de ordenar la cola de prioridad de acuerdo al costo
    private class PQsort implements Comparator<Nodo> 
    {
        /*Este metodo es el encargado de darle la instruccion a la cola de prioridad que se ordene
        * de cierta manera, ya sea que se ordene con respecto a el costo o a una typeHeuristic en especifico
        *
        * la manera en la que esta funciona es simple,
        * Si el primer valor a comparar es menor que el segundo entonces retorna un numero negativo.
        * Si ambos numeros tienen el mismo valor entonces retorna cero.
        * Si el primer valor es mayor que el segundo entonces retornara un numero positivo
        *
        * Si quisiesemos ordenar de forma ascendente lo normal seria que el primer valor restado con el segundo
        * diese un numero negativo*/
        @Override
        public int compare(Nodo a, Nodo b) 
        {
            double dif = a.getFn() - b.getFn();
            
            if (dif > 0)
            {
                return 1;
            }
            else if (dif < 0)
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }
    }
}
