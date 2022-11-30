public class App {
    public static void main(String[] args) throws Exception {
        
        Graph graph = new Graph("graph6.txt");
        int[] vcTest = {1,5,7,9};
        GraphToolBox.exactVC(graph);
        GraphToolBox.inexactVC(graph);
        GraphToolBox.optimalIS(graph);
        GraphToolBox.inexactIS(graph);

        GraphToolBox.isVC(graph, vcTest);

    }
}
