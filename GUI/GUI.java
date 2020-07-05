package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

import Graph.*;

public class GUI extends JFrame {
    private JLabel labelIn = new JLabel("Input matrix:");
    private JTextArea textIn = new JTextArea("2 3 1 5\n0 1 7 -3\n2 1 -4 3\n 2 5 3 0");
    private JButton OKButton = new JButton("OK");
    private JButton randomButton = new JButton("Randomize");
    private JLabel labelOut = new JLabel("Output matrix:");
    private JTextArea textOut = new JTextArea();

    private JMenuBar menubar = new JMenuBar();
    private JMenu helpMenu = new JMenu("Help");
    private JMenuItem about = new JMenuItem("About");
    private JMenuItem info = new JMenuItem("Info");


    public GUI() {
        super("Program");
        this.setBounds(100, 100, 240, 180);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());
        
        Container northContainer = new Container(); // менюшка
        northContainer.setLayout(new FlowLayout());
        helpMenu.add(about);
        helpMenu.add(info);
        menubar.add(helpMenu);
        northContainer.add(menubar);

        Container westContainer = new Container(); // ввод 
        westContainer.setLayout(new FlowLayout());
        westContainer.add(labelIn);
        westContainer.add(textIn);

        Container centerContainer = new Container(); // контейнер под размещение в нем графа
        
        /*
        здесь должна быть реализация окна графа
        */

        Container eastContainer = new Container();
        eastContainer.setLayout(new FlowLayout());
        eastContainer.add(labelOut);
        eastContainer.add(textOut);


        Container southContainer = new Container();
        southContainer.setLayout(new FlowLayout());
        OKButton.addActionListener(new OKButtonListener ());
        randomButton.addActionListener(new randomListener ());
        southContainer.add(OKButton);
        southContainer.add(randomButton);

        container.add(northContainer, BorderLayout.NORTH);
        container.add(westContainer, BorderLayout.WEST);
        container.add(centerContainer, BorderLayout.CENTER);
        container.add(eastContainer, BorderLayout.EAST);
        container.add(southContainer, BorderLayout.SOUTH);
    }

    class OKButtonListener implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            if(textIn.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Your matrix is empty", "Message", JOptionPane.PLAIN_MESSAGE);
            } else {
                Graph graph = new Graph(textIn.getText());
                graph.FloydWarshall();
                textOut.setText(graph.print());
            }
        }
    }

    class randomListener implements ActionListener {
        public void actionPerformed(ActionEvent e) { // код рандомизации графа
            /* 
            случайное число вершин + случайное значение в матрице
            */
        }
    }

    //

}
