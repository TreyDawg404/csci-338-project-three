import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yaw
 */
public class GraphToolBox {
    
    
    
    /**
     * Return an array containing the vertex numbers of an optimal VC
     * @param inputGraph - a graph generated from Graph.java
     * @return array of Vertex Numbers
     * @author Parnell
     */
     public static int[] exactVC(Graph inputGraph) {
        //Generate Power Set
        int n = inputGraph.getGraph().length;
        int [] allVertex = new int [inputGraph.getGraph().length];

        //Creating an array with all the vertices from graph
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
            //Converting VertexCover ArrayList into array form.
            for(int r = 0; r < VertexCover.size(); r++){
                vertexCoverArray[r] = VertexCover.get(r);
            }
            //Test if the set is a vertex cover
            if(isVC(inputGraph,vertexCoverArray)){
                //If it is smaller, replace current "VC" array.
                if(bestVertexCover.length == 0){
                    bestVertexCover = vertexCoverArray;
                }
                if(vertexCoverArray.length < bestVertexCover.length){
                    bestVertexCover = vertexCoverArray;
                }
            }


        }
        System.out.println(Arrays.toString(bestVertexCover));
        System.out.println("Length of best Vertex Cover: " + bestVertexCover.length);
        return bestVertexCover;
    }

    
    
    /**
     * Return (in polynomial time) an array containing the vertex numbers of a VC
     * @param inputGraph - a graph generated from Graph.java
     * @return array of Vertex Numbers
     * @author Grossman
     */
    public static int[] inexactVC(Graph inputGraph) {
        ArrayList<Integer> added = new ArrayList<Integer>();
        for (int i = 0; i < inputGraph.getGraph().length; i++){
            added.add(i);
            if (isVC(inputGraph, added.stream().mapToInt(Integer::valueOf).toArray())){
                System.out.println(added.size());
                return null;
            }
        }
        return null;
    }
    
    
    
    // return an array containing the vertex numbers of an optimal IS.     
    public static int[] optimalIS(Graph inputGraph) {
        //variable setup 
        int numVerts = inputGraph.getGraph().length;
        int vertexArr[] = new int[numVerts];
        long powerSetSize = (long)Math.pow(2,numVerts);
        int counter, j;
        int bestIndependentSet[] = new int[0];

        //generate an array of all the vertices in the graph 
        for(int x = 0; x<numVerts;x++)
        {
            vertexArr[x] = x;
        }
      
        /*
         * this set of for loops generates a power set
         * then puts each individual subset into an array
         * and then tests that array to see if it's an IS. 
         */
        for(counter = 0; counter < powerSetSize; counter++)
        {
            List<Integer> IndependentSet = new ArrayList<>();
            //create 
            for(j = 0;j < numVerts; j++)
            {
                if((counter & (1 << j)) > 0)
                {
                    //add element of subset to an arraylist
                    /*
                     *  !!!!!NOTICE!!!! THIS "IF" DELETES ALL DUPLICATES FROM THE POWER SET
                     */
                    if(!IndependentSet.contains(vertexArr[j]))
                    {
                        IndependentSet.add(vertexArr[j]);
                    }
                    //IndependentSet.add(vertexArr[j]);
                    //print out element of subset 
                    //System.out.print(vertexArr[j]);
                }
                
                
            }
            //create and fill array to hold elements from arraylist
            int ISArray[] = new int[IndependentSet.size()];
            for(int x = 0; x < IndependentSet.size();x++)
            {
                //fill ISArray
                ISArray[x] = IndependentSet.get(x);
                //print out current subset in array
                //System.out.print(ISArray[x]);
            }

            
            //now we test for the best Independent set 
            //call isIS and send in the graph, and the subset Array

            //System.out.println(Arrays.toString(ISArray));
            if(isIS(inputGraph, ISArray))
            {
                //if the BIS hasn't been set (length = 0)
                if(bestIndependentSet.length == 0)
                {
                    bestIndependentSet = ISArray;
                }
                if(bestIndependentSet.length < ISArray.length)
                {
                    System.out.println(Arrays.toString(bestIndependentSet));
                    bestIndependentSet = ISArray;
                }
            }

            //System.out.println();
        }
        System.out.println(Arrays.toString(bestIndependentSet));
        System.out.println("Best Independent Set Length: " + bestIndependentSet.length);

        return bestIndependentSet;
    }
    
    
    
    // return (in polynomial time) an array containing the vertex numbers of a IS.
    public static int[] inexactIS(Graph inputGraph) {
        int independentSet[] = new int[0];
        int placeHolder[][] = inputGraph.getGraph();
        int temp[][] = new int[placeHolder.length][];
        for(int i = 0; i < placeHolder.length; i++)
        {
            int innerArray[] = placeHolder[i];
            int length = innerArray.length;
            temp[i] = new int[length];
            System.arraycopy(innerArray, 0, temp[i], 0, length);
        }
        int tempSize = temp.length;
        while(tempSize > 0)
        {
            int minIndex = 0;
            for(int i = 0; i < temp.length; i++)
            {
                if(temp[i] != null)
                {
                    minIndex = i;
                    break;
                }
            }

            if(temp[minIndex] == null)
                break;

            for(int i = 0; i < temp.length; i++)
            {
                if(temp[i] != null)
                {
                    if(temp[i].length < temp[minIndex].length)
                    {
                        minIndex = i;
                    }
                }
            }
            for(int i : temp[minIndex])
            {
                if(temp[i] != null)
                    temp[i] = null;
            }
            tempSize -= (temp[minIndex].length + 1);
            temp[minIndex] = null;
            int tempIS[] = new int[independentSet.length + 1];
            System.arraycopy(independentSet, 0, tempIS, 0, independentSet.length);
            tempIS[tempIS.length - 1] = minIndex;
            independentSet = new int[tempIS.length];
            System.arraycopy(tempIS, 0, independentSet, 0, tempIS.length);
        }
        return independentSet;
    }

    
    
    /**
     * Tests if a given set of vertices represents a vertex cover of a given graph
     * @param inputGraph - a graph generated from Graph.java
     * @param verts - a list of vertices seperated by commas
     * @return boolean
     * @author Grossman
     */
    public static boolean isVC(Graph inputGraph, int[] verts){

        // make an arraylist of edges
        ArrayList<String> edges = new ArrayList<String>();

        // add every possible edge from the graph to the arraylist
        for (int parent = 0; parent < inputGraph.getGraph().length; parent++){
            for (int child : inputGraph.getGraph()[parent]){
                edges.add("("+parent + "), (" + child + ")");
            }
        }

        // if an edge contains an endpoint in the given solution, remove it
        for (int vertex : verts){
            for (int marker = 0; marker < edges.size(); marker++){
                if (edges.get(marker).contains("(" + String.valueOf(vertex) + ")")){

                    edges.remove(marker);
                    // set back counter if deletion occurs to prevent skipping
                    marker--;
                }
            }
        }

        // if no edges remain, accept the solution
        if (edges.size() == 0){return true;}
        else{return false;}
    }


    /**
     * Tests if a given set of vertices represents an independent set of a given graph
     * @param inputGraph - a graph generated from Graph.java
     * @param verts - a list of vertices seperated by commas
     * @return boolean
     * @author Grossman
     */
    public static boolean isIS(Graph inputGraph, int[] verts){
        
        // make an arraylist of edges
        ArrayList<String> edges = new ArrayList<String>();

        // add every possible edge from the graph to the arraylist
        for (int parent = 0; parent < inputGraph.getGraph().length; parent++){
            for (int child : inputGraph.getGraph()[parent]){
                edges.add(parent + ", " + child);
            }
        }

        // if an edge is shared by two endpoints in the given solution, reject
        for (int marker = 0; marker < edges.size(); marker++){
            int connections = 0;
            for (int vertex : verts){
                if (edges.get(marker).contains(String.valueOf(vertex))){
                    System.out.print(edges.get(marker)+" ");
                    connections++;
                    System.out.println(connections);
                    if (connections > 1){
                        return false;
                    }
                }
            }
        }
        return true;
    }



}
