import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yaw
 */
public class GraphToolBox {
    // return an array containing the vertex numbers of an optimal VC.
    /**
     * @param inputGraph - a graph generated from Graph.java
     * @return array of Vertex Numbers
     * @author Parnell
     */
    public static int[] exactVC(Graph inputGraph) {
        //Generate Power Set

        int n = inputGraph.getGraph().length;
        int [] allVertex = new int [inputGraph.getGraph().length];

        //Creating an array with all teh vertices from graph
        for(int i = 0; i < inputGraph.getGraph().length; i++){
            allVertex[i] = i;
        }


        int [] bestVertexCover = new int[inputGraph.getGraph().length];
        for (int i = 0; i < (1<<n); i++){
            List<Integer> VertexCover = new ArrayList<>();
            int m = 1;
            for (int j = 0; j < n-1; j++){
                if((i&m) > 0){
                    VertexCover.add(allVertex[j]);
                    //System.out.println(VertexCover);
                }
                m = m << 1;
            }

            int [] vertexCoverArray = new int[VertexCover.size()];
            for(int r = 0; r < VertexCover.size(); r++){
                vertexCoverArray[r] = VertexCover.get(r);
                //System.out.print(vertexCoverArray[r]);
            }
            if(isVC(inputGraph,vertexCoverArray)){
                if(bestVertexCover.length == 0){
                    bestVertexCover = vertexCoverArray;
                }
                if(vertexCoverArray.length < bestVertexCover.length){
                    bestVertexCover = vertexCoverArray;
                }
            }


        }
        //Test if the set is a vertex cover
        //If it is smaller, replace current "VC" array.
        System.out.println(Arrays.toString(bestVertexCover));
        System.out.println(bestVertexCover.length);
        return bestVertexCover;
    }


    // return (in polynomial time) an array containing the vertex numbers of a VC.
    public static int[] inexactVC(Graph inputGraph) {
        return null;
    }
    
    // return an array containing the vertex numbers of an optimal IS.     
    public static int[] optimalIS(Graph inputGraph) {

        /*
         * Plan is to extract data from the graph, that data being:
         * - The number of vertices (should be length of array?)
         * - Each vertice's "neigbors" i.e. adjacent vertices
         * - Randomly choose any 1 vertex, mark it.
         * - Randomly select a vertex not in it's list of neighbors, mark
         * - Repeat until ?
         * - Record number of vertices.
         * - If highest, keep
         */

        int vertexCount = 0; //count of # of vertices in a set. if it's the highest, keep. 
        int independentSet[]; //will store an indeterminate number of vertices in the IS.
        int numVerts = inputGraph.getGraph().length; // number of vertices? 


        System.out.println(numVerts + " is the number of vertices in the graph");

        return null;
    }
    
    // return (in polynomial time) an array containing the vertex numbers of a IS.
    public static int[] inexactIS(Graph inputGraph) {
        return null;
    }

    /**
     * @param inputGraph - a graph generated from Graph.java
     * @param verts - a list of vertices seperated by commas
     * @return boolean
     * @author Trey Grossman
     */
    public static boolean isVC(Graph inputGraph, int[] verts){
        int vertFlag = 0; // flag used to track pass/fail of function
        // iterate through each vertex and its children
        for (int i = 0; i < inputGraph.getGraph().length; i++){
            vertFlag = 0; // reset flag
            for (int j = 0; j < inputGraph.getGraph()[i].length; j++){
                // iterate through each vertex listed in test VC "verts"
                for (int k : verts){
                    // switch flag if either the parent or child vertex matches the current test vertex
                    if (k == inputGraph.getGraph()[i][j] || k == i){vertFlag = 1;}
                }
            }
            // if flag was not changed per parent vertex cycle, return false
            if (vertFlag == 0){
                System.out.println("manual VC test: "+false); // test print
                return false;
            }
        }
        System.out.println("manual VC test: "+true); // test print
        return true;
    }
    public static boolean isIS(Graph inputGraph, int[] verts){
        for (int i : verts){
            for (int j : inputGraph.getGraph()[i]){
                for (int k : verts){
                    if (j == k){
                        System.out.println("manual IS test: "+false); // test print
                        return false;
                    }
                }
            }
        }
        System.out.println("manual IS test: "+true); // test print
        return true;
    }
}
