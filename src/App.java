public class App {
    public static void main(String[] args) throws Exception {

        Graph g = new Graph("graph7.txt");

        GraphToolBox.inexactVC(g);
        GraphToolBox.exactVC(g);
        GraphToolBox.inexactIS(g);
        GraphToolBox.optimalIS(g);

    }
}
