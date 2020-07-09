package Graph;

import java.util.Scanner;

public class Graph {
    int n;
    int curI, curJ, curK;
    private int[][] matrix;
    static int I = 99999999; // Integer.MAX_VALUE

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


    public Graph(String var1) {
        Scanner var2 = new Scanner(var1);
        var2.useDelimiter("\n");
        int i = 0, j = 0;
        String tmp = var2.next();
        String[] arr = tmp.split(" ");
        n = arr.length;
        matrix = new int[n][n];
        for (String it : arr) {
            matrix[i][j] = Integer.parseInt(it);
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

        if(curJ < n) {
            if ((matrix[curI][curK] + matrix[curK][curJ] < matrix[curI][curJ]) && (curI != curJ) ) {
                changeMatrix(matrix[curI][curK] + matrix[curK][curJ]);
            }
            curJ++;
        }
        else if(curJ == n) { // обязательно выполняется
            curJ = 0;
            if(curI < n) {
                // то же самое
                if ((matrix[curI][curK] + matrix[curK][curJ] < matrix[curI][curJ]) && (curI != curJ) ) {
                    changeMatrix(matrix[curI][curK] + matrix[curK][curJ]);
                }
                curI++;
            }
            else if(curI == n)  { // обязательно выполняется
                // то же самое
                curI = 0;
                if(curK < n) {
                    if ((matrix[curI][curK] + matrix[curK][curJ] < matrix[curI][curJ]) && (curI != curJ) ) {
                        changeMatrix(matrix[curI][curK] + matrix[curK][curJ]);
                    }
                    curK++;
                }
                else {
                    return;
                }
            }

        }
    }

    public String print(){
        String var1 = "";
        for ( int i = 0; i < n; ++i) {
            for ( int j = 0; j < n; ++j) {
                if(matrix[i][j]==I)
                    var1 += " " + "I";
                else
                    var1 += " " + matrix[i][j];
            }
            var1+="\n";
        }
        System.out.println(var1);
        return var1;
    }
}
