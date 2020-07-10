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
    private boolean permission = true;
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

    private Vizualizator visual = null;

    private Container container = new Container();
    Container centerContainer = new Container();

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


        //centerContainer = new Container(); // контейнер под размещение в нем графа
        //centerContainer.
        centerContainer.setLayout(new FlowLayout());


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
            if(visual != null)
                return;

            if(textIn.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Your matrix is empty", "Message", JOptionPane.PLAIN_MESSAGE);
            } else {

                graph = new Graph(textIn.getText());
                int[][] matr = graph.getMatrix();

                System.out.println(container.getSize().height);
                System.out.println(container.getSize().width);


                delVertexText.setText("Example: 3");
                addEdgeText.setText("Example: 1->2=3");
                changeEdgeText.setText("Example: 1->2=4");
                delEdgeText.setText("Example: 1->2");


                visual = new Vizualizator();
                visual.initMatrix(matr, matr[0].length);
                visual.functionVisual(centerContainer.getHeight(), centerContainer.getWidth());
                int h = centerContainer.getSize().height;
                int w = centerContainer.getSize().width;
                System.out.println(h);
                System.out.println(w);
                centerContainer.add(visual);
                container.revalidate();
                //centerContainer.setVisible(false);
                centerContainer.setVisible(true);
                /*container.add(visual);
                container.setVisible(false);
                container.setVisible(true);*/

            }
        }
    }

    class RandomActionListener implements ActionListener { // тоже можно проверить
        public void actionPerformed (ActionEvent e) {



            if(permission){

                delVertexText.setText("Example: 3");
                addEdgeText.setText("Example: 1->2=3");
                changeEdgeText.setText("Example: 1->2=4");
                delEdgeText.setText("Example: 1->2");

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

                    int[] randomArray = new int[edges];
                    for(int i = 0; i < edges; i++){
                        Random rand = new Random();
                        randomArray[i] = rand.nextInt(20);
                        if(randomArray[i] == 0){
                            i--;
                        }
                    }

                    for(int i = 0; i < edges; i++){
                        Random rand = new Random();
                       // Random rand_k = new Random();
                        int k = rand.nextInt(vertexes);
                        int j = rand.nextInt(vertexes);
                        if(matr[k][j] > 0){
                            i--;
                        }
                        else {
                            matr[k][j] = randomArray[i];
                        }

                    }

                    str += "\n";
                    str += "\n";

                    for(int i = 0; i < vertexes; i++) {
                        for (int j = 0; j < vertexes; j++) {
                            str += matr[i][j] + " ";
                        }
                        str += "\n";
                    }

                    /*for(int i = 0; i < vertexes; i++) {
                        for(int j = 0; j < vertexes; j++) {
                            Random rand = new Random();
                            matr[i][j] = (edges > 0 ? rand.nextInt(20) : 0);
                            edges--;
                            str+=matr[i][j] + " ";
                        }
                        str+= "\n";
                    }*/
                    textIn.setText(str);
                    graph = new Graph(str);

                    visual = new Vizualizator();
                    visual.initMatrix(matr, matr[0].length);
                    visual.functionVisual(centerContainer.getHeight(), centerContainer.getWidth());

                    centerContainer.add(visual);
                    centerContainer.setVisible(true);

            }


            }
        }
    }

    class StepListener implements ActionListener { // проверить и имплеменитровать графику
        public void actionPerformed(ActionEvent e) {

            //graph.print();
            permission = false;
            graph.FWStep();
           // graph.print();

            visual.updateResultMatrix(graph.getMatrix(), graph.getMatrix().length);

        }
    }

    class QuickListener implements ActionListener { // проверить и имплементировать графику
        public void actionPerformed(ActionEvent e) {

            permission = false;
            graph.FloydWarshall();
            visual.updateResultMatrix(graph.getMatrix(), graph.getMatrix().length);


           /* str += "\n";
            str += "\n";

            for(int i = 0; i < vertexes; i++) {
                for (int j = 0; j < vertexes; j++) {
                    str += matr[i][j] + " ";
                }
                str += "\n";
            }*/

        }
    }

    class addVertexListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //исключения и проверка корректности ввода
            //формат ввода - число(номер вершины)
            //добавляем вершину в матрицу и увеличиваем n
            if(permission){
                graph.updateMatrix();
                visual.addVert();
            }

        }
    }
    class addEdgeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //исключения и проверка корректности ввода
            //формат ввода: v1->v2=число

            if(permission){

                String v = addEdgeText.getText().toString();
                int splitIndex1 = v.indexOf("->",0);
                int splitIndex2 = v.indexOf("=", 0);

                String first = v.substring(0, splitIndex1);
                String second = v.substring(splitIndex1 + 2, splitIndex2);
                String third = v.substring(splitIndex2 + 1);

                int v1 = Integer.parseInt(first);
                int v2 = Integer.parseInt(second);
                int edge = Integer.parseInt(third);

                delEdgeText.setText("");

                graph.addEdge(v1, v2, edge);
                visual.addEdge(v1, v2, edge);
            }



        }
    }
    class changeButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            //формат ввода: v1->v2=3
            if(permission){

                String v = addEdgeText.getText().toString();
                int splitIndex1 = v.indexOf("->",0);
                int splitIndex2 = v.indexOf("=", 0);

                String first = v.substring(0, splitIndex1);
                String second = v.substring(splitIndex1 + 2, splitIndex2);
                String third = v.substring(splitIndex2 + 1);

                int v1 = Integer.parseInt(first);
                int v2 = Integer.parseInt(second);
                int edge = Integer.parseInt(third);

                delEdgeText.setText("");

                graph.changeEdge(v1, v2, edge);
                visual.changeEdge(v1, v2, edge);

            }

        }
    }

    class delVertexListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            //исключения и проверка корректности ввода
            //формат ввода: нет ввода
            if(permission){
                Scanner in = new Scanner(delVertexText.getText());
                int v = in.nextInt();
                graph.deleteVert(v);
                visual.removeVert(v);

                delVertexText.setText("");
            }



        }
    }

    class delEdgeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

          //исключения и проверка корректности ввода
            //формат ввода: v1->v2

            if(permission){
                String v = delEdgeText.getText().toString();
                int splitIndex = v.indexOf("->",0);

                String first = v.substring(0, splitIndex);
                String second = v.substring(splitIndex + 2);

                int v1 = Integer.parseInt(first);
                int v2 = Integer.parseInt(second);

                delEdgeText.setText("");

                graph.deleteEdge(v1, v2);
                visual.removeEdge(v1, v2);
            }


        }
    }

}

