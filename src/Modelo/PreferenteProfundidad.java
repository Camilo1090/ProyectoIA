/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.Stack;

/**
 *
 * @author Camilo Ruiz Casanova
 */
public class PreferenteProfundidad extends Busqueda
{
    private final int[] orden;
    
    public PreferenteProfundidad(int matriz[][], int iniX, int iniY, int[] orden)
    {
        this.matriz = matriz;
        this.iniX = iniX;
        this.iniY = iniY;
        this.orden = orden;
        
        pila = new Stack<>();
        //Se añade el primer nodo a la pila de prioridad que en este caso seria el inicio
        pila.push(new Nodo(iniX, iniY, null, 0, true));
    }

    /*Metodo encargado de realizar la busqueda, esta comienza y no para hasta que
    * encuentre la meta o la pila de prioridad quede vacia, que en este caso,
    * no se encontraria la meta*/
    public void busqueda()
    {
        boolean fin = false; //Variable que comprueba si a terminado
        int c = 0;
        
        while (!fin && pila.size() > 0)
        {
            Nodo nodo = pila.pop(); //Saca y remueve el nodo que se va a expandir
            //Se usa para hallar cual es la maxima profundidad
            actualizarProfundidad(nodo.getCamino().size() - 1); //Se le resta un 1 de el nodo raiz
            
            if (isGoal1(nodo) && !nodo.meta1)
            {
                //nodoMeta = nodo;
                //fin = true;
                nodo.meta1 = true;
                nodo.evitar = false;
                nodo.posMeta1 = nodo.getCamino().size();
                System.out.println("-----------------------------------------META1");
            }
            else if (isGoal2(nodo) && nodo.meta1 && !nodo.meta2)
            {
                //nodoMeta = nodo;
                //fin = true;
                nodo.meta2 = true;
                nodo.evitar = false;
                nodo.posMeta2 = nodo.getCamino().size();
                System.out.println("-----------------------------------------META2");
            }
            else if (isGoal3(nodo) && nodo.meta1 && nodo.meta2 && !nodo.meta3)
            {
                nodoMeta = nodo;
                nodo.meta3 = true;
                nodo.evitar = false;
                fin = true;
                System.out.println("-----------------------------------------META3");
            }
            
            if (!isAquaman(nodo) && !fin) //Se comprueba que el robot esta cargado para poder seguir expandiendo
            { 
                expandir(nodo, orden[3]);
                expandir(nodo, orden[2]);
                expandir(nodo, orden[1]);
                expandir(nodo, orden[0]);
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
        // evitar devolverse
//        if (nodo.anterior[0] == x && nodo.anterior[1] == y && nodo.evitar)
//        {
//            seguir = false;
//        }
        // evitar ciclos
        if (nodo.evitar && !nodo.meta1)
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
        else if (nodo.evitar && nodo.meta1 && !nodo.meta2)
        {
            for (int i = nodo.posMeta1; i < nodo.getCamino().size(); i++)
            {
                if (nodo.getCamino().get(i)[0] == x && nodo.getCamino().get(i)[1] == y)
                {
                    seguir = false;
                    break;
                }
            }
        }
        else if (nodo.evitar && nodo.meta1 && nodo.meta2 && !nodo.meta3)
        {
            for (int i = nodo.posMeta2; i < nodo.getCamino().size(); i++)
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
            
            //Se añade el nuevo nodo a la pila de prioridad
            if (bonus)
            {
                pila.push(new Nodo(x, y, nodo, costo, new int[]{nodo.getX(), nodo.getY()}, true));
            }
            else
            {
                pila.push(new Nodo(x, y, nodo, costo, true));
            }
            
            nodosCreados++;
        }
    }
}
