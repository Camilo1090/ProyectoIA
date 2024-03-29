/***********************************************
 * Camilo Ruiz Casanova - 1324486
 * Andres Martinez Polanco - 1324539
 * Universidad del Valle
 **********************************************/
package Modelo;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *
 * @author camilo
 */
public class Avara extends Busqueda
{
    private final PriorityQueue<Nodo> priorityQueue;
    private final int tipoHeuristica;
    private final int[] posMeta1;
    private final int[] posMeta2;
    private final int[] posMeta3;

    public Avara(int[][] matriz, int iniX, int iniY, int[] posMeta1, int[] posMeta2, int[] posMeta3, int tipoHeuristica)
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
        priorityQueue = new PriorityQueue(pqs);
        //Se agrega el primer nodo a la cola de prioridad que en este caso seria el inicio
        if (tipoHeuristica == 1)
        {
            //Se agrega el primer nodo a la cola de prioridad
            priorityQueue.offer(new Nodo(iniX, iniY, null, 0, true, calcularHeuristica1(this.getIniX(), this.getIniY(), posMeta1)));
        }
        else
        {
            //Se agrega el primer nodo a la cola de prioridad
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
        
        while (!fin && priorityQueue.size() > 0)
        {
            Nodo nodo = priorityQueue.poll(); //Saca y remueve el nodo que se va a expandir
            //Se usa para hallar cual es la maxima profundidad
            actualizarProfundidad(nodo.getCamino().size() - 1); //Se le resta un 1 de el nodo raiz

            if (isGoal1(nodo) && !nodo.isMeta1())
            {                
                nodo.setMeta1(true);
                nodo.setEvitar(false);
                nodo.setPosMeta1(nodo.getCamino().size());
                //nodoMeta = nodo;
                //fin = true;
                //nodosExpandidos++;
                System.out.println("-----------------------------------------META1");
            }
            else if (isGoal2(nodo) && nodo.isMeta1() && !nodo.isMeta2())
            {
                nodo.setMeta2(true);
                nodo.setEvitar(false);
                nodo.setPosMeta2(nodo.getCamino().size());
                //nodoMeta = nodo;
                //fin = true;
                //nodosExpandidos++;
                System.out.println("-----------------------------------------META2");
            }
            else if (isGoal3(nodo) && nodo.isMeta1() && nodo.isMeta2() && !nodo.isMeta3())
            {
                nodo.setMeta3(true);
                nodo.setEvitar(false);
                setNodoMeta(nodo);
                fin = true;
                setNodosExpandidos(getNodosExpandidos() + 1);
                System.out.println("-----------------------------------------META3");
            }
            
            if (!isAquaman(nodo) && !fin) //Se comprueba que no sea un nodo aquaman
            {
                //System.out.println("------------------NODO: (" + nodo.getX() + ", " + nodo.getY() + ") -- h = "+ nodo.getHeuristica());
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

    //Metodo encargado de expandir un nodo en un operado determinado
    public void expandir(Nodo nodo, int operador)
    {
        int x = 0;
        int y = 0;
        
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
        
        // variable para evitar devolverse o evitar ciclos
        boolean seguir = true;
        // evitar devolverse
//        if (nodo.anterior[0] == x && nodo.anterior[1] == y && nodo.evitar)
//        {
//            seguir = false;
//        }
        // evitar ciclos
        // el nodo contiene las posiciones de su rama
        // se verifica que la siguiente posicion no se encuentre entre dichas posiciones
        // esto se realiza por intervalos segun que metas se hayan encontrado
        if (nodo.isEvitar() && !nodo.isMeta1())
        {
            for (int[] pos : nodo.getCamino())
            {
                if (pos[0] == x && pos[1] == y)
                {
                    seguir = false;
                    break;
                }
            }
        }
        else if (nodo.isEvitar() && nodo.isMeta1() && !nodo.isMeta2())
        {
            for (int i = nodo.getPosMeta1(); i < nodo.getCamino().size(); i++)
            {
                if (nodo.getCamino().get(i)[0] == x && nodo.getCamino().get(i)[1] == y)
                {
                    seguir = false;
                    break;
                }
            }
        }
        else if (nodo.isEvitar() && nodo.isMeta1() && nodo.isMeta2() && !nodo.isMeta3())
        {
            for (int i = nodo.getPosMeta2(); i < nodo.getCamino().size(); i++)
            {
                if (nodo.getCamino().get(i)[0] == x && nodo.getCamino().get(i)[1] == y)
                {
                    seguir = false;
                    break;
                }
            }
        }
        
        if (posicionValida(x, y) && seguir)
        {            
            boolean bonus = (nodo.getTurnosBonus() - 1) > 0; // si lleva bonus de una tortuga anterior
            boolean turtle = isTurtle(nodo) && !(nodo.getTurnosBonus() > 0); // si esta en una tortuga y se puede coger su bonus
            
            // determina si ya se ha usado la tortuga
            if (turtle)
            {
                for (int[] tortuga : nodo.getTortugas()) 
                {
                    if (tortuga[0] == nodo.getX() && tortuga[1] == nodo.getY())
                    {
                        turtle = false;
                        break;
                    }
                }
            }
            
            double costo = calcularCosto(x, y, nodo, nodo.getCosto(), (bonus || turtle));
            double heuristica = 0;
            
            // tipo de heuristica a usar
            // dependiendo de que meta se busque se calcula una heuristica
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
            else if (getTipoHeuristica() == 2)
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
            else
            {
                if (!nodo.isMeta1())
                {
                    heuristica = calcularHeuristica3(x, y, 1);
                }
                else if (nodo.isMeta1() && !nodo.isMeta2())
                {
                    heuristica = calcularHeuristica3(x, y, 2);
                }
                else if (nodo.isMeta1() && nodo.isMeta2() && !nodo.isMeta3())
                {
                    heuristica = calcularHeuristica3(x, y, 3);
                }
            }
            
            //Se agrega el nuevo nodo a la cola de prioridad
            //Si se usa una tortuga, se guarda para no usarla de nuevo
            if (turtle)
            {
                getPriorityQueue().offer(new Nodo(x, y, nodo, costo, new int[]{nodo.getX(), nodo.getY()}, true, heuristica));
            }
            else
            {
                getPriorityQueue().offer(new Nodo(x, y, nodo, costo, true, heuristica));
            }
            
            setNodosCreados(getNodosCreados() + 1); // se aumenta la cantidad de nodos creados
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
        heuristica = calcularManhattan(new int[]{posx, posy}, meta) * (0.5);
        return heuristica;
    }
    
    // Heuristica basada en las 3 metas
    public final double calcularHeuristica3(int posx, int posy, int meta)
    {
        double heuristica;
        
        switch (meta) 
        {
            case 1:
                heuristica = (calcularManhattan(new int[]{posx, posy}, getPosMeta1()) * (0.5)) + 
                        (calcularManhattan(getPosMeta1(), getPosMeta2()) * (0.5)) + 
                        (calcularManhattan(getPosMeta2(), getPosMeta3()) * (0.5));
                break;
            case 2:
                heuristica = (calcularManhattan(new int[]{posx, posy}, getPosMeta2()) * (0.5)) + 
                        (calcularManhattan(getPosMeta2(), getPosMeta3()) * (0.5));
                break;
            default:
                heuristica = calcularManhattan(new int[]{posx, posy}, getPosMeta3()) * (0.5);
                break;
        }
        
        return heuristica;
    }

    // Metodo encargado de retornar la distancia en L
    public final int calcularManhattan(int[] pos, int[] meta)
    {
        int distanciaL;
        int distanciaX = Math.abs(pos[0] - meta[0]);
        int distanciaY = Math.abs(pos[1] - meta[1]);        
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
     * @return the tipoHeuristica
     */
    public int getTipoHeuristica() {
        return tipoHeuristica;
    }

    /**
     * @return the posMeta1
     */
    public int[] getPosMeta1() {
        return posMeta1;
    }

    /**
     * @return the posMeta2
     */
    public int[] getPosMeta2() {
        return posMeta2;
    }

    /**
     * @return the posMeta3
     */
    public int[] getPosMeta3() {
        return posMeta3;
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
            double dif = a.getHeuristica() - b.getHeuristica();
            
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
