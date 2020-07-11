package GUI;

//import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class VizualizatorTest {

    private Vizualizator viz;

    @Test
    void addVert() throws IOException {
        viz=new Vizualizator();
        int matrix[][]={{0,1,2},{3,0,4},{5,6,0}};
        viz.initMatrix(matrix,3);
        viz.functionVisual(20,30);
        viz.addVert();
        viz.addEdge(3,4,1);
        assertTrue(viz.matrix[2][3]==1);
    }
    @Test
    void addNonEdge_() throws IOException {
        try{
            viz=new Vizualizator();
            int matrix[][]={{0,1,2},{3,0,4},{5,6,0}};
            viz.initMatrix(matrix,3);
            viz.functionVisual(20,30);
            viz.addVert();
            viz.addVert();
            viz.removeVert(4);
            viz.removeVert(5);
            viz.addEdge(4,5,5);
        }
        catch(IOException e){
            assertEquals("This edge does not exist",e.getMessage());
        }
    }
    @Test
    void removeVert() throws IOException {
        viz=new Vizualizator();
        int matrix[][]={{0,1,2},{3,0,4},{5,6,0}};
        viz.initMatrix(matrix,3);
        viz.functionVisual(20,30);
        viz.removeVert(1);
        assertTrue(viz.matrix[0][1]==0);
    }

    @Test
    void addEdge() throws IOException {
        viz=new Vizualizator();
        int matrix[][]={{0,1,2},{3,0,4},{5,6,0}};
        viz.initMatrix(matrix,3);
        viz.functionVisual(20,30);
        viz.addVert();
        viz.addEdge(3,4,5);
        assertTrue(viz.matrix[2][3]==5);
    }

    @Test
    void addNonEdge(){
        try{
            viz=new Vizualizator();
            int matrix[][]={{0,1,2},{3,0,4},{5,6,0}};
            viz.initMatrix(matrix,3);
            viz.functionVisual(20,30);
            viz.addVert();
            viz.removeVert(4);
            viz.addEdge(3,4,5);
        }
        catch(IOException e){
            assertEquals("This edge does not exist",e.getMessage());
        }
    }

    @Test
    void addSecondEdge(){
        try{
            viz=new Vizualizator();
            int matrix[][]={{0,1,2},{3,0,4},{5,6,0}};
            viz.initMatrix(matrix,3);
            viz.functionVisual(20,30);
            viz.addVert();
            viz.addEdge(3,4,5);
            viz.addEdge(3,4,5);
        }
        catch(IOException e){
            assertEquals("This edge does not exist",e.getMessage());
        }
    }

    @Test
    void changeEdge() throws IOException {
        viz=new Vizualizator();
        int matrix[][]={{0,1,2},{3,0,4},{5,6,0}};
        viz.initMatrix(matrix,3);
        viz.functionVisual(20,30);
        viz.addVert();
        viz.addVert();
        viz.addEdge(4,5,5);
        viz.changeEdge(4,5,10);
        assertTrue(viz.matrix[3][4]==10);
    }

    @Test
    void changeNonEdge() throws IOException {
        try {
            viz = new Vizualizator();
            int matrix[][] = {{0, 1, 2}, {3, 0, 4}, {5, 6, 0}};
            viz.initMatrix(matrix, 3);
            viz.functionVisual(20, 30);
            viz.addVert();
            viz.addVert();
            viz.addEdge(3, 4, 5);
            viz.changeEdge(5, 6, 10);
        } catch (IOException e) {
            assertEquals("This edge does not exist", e.getMessage());
        }
    }

        @Test
        void removeEdge() throws IOException {
            viz=new Vizualizator();
            int matrix[][]={{0,1,2},{3,0,4},{5,6,0}};
            viz.initMatrix(matrix,3);
            viz.functionVisual(20,30);
            viz.addVert();
            viz.addVert();
            viz.addEdge(4,5,5);
            viz.removeEdge(3,4);
            System.out.println(viz.matrix[3][4]);
            assertFalse(viz.matrix[3][4]==0);
        }

        @Test
        void removeNonEdge(){
            try{
                viz=new Vizualizator();
                int matrix[][]={{0,1,2},{3,0,4},{5,6,0}};
                viz.initMatrix(matrix,3);
                viz.functionVisual(20,30);
                viz.addVert();
                viz.addEdge(3,4,5);
                viz.removeEdge(3,4);
                viz.removeEdge(3,4);}
            catch(IOException e){
                assertEquals("This edge does not exist",e.getMessage());
            }
        }


        @Test
        void removeNonVert(){
        try{
            viz = new Vizualizator();
            int matrix[][] = {{0, 1, 2}, {3, 0, 4}, {5, 6, 0}};
            viz.initMatrix(matrix, 3);
            viz.functionVisual(20,30);
            viz.addVert();
            viz.removeVert(4);
            viz.removeVert(4);}
        catch (IOException e){
            assertEquals("This vertex does not exist",e.getMessage()); }
        }

    }

