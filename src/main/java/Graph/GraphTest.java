package Graph;

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
        assertEquals("0 12 3 4\n1 0 4 5\n5 9 0 1\n4 8 7 0",graph.print());
        graph1.FloydWarshall();
        assertEquals("0 10 18 8 31 20\n10 0 16 9 21 21\n26 16 0 25 37 15\n7 9 25 0 30 12\n64 54 38 63 0 23\n41 31 15 40 23 0",graph1.print());
    }

}