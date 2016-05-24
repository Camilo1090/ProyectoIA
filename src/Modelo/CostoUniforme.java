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
public class CostoUniforme extends Busqueda
{
    public CostoUniforme(int matriz[][], int iniX, int iniY)
    {
        this.matriz = matriz;
        this.iniX = iniX;
        this.iniY = iniY;
        //Se inicializa la cola de prioridad con el formato de ordenamiento
        PQsort pqs = new PQsort();
        priorityQueue = new PriorityQueue<>(pqs);
        //Se añade el primer nodo a la cola de prioridad que en este casi seria el inicio
        priorityQueue.offer(new Nodo(iniX, iniY, null, 0));
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

            if (isGoal1(nodo) && nodo.meta1)
            {
                nodoMeta = nodo;
                fin = true;
                nodo.meta1 = true;
                System.out.println("-----------------------------------------META1");
            }
            else if (isGoal2(nodo) && nodo.meta1 && !nodo.meta2)
            {
                nodoMeta = nodo;
                fin = true;
                nodo.meta2 = true;
                System.out.println("-----------------------------------------META2");
            }
            else if (isGoal3(nodo) && !nodo.meta1 && !nodo.meta2 && !nodo.meta3)
            {
                nodoMeta = nodo;
                nodo.meta3 = true;
                fin = true;
                System.out.println("-----------------------------------------META3");
                System.out.println("-----------------------------------------" + nodo.tortugas.size());
            }
            
            if (!isAquaman(nodo) && !fin) // //Se comprueba que el robot esta cargado para poder seguir expandiendo
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
        if (posicionValida(x, y))
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
            
            //Se añade el nuevo nodo a la cola de prioridad
            if (bonus)
            {
                priorityQueue.offer(new Nodo(x, y, nodo, costo, new int[]{nodo.getX(), nodo.getY()}));
            }
            else
            {
                priorityQueue.offer(new Nodo(x, y, nodo, costo));
            }
            
            nodosCreados++;
        }
    }

    //Subclase encargada de ordenar la cola de prioridad de acuerdo al costo
    static class PQsort implements Comparator<Nodo> 
    {
        /*Este metodo es el encargado de darle la instruccion a la cola de prioridad que se ordene
        * de cierta manera, ya sea que se ordene con respecto a el costo o a una typeHeuristic en especifico
        *
        * la manera en la que esta funciona es simple,
        * Si el primer valor a comparar es menor que el segundo entonces retorna un numero negativo.
        * Si ambos numeros tienen el mismo valor entonces retorna cero.
        * Si el primer valor es mayor que el segundo entonces retornara un numero positivo
        *
        * Si quisiesemos ordenar de forma acendente lo normal seria que el primer valor restado con el segundo
        * diese un numero negativo*/
        @Override
        public int compare(Nodo a, Nodo b) 
        {
            return (int) (a.getCosto() - b.getCosto());
        }
    }
}
