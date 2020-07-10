package Graph;

import java.util.Scanner;

public class Graph {
    int n;
    int curI, curJ, curK;
    private int[][] matrix;
    static int I = 99999999; // Integer.MAX_VALUE

    public Graph() {
        n = curI = curJ = curK = 0;
        matrix = new int[0][0];
    }

    public Graph(String var1) throws NumberFormatException {
        curK = 0;
        curI = 0;
        curJ = 0;
        Scanner var2 = new Scanner(var1);
        var2.useDelimiter("\n");
        int i = 0, j = 0;
        String tmp = var2.next();
        String[] arr = tmp.split(" ");
        n = arr.length;
        matrix = new int[n][n];
        for (String it : arr) {
            try{
                matrix[i][j] = Integer.parseInt(it);}
            catch(NumberFormatException e){
                System.err.println("You entered the wrong data type:use the int type.");}
            if(matrix[i][j]<0)
                System.err.println("The matrix cannot have negative numbers!");
            j++;
        }
        i++;
        j=0;
        while(var2.hasNext()) {
            tmp=var2.next();
            for (String it : tmp.split(" ")) {
                matrix[i][j]=Integer.parseInt (it);
                j++;
            }
            i++;
            j=0;
        }
        var2.close();
    }

    public void FloydWarshall() {
        int i, j, k;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (i == j)
                    matrix[i][j] = 0;
                else if (matrix[i][j] == 0)
                    matrix[i][j] = I;
            }
        }
        for (k = 0; k < n; k++) {
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    if ((matrix[i][k] + matrix[k][j] < matrix[i][j]) && (i != j) ) {
                        matrix[i][j] = matrix[i][k] + matrix[k][j];
                    }
                }
            }
        }
    }

    public void FWStep() {

        if(curK == n){
            System.out.println("finish");
            return;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j)
                    matrix[i][j] = 0;
                else if (matrix[i][j] == 0)
                    matrix[i][j] = I;
            }
        }

        if(curI < n && curJ >= n) {
            curJ = 0;
            curI++;
        }
        if(curK < n && curI >= n) {
            curI = 0;
            curK++;
        }

        System.out.println(curK);
        System.out.println(curI);

        for (; curK < n; curK++) {
            for (; curI < n; curI++) {
                for (curJ = 0; curJ < n; curJ++) {
                    //System.out.println(curJ);
                    if ((matrix[curI][curK] + matrix[curK][curJ] < matrix[curI][curJ]) ) {
                        matrix[curI][curJ] = matrix[curI][curK] + matrix[curK][curJ];
                        System.out.println("((((");
                    }

                }
                return;

            }
        }
    }

    public String print(){
        String var1 = "";
        for ( int i = 0; i < n; ++i) {
            for ( int j = 0; j < n; ++j) {
                if(j==0){
                    if(matrix[i][j]==I)
                        var1 += "I";
                    else
                        var1 +=matrix[i][j];}
                else{
                    if(matrix[i][j]==I)
                        var1 += " " + "I";
                    else
                        var1 += " " + matrix[i][j];}
            }
            if(i!=n-1)
                var1+="\n";
        }
        System.out.println(var1);
        return var1;
    }


    public int[][] getMatrix() {
        return matrix;
    }

    public void changeMatrix(int value) {
        matrix[curI][curJ] = value;
    }
    public int getN() { return n;}
    public int getJ() { return curJ;}
    public int getI() { return curI;}
    public int getK() { return curK;}

    public void setMatrix(int[][] newMatrix) {

    }

    public void updateMatrix(){

        n = n + 1;
        matrix = java.util.Arrays.copyOf(matrix, n);

        for(int i = 0; i < n-1; i++) {
            matrix[i] = java.util.Arrays.copyOf(matrix[i], n);
        }
        matrix[n-1] = new int[n];

    }

    public void deleteEdge(int v1, int v2){
        if(v1 > 0 && v1 <= n && v2 > 0 && v2 <= n){
            System.out.println("start delete");

            matrix[v1-1][v2-1] = 0;
        }
    }

    public void changeEdge(int v1, int v2, int newEdge){
        if(v1 > 0 && v1 <= n && v2 > 0 && v2 <= n && matrix[v1-1][v2-2] != 0){//ребро существует
            matrix[v1-1][v2-2] = newEdge;
        }
    }

    public void addEdge(int v1, int v2, int newEdge){
        if(v1 > 0 && v1 <= n && v2 > 0 && v2 <= n && matrix[v1-1][v2-2] == 0){//ребро не существует
            matrix[v1-1][v2-2] = newEdge;
        }
    }

    public void deleteVert(int v){
        //убираем все связи вершины
        if(v > 0 && v <= n){
            for(int i = 0; i < n; i++){
                matrix[i][v-1] = -1;
                matrix[v-1][i] = -1;
            }
        }

    }
}
