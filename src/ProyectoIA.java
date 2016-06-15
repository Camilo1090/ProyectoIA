/***********************************************
 * Camilo Ruiz Casanova - 1324486
 * Andres Martinez Polanco - 1324539
 * Universidad del Valle
 **********************************************/
import Controlador.MenuPrincipal_Eventos;
import Vista.MenuPrincipal;

/**
 *
 * @author Camilo Ruiz Casanova
 */
public class ProyectoIA 
{
    public static void main(String[] args) 
    {
        setLookAndFeel();
        MenuPrincipal menuPrincipal = new MenuPrincipal();
        MenuPrincipal_Eventos menuPrincipal_Eventos = new MenuPrincipal_Eventos(menuPrincipal);
        menuPrincipal.setLocationRelativeTo(null);
        menuPrincipal.setVisible(true);
    }
    
    public static void setLookAndFeel() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel".equals(info.getClassName()) || "com.sun.java.swing.plaf.gtk.GTKLookAndFeel".equals(info.getClassName())) {   
                   javax.swing.UIManager.setLookAndFeel(info.getClassName());
                   break;
                } 
            }
        } catch(Exception e) {
          System.out.println("Error setting native LAF: " + e);
        }
    }
}
