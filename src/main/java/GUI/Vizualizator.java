package GUI;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEvent;

import com.mxgraph.view.mxGraph;

import com.mxgraph.view.mxEdgeStyle;
import com.mxgraph.view.mxStylesheet;


import com.mxgraph.util.mxConstants;



import javax.swing.*;
//import java.io.PrintWriter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;



public class Vizualizator extends JPanel{
    private static int n = 0;
    private static int curCount = 0;
    int[][] matrix;//заданная изначально матрица
    int[][] resultMatrix;//матрица достижимости
    int[] vertName;

    private mxGraph graph;
    private mxGraphComponent graphComponent;
    private Object parent;
    private Object points[];
    //private HashMap <Object, HashMap<Object, Object>> edges;
    private MouseAdapter mouseAdapter;



    private void upDateVertCount(boolean flag){
        if(flag) {
            n = n + 1;
            points = java.util.Arrays.copyOf(points, n);
            vertName = java.util.Arrays.copyOf(vertName, n);
            matrix = java.util.Arrays.copyOf(matrix, n);
            resultMatrix = java.util.Arrays.copyOf(resultMatrix, n);

            for(int i = 0; i < n-1; i++) {
                matrix[i] = java.util.Arrays.copyOf(matrix[i], n);
                resultMatrix[i] = java.util.Arrays.copyOf(resultMatrix[i], n);
            }
            matrix[n-1] = new int[n];
            resultMatrix[n-1] = new int[n];

            for(int i = 0; i < n; i++){
                matrix[n-1][i] = 0;
                //check rs matr
                resultMatrix[n-1][i] = 99999999;
            }
        }

    }

    public void initMatrix(int[][] adjMatrix, int newN){

        n = newN;
        curCount = n;
        vertName = new int[n];
        matrix = new int[n][n];
        resultMatrix = new int[n][n];
        points = new Object[n];//объекты вершмн
        //edges = new HashMap <Object, HashMap<Object, Object>>();

        matrix = java.util.Arrays.copyOf(adjMatrix, n);
        resultMatrix = java.util.Arrays.copyOf(adjMatrix, n);
        for(int i = 0; i < n; i++){
            matrix[i] = java.util.Arrays.copyOf(adjMatrix[i], n);
            resultMatrix[i] = java.util.Arrays.copyOf(adjMatrix[i], n);
            vertName[i] = 0;
        }

    }

