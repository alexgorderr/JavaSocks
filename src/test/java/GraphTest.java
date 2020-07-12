import Graph.Graph;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


class GraphTest {
    private Graph graph,graph1;

    @BeforeEach
    void setUp() throws Exception {
        String str="0 0 3 0\n1 0 5 0\n0 0 0 1\n4 8 0 0";
        String str1="0 10 18 8 0 0\n10 0 16 9 21 0\n0 16 0 0 0 15\n7 9 0 0 0 12\n0 0 0 0 0 23\n0 0 15 0 23 0";
        graph=new Graph(str);
        graph1=new Graph(str1);
    }

    @Test
    void floydWarshall() {
        graph.FloydWarshall();
        assertEquals(" #  1  2  3  4  \n1  0  12  3  4  \n2  1  0  4  5  \n3  5  9  0  1  \n4  4  8  7  0  \n",graph.print());
        graph1.FloydWarshall();
        assertEquals(" #  1  2  3  4  5  6  \n1  0  10  18  8  31  20  \n2  10  0  16  9  21  21  \n3  26  16  0  25  37  15  \n4  7  9  25  0  30  12  \n5  64  54  38  63  0  23  \n6  41  31  15  40  23  0  \n",graph1.print());
    }

}