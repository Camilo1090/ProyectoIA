/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private final PriorityQueue<Nodo> priorityQueue;
    private final int tipoHeuristica;
    private final int[] posMeta1;
    private final int[] posMeta2;
    private final int[] posMeta3;

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
        priorityQueue.offer(new Nodo(iniX, iniY, null, 0, true, calcularManhattan(this.iniX, this.iniY, posMeta1))); //Se añade el primer nodo a la cola de prioridad
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

            if (isGoal1(nodo) && !nodo.meta1)
            {
                //nodoMeta = nodo;
                //fin = true;
                nodo.meta1 = true;
                nodo.evitar = false;
                //nodosExpandidos++;
                System.out.println("-----------------------------------------META1");
            }
            else if (isGoal2(nodo) && nodo.meta1 && !nodo.meta2)
            {
                nodoMeta = nodo;
                fin = true;
                nodo.meta2 = true;
                nodo.evitar = false;
                //nodosExpandidos++;
                System.out.println("-----------------------------------------META2");
            }
            else if (isGoal3(nodo) && nodo.meta1 && nodo.meta2 && !nodo.meta3)
            {
                nodoMeta = nodo;
                nodo.meta3 = true;
                nodo.evitar = false;
                fin = true;
                nodosExpandidos++;
                System.out.println("-----------------------------------------META3");
            }
            
            if (!isAquaman(nodo) && !fin) //Se comprueba que el robot esta cargado para poder seguir expandiendo
            { 
                expandir(nodo, 1);
                expandir(nodo, 2);
                expandir(nodo, 3);
                expandir(nodo, 4);
                nodosExpandidos++;
            }
            
            System.out.println(c);
            c++; 
        }
        //Se caulcula cual es el factor de ramificacion una vez a encontrado la meta
        factorRamificacion = calcularFactorRamificacion(profundidad, nodosCreados);
    }

    //Metodo encargado de expandir un nodo en una direccion determinada
    public void expandir(Nodo nodo, int direccion)
    {
        int x = 0;
        int y = 0;
        
        switch (direccion)
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
        
        /*Esta condicion comprueba que el nodo este cargado, que al lugar que se
        dirige es un acceso valido y que no lo halla recorrido antes*/
        boolean seguir = true;
        if (nodo.anterior[0] == x && nodo.anterior[1] == y && nodo.evitar)
        {
            seguir = false;
        }
        
        if (posicionValida(x, y) && seguir)
        {            
            boolean bonus = isTurtle(nodo);
            
            for (int[] tortuga : nodo.tortugas) 
            {
                if (tortuga[0] == nodo.getX() && tortuga[1] == nodo.getY())
                {
                    bonus = false;
                    break;
                }
            }
            
            double costo = calcularCosto(x, y, nodo, nodo.getCosto(), (nodo.isBonus() | bonus));
            double heuristica = 0;
            
            if (tipoHeuristica == 1)
            {
                if (!nodo.meta1)
                {
                    heuristica = calcularManhattan(x, y, posMeta1);
                }
                else if (nodo.meta1 && !nodo.meta2)
                {
                    heuristica = calcularManhattan(x, y, posMeta2);
                }
                else if (nodo.meta1 && nodo.meta2 && !nodo.meta3)
                {
                    heuristica = calcularManhattan(x, y, posMeta3);
                }
            }
            else 
            {
                if (!nodo.meta1)
                {
                    heuristica = calcularHeuristica(x, y, posMeta1);
                }
                else if (nodo.meta1 && !nodo.meta2)
                {
                    heuristica = calcularHeuristica(x, y, posMeta2);
                }
                else if (nodo.meta1 && nodo.meta2 && !nodo.meta3)
                {
                    heuristica = calcularHeuristica(x, y, posMeta3);
                }
            }
            
            //Se añade el nuevo nodo a la cola de prioridad
            if (bonus)
            {
                priorityQueue.offer(new Nodo(x, y, nodo, costo, new int[]{nodo.getX(), nodo.getY()}, true, heuristica));
            }
            else
            {
                priorityQueue.offer(new Nodo(x, y, nodo, costo, true, heuristica));
            }
            
            nodosCreados++;
        }
    }

    //Metodo encargado de retornar la distancia en L
    public int calcularManhattan(int posx, int posy, int[] meta)
    {
        int distanciaL;
        int distanciaX = Math.abs(posx - meta[0]);
        int distanciaY = Math.abs(posy - meta[1]);        
        distanciaL = distanciaX + distanciaY;

        return distanciaL;
    }
    //Heuristica distancia en L * (7 - charge)
    public double calcularHeuristica(int posx, int posy, int[] meta)
    {
        double heuristica;
        heuristica = calcularManhattan(posx, posy, meta) * (0.5);
        return heuristica;
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
            return (int) (a.getFn() - b.getFn());
        }
    }
}
