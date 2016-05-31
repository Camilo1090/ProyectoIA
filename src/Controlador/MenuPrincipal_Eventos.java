/***********************************************
 * Camilo Ruiz Casanova - 1324486
 * Andres Felipe Polanco - 1324539
 * Universidad del Valle
 **********************************************/
package Controlador;

import Vista.*;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Camilo Ruiz Casanova
 */
public class MenuPrincipal_Eventos 
{
    private final MenuPrincipal menuPrincipal;
    
    private File archivo;
    private String algoritmo;
    private int heuristica;
    
    public MenuPrincipal_Eventos(final MenuPrincipal menuPrincipal)
    {
        this.menuPrincipal = menuPrincipal;
        
        this.menuPrincipal.bArchivo.addActionListener(
            (ActionEvent ae) -> 
            {
                cargarArchivo();
            }
        );
        
        this.menuPrincipal.bEmpezar.addActionListener(
            (ActionEvent ae) -> 
            {
                empezar();
            }
        );
        
        this.menuPrincipal.cbAlgoritmos.addActionListener(
            (ActionEvent ae) -> 
            {
                activarHeuristicas();
                activarOrden();
            }
        );
    }
    
    public void cargarArchivo()
    {
        this.menuPrincipal.fileChooser.setFileFilter(new FileNameExtensionFilter(".txt", "txt"));
        int op = this.menuPrincipal.fileChooser.showOpenDialog(menuPrincipal);
        
        if (op == JFileChooser.APPROVE_OPTION)
        {
            String ruta = this.menuPrincipal.fileChooser.getSelectedFile().getAbsolutePath();
            this.menuPrincipal.tfArchivo.setText(ruta);
            this.archivo = new File(ruta);
        }
    }
    
    public void empezar()
    {
        this.algoritmo = this.menuPrincipal.cbAlgoritmos.getSelectedItem().toString();
        this.heuristica = Integer.parseInt(this.menuPrincipal.cbHeuristica.getSelectedItem().toString());
        
        if (this.archivo == null)
        {
            JOptionPane.showMessageDialog(menuPrincipal, "Debe seleccionar un archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            // mapa
            Tablero tablero = new Tablero(this.algoritmo);
            tablero.getMapa().loadMap(archivo);
            
            if (tablero.getMapa().isLoadMap())
            {
                tablero.setLocationRelativeTo(null);
                tablero.setMovimiento(0);
                Tablero_Eventos tableroEventos = new Tablero_Eventos(tablero);                
                if (this.menuPrincipal.cbAlgoritmos.getSelectedItem().equals("Preferente por Profundidad"))
                {
                    int[] orden = new int[]{this.menuPrincipal.cbPrimero.getSelectedIndex()+1,
                                        this.menuPrincipal.cbSegundo.getSelectedIndex()+1,
                                        this.menuPrincipal.cbTercero.getSelectedIndex()+1,
                                        this.menuPrincipal.cbCuarto.getSelectedIndex()+1};
                    //System.out.println(orden[0] + " " + orden[1] + " " + orden[2] + " " + orden[3]);
                    tableroEventos.setOrden(orden);
                }
                else if (this.menuPrincipal.cbAlgoritmos.getSelectedItem().equals("A*") || this.menuPrincipal.cbAlgoritmos.getSelectedItem().equals("Avara"))
                {
                    tableroEventos.setHeuristica(heuristica);
                }
                tableroEventos.realizarBusqueda();
                tablero.setVisible(true);
            }
            else
            {
                
            }
        }
    }
    
    public void activarHeuristicas()
    {
        if (this.menuPrincipal.cbAlgoritmos.getSelectedItem().equals("A*") || this.menuPrincipal.cbAlgoritmos.getSelectedItem().equals("Avara"))
        {
            this.menuPrincipal.cbHeuristica.setEnabled(true);
        }
        else
        {
            this.menuPrincipal.cbHeuristica.setEnabled(false);
        }
    }
    
    public void activarOrden()
    {
        if (this.menuPrincipal.cbAlgoritmos.getSelectedItem().equals("Preferente por Profundidad"))
        {
            this.menuPrincipal.cbPrimero.setEnabled(true);
            this.menuPrincipal.cbSegundo.setEnabled(true);
            this.menuPrincipal.cbTercero.setEnabled(true);
            this.menuPrincipal.cbCuarto.setEnabled(true);
        }
        else
        {
            this.menuPrincipal.cbPrimero.setEnabled(false);
            this.menuPrincipal.cbSegundo.setEnabled(false);
            this.menuPrincipal.cbTercero.setEnabled(false);
            this.menuPrincipal.cbCuarto.setEnabled(false);
        }
    }
}
