package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Random;
import java.util.Random.*;
import java.util.Scanner;
import com.mxgraph.io.mxCodec;

import Graph.*;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxGraph;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import com.mxgraph.util.mxXmlUtils;

public class GUI {
    private Graph graph;

    private JButton saveButton = new JButton("Save");
    private JTextField loadText = new JTextField();
    private JButton loadButton = new JButton("Load");

    private JLabel labelIn = new JLabel("Input matrix:");
    private JTextArea textIn = new JTextArea("2 3 1 5\n0 1 7 4\n2 1 1 3\n2 5 3 0");
    private JLabel labelOut = new JLabel("Output matrix:");
    private JTextArea textOut = new JTextArea();

    private JSpinner spinnerV = new JSpinner();
    private JSpinner spinnerE = new JSpinner();

    private JButton InputButton = new JButton("Input");
    private JButton RandomButton = new JButton("Random");
    private JButton StepButton = new JButton("Step"); // by step by step by step ...
    private JButton Result = new JButton("Result");
    private JButton ResetButton = new JButton("Reset");

    private JButton addVertexButton = new JButton("Add vertex");
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
    private Container centerContainer = new Container();

    public GUI() {
        JFrame frame = new JFrame("Program");
        frame.setBounds(0, 0, 1240, 640);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        container = frame.getContentPane();
        container.setLayout(new BorderLayout());

        Container northContainer = new Container(); // менюшка
        northContainer.setLayout(new FlowLayout());
        saveButton.addActionListener(new saveListener());
        northContainer.add(saveButton);
        northContainer.add(loadText);
        loadButton.addActionListener(new loadListener());
        northContainer.add(loadButton);

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

        centerContainer.setLayout(new FlowLayout());
        visual = new Vizualizator();
        centerContainer.add(visual);

        Container eastContainer = new Container();

        eastContainer.setLayout(new GridLayout(5, 2));
        eastContainer.add(new JPanel());
        addVertexButton.addActionListener(new addVertexListener());
        eastContainer.add(addVertexButton);
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
        Result.addActionListener(new QuickListener());
        southContainer.add(Result);
        ResetButton.addActionListener(new resetListener());
        southContainer.add(ResetButton);


        container.add(northContainer, BorderLayout.NORTH);
        container.add(westContainer, BorderLayout.WEST);
        container.add(centerContainer, BorderLayout.CENTER);
        container.add(eastContainer, BorderLayout.EAST);
        container.add(southContainer, BorderLayout.SOUTH);

        // скрываю кнопочки
        RandomButton.setEnabled(true);
        InputButton.setEnabled(true);
        StepButton.setEnabled(false);
        Result.setEnabled(false);
        addVertexButton.setEnabled(false);
        addEdgeButton.setEnabled(false);
        changeButton.setEnabled(false);
        delVertexButton.setEnabled(false);
        delEdgeButton.setEnabled(false);
        saveButton.setEnabled(false);

        frame.setVisible(true);
    }
    // тут классы "реагенты" кнопочек
    class InputButtonListener implements ActionListener { // тоже можно проверить
        public void actionPerformed (ActionEvent e) {
            if(textIn.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Your matrix is empty", "Message", JOptionPane.PLAIN_MESSAGE);
            } else {
                RandomButton.setEnabled(false);
                InputButton.setEnabled(false);
                StepButton.setEnabled(true);
                Result.setEnabled(true);
                addVertexButton.setEnabled(true);
                addEdgeButton.setEnabled(true);
                changeButton.setEnabled(true);
                delVertexButton.setEnabled(true);
                delEdgeButton.setEnabled(true);
                try {
                    graph = new Graph(textIn.getText());
                } catch (Exception exception) {
                    System.err.println("Failed to create a graph");
                }
                int[][] matr = graph.getMatrix();

                System.out.println(container.getSize().height);
                System.out.println(container.getSize().width);

                delVertexText.setText("Example: 3");
                addEdgeText.setText("Example: 1->2=3");
                changeEdgeText.setText("Example: 1->2=4");
                delEdgeText.setText("Example: 1->2");

                int h = centerContainer.getSize().height;
                int w = centerContainer.getSize().width;
                System.out.println(h);
                System.out.println(w);
                container.revalidate();

                centerContainer.setVisible(true);
            }
        }
    }

    class RandomActionListener implements ActionListener { // тоже можно проверить
        public void actionPerformed (ActionEvent e) {
            saveButton.setEnabled(true);
            delVertexText.setText("Example: 3");
            addEdgeText.setText("Example: 1->2=3");
            changeEdgeText.setText("Example: 1->2=4");
            delEdgeText.setText("Example: 1->2");

            int vertexes = (int)spinnerV.getValue();
            int edges = (int)spinnerE.getValue();
            if(vertexes <= 0 || vertexes > 20) {
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
                try {
                    graph = new Graph(str);
                } catch (Exception exception) {
                    System.err.println("Failed to create a graph");
                }
                centerContainer.remove(visual);
                visual = new Vizualizator();
                centerContainer.add(visual);
                visual.initMatrix(matr, matr[0].length);
                visual.functionVisual(centerContainer.getHeight(), centerContainer.getWidth());
                centerContainer.setVisible(true);
            }
        }
    }

