package Vizualizator;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;


import javax.swing.*;
import java.io.PrintWriter;


public class Vizualizator extends JPanel{
    private static int n;
    private static int m;
    int[][] matrix;
    private boolean [] used;

    private PrintWriter cout;

    public void initMatrix(int[][] adjMatrix){
        matrix = new int[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                matrix[i][j] = adjMatrix[i][j];
            }

        }
    }

    public void functionVisual() {
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();
        Object[] points = new Object[n];
        double phi0 = 0;
        double phi = 2 * Math.PI / n;
        int r = 250; // радиус окружности

        for (int i = 0; i < points.length; i++) {
            //points[i] - вершина
            points[i] = graph.insertVertex(parent, null, i + 1, 300 + r * Math.cos(phi0), 300 + r * Math.sin(phi0), 18, 18, "shape=ellipse");
            phi0 += phi;
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(matrix[i][j] > 0)
                    graph.insertEdge(parent, null, matrix[i][j], points[i], points[j] );
            }
        }

        graph.getModel().endUpdate();
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        this.add(graphComponent);
        this.revalidate();
    }

    public void removeVert(){}
    public void addVert(){}
    public void addEdge(){}
    public void removeEdge(){}

}