/***********************************************
 * Camilo Ruiz Casanova - 1324486
 * Andres Felipe Polanco - 1324539
 * Universidad del Valle
 **********************************************/
package Modelo;

import java.util.Stack;

/**
 *
 * @author Camilo Ruiz Casanova
 */
public class PreferenteProfundidad extends Busqueda
{
    private final Stack<Nodo> pila;
    private final int[] orden;
    
    public PreferenteProfundidad(int matriz[][], int iniX, int iniY, int[] orden)
    {
        this.matriz = matriz;
        this.iniX = iniX;
        this.iniY = iniY;
        this.orden = orden;
        
        pila = new Stack<>();
        //Se añade el primer nodo a la pila que en este caso seria el inicio
        pila.push(new Nodo(iniX, iniY, null, 0, true));
    }

    /*Metodo encargado de realizar la busqueda, esta comienza y no para hasta que
    * encuentre la meta o la pila quede vacia, que en este caso,
    * no se encontraria la meta*/
    public void busqueda()
    {
        boolean fin = false; //Variable que comprueba si ha terminado la busqueda
        int c = 0; // variable para contar cuantos ciclos de busqueda se hacen
        
        while (!fin && pila.size() > 0)
        {
            Nodo nodo = pila.pop(); //Saca y remueve el nodo que se va a expandir
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
                setNodoMeta(nodo);
                nodo.setMeta3(true);
                nodo.setEvitar(false);
                fin = true;
                setNodosExpandidos(getNodosExpandidos() + 1);
                System.out.println("-----------------------------------------META3");
            }
            
            if (!isAquaman(nodo) && !fin) //Se comprueba que no sea un nodo aquaman
            {
                //System.out.println("------------------NODO: (" + nodo.getX() + ", " + nodo.getY() + ")");
                expandir(nodo, orden[3]);
                expandir(nodo, orden[2]);
                expandir(nodo, orden[1]);
                expandir(nodo, orden[0]);
                setNodosExpandidos(getNodosExpandidos() + 1);
            }
            
//            if (c > 100)
//            {
//                break;
//            }
            
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
        
        // variable para evitar devolverse o evitar ciclos
        boolean seguir = true;
        // evitar devolverse
//        if (nodo.anterior[0] == x && nodo.anterior[1] == y && nodo.evitar)
//        {
//            seguir = false;
//        }
        // evitar ciclos
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
            boolean bonus = (nodo.getTurnosBonus() - 1) > 0;
            boolean turtle = isTurtle(nodo) && !(nodo.getTurnosBonus() > 0);
            
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
            
            //Se añade el nuevo nodo a la pila
            if (turtle)
            {
                pila.push(new Nodo(x, y, nodo, costo, new int[]{nodo.getX(), nodo.getY()}, true));
            }
            else
            {
                pila.push(new Nodo(x, y, nodo, costo, true));
            }
            
            setNodosCreados(getNodosCreados() + 1);
        }
    }
}
