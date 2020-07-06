package Graph;

import java.util.*;

public class Graph {
    int n;
    public Graph(String var1) {
        Scanner var2 = new Scanner(var1);
        var2.useDelimiter("\n");
        int i = 0, j = 0;
        String tmp = var2.next();
        String[] arr = tmp.split(" ");
        n = arr.length;
        int[][] matrix= new int[n][n];
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

    public void FloydWarshall(int[][] matrix) {
        int i, j, k;
        for (k = 0; k < n; k++) {
            for (j = 0; j < n; j++) {
                for (i = 0; i < n; i++) {
                    if (matrix[i][k] + matrix[k][j] < matrix[i][j])
                        matrix[i][j] = matrix[i][k] + matrix[k][j];

                }
            }
        }
    }
    public String print() {
        String var1 = "";
        return var1;
    }
}
