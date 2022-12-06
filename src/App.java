public class App {
    public static void main(String[] args) throws Exception {

        /*
         * Manual vertex cover verifier
         * Provide file name of graph and list of vertices seperated by commas
         */
        
        Graph graph1 = new Graph("graph6.txt"); // graph
        int[] vcTest = {}; // vertex cover to test
        GraphToolBox.isVC(graph1, vcTest);
        /*
         * Manual independent set verifier
         * Provide file name of graph and list of vertices seperated by commas
         */
        
        Graph graph2 = new Graph("graph5.txt"); // graph
        int[] isTest = {}; // vertex cover to test
        GraphToolBox.optimalIS(graph2);
        GraphToolBox.isIS(graph2, isTest);

    }
}
