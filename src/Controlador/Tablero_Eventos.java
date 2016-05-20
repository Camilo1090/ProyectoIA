/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.Tablero;

/**
 *
 * @author camilo
 */
public class Tablero_Eventos 
{
    private final Tablero tablero;
    private final String algoritmo;
    private int heuristica;
    
    public Tablero_Eventos(final Tablero tablero)
    {
        this.tablero = tablero;
        this.algoritmo = tablero.getAlgoritmo();
    }
    
    //Metodo encargado de cargar el mapa dependiendo de que busqueda se a seleccionado
    public void realizarBusqueda()
    {
        if (this.algoritmo.equals("A*") && this.heuristica == 1)
        {
            //Se busca el inicio y el fin de la buqueda
            int potitionInitial[] = new Aux().findPosStart(tablero.getMapa().getPositionsMap());
            int potitionEnd[] = new Aux().findPosEnd(tablero.getMapa().getPositionsMap());
            BusquedaAsterisco busquedaAsterisco = new BusquedaAsterisco(
                    tablero.getMapa().getPositionsMap(),
                    potitionInitial[0],
                    potitionInitial[1],
                    potitionEnd[0],
                    potitionEnd[1],
                    1 //Indica que es la heuristica 1
            );
            busquedaAsterisco.busqueda(); //Se realiza la busqueda
            //Se carga el camino en la vista para que el robot lo recorra
            tablero.cargarCamino(busquedaAsterisco.getNodoMeta().getPath());
            //Se inicializa los detalles de la expancion de la busqueda
            tablero.getLNodosExpandidos().setText("Numero de Nodos Expandidos: " + busquedaAsterisco.getNodosExpandidos());
            tablero.getLNodosCreados().setText("Numero de Nodos Creados: " + busquedaAsterisco.getNodosCreados());
            tablero.getLCostoTotal().setText("Costo Total de la Solucion: " + busquedaAsterisco.getNodoMeta().getCost());
            tablero.getLFactorRamificacion().setText("Factor de Ramificacion: " + busquedaAsterisco.getFactRamificacion());
            tablero.getLProfundidad().setText("Profundidad del Arbol: " + busquedaAsterisco.getProfundidad());
        }
        else if (this.algoritmo.equals("A*") && this.heuristica == 2)
        {
            //Se busca el inicio y el fin de la buqueda
            int potitionInitial[] = new Aux().findPosStart(tablero.getMapa().getPositionsMap());
            int potitionEnd[] = new Aux().findPosEnd(tablero.getMapa().getPositionsMap());
            BusquedaAsterisco busquedaAsterisco = new BusquedaAsterisco(
                    tablero.getMapa().getPositionsMap(),
                    potitionInitial[0],
                    potitionInitial[1],
                    potitionEnd[0],
                    potitionEnd[1],
                    2 //Indica que es la heuristica 2
            );
            busquedaAsterisco.busqueda(); //Se realiza la busqueda
            //Se carga el camino en la vista para que el robot lo recorra
            tablero.loadPath(busquedaAsterisco.getNodoMeta().getPath());
            //Se inicializa los detalles de la expancion de la busqueda
            tablero.getLabelNodosExpandidos().setText("Numero de Nodos Expandidos: " + busquedaAsterisco.getNodosExpandidos());
            tablero.getLabelNodosCreados().setText("Numero de Nodos Creados: " + busquedaAsterisco.getNodosCreados());
            tablero.getLabelCostoTotal().setText("Costo Total de la Solucion: " + busquedaAsterisco.getNodoMeta().getCost());
            tablero.getLabelFactorRamificacion().setText("Factor de Ramificacion: " + busquedaAsterisco.getFactRamificacion());
            tablero.getLabelProfundidad().setText("Profundidad del Arbol: " + busquedaAsterisco.getProfundidad());
        }
        else if (this.algoritmo.equals("Avara") && this.heuristica == 1)
        {
            //Se busca el inicio y el fin de la buqueda
            int potitionInitial[] = new Aux().findPosStart(tablero.getMapa().getPositionsMap());
            int potitionEnd[] = new Aux().findPosEnd(tablero.getMapa().getPositionsMap());
            BusquedaAsterisco busquedaAsterisco = new BusquedaAsterisco(
                    tablero.getMapa().getPositionsMap(),
                    potitionInitial[0],
                    potitionInitial[1],
                    potitionEnd[0],
                    potitionEnd[1],
                    1 //Indica que es la heuristica 1
            );
            busquedaAsterisco.busqueda(); //Se realiza la busqueda
            //Se carga el camino en la vista para que el robot lo recorra
            tablero.loadPath(busquedaAsterisco.getNodoMeta().getPath());
            //Se inicializa los detalles de la expancion de la busqueda
            tablero.getLNodosExpandidos().setText("Numero de Nodos Expandidos: " + busquedaAsterisco.getNodosExpandidos());
            tablero.getLNodosCreados().setText("Numero de Nodos Creados: " + busquedaAsterisco.getNodosCreados());
            tablero.getLCostoTotal().setText("Costo Total de la Solucion: " + busquedaAsterisco.getNodoMeta().getCost());
            tablero.getLFactorRamificacion().setText("Factor de Ramificacion: " + busquedaAsterisco.getFactRamificacion());
            tablero.getLProfundidad().setText("Profundidad del Arbol: " + busquedaAsterisco.getProfundidad());
        }
        else if (this.algoritmo.equals("Avara") && this.heuristica == 2)
        {
            //Se busca el inicio y el fin de la buqueda
            int potitionInitial[] = new Aux().findPosStart(tablero.getMapa().getPositionsMap());
            int potitionEnd[] = new Aux().findPosEnd(tablero.getMapa().getPositionsMap());
            BusquedaAsterisco busquedaAsterisco = new BusquedaAsterisco(
                    tablero.getMapa().getPositionsMap(),
                    potitionInitial[0],
                    potitionInitial[1],
                    potitionEnd[0],
                    potitionEnd[1],
                    2 //Indica que es la heuristica 2
            );
            busquedaAsterisco.busqueda(); //Se realiza la busqueda
            //Se carga el camino en la vista para que el robot lo recorra
            tablero.loadPath(busquedaAsterisco.getNodoMeta().getPath());
            //Se inicializa los detalles de la expancion de la busqueda
            tablero.getLNodosExpandidos().setText("Numero de Nodos Expandidos: " + busquedaAsterisco.getNodosExpandidos());
            tablero.getLNodosCreados().setText("Numero de Nodos Creados: " + busquedaAsterisco.getNodosCreados());
            tablero.getLCostoTotal().setText("Costo Total de la Solucion: " + busquedaAsterisco.getNodoMeta().getCost());
            tablero.getLFactorRamificacion().setText("Factor de Ramificacion: " + busquedaAsterisco.getFactRamificacion());
            tablero.getLProfundidad().setText("Profundidad del Arbol: " + busquedaAsterisco.getProfundidad());
        }
        else if (this.algoritmo.equals("Costo Uniforme"))
        {            
            //Se busca el inicio de la buqueda
            int potitionInitial[] = new Aux().findPosStart(tablero.getMapa().getPositionsMap());
            BusquedaCostoUniforme busquedaCostoUniforme = new BusquedaCostoUniforme(
                    tablero.getMapa().getPositionsMap(),
                    potitionInitial[0],
                    potitionInitial[1]
            );
            busquedaCostoUniforme.busqueda(); //Se realiza la busqueda
            //Se carga el camino en la vista para que el robot lo recorra
            tablero.loadPath(busquedaCostoUniforme.getNodoMeta().getPath());
            //Se inicializa los detalles de la expancion de la busqueda
            tablero.getLabelNodosExpandidos().setText("Numero de Nodos Expandidos: " + busquedaCostoUniforme.getNodosExpandidos());
            tablero.getLabelNodosCreados().setText("Numero de Nodos Creados: " + busquedaCostoUniforme.getNodosCreados());
            tablero.getLabelCostoTotal().setText("Costo Total de la Solucion: " + busquedaCostoUniforme.getNodoMeta().getCost());
            tablero.getLabelFactorRamificacion().setText("Factor de Ramificacion: " + busquedaCostoUniforme.getFactRamificacion());
            tablero.getLabelProfundidad().setText("Profundidad del Arbol: " + busquedaCostoUniforme.getProfundidad());
        }
    }
}
