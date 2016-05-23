/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
}
