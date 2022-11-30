public class App {
    public static void main(String[] args) throws Exception {
        
        /*
         * Manual vertex cover verifier
         * Provide file name of graph and list of vertices seperated by commas
         */
        Graph graph = new Graph("graph6.txt"); // graph
        int[] vcTest = {1,4,5,7,9}; // vertex cover to test
        GraphToolBox.isVC(graph, vcTest);

    }
}
