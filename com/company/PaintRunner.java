package com.company;
/*
   Here are list of all the libraries you need to import:

   1) Swing
   2) AWT

*/
import javax.swing.*;
import java.awt.*;
    public class PaintRunner extends JFrame {

        // Integers that represent the starting size of the frame; they are set to the width and height of the frame in PaintPanel.java
        static int PanelX=400;
        static int PanelY=400;

        // An object that represents the editor window, import with the AWT library
        static JFrame paintFrame;

        // The method that actually runs; runs only once
        public static void main(String[] args){

            // Makes a window with the title "Editor"
            paintFrame = new JFrame("Editor");
            paintFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            paintFrame.setResizable(true);
            paintFrame.setMinimumSize(new Dimension(PanelX, PanelY));
            paintFrame.setContentPane(new PaintPanel() );
            paintFrame.setVisible(true);
            System.out.print("USE W AND S TO CHANGE SIZE; USE E AND D TO CHANGE MONOCHROME VALUE."); // Instructions for the user

        }
    }


