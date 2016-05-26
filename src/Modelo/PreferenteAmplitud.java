/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.LinkedList;


/**
 *
 * @author camilo
 */
public class PreferenteAmplitud extends Busqueda
{
    private final LinkedList<Nodo> cola;
    
    public PreferenteAmplitud(int matriz[][], int iniX, int iniY)
    {
        this.matriz = matriz;
        this.iniX = iniX;
        this.iniY = iniY;
        
        cola = new LinkedList<>();
        //Se añade el primer nodo a la cola de prioridad que en este casi seria el inicio
        cola.offer(new Nodo(iniX, iniY, null, 0, true));
    }

    /*Metodo encargado de realizar la busqueda, esta comienza y no para hasta que
    * encuentre la meta o la cola de prioridad quede vacia, que en este caso,
    * no se encontraria la meta*/
    public void busqueda()
    {
        boolean fin = false; //Variable que comprueba si a terminado
        int c = 0;
        
        while (!fin && cola.size() > 0)
        {
            Nodo nodo = cola.poll(); //Saca y remueve el nodo que se va a expandir
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
            
            if (!isAquaman(nodo) && !fin) //Se comprueba que el robot esta cargado para poder seguir expandiendo
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

    //Metodo encargado de expandir un nodo en una operador determinada
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
        
        /*Esta condicion comprueba que el nodo este cargado, que al lugar que se
        dirige es un acceso valido y que no lo halla recorrido antes*/
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
            
            double costo = calcularCosto(x, y, nodo, nodo.getCosto(), (nodo.isBonus() | bonus));
            
            //Se añade el nuevo nodo a la cola de prioridad
            if (bonus)
            {
                cola.offer(new Nodo(x, y, nodo, costo, new int[]{nodo.getX(), nodo.getY()}, true));
            }
            else
            {
                cola.offer(new Nodo(x, y, nodo, costo, true));
            }
            
            setNodosCreados(getNodosCreados() + 1);
        }
    }
}
