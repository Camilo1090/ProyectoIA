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
public class CostoUniforme extends Busqueda
{
    private final PriorityQueue<Nodo> priorityQueue;
    
    public CostoUniforme(int matriz[][], int iniX, int iniY)
    {
        this.matriz = matriz;
        this.iniX = iniX;
        this.iniY = iniY;
        //Se inicializa la cola de prioridad con el formato de ordenamiento
        PQsort pqs = new PQsort();
        priorityQueue = new PriorityQueue<>(pqs);
        //Se añade el primer nodo a la cola de prioridad que en este casi seria el inicio
        priorityQueue.offer(new Nodo(iniX, iniY, null, 0, true));
    }

    /*Metodo encargado de realizar la busqueda, esta comienza y no para hasta que
    * encuentre la meta o la cola de prioridad quede vacia, que en este caso,
    * no se encontraria la meta*/
    public void busqueda()
    {
        boolean fin = false; //Variable que comprueba si ha terminado
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
                //nodoMeta = nodo;
                //fin = true;
                //nodosExpandidos++;
                System.out.println("-----------------------------------------META1");
            }
            else if (isGoal2(nodo) && nodo.isMeta1() && !nodo.isMeta2())
            {
                nodo.setMeta2(true);
                nodo.setEvitar(false);
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

    //Metodo encargado de expandir un nodo en un operador determinado
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
        
        /*Esta condicion comprueba que no se este devolviendo*/
        boolean seguir = true;
        if (nodo.getAnterior()[0] == x && nodo.getAnterior()[1] == y && nodo.isEvitar())
        {
            seguir = false;
        }
        
        if (posicionValida(x, y) && seguir)
        {            
            boolean bonus = isTurtle(nodo);
            
            for (int[] tortuga : nodo.getTortugas()) 
            {
                if (tortuga[0] == nodo.getX() && tortuga[1] == nodo.getY())
                {
                    bonus = false;
                    break;
                }
            }
            
            double costo = calcularCosto(x, y, nodo, nodo.getCosto(), (nodo.isBonus() || bonus));
            
            //Se añade el nuevo nodo a la cola de prioridad
            if (bonus)
            {
                priorityQueue.offer(new Nodo(x, y, nodo, costo, new int[]{nodo.getX(), nodo.getY()}, true));
            }
            else
            {
                priorityQueue.offer(new Nodo(x, y, nodo, costo, true));
            }
            
            setNodosCreados(getNodosCreados() + 1);
        }
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
        * Si quisiesemos ordenar de forma acendente lo normal seria que el primer valor restado con el segundo
        * diese un numero negativo*/
        @Override
        public int compare(Nodo a, Nodo b) 
        {
            return (int) (a.getCosto() - b.getCosto());
        }
    }
}
