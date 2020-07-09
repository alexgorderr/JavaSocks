package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.Random.*;
import java.util.Scanner;

import Graph.*;

public class GUI {
    private Graph graph;
    private JLabel labelIn = new JLabel("Input matrix:");
    private JTextArea textIn = new JTextArea("2 3 1 5\n0 1 7 4\n2 1 1 3\n2 5 3 0");
    private JLabel labelOut = new JLabel("Output matrix:");
    private JTextArea textOut = new JTextArea();

    private JSpinner spinnerV = new JSpinner();
    private JSpinner spinnerE = new JSpinner();

    private JButton InputButton = new JButton("Input");
    private JButton RandomButton = new JButton("Random");
    private JButton StepButton = new JButton("Step"); // by step by step by step ...
    private JButton Quick = new JButton("Quick");

    private JMenuBar menubar = new JMenuBar();
    private JMenu helpMenu = new JMenu("Help");
    private JMenuItem about = new JMenuItem("About");
    private JMenuItem info = new JMenuItem("Info");

    private JButton addVertex = new JButton("Add vertex");
    private JTextField addEdgeText = new JTextField("", 7);
    private JButton addEdgeButton = new JButton("Add edge");
    private JTextField changeEdgeText = new JTextField("", 7);
    private JButton changeButton = new JButton("Change");
    private JTextField delVertexText = new JTextField("", 7);
    private JButton delVertexButton = new JButton("Delete vertex");
    private JTextField delEdgeText = new JTextField("", 5);
    private JButton delEdgeButton = new JButton("Delete edge");

    private Vizualizator visual = new Vizualizator();

    private Container container = new Container();
    public GUI() {
        JFrame frame = new JFrame("Program");
        frame.setBounds(0, 0, 1240, 640);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        container = frame.getContentPane();
        container.setLayout(new BorderLayout());

        Container northContainer = new Container(); // менюшка
        northContainer.setLayout(new FlowLayout());
        helpMenu.add(about);
        helpMenu.add(info);
        menubar.add(helpMenu);
        northContainer.add(menubar);

        Container westContainer = new Container(); // ввод
        westContainer.setLayout(new GridLayout(3, 2));
        Container rand = new Container();
        rand.setLayout(new FlowLayout());
        rand.add(new JLabel("Vertexes"));
        rand.add(spinnerV);
        rand.add(new JLabel("Edges"));
        rand.add(spinnerE);
        westContainer.add(rand);
        RandomButton.addActionListener(new RandomActionListener());
        westContainer.add(RandomButton);
        westContainer.add(labelIn);
        westContainer.add(textIn);
        westContainer.add(labelOut);
        westContainer.add(textOut);


        Container centerContainer = new Container(); // контейнер под размещение в нем графа



        Container eastContainer = new Container();
        eastContainer.setLayout(new GridLayout(5, 2));
        eastContainer.add(new JPanel());
        addVertex.addActionListener(new addVertexListener());
        eastContainer.add(addVertex);
        eastContainer.add(addEdgeText);
        addEdgeButton.addActionListener(new addEdgeListener());
        eastContainer.add(addEdgeButton);
        eastContainer.add(changeEdgeText);
        changeButton.addActionListener(new changeButtonListener());
        eastContainer.add(changeButton);
        eastContainer.add(delVertexText);
        delVertexButton.addActionListener(new delVertexListener());
        eastContainer.add(delVertexButton);
        eastContainer.add(delEdgeText);
        delEdgeButton.addActionListener(new delEdgeListener());
        eastContainer.add(delEdgeButton);


        Container southContainer = new Container();
        southContainer.setLayout(new FlowLayout());
        InputButton.addActionListener(new InputButtonListener ());
        southContainer.add(InputButton);
        StepButton.addActionListener(new StepListener());
        southContainer.add(StepButton);
        Quick.addActionListener(new QuickListener());
        southContainer.add(Quick);


        container.add(northContainer, BorderLayout.NORTH);
        container.add(westContainer, BorderLayout.WEST);
        container.add(centerContainer, BorderLayout.CENTER);
        container.add(eastContainer, BorderLayout.EAST);
        container.add(southContainer, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    // тут классы "реагенты" кнопочек
    class InputButtonListener implements ActionListener { // тоже можно проверить
        public void actionPerformed (ActionEvent e) {

            if(textIn.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Your matrix is empty", "Message", JOptionPane.PLAIN_MESSAGE);
            } else {

                graph = new Graph(textIn.getText());
                int[][] matr = graph.getMatrix();

                visual.initMatrix(matr, matr[0].length);
                visual.functionVisual();
                container.add(visual);
                container.setVisible(true);
            }
        }
    }

    class RandomActionListener implements ActionListener { // тоже можно проверить
        public void actionPerformed (ActionEvent e) {
            int vertexes = (int)spinnerV.getValue();
            int edges = (int)spinnerE.getValue();
            if(vertexes <= 0) {
                JOptionPane.showMessageDialog(null, "Wrong vertexes input", "Message", JOptionPane.PLAIN_MESSAGE);
            }
            else if(edges <= 0 || edges >= vertexes*(vertexes-1)) {
                JOptionPane.showMessageDialog(null, "Wrong edges input", "Message", JOptionPane.PLAIN_MESSAGE);
            }
            else {
                int[][] matr = new int[vertexes][vertexes];
                String str = "";
                for(int i = 0; i < vertexes; i++) {
                    for(int j = 0; j < vertexes; j++) {
                        Random rand = new Random();
                        matr[i][j] = (edges > 0 ? rand.nextInt(20) : 0);
                        edges--;
                        str+=matr[i][j] + " ";
                    }
                    str+= "\n";
                }
                textIn.setText(str);
                graph = new Graph(str);
                Vizualizator visual = new Vizualizator();
                visual.initMatrix(matr, matr[0].length);
                visual.functionVisual();
                container.add(visual);
                container.setVisible(true);
            }
        }
    }

    class StepListener implements ActionListener { // проверить и имплеменитровать графику
        public void actionPerformed(ActionEvent e) {
            graph.FWStep();
        }
    }

    class QuickListener implements ActionListener { // проверить и имплементировать графику
        public void actionPerformed(ActionEvent e) {
            graph.FloydWarshall();
            visual.initMatrix(graph.getMatrix(), graph.getMatrix().length);
        }
    }

    class addVertexListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int[][] matr = graph.getMatrix();
            int num = matr.length;
            matr = new int[num+1][num+1];
            // не закончено
        }
    }
    class addEdgeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int from, to, weight;
            Scanner in = new Scanner(addEdgeText.getText());
            from = in.nextInt();
            to = in.nextInt();
            weight = in.nextInt();
            // не закончено
        }
    }
    class changeButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // не сделано
        }
    }
    class delVertexListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // не сделано
        }
    }
    class delEdgeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // не сделано
        }
    }

}

