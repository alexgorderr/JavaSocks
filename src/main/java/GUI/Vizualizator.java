package GUI;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxEvent;

import com.mxgraph.util.mxEventSource;
import com.mxgraph.view.mxGraph;

import com.mxgraph.view.mxEdgeStyle;
//import com.mxgraph.view.mxStylesheet;


import com.mxgraph.util.mxConstants;



import javax.swing.*;
//import java.io.PrintWriter;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;



public class Vizualizator extends JPanel{
    private static int n = 0;
    private static int curCount = 0;
    private int height;
    private int width;
    int[][] matrix;//заданная изначально матрица
    int[][] resultMatrix;//матрица достижимости
    int[] vertName;


    private mxGraph graph;
    private mxGraph stepGraph;
    private mxGraphComponent graphComponent;//модель графа
    private mxGraphComponent stepGraphComponent;//модель графа достижимости на текущем шаге
    private Object parent;
    private Object points[];
    //private HashMap <Object, HashMap<Object, Object>> edges;
    private MouseAdapter mouseAdapter;
   // private mxEventSource.mxIEventListener listener;
    private MouseMotionListener eventListener;

    public void updateResultMatrix(int[][] matrix, int curN){
        //this.setSize();

        if(curN != n)
            return;
        resultMatrix = java.util.Arrays.copyOf(resultMatrix, n);

        for(int i = 0; i < n; i++) {
            resultMatrix[i] = java.util.Arrays.copyOf(matrix[i], n);
        }
    }

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


