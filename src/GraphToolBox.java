/**
 *
 * @author yaw
 */
public class GraphToolBox {
    // return an array containing the vertex numbers of an optimal VC.
    public static int[] exactVC(Graph inputGraph) {
        return null;
    }
    
    // return (in polynomial time) an array containing the vertex numbers of a VC.
    public static int[] inexactVC(Graph inputGraph) {
        return null;
    }
    
    // return an array containing the vertex numbers of an optimal IS.
    public static int[] optimalIS(Graph inputGraph) {
        return null;
    }
    
    // return (in polynomial time) an array containing the vertex numbers of a IS.
    public static int[] inexactIS(Graph inputGraph) {
        return null;
    }

    public static boolean isVC(Graph inputGraph, int[] verts){
        int vertFlag = 0;
        for (int i = 0; i < inputGraph.getGraph().length; i++){
            vertFlag = 0;
            for (int j = 0; j < inputGraph.getGraph()[i].length; j++){
                for (int k : verts){
                    if (k == inputGraph.getGraph()[i][j] || k == i){
                        vertFlag = 1;
                    }
                }
                System.out.println(inputGraph.getGraph()[i][j]);
            }
            if (vertFlag == 0){
                System.out.println(false);
                return false;
            }
        }
        System.out.println(true);
        return true;
    }
}
