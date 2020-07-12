package Graph;

import java.util.Scanner;

public class Graph {
    int n;
    int curI, curJ, curK;
    private int[][] matrix;

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
        for(i = 0; i < n; i++)
            if(matrix[i][i] != -1)
                matrix[i][i] = 0;

        for (k = 0; k < n; k++) {
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++) {
                    if(matrix[i][k] <=0 || matrix[k][j] <= 0 || matrix[i][j] == -1) continue;
                    if ((matrix[i][k] + matrix[k][j] < matrix[i][j] || matrix[i][j] == 0) && (i != j) ) {
                        matrix[i][j] = matrix[i][k] + matrix[k][j];
                    }
                }
            }
        }
    }

    public void FWStep() {

        if(curI == 0 && curJ == 0 && curK == 0)
            for(int i = 0; i < n; i++)
                if(matrix[i][i] != -1)
                    matrix[i][i] = 0;
        if(curK == n){
            System.out.println("finish");
            return;
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
                    if(matrix[curI][curK] <=0 || matrix[curK][curJ] <= 0 || matrix[curI][curJ] == -1) continue;
                    if ((matrix[curI][curK] + matrix[curK][curJ] < matrix[curI][curJ] || matrix[curI][curJ] == 0) && (curI != curJ) ) {
                        matrix[curI][curJ] = matrix[curI][curK] + matrix[curK][curJ];
                        System.out.println("((((");
                    }

                }
                return;
            }
        }
    }

    public String print() {
        String res = "";
        String str = " #  ";
        StringBuffer buff = new StringBuffer();
        boolean[] check = new boolean[n];
        for(int i = 0; i < n; i++) {
            if(matrix[i][0] < 0) check[i] = true;
        }
        for(int i = 0; i < n; i++) {
            if(check[i] != true) continue;
            for(int j = 0; j < n; j++) {
                if(i==j) continue;
                if(matrix[i][j] >= 0) check[i] = false;
            }
        }

        for(int i = 0; i < n; i++) {
            int num = i+1;
            if(check[i] == true) continue;
            buff.append(num);
            for(int k = 0; k < 4-buff.length();k++) buff.append(" ");
            str+= buff;
            buff.delete(0, buff.length());
        }
        res += str + "\n";

        for(int i = 0; i < n; i++) {
            str = "";
            int num = i+1;
            if(check[i] == true) continue;
            buff.append(num);
            for(int k = 0; k < 4-buff.length();k++) buff.append(" ");
            str+= buff;
            buff.delete(0, buff.length());
            for(int j = 0; j < n; j++) {
                if(check[j] == true) continue;
                buff.append(matrix[i][j]);
                for(int k = 0; k < 4-buff.length();k++) buff.append(" ");
                str+= buff;
                buff.delete(0, buff.length());
            }
            res+= str + "\n";
        }


        return res;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getN() { return n;}
    public int getJ() { return curJ;}
    public int getI() { return curI;}
    public int getK() { return curK;}


    public void resetGraph(int[][] matr) {
        this.curI = this.curJ = this.curK = 0;
        for(int i = 0; i < matr.length; i++) {
            for(int j = 0; j < matr.length; j++) {
                this.matrix[i][j] = matr[i][j];
            }
        }
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
        if(v1 > 0 && v1 <= n && v2 > 0 && v2 <= n && matrix[v1][v2] > 0){
            System.out.println("start delete");

            matrix[v1-1][v2-1] = 0;
        }
    }

    public void changeEdge(int v1, int v2, int newEdge){
        if(v1 > 0 && v1 <= n && v2 > 0 && v2 <= n && matrix[v1-1][v2-1] > 0){ //ребро существует
            matrix[v1-1][v2-1] = newEdge;
        }
    }

    public void addEdge(int v1, int v2, int newEdge){
        if(v1 > 0 && v1 <= n && v2 > 0 && v2 <= n && matrix[v1-1][v2-1] == 0) { // ребро не существует
            matrix[v1-1][v2-1] = newEdge;
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
