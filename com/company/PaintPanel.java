package com.company;
/*
   Here is a list of libraries you need to import

   1) AWT
   2) AWT.EVENT
   3) SWING
   4) UTIL

*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class PaintPanel extends JPanel{

    boolean isFinished; // Used to only draw background rectange once, otherwise there will be no brush, just a pixel moving around

    // Integers that represent RGB, size of brush, x and y position of brush, and storing variables to keep old value of frame size
    private int R, G, B, size, x, y, formerPanelX, formerPanelY;

    // ArrayLists for storing x, y, size, and RGB values (for when screen size is updated)
    private ArrayList<Integer> xInfo;
    private ArrayList<Integer> yInfo;
    private ArrayList<Integer> sizeInfo;
    private ArrayList<Integer> RInfo;
    private ArrayList<Integer> GInfo;
    private ArrayList<Integer> BInfo;

    // Constructor is used to initialize variables
    public PaintPanel(){
        addKeyListener(new Key() ); // Allows JPanel to track key presses
        setFocusable(true); // Sets a "focus" on the keyboard
        addMouseMotionListener(new Mouse() ); // Allows JPanel to track mouse movements
        setFocusable(true); // Sets a "focus" on the mouse

        // Instantiation of variables
        x              = MouseInfo.getPointerInfo().getLocation().x;
        y              = MouseInfo.getPointerInfo().getLocation().y;
        size           = 10;
        R              = 255/2;
        G              = 255/2;
        B              = 255/2;
        formerPanelX   = 400;
        formerPanelY   = 400;
        xInfo          = new ArrayList<>();
        yInfo          = new ArrayList<>();
        sizeInfo       = new ArrayList<>();
        RInfo          = new ArrayList<>();
        GInfo          = new ArrayList<>();
        BInfo          = new ArrayList<>();
        isFinished     = true;
    }

    public void paintComponent(Graphics g){

        // Used to see if screen is updated
        PaintRunner.PanelX = PaintRunner.paintFrame.getWidth();
        PaintRunner.PanelY = PaintRunner.paintFrame.getHeight();

        fillRect(g); // Fills the screen only once

        // Draws the brush
        g.setColor(new Color(R, G, B) );
        g.fillRect(x, y, size, size);
        repaintBackground(g);
        repaint();
    }

    public void repaintBackground(Graphics g){

        // Checks if screen is updated
        if(formerPanelX != PaintRunner.PanelX || formerPanelY != PaintRunner.PanelY){ // If the former width, height and current width height don't match, that means screen size has updated

            // Sets the former
            formerPanelX = PaintRunner.PanelX;
            formerPanelY = PaintRunner.PanelY;
            isFinished = true;
            fillRect(g);
        }
    }

    // Draws the background only once, thereby ensuring that what you draw is seen
    public void fillRect(Graphics g){
        if(isFinished){
            g.setColor(Color.white);
            g.fillRect(0, 0, formerPanelX, formerPanelY);

            // Since drawing the background hides all the work you've done, this simply redraws the pixels you drew
            for(int i = 0; i < xInfo.size(); i++){
                g.setColor(new Color(RInfo.get(i), GInfo.get(i), BInfo.get(i) ) );
                g.fillRect(xInfo.get(i), yInfo.get(i), sizeInfo.get(i), sizeInfo.get(i) );
            }
        }
        isFinished = false;
    }

    // The class that "listens" for any event pertaining to the movement of the mouse; in this scenario, the event of dragging a clicked mouse
    class Mouse extends MouseMotionAdapter{

        @Override // Overrides this method; it is found in the class MouseMotionAdatper, which Mouse.java extends

        public void mouseDragged(MouseEvent e){ // If the mouse is dragged across the screen

            Point circleCoordinates = MouseInfo.getPointerInfo().getLocation(); // Stores the x and y values of the mouse in a Point object
            x = circleCoordinates.x;
            xInfo.add(x);
            y = circleCoordinates.y;
            yInfo.add(y);

            // Stores all the pixels' sizes and RGB values whenever you draw, so that if you resize the frame, you don't lose all your pixel info
            sizeInfo.add(size);
            RInfo.add(R);
            GInfo.add(G);
            BInfo.add(B);
        }
    }

    // The class that "listens" for any event pertaining to the keyboard; in this scenario, when a key is pressed
    class Key extends KeyAdapter{

        public void keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_W){ // If the "W" key is pressed

                size ++; // Increases the size
            } else if(e.getKeyCode() == KeyEvent.VK_S){ // If the "S" key is pressed and the "W" key isn't pressed

                size --; // Decreases the size

                // Error checking
                if(size <0){ // The error that this if statement is catching is that the size of a rectangle can't be less than 0
                    size = 0;
                }
            }

            if(e.getKeyCode() == KeyEvent.VK_E){ // If the "E" key is pressed

                // Increases color's monochrome value
                R++;
                G++;
                B++;

                if(R > 255 || G > 255 || B > 255){ // Catches the exception that RGB values can't be greater than 255
                    R = 255;
                    G = 255;
                    B = 255;
                }
            } else if(e.getKeyCode() == KeyEvent.VK_D){ // If the "D" key is pressed and the "E" key isn't pressed

                // Decreases color's monochrome value
                R--;
                G--;
                B--;

                if(R < 0 || G < 0 || B < 0){ // Catches the exception that RGB values can't be less than 0
                    R = 0;
                    G = 0;
                    B = 0;
                }
            }
        }
    }
}