    public void functionVisual() {

        graph = new mxGraph();

        if(mouseAdapter == null)
            mouseAdapter = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    mxCell cell = null;
                    if(graphComponent != null) {
                        cell = (mxCell) graphComponent.getCellAt(mouseEvent.getX(), mouseEvent.getY());
                    }
                    if(cell != null)
                        displayStepResult((int)cell.getValue());
                    super.mousePressed(mouseEvent);
                }
            };



        parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();

        double phi0 = 0;
        double phi = 2 * Math.PI / n;
        int r = 250; // радиус окружности

        for (int i = 0; i < points.length; i++) {
            //points[i] - вершина
            if(vertName[i] == -1)//вершина удалена
                continue;
            points[i] = graph.insertVertex(parent, null, i + 1, 300 + r * Math.cos(phi0), 300 + r * Math.sin(phi0), 18, 18, "shape=ellipse");
            phi0 += phi;
            vertName[i] = 1;

        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(matrix[i][j] > 0) {
                    var style = graph.getStylesheet().getDefaultEdgeStyle();
                    // Object standartColor = style.get("strokeColor");
                    // style.put("strokeColor", "#000000");
                    graph.getModel().setStyle(graph.insertEdge(parent, null, matrix[i][j], points[i], points[j]), "edgeStyle=myEdgeStyle");
                    //style.put("strokeColor", standartColor);
                    //graph.insertEdge(parent, null, matrix[i][j], points[i], points[j]);
                }
            }
        }

        graph.getModel().endUpdate();

        mxParallelEdgeLayout layout = new mxParallelEdgeLayout(graph);
        layout.execute(graph.getDefaultParent());


        graphComponent = new mxGraphComponent(graph);


        graphComponent.getGraphControl().addMouseListener(mouseAdapter);


        this.add(graphComponent);
        this.revalidate();
    }

    public void addVert(){

        curCount++;
        upDateVertCount(true);

        graph.getModel().beginUpdate();
        points[n-1] = graph.insertVertex(parent, null, n, 300, 300, 18, 18, "shape=ellipse");
        vertName[n - 1] = 1;
        graph.getModel().endUpdate();


    }

    public void removeVert(int vertID){//название вершины (от 1 и ...)

        if(vertID > 0 && vertID < n + 1){
            curCount--;
            //upDateVertCount(false);
            graph.getModel().beginUpdate();

            Object pointsForRemove[] = new Object[1];

            pointsForRemove[0] = points[vertID-1];

            graph.removeCells(pointsForRemove);

            graph.getModel().endUpdate();

            for(int i = 0; i < n; i++){
                matrix[i][vertID-1]=0;
                matrix[vertID-1][i]=0;

            }

            vertName[vertID-1] = -1;

        }

    }

    public void addEdge(int v1, int v2, int edge){


        if(v1 > 0 && v2 > 0 && v1 < n + 1 && v2 < n + 1 && vertName[v1-1] == 1 && vertName[v2-1] == 1){//условия существования вершин

            graph.getModel().beginUpdate();
            matrix[v1-1][v2-1] = edge;

            graph.getModel().beginUpdate();

            this.remove(graphComponent);

            functionVisual();


            graph.getModel().endUpdate();

        }


    }

    public void changeEdge(int v1, int v2, int newEdge){

        if(v1 > 0 && v2 > 0 && v1 < n + 1 && v2 < n + 1 && vertName[v1-1] == 1 && vertName[v2-1] == 1){//условия существования вершин
            graph.getModel().beginUpdate();
            removeEdge(v1, v2);
            addEdge(v1, v2, newEdge);
            graph.getModel().endUpdate();
        }


    }

    public void removeEdge(int v1, int v2){

        if(v1 > 0 && v2 > 0 && v1 < n && v2 < n && vertName[v1-1] == 1 && vertName[v2-1] == 1){//условия существования вершин

            matrix[v1-1][v2-1] = 0;

            graph.getModel().beginUpdate();
            this.remove(graphComponent);
            functionVisual();
            //m = 0;
            graph.getModel().endUpdate();

        }

    }

    private void displayStepResult(int vert){//матрица достижимсти на текущем шаге

        if(vert <= 0){return;}//никакая вершина не выбрана, но можно нарисовать все ребра

        graph.getModel().beginUpdate();
        for(int i = 0; i < n; i++){

            int edge = resultMatrix[vert-1][i];

            if(edge > 0) {
                var style = graph.getStylesheet().getDefaultEdgeStyle();
                style.put("strokeColor", "#000000");
                style.put("fontColor", "#000000");
                System.out.println(3333333);

                graph.getModel().setStyle(graph.insertEdge(parent, null, edge, points[vert-1], points[i]), "edgeStyle=myEdgeStyle");
                System.out.println(88888);

            }
        }
        graph.getModel().endUpdate();

    }


    public void displayResult(int[][] matr) {//рисует граф по матрице достижимости

        this.remove(graphComponent);
        graph = new mxGraph();
        parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();
        double phi0 = 0;
        double phi = 2 * Math.PI / n;
        int r = 250; // радиус окружности


        //отображаем все вершины
        for (int i = 0; i < points.length; i++) {
            //points[i] - вершина
            if (vertName[i] == -1)//вершина удалена
                continue;
            points[i] = graph.insertVertex(parent, null, i + 1, 300 + r * Math.cos(phi0), 300 + r * Math.sin(phi0), 18, 18, "shape=ellipse");
            phi0 += phi;
        }


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] > 0) {
                    HashMap<Object, Object> valH = new HashMap<Object, Object>();
                    //вес ребра между вершинами - длина кратчайшего пути между ними
                    //var edgeStyle = graph.getStylesheet().getDefaultEdgeStyle();
                    // edgeStyle.put(mxConstants.STYLE_EDGE, mxEdgeStyle.EntityRelation);

                    valH.put(points[j], graph.insertEdge(parent, null, matr[i][j], points[i], points[j]));//, mxConstants.STYLE_EDGE));
                    //edges.put(points[i], valH);
                }
            }
        }

        graph.getModel().endUpdate();

        graphComponent = new mxGraphComponent(graph);
        this.add(graphComponent);

        this.revalidate();
    }

}
