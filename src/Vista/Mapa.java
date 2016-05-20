/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Mapa extends JPanel 
{
    //Variables de las imagenes
    private Image imgRobot;
    private Image imgMarlin;
    private Image imgNemo;
    private Image imgDory;
    private Image imgShark;
    private Image imgAquaman;
    private Image imgTurtle;
    private Image imgRock;

    //Matriz que contiene los valores del mapa
    private int positionsMap[][];
    //Arreglo con la posicion actual del robot
    private int robot[];

    //Verifica si se ha cargado el mapa
    private boolean loadMap;

    //Variable encargada de escalar el juego para que se tenga el tamaño correcto
    double factEscGrid;
    double factEscSquare;
    double factEscImage;
    double factEscSpace;
    double sizeGrid;
    double sizeSquare;
    double sizeImage;
    double sizeSpace;

    public Mapa()
    {
        loadImages(); //Se cargan las
        loadMap = false;
        robot = new int[2];
    }

    //Metodo encargado de cargar las imagenes de la carpeta de imagenes
    public void loadImages()
    {
        try
        {
            imgRobot = new ImageIcon(getClass().getResource("../img/robot.png")).getImage();
            imgMarlin = new ImageIcon(getClass().getResource("../img/marlin.png")).getImage();
            imgNemo = new ImageIcon(getClass().getResource("../img/nemo.png")).getImage();
            imgDory = new ImageIcon(getClass().getResource("../img/dory.png")).getImage();
            imgShark = new ImageIcon(getClass().getResource("../img/shark.png")).getImage();
            imgAquaman = new ImageIcon(getClass().getResource("../img/aquaman.png")).getImage();
            imgTurtle = new ImageIcon(getClass().getResource("../img/turtle.png")).getImage();
            imgRock = new ImageIcon(getClass().getResource("../img/rock.png")).getImage();
        }
        catch (NullPointerException e)
        {
            //Si se llega a capturar un error por error al cargar las imagenes se inicializan las imagenes con nada
            System.err.println("Error al cargar las imagenes de los iconos");
            imgRobot = Toolkit.getDefaultToolkit().getImage("");
            imgMarlin = Toolkit.getDefaultToolkit().getImage("");
            imgNemo = Toolkit.getDefaultToolkit().getImage("");
            imgDory = Toolkit.getDefaultToolkit().getImage("");
            imgShark = Toolkit.getDefaultToolkit().getImage("");
            imgAquaman = Toolkit.getDefaultToolkit().getImage("");
            imgTurtle = Toolkit.getDefaultToolkit().getImage("");
            imgRock = Toolkit.getDefaultToolkit().getImage("");
        }
    }

   //Metodo encargado de cargar los valores del arreglo del mapa pasandole un archivo externo
    public void loadMap(File file)
    {
        String buffer;
        boolean correct = true; // variable usada para verificar si esta en el rango correcto de numeros
        if (file != null)
        {
            try 
            {
                FileReader archivos = new FileReader(file);
                BufferedReader lee = new BufferedReader(archivos);
                if ((buffer = lee.readLine())!=null)
                {
                    int i = Integer.parseInt(buffer);
                    positionsMap = new int[i][i];
                }
                //Ciclo para almacenar los datos del texto en el arreglo del mapa positionsMap
                for (int i = 0; (buffer=lee.readLine())!=null && correct; i++) 
                {
                    StringTokenizer st = new StringTokenizer(buffer, " ");
                    for (int j = 0; st.hasMoreElements() && correct; j++) 
                    {
                        int number = Integer.parseInt(st.nextToken());
                        //Condicional para verificar si esta en el rango correcto de numeros( de 0 a 8 )
                        if ( 0 <= number && number <= 8)
                        {
                            positionsMap[i][j] = number;
                        }else 
                        {
                            correct = false;
                        }
                    }
                }
                lee.close();
                if (correct)
                {
                    loadMap = true;
                    System.out.println("Mapa cargado exitosamente");
                }else
                {
                    System.err.println("No se an ingresado correctamente los valores");
                }

            }
            catch (IOException e)
            {
                loadMap = false;
                System.err.println("Error al cargar el mapa");
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                loadMap = false;
                System.err.println("Error al cargar el mapa hay elementos de mas");
            }
            catch (NumberFormatException e)
            {
                System.err.println("Error al cargar el mapa formato de numeros no correcto");
            }
        }
    }
    //Metodo sobre escrito encargado de refrescar la pantalla con los graficos
    @Override
    public void paint(Graphics g)
    {
        if (loadMap)
        {
            Image imageBuffer = createImage(getWidth(), getHeight());
            Graphics graphicsBuffer = imageBuffer.getGraphics();
            escalas(0.037,0.9259,0.7407, 0.0925);
            drawGrids(graphicsBuffer);
            drawIcons(graphicsBuffer);

            g.drawImage(imageBuffer,0,0,Color.cyan,null);
        }
    }

    //Metodo encargado de inicializar las escalas con las que se esta trabajando los graficos
    public void escalas(double factGrid, double factSquare, double factImage, double factSpace)
    {
        int n = positionsMap.length;
        int tamanoRelativo = getHeight(); //Tamano relativo usado para verificar cual eje es menor y usarlo para escalar con respecto a ese
        if (getWidth() < getHeight()) //Se Comprueba que eje es menor
        {
            tamanoRelativo = getWidth();
        }
        factEscGrid = tamanoRelativo * factGrid;
        factEscSquare = tamanoRelativo * factSquare;
        factEscImage = tamanoRelativo * factImage;
        factEscSpace = tamanoRelativo * factSpace;
        sizeGrid = factEscGrid/n;
        sizeSquare = (factEscSquare + (2* sizeGrid))/n;
        sizeImage = factEscImage / n;
        sizeSpace = factEscSpace / n;
    }

    //Metodo encargado de dibujar la grilla de el tablero de busqueda
    public void drawGrids(Graphics g)
    {
        int n = positionsMap.length;
        for (int x = 0; x <= n* sizeSquare; x+= sizeSquare) 
        {
            g.fillRect(x, 0, (int) (2 * sizeGrid), (int) (n * sizeSquare));
        }
        for (int y = 0; y <= n* sizeSquare; y+= sizeSquare) 
        {
            g.fillRect(0, y, (int) (n * sizeSquare) + 1, (int) (2 * sizeGrid));
        }
    }

    //Metodo encargado de dibujar los elementos del tablero
    public void drawIcons(Graphics g)
    {
        int n = positionsMap.length;
        double y = sizeGrid + (sizeSpace);
        for (int i = 0; i < n; i++, y+= sizeSquare) 
        {
            double x = sizeGrid + (sizeSpace);
            for (int j = 0; j < n; j++, x+= sizeSquare) 
            {
                if (positionsMap[i][j] != 0)
                {
                    g.drawImage(getIcons(positionsMap[i][j]), (int)x,(int)y,(int) sizeImage,(int) sizeImage, null);
                }
            }
        }
        int posYRobot =(int)( sizeGrid + sizeSpace +(sizeSquare *robot[0]));
        int posXRobot =(int)( sizeGrid + sizeSpace +(sizeSquare *robot[1]));
        g.drawImage(imgRobot, posXRobot,posYRobot,(int) sizeImage,(int) sizeImage, null);
    }

    //Metodo encargado de retornar el icono de acuerdo a su numero
    public Image getIcons(int numberIcon)
    {
        Image icon;
        switch (numberIcon){
            case 1:{
                icon = imgRock;
            }break;
            case 2:{
                icon = null;
            }break;
            case 3:{
                icon = imgShark;
            }break;
            case 4:{
                icon = imgTurtle;
            }break;
            case 5:{
                icon = imgDory;
            }break;
            case 6:{
                icon = imgMarlin;
            }break;
            case 7:{
                icon = imgNemo;
            }break;
            case 8:{
                icon = imgAquaman;
            }break;
            default:{
                System.err.println("Representacion de imagen no valida");
                icon = null;
            }break;
        }
        return icon;
    }

    //Metodo encargado de retornar loadMap el cual esta en true si el mapa a sido cargado exitosamente
    public boolean isLoadMap() {
        return loadMap;
    }

    public void setLoadMap(boolean loadMap) {
        this.loadMap = loadMap;
    }

    public int[][] getPositionsMap() {
        return positionsMap;
    }

    public void setRobot(int[] robot) {
        this.robot = robot;
    }
}
