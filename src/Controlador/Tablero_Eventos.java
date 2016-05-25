/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.CostoUniforme;
import Modelo.PreferenteAmplitud;
import Modelo.PreferenteProfundidad;
import Vista.Tablero;
import java.awt.event.ActionEvent;

/**
 *
 * @author camilo
 */
public class Tablero_Eventos 
{
    private final Tablero tablero;
    private final String algoritmo;
    private int heuristica;
    private String solucion;
    private int[] orden;
    
    public Tablero_Eventos(final Tablero tablero)
    {
        this.tablero = tablero;
        this.algoritmo = tablero.getAlgoritmo();
        this.solucion = "";
        this.orden = new int[4];
        
        this.tablero.bAnterior.addActionListener(
            (ActionEvent ae) -> 
            {
                this.tablero.anterior();
            }
        );
        
        this.tablero.bSiguiente.addActionListener(
            (ActionEvent ae) -> 
            {
                this.tablero.siguiente();
            }
        );
        
        this.tablero.bReiniciar.addActionListener(
            (ActionEvent ae) -> 
            {
                this.tablero.reiniciar();
            }
        );
        
        this.tablero.bCerrar.addActionListener(
            (ActionEvent ae) -> 
            {
                cerrarVentana();
            }
        );
    }
    
    //Metodo encargado de cargar el mapa dependiendo de que busqueda se a seleccionado
    public void realizarBusqueda()
    {
//        if (this.algoritmo.equals("A*") && this.heuristica == 1)
//        {
//            BusquedaAsterisco busquedaAsterisco = new BusquedaAsterisco(
//                    tablero.getMapa().getPositionsMap(),
//                    potitionInitial[0],
//                    potitionInitial[1],
//                    potitionEnd[0],
//                    potitionEnd[1],
//                    1 //Indica que es la heuristica 1
//            );
//            busquedaAsterisco.busqueda(); //Se realiza la busqueda
//            //Se carga el camino en la vista para que el robot lo recorra
//            tablero.cargarCamino(busquedaAsterisco.getNodoMeta().getPath());
//        }
//        else if (this.algoritmo.equals("A*") && this.heuristica == 2)
//        {
//            //Se busca el inicio y el fin de la buqueda
//            int potitionInitial[] = new Aux().findPosStart(tablero.getMapa().getPositionsMap());
//            int potitionEnd[] = new Aux().findPosEnd(tablero.getMapa().getPositionsMap());
//            BusquedaAsterisco busquedaAsterisco = new BusquedaAsterisco(
//                    tablero.getMapa().getPositionsMap(),
//                    potitionInitial[0],
//                    potitionInitial[1],
//                    potitionEnd[0],
//                    potitionEnd[1],
//                    2 //Indica que es la heuristica 2
//            );
//            busquedaAsterisco.busqueda(); //Se realiza la busqueda
//            //Se carga el camino en la vista para que el robot lo recorra
//            tablero.loadPath(busquedaAsterisco.getNodoMeta().getPath());
//        }
//        else if (this.algoritmo.equals("Avara") && this.heuristica == 1)
//        {
//            //Se busca el inicio y el fin de la buqueda
//            int potitionInitial[] = new Aux().findPosStart(tablero.getMapa().getPositionsMap());
//            int potitionEnd[] = new Aux().findPosEnd(tablero.getMapa().getPositionsMap());
//            BusquedaAsterisco busquedaAsterisco = new BusquedaAsterisco(
//                    tablero.getMapa().getPositionsMap(),
//                    potitionInitial[0],
//                    potitionInitial[1],
//                    potitionEnd[0],
//                    potitionEnd[1],
//                    1 //Indica que es la heuristica 1
//            );
//            busquedaAsterisco.busqueda(); //Se realiza la busqueda
//            //Se carga el camino en la vista para que el robot lo recorra
//            tablero.loadPath(busquedaAsterisco.getNodoMeta().getPath());
//        }
//        else if (this.algoritmo.equals("Avara") && this.heuristica == 2)
//        {
//            //Se busca el inicio y el fin de la buqueda
//            int potitionInitial[] = new Aux().findPosStart(tablero.getMapa().getPositionsMap());
//            int potitionEnd[] = new Aux().findPosEnd(tablero.getMapa().getPositionsMap());
//            BusquedaAsterisco busquedaAsterisco = new BusquedaAsterisco(
//                    tablero.getMapa().getPositionsMap(),
//                    potitionInitial[0],
//                    potitionInitial[1],
//                    potitionEnd[0],
//                    potitionEnd[1],
//                    2 //Indica que es la heuristica 2
//            );
//            busquedaAsterisco.busqueda(); //Se realiza la busqueda
//            //Se carga el camino en la vista para que el robot lo recorra
//            tablero.loadPath(busquedaAsterisco.getNodoMeta().getPath());
//        }
        if (this.algoritmo.equals("Costo Uniforme"))
        {
            CostoUniforme cu1 = new CostoUniforme(
                    tablero.getMapa().getPositionsMap(),
                    tablero.getMapa().getInitPos()[0],
                    tablero.getMapa().getInitPos()[1]
            );
            
            long t = System.currentTimeMillis();
            cu1.busqueda();
            t = System.currentTimeMillis() - t;
            double time = t/1000.0;
            
            //Se carga el camino en la vista para que el robot lo recorra
            tablero.cargarCamino(cu1.getNodoMeta().getCamino());
            
            solucion += "Tiempo: " + time + " s\n";
            solucion += "Pasos Solucion:";
            String pasos = "";
            for (int[] pos : cu1.getNodoMeta().getCamino()) {
                pasos += " (" + pos[0] + ", " + pos[1] + ") -->";
            }
            solucion += pasos.substring(0, pasos.length() - 4);
            solucion += "\nNumero de Nodos Expandidos: " + cu1.getNodosExpandidos() + "\n";
            solucion += "Numero de Nodos Creados: " + cu1.getNodosCreados() + "\n";
            solucion += "Costo Total de la Solucion: " + cu1.getNodoMeta().getCosto() + "\n";
            solucion += "Factor de Ramificacion: " + cu1.getFactorRamificacion() + "\n";
            solucion += "Profundidad del Arbol: " + cu1.getProfundidad() + "\n";
            
            this.tablero.taSolucion.setText(solucion);
        }
        else if (this.algoritmo.equals("Preferente por Amplitud"))
        {
            PreferenteAmplitud pa1 = new PreferenteAmplitud(
                    tablero.getMapa().getPositionsMap(),
                    tablero.getMapa().getInitPos()[0],
                    tablero.getMapa().getInitPos()[1]
            );
            
            long t = System.currentTimeMillis();
            pa1.busqueda();
            t = System.currentTimeMillis() - t;
            double time = t/1000.0;
            
            //Se carga el camino en la vista para que el robot lo recorra
            tablero.cargarCamino(pa1.getNodoMeta().getCamino());
            
            solucion += "Tiempo: " + time + " s\n";
            solucion += "Pasos Solucion:";
            String pasos = "";
            for (int[] pos : pa1.getNodoMeta().getCamino()) {
                pasos += " (" + pos[0] + ", " + pos[1] + ") -->";
            }
            solucion += pasos.substring(0, pasos.length() - 4);
            solucion += "\nNumero de Nodos Expandidos: " + pa1.getNodosExpandidos() + "\n";
            solucion += "Numero de Nodos Creados: " + pa1.getNodosCreados() + "\n";
            solucion += "Costo Total de la Solucion: " + pa1.getNodoMeta().getCosto() + "\n";
            solucion += "Factor de Ramificacion: " + pa1.getFactorRamificacion() + "\n";
            solucion += "Profundidad del Arbol: " + pa1.getProfundidad() + "\n";
            
            this.tablero.taSolucion.setText(solucion);
        }
        else if (this.algoritmo.equals("Preferente por Profundidad"))
        {
            PreferenteProfundidad pp1 = new PreferenteProfundidad(
                    tablero.getMapa().getPositionsMap(),
                    tablero.getMapa().getInitPos()[0],
                    tablero.getMapa().getInitPos()[1],
                    orden
            );
            
            long t = System.currentTimeMillis();
            pp1.busqueda();
            t = System.currentTimeMillis() - t;
            double time = t/1000.0;
            
            //Se carga el camino en la vista para que el robot lo recorra
            tablero.cargarCamino(pp1.getNodoMeta().getCamino());
            
            solucion += "Tiempo: " + time + " s\n";
            solucion += "Pasos Solucion:";
            String pasos = "";
            for (int[] pos : pp1.getNodoMeta().getCamino()) {
                pasos += " (" + pos[0] + ", " + pos[1] + ") -->";
            }
            solucion += pasos.substring(0, pasos.length() - 4);
            solucion += "\nNumero de Nodos Expandidos: " + pp1.getNodosExpandidos() + "\n";
            solucion += "Numero de Nodos Creados: " + pp1.getNodosCreados() + "\n";
            solucion += "Costo Total de la Solucion: " + pp1.getNodoMeta().getCosto() + "\n";
            solucion += "Factor de Ramificacion: " + pp1.getFactorRamificacion() + "\n";
            solucion += "Profundidad del Arbol: " + pp1.getProfundidad() + "\n";
            
            this.tablero.taSolucion.setText(solucion);
        }
    }
    
    public void cerrarVentana()
    {
        this.tablero.setVisible(false);
    }

    public int getHeuristica() {
        return heuristica;
    }

    public void setHeuristica(int heuristica) {
        this.heuristica = heuristica;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public int[] getOrden() {
        return orden;
    }

    public void setOrden(int[] orden) {
        this.orden = orden;
    }
}
