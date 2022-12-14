import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        System.out.println();
        return bestVertexCover;
    }

    
    
    /**
     * Return (in polynomial time) an array containing the vertex numbers of a VC
     * @param inputGraph - a graph generated from Graph.java
     * @return array of Vertex Numbers
     * @author Grossman
     */
    public static int[] inexactVC(Graph inputGraph) {
        
        // make an arraylist of edges
        ArrayList<String> edges = new ArrayList<String>();
        ArrayList<Integer> solution = new ArrayList<Integer>();
        Random random = new Random();

        // add every possible edge from the graph to the arraylist
        for (int parent = 0; parent < inputGraph.getGraph().length; parent++){
            for (int child : inputGraph.getGraph()[parent]){
                edges.add("( "+parent + " ),( " + child+" )");
            }
        }

        // select random edge, add its vertices to the solution and remove all other connected edges
        while (!edges.isEmpty()){
            // select random edge and extract vertex values
            String edge = edges.get(random.nextInt(edges.size()));
            String work = edge;
            String vert1 = work.split(",")[0].split(" ")[1];
            String vert2 = work.split(",")[1].split(" ")[1];
            // add both vertices to tenative solution
            if (!solution.contains(Integer.parseInt(vert1))){solution.add(Integer.parseInt(vert1));} 
            if (!solution.contains(Integer.parseInt(vert2))){solution.add(Integer.parseInt(vert2));}
            // find edges that contain each vertex and remove them
            for (int e = 0; e < edges.size(); e++){
                if (edges.get(e).contains("( "+vert1+" )") || edges.get(e).contains("( "+vert2+" )")){
                    edges.remove(e);
                    // set back counter if deletion ocurrs to prevent skipping
                    e--;
                }
            }
        }
        int[] unList = solution.stream().mapToInt(Integer::valueOf).toArray();
        System.out.println(Arrays.toString(unList));
        System.out.println("Length of approximate best Vertex Cover: " + unList.length);
        System.out.println();
        return unList;
    }
    
    
    
    /**
     * Return an array containing the vertex numbers of an optimal IS
     * @param inputGraph - a graph generated from Graph.java
     * @return array of Vertex Numbers
     * @author Cade
     */    
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
                    //System.out.println(Arrays.toString(bestIndependentSet));
                    bestIndependentSet = ISArray;
                }
            }

            //System.out.println();
        }
        System.out.println(Arrays.toString(bestIndependentSet));
        System.out.println("Length of best Independent Set: " + bestIndependentSet.length);
        System.out.println();
        return bestIndependentSet;
    }
    
    
    
    /**
     * Return (in polynomial time) an array containing the vertex numbers of a IS
     * @param inputGraph - a graph generated from Graph.java
     * @return array of Vertex Numbers
     * @author Bernard
     */   
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
        boolean allNull = false;
        while(!allNull)
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
            temp[minIndex] = null;
            int tempIS[] = new int[independentSet.length + 1];
            System.arraycopy(independentSet, 0, tempIS, 0, independentSet.length);
            tempIS[tempIS.length - 1] = minIndex;
            independentSet = new int[tempIS.length];
            System.arraycopy(tempIS, 0, independentSet, 0, tempIS.length);
            int sizeset = temp.length;
            for(int i = 0; i < temp.length; i++)
            {
                if(temp[i] == null)
                {
                    sizeset--;
                }
            }
            if(sizeset == 0)
                allNull = true;
        }
        System.out.print("[");
        for(int i = 0; i < independentSet.length - 1; i++)
        {
            if (i == independentSet.length){
                System.out.print(independentSet[i]+",");
            }
            else{
                System.out.print(independentSet[i]+", ");
            }
        }
        System.out.println(independentSet[independentSet.length-1] + "]\nLength of approximate best Independent Set: " + independentSet.length);
        System.out.println();
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
                    connections++;
                    if (connections > 1){
                        return false;
                    }
                }
            }
        }
        return true;
    }



}