        matrix = java.util.Arrays.copyOf(adjMatrix, n);
        resultMatrix = java.util.Arrays.copyOf(adjMatrix, n);
        for(int i = 0; i < n; i++){
            matrix[i] = java.util.Arrays.copyOf(adjMatrix[i], n);
            resultMatrix[i] = java.util.Arrays.copyOf(adjMatrix[i], n);
            vertName[i] = 0;
        }

    }

    private void returnGraphModel(){

        this.remove(this.getComponents()[0]);
        this.setVisible(false);
        this.add(graphComponent);
        this.setVisible(true);
        this.revalidate();

    }
    public void functionVisual(int h, int w) {

        graph = new mxGraph();
        height = h;
        width = w;
        this.setSize(h, w);


       // boolean permission = false;

       if(mouseAdapter == null)
            mouseAdapter = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    //permission = true;
                    mxCell cell = null;
                    if(graphComponent != null) {
                        cell = (mxCell) graphComponent.getCellAt(mouseEvent.getX(), mouseEvent.getY());
                    }
                    if(cell != null)
                        displayStepResult((int)cell.getValue());
                    else{
                        if(stepGraphComponent != null){
                            returnGraphModel();
                        }
                    }
                    super.mouseClicked(mouseEvent);
                }
                @Override
                public void mouseDragged(MouseEvent e) {
                    //System.out.println("?????//");
                    //graphComponent.getToolkit().
                    //graphComponent.getCom
                    mxCell cell = null;
                    cell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());
                    if(cell != null) {

                        if(cell.isVertex() && cell.isConnectable()){
                            System.out.println("*****");
                            super.mouseDragged(e);
                        }else{
                            //mxGraph.prototype.resetEdgesOnMove:
                            /*graph.getModel();
                            graph.startEditingAtCell(change.child);
                            grap.start*/

                            /*if(cell.isConnectable())
                                return;*/
                           // graphComponent.getCursor().
                            //super.mouseReleased();
                           // super.mouseReleased(e);
                        }
                        //return;
                        //

                    }else {
                        return;
                        //System.out.println("?????//");
                    }

                    //super.mouseDragged(e);
                }

            };
       /*if(eventListener == null)
           eventListener = new EventListener() {
               @Override
               public String toString() {
                   return super.toString();
               }
           }*/
       /* eventListener = new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                System.out.println("$$$$$");
                mxCell cell = null;
                cell = (mxCell) graphComponent.getCellAt(mouseEvent.getX(), mouseEvent.getY());
                if(cell != null)
                    super.mouseDragged(mouseEvent);
            }
        };*/




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
                //stepGraph.insertVertex()
                phi0 += phi;
                vertName[i] = 1;

        }

            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    if(matrix[i][j] > 0) {
                        //var style = graph.getStylesheet().getDefaultEdgeStyle();

                        //Object o = graph.createEdge(parent, null, matrix[i][j], points[i], points[j],"edgeStyle=myEdgeStyle");
                        //graph.createEdge()
                        Object o = graph.insertEdge(parent, null, matrix[i][j], points[i], points[j]);
                        //graph.getModel().is
                        //o.
                        //graph.getModel().setStyle(, "edgeStyle=myEdgeStyle");
                        //style.put("strokeColor", standartColor);
                        //graph.insertEdge(parent, null, matrix[i][j], points[i], points[j]);
                    }
                }
            }

        graph.getModel().endUpdate();

        mxParallelEdgeLayout layout = new mxParallelEdgeLayout(graph);
        layout.execute(graph.getDefaultParent());

        //graph.getModel().addListener(mxEvent.CHANGE, listener);


        graphComponent = new mxGraphComponent(graph);


        graphComponent.getGraphControl().addMouseListener(mouseAdapter);


        //graphComponent.getGraphControl().removeMouseMotionListener(graphComponent.getMouseMotionListeners()[0]);
        graphComponent.getGraphControl().addMouseMotionListener(mouseAdapter);


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

    public void removeVert(int vertID) throws IOException {//название вершины (от 1 и ...)

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
        else{
            throw new IOException("This vertex does not exist");        }

    }

    public void addEdge(int v1, int v2, int edge) throws IOException {


        if(v1 > 0 && v2 > 0 && v1 < n + 1 && v2 < n + 1 && vertName[v1-1] == 1 && vertName[v2-1] == 1){//условия существования вершин

            graph.getModel().beginUpdate();
            matrix[v1-1][v2-1] = edge;

            graph.getModel().beginUpdate();

            this.remove(graphComponent);

            functionVisual(height, width);


            graph.getModel().endUpdate();

        }
        else{
            throw new IOException("This edge does not exist");
        }


    }

    public void changeEdge(int v1, int v2, int newEdge){
        try{
        if(v1 > 0 && v2 > 0 && v1 < n + 1 && v2 < n + 1 && vertName[v1-1] == 1 && vertName[v2-1] == 1){//условия существования вершин
            graph.getModel().beginUpdate();
            removeEdge(v1, v2);
            addEdge(v1, v2, newEdge);
            graph.getModel().endUpdate();
        }}
        catch(IOException e){
            System.err.println("This edge can't be change");
        }


    }

    public void removeEdge(int v1, int v2) throws IOException {

        if(v1 > 0 && v2 > 0 && v1 <= n && v2 <= n && vertName[v1-1] == 1 && vertName[v2-1] == 1){//условия существования вершин

            System.out.println("next step");
            matrix[v1-1][v2-1] = 0;

            graph.getModel().beginUpdate();
            this.remove(graphComponent);
            functionVisual(height, width);
            //m = 0;
            graph.getModel().endUpdate();

        }
        else{
            throw new IOException("This edge does not exist");
        }

    }

    private void displayStepResult(int vert){//матрица достижимсти на текущем шаге

        if(vert <= 0){return;}//никакая вершина не выбрана, но можно нарисовать все ребра

        this.remove(this.getComponents()[0]);
        this.setVisible(false);
        this.setVisible(true);


        stepGraph = new mxGraph();
        stepGraph.getModel().beginUpdate();
        Object[] stepPoints = new Object[n];


        double phi0 = 0;
        double phi = 2 * Math.PI / n;
        int r = 250; // радиус окружности

        for (int i = 0; i < points.length; i++) {

            if(vertName[i] > 0){
                stepPoints[i] = stepGraph.insertVertex(stepGraph.getDefaultParent(), null, i+1, 300 + r * Math.cos(phi0), 300 + r * Math.sin(phi0), 18, 18,"shape=ellipse" );
                phi0 += phi;
            }

        }
        stepGraph.getModel().endUpdate();


           stepGraph.getModel().beginUpdate();
            for(int i = 0; i < n; i++){

                int edge = resultMatrix[vert-1][i];

                if(edge > 0) {

                    var style = stepGraph.getStylesheet().getDefaultEdgeStyle();
                    style.put("strokeColor", "#000000");
                    style.put("fontColor", "#000000");

                    stepGraph.getModel().setStyle(stepGraph.insertEdge(stepGraph.getDefaultParent(), null, edge, stepPoints[vert-1], stepPoints[i]), "edgeStyle=myEdgeStyle");

                }
            }

        stepGraph.getModel().endUpdate();
        stepGraphComponent = new mxGraphComponent(stepGraph);

        stepGraphComponent.getGraphControl().addMouseListener(mouseAdapter);


        this.add(stepGraphComponent);
        this.revalidate();

    }


    public void displayResult(int[][] matr) {//рисует граф по матрице достижимости

        //this.remove(graphComponent);


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

       // mxParallelEdgeLayout layoutParallel = new mxParallelEdgeLayout(graph);
       // mxCircleLayout layoutCircle = new mxCircleLayout(graph);
        //layoutParallel.execute(graph.getDefaultParent());
      //  layoutCircle.execute(graph.getDefaultParent());

        //graphComponent - наша текущая модель
        this.remove(graphComponent);

        stepGraphComponent = new mxGraphComponent(graph);
        this.add(stepGraphComponent);

        //graphComponent = new mxGraphComponent(graph);
        //this.add(graphComponent);

        this.revalidate();
    }

}

