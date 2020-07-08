package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Graph.*;
import Vizualizator.Vizualizator;

public class GUI {
    private JLabel labelIn = new JLabel("Input matrix:");
    private JTextArea textIn = new JTextArea("2 3 1 5\n0 1 7 -3\n2 1 -4 3\n2 5 3 0");
    private JLabel labelOut = new JLabel("Output matrix:");
    private JTextArea textOut = new JTextArea();

    private JLabel addLabel = new JLabel("Add");
    private JTextField addText = new JTextField("", 10);
    private JButton addButton = new JButton("Add");
    private JLabel delLabel = new JLabel("Delete");
    private JTextField delText = new JTextField("", 10);
    private JButton delButton = new JButton("Delete");

//    private Vizualizator visual = new Vizualizator();

    private JButton OKButton = new JButton("OK");
    private JButton RandomButton = new JButton("Random");

    private JMenuBar menubar = new JMenuBar();
    private JMenu helpMenu = new JMenu("Help");
    private JMenuItem about = new JMenuItem("About");
    private JMenuItem info = new JMenuItem("Info");

    private Container container = new Container();
    public GUI() {
        JFrame frame = new JFrame("Program");
        frame.setBounds(100, 100, 640, 480);
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
        westContainer.setLayout(new GridLayout(2, 2));
        westContainer.add(labelIn);
        westContainer.add(textIn);
        westContainer.add(labelOut);
        westContainer.add(textOut);

        Container centerContainer = new Container(); // контейнер под размещение в нем графа

        JPanel center = new JPanel();
        Vizualizator visual = new Vizualizator();
        center.add(visual);
        centerContainer.add(center);
        /*
        здесь должна быть реализация окна графа
        */

        Container eastContainer = new Container();
        eastContainer.setLayout(new GridLayout(2, 2));
//        eastContainer.add(addLabel);
        eastContainer.add(addText);
        addButton.addActionListener(new AddActionListener());
        eastContainer.add(addButton);
//        eastContainer.add(delLabel);
        eastContainer.add(delText);
        delButton.addActionListener(new DelActionListener());
        eastContainer.add(delButton);

        Container southContainer = new Container();
        southContainer.setLayout(new FlowLayout());
        OKButton.addActionListener(new OKButtonListener ());
        southContainer.add(OKButton);
        RandomButton.addActionListener(new RandomActionListener());
        southContainer.add(RandomButton);


        container.add(northContainer, BorderLayout.NORTH);
        container.add(westContainer, BorderLayout.WEST);
        container.add(centerContainer, BorderLayout.CENTER);
        container.add(eastContainer, BorderLayout.EAST);
        container.add(southContainer, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    class OKButtonListener implements ActionListener {

        public void actionPerformed (ActionEvent e) {
            if(textIn.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Your matrix is empty", "Message", JOptionPane.PLAIN_MESSAGE);
            } else {

                Graph graph = new Graph(textIn.getText());
                int[][] matr = graph.getMatrix();
                JPanel newPanel = new JPanel();

                Vizualizator visual = new Vizualizator();
                visual.initMatrix(matr);
                visual.functionVisual();

                graph.FloydWarshall();
                textOut.setText(graph.print());
            }
        }
    }

    class AddActionListener implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            // обработка нажатия клавиши добавления
            System.out.println("Add pressed");
        }
    }

    class DelActionListener implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            // обработка нажатия клавиши удаления
            System.out.println("Delete pressed");
        }
    }

    class RandomActionListener implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            System.out.println("Random pressed");
        }
    }

    //

}