    class StepListener implements ActionListener { // проверить и имплеменитровать графику
        public void actionPerformed(ActionEvent e) {
            if(graph.getK() == graph.getN()) {
                StepButton.setEnabled(false);
                Result.setEnabled(false);
            }
            //graph.print();
            graph.FWStep();
           // graph.print();

            visual.updateResultMatrix(graph.getMatrix(), graph.getMatrix().length);
            textIn.setText(graph.print());
        }
    }

    class QuickListener implements ActionListener { // проверить и имплементировать графику
        public void actionPerformed(ActionEvent e) {
            StepButton.setEnabled(false);
            graph.FloydWarshall();
            visual.updateResultMatrix(graph.getMatrix(), graph.getMatrix().length);
            textOut.setText(graph.print());
        }
    }

    class addVertexListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //исключения и проверка корректности ввода
            //формат ввода - число(номер вершины)
            //добавляем вершину в матрицу и увеличиваем n
            graph.updateMatrix();
            visual.addVert();
            textIn.setText(graph.print());

        }
    }

    class addEdgeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //исключения и проверка корректности ввода
            //формат ввода: v1->v2=число

            String v = addEdgeText.getText().toString();
            int splitIndex1 = v.indexOf("->",0);
            int splitIndex2 = v.indexOf("=", 0);

            String first = v.substring(0, splitIndex1);
            String second = v.substring(splitIndex1 + 2, splitIndex2);
            String third = v.substring(splitIndex2 + 1);

            int v1 = Integer.parseInt(first);
            int v2 = Integer.parseInt(second);
            int edge = Integer.parseInt(third);

            addEdgeText.setText("");

            graph.addEdge(v1, v2, edge);
            try {
                visual.addEdge(v1, v2, edge);
            } catch (IOException ioException) {
                System.err.println("Failed to add an edge");
            }
            textIn.setText(graph.print());
        }
    }

    class changeButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            //формат ввода: v1->v2=3

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
            textIn.setText(graph.print());
        }
    }

    class delVertexListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            //исключения и проверка корректности ввода
            //формат ввода: нет ввода
            Scanner in = new Scanner(delVertexText.getText());
            int v = in.nextInt();
            graph.deleteVert(v);
            try {
                visual.removeVert(v);
            } catch (IOException ioException) {
                System.err.println("Failed to remove the vertex.");
            }
            textIn.setText(graph.print());
            delVertexText.setText("");
        }
    }

    class delEdgeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

        //исключения и проверка корректности ввода
            //формат ввода: v1->v2
            String v = delEdgeText.getText().toString();
            int splitIndex = v.indexOf("->",0);

            String first = v.substring(0, splitIndex);
            String second = v.substring(splitIndex + 2);

            int v1 = Integer.parseInt(first);
            int v2 = Integer.parseInt(second);

            delEdgeText.setText("");

            graph.deleteEdge(v1, v2);
            try {
                visual.removeEdge(v1, v2);
            } catch (IOException ioException) {
                System.err.println("Failed to remove an edge");
            }
            textIn.setText(graph.print());
        }
    }

    class resetListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            centerContainer.remove(visual);
            graph = new Graph();
            visual = new Vizualizator();
            centerContainer.add(visual);

            textIn.setText("2 3 1 5\n0 1 7 4\n2 1 1 3\n2 5 3 0");
            textOut.setText("");
            addEdgeText.setText("");
            changeEdgeText.setText("");
            delVertexText.setText("");
            delEdgeText.setText("");

            RandomButton.setEnabled(true);
            InputButton.setEnabled(true);
            StepButton.setEnabled(false);
            Result.setEnabled(false);
            addVertexButton.setEnabled(false);
            addEdgeButton.setEnabled(false);
            changeButton.setEnabled(false);
            delVertexButton.setEnabled(false);
            delEdgeButton.setEnabled(false);

        }
    }

    class saveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            mxCodec codec = new mxCodec();
            Node node = codec.encode(visual.returnGraph().getModel());
            String str = mxXmlUtils.getXml(node);
            JFileChooser filer = new JFileChooser();
            filer.setDialogTitle("Проводник");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");
            filer.setFileFilter(filter);
            filer.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int num = filer.showSaveDialog(null);
            if(num == JFileChooser.APPROVE_OPTION) {
                try {
                    FileWriter writer;
                    if(filer.getSelectedFile().getPath().endsWith(".xml")) {
                        writer = new FileWriter(filer.getSelectedFile().getPath());
                    }
                    else {
                        File tmp = new File(filer.getSelectedFile().getPath() + ".xml");
                        writer = new FileWriter(tmp);
                    }
                    writer.write(str);
                    writer.close();
                    System.out.println(str);
                }
                catch(Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
        ;
    }

    class loadListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser filer = new JFileChooser();
            filer.setDialogTitle("Проводник");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");
            filer.setFileFilter(filter);
            filer.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int num = filer.showSaveDialog(null);
            if(num == JFileChooser.APPROVE_OPTION) {
                try {
                    mxGraph tmp = new mxGraph();
                    Document doc = mxXmlUtils.parseXml(mxUtils.readFile(filer.getSelectedFile().toString()));
                    System.out.println(mxUtils.readFile(filer.getSelectedFile().toString()));
                    mxCodec codec = new mxCodec(doc);
                    codec.decode(doc.getDocumentElement(), tmp.getModel());
                    visual.setGraph(tmp);
                }
                catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}

