package GUI;


import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.model.mxCell;

import com.mxgraph.swing.mxGraphComponent;
//import com.mxgraph.mxCa
import com.mxgraph.canvas.mxGraphics2DCanvas;
import com.mxgraph.swing.view.mxCellEditor;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEvent.*;

import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource;
import com.mxgraph.view.*;
import com.mxgraph.util.mxUndoableEdit;






import javax.swing.*;
//import java.io.PrintWriter;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;



public class Vizualizator extends JPanel{
    mxCellEditor editor;

    private static int n = 0;
    private static int curCount = 0;
    private int height;
    private int width;
    int[][] matrix;//заданная изначально матрица
    int[][] resultMatrix;//матрица достижимости
    int[] vertName;
    private boolean flag = false;


    private mxGraph graph;
    private mxGraph stepGraph;
    private mxGraphComponent graphComponent; // модель графа
    private mxGraphComponent stepGraphComponent; // модель графа достижимости на текущем шаге
    private Object parent;
    private Object points[];

    private MouseAdapter mouseAdapter;

   // private mxEventSource.mxIEventListener listener;
    //private MouseMotionListener eventListener;

    public void updateResultMatrix(int[][] matrix, int curN){
        //editor.getEditor()

        flag = true;//алгоритм работает
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

       if(mouseAdapter == null)
            mouseAdapter = new MouseAdapter() {

                @Override
                public void mouseMoved(MouseEvent e) {

                    graphComponent.setEnabled(true);
                    mxCell cell;
                    cell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());


                    if(cell != null){
                        if(cell.isVertex()){
                            double x = cell.getGeometry().getCenterX();
                            double y = cell.getGeometry().getCenterY();
                            if(Math.abs(e.getX()-x) < 10 && Math.abs(e.getY() - y) < 10)
                                graphComponent.setEnabled(false);

                            graphComponent.getGraph().setCellsMovable(true);
                        }
                        if(cell.isEdge()){
                            graphComponent.getGraph().setCellsMovable(false);
                        }
                    }
                    super.mouseMoved(e);
                }

                @Override
                public void mouseClicked(MouseEvent mouseEvent) {

                    if(!flag)//алгоритм еще не работает
                        return;

                    mxCell cell = null;

                    if(graphComponent != null) {
                        cell = (mxCell) graphComponent.getCellAt(mouseEvent.getX(), mouseEvent.getY());
                    }

                    if(cell != null && cell.isVertex())
                        displayStepResult((int)cell.getValue());
                    else{
                        if(stepGraphComponent != null){
                            returnGraphModel();
                        }
                    }
                    super.mouseClicked(mouseEvent);
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
                points[i] = graph.insertVertex(parent, null, i + 1, 300 + r * Math.cos(phi0), 300 + r * Math.sin(phi0), 40, 40, "shape=ellipse");

                phi0 += phi;
                vertName[i] = 1;

        }


            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    if(matrix[i][j] > 0) {
                        Object o = graph.insertEdge(parent, null, matrix[i][j], points[i], points[j]);
                    }
                }
            }

        graph.getModel().endUpdate();

        mxParallelEdgeLayout layout = new mxParallelEdgeLayout(graph);
        layout.execute(graph.getDefaultParent());

        graphComponent = new mxGraphComponent(graph);

        graphComponent.getGraphControl().addMouseListener(mouseAdapter);
        graphComponent.getGraphControl().addMouseMotionListener(mouseAdapter);

        graphComponent.getGraph().setCellsMovable(false);
        graphComponent.getGraph().setCellsEditable(false);
        graphComponent.getGraph().setCellsResizable(false);

        this.add(graphComponent);
        this.revalidate();
    }

    public void addVert(){

        curCount++;
        upDateVertCount(true);

        graph.getModel().beginUpdate();
        points[n-1] = graph.insertVertex(parent, null, n, 300, 300, 40, 40, "shape=ellipse");
        vertName[n - 1] = 1;
        graph.getModel().endUpdate();

    }

    public void removeVert(int vertID) throws IOException {//название вершины (от 1 и ...)

        if(vertID > 0 && vertID < n + 1){
            curCount--;
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
        else {
            throw new IOException("This vertex does not exist");
        }

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
        else {
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
            }
        }
        catch(IOException e){
            System.err.println("This edge can't be change");
        }
    }

    public void removeEdge(int v1, int v2) throws IOException {

        if(v1 > 0 && v2 > 0 && v1 <= n && v2 <= n && vertName[v1-1] == 1 && vertName[v2-1] == 1){//условия существования вершин

            matrix[v1-1][v2-1] = 0;

            graph.getModel().beginUpdate();
            this.remove(graphComponent);
            functionVisual(height, width);
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
                stepPoints[i] = stepGraph.insertVertex(stepGraph.getDefaultParent(), null, i+1, 300 + r * Math.cos(phi0), 300 + r * Math.sin(phi0), 40, 40,"shape=ellipse" );
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

        MouseAdapter stepMouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                //super.mouseClicked(e);
                if(!flag)//алгоритм еще не работает
                    return;

                mxCell cell = null;

                if(stepGraphComponent != null) {
                    cell = (mxCell) stepGraphComponent.getCellAt(mouseEvent.getX(), mouseEvent.getY());
                }

                if(cell != null && cell.isVertex())
                    displayStepResult((int)cell.getValue());
                else{
                    if(stepGraphComponent != null){
                        returnGraphModel();
                    }
                }
                super.mouseClicked(mouseEvent);
            }

        };

        //stepGraphComponent.getGraphControl().addMouseListener(mouseAdapter);
        stepGraphComponent.getGraphControl().addMouseListener(stepMouseAdapter);

        stepGraphComponent.getGraph().setCellsMovable(false);
        stepGraphComponent.getGraph().setCellsEditable(false);
        stepGraphComponent.getGraph().setCellsResizable(false);


        this.add(stepGraphComponent);
        this.revalidate();

    }

    public int[][] getBaseMatrix() {
        return matrix;
    }


}

