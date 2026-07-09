/*
    2D maze Search : Performs a depth first search in a maze.
    Copyright Yossep BINYOUM 2025-2026, All rights reserved.
    Inspired by the Book Practical-AI by Mark Watson.
*/

package search.maze;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class MazeDepthFirstSearch extends javax.swing.JFrame
{
    JPanel jPanel1 = new JPanel();
    DepthFirstSearchEngine currentSearchEngine = null;

    public MazeDepthFirstSearch()
    {
        try{
            jbInit();
        }catch(Exception e){
            System.out.println("GUI Initialization error: " + e);
        }
        currentSearchEngine = new DepthFirstSearchEngine(10, 10);
        repaint();
    }

    public void paint(Graphics g_unused)
    {
        if(currentSearchEngine == null) return;

        Maze maze = currentSearchEngine.getMaze();

        int width = maze.getWidth();
        int height = maze.getHeight();

        System.out.println("Size of current maze : " + width + " by " + height);

        Graphics g = jPanel1.getGraphics();
        BufferedImage image = new BufferedImage(320, 320, BufferedImage.TYPE_INT_RGB);

        Graphics g2 = image.getGraphics();
        g2.setColor(Color.white);
        g2.fillRect(0, 0, 320, 320);
        g2.setColor(Color.black);

        for(int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){

                short val = maze.getValue(x, y);
                if(val == Maze.OBSTICLE){
                    g2.setColor(Color.lightGray);
                    g2.fillRect(6 + x * 29, 3 + y * 29, 29, 29);
                    g2.setColor(Color.black);
                    g2.drawRect(6 + x * 29, 3 + y * 29, 29, 29);
                }
                else if(val == Maze.START_LOC_VALUE || val == 1){
                    g2.setColor(Color.blue);
                    g2.drawString("S", 16 + x * 29, 19 + y * 29);
                    g2.setColor(Color.black);
                    g2.drawRect(6 + x * 29, 3 + y * 29, 29, 29);
                }
                else if(val == Maze.GOAL_LOC_VALUE){
                    g2.setColor(Color.red);
                    g2.drawString("G", 16 + x * 29, 19 + y * 29);
                    g2.setColor(Color.black);
                    g2.drawRect(6 + x * 29, 3 + y * 29, 29, 29);
                }
                else{
                    g2.setColor(Color.black);
                    g2.drawRect(6 + x * 29, 3 + y * 29, 29, 29);
                }
            }
        }
        g2.setColor(Color.black);

        // redraw the path in black
        Location[] path = currentSearchEngine.getPath();

        if (path.length == 0){System.out.println("Zero solution... ");}
        else{
           for (int i = 1; i < path.length; i++){
                int x = path[i].x;
                int y = path[i].y;

                short val = maze.getValue(x, y);
                g2.drawString("" + val, 16 + x * 28, 19 + y * 29);
            } 
        }
        

        g.drawImage(image, 30, 40, 320, 320, null);

    }

    public static void main(String[] args)
    {
        new MazeDepthFirstSearch();
    }

    private void jbInit() throws Exception
    {
        this.setContentPane(jPanel1);
        this.setCursor(null);
        this.setDefaultCloseOperation(3);
        this.setTitle("MazeDepthFirstSearch");
        this.getContentPane().setLayout(null);
        
        jPanel1.setBackground(Color.white);
        jPanel1.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
        jPanel1.setDoubleBuffered(false);
        jPanel1.setRequestFocusEnabled(false);
        jPanel1.setLayout(null);

        this.setSize(350, 350);
        this.setVisible(true);
    }
}