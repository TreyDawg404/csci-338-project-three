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
        List<Integer> goodVC = new ArrayList<>();
        List<Integer> touched = new ArrayList<>();
        for (int i = 0; i < inputGraph.getGraph().length; i++){
            goodVC.add(i);
            if (!touched.contains(i)){
                touched.add(i);
            }
            for (int nei : inputGraph.getGraph()[i]){
                if (!touched.contains(nei)){
                    touched.add(nei);
                }
            }
            int[] converter = new int[goodVC.size()];
            for (int j = 0; j < goodVC.size(); j++){
                converter[j] = goodVC.get(j);
            }
            if(isVC(inputGraph, converter)){
                System.out.println("Found solution of length "+converter.length);
                System.out.println("Unique nodes covered by this solution: "+touched.size());
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
    public static int[] inexactIS(Graph inputGraph) F{
        int independentSet[] = new int[0];
        int temp[][] = inputGraph.getGraph();
        int remainingVertices[] = new int[temp.length];
        int deleted[] = new int[temp.length];
        int deletedSize = 0;
        int deletedPlace = 0;
        int sizeof = temp.length;
        for(int index = 0; index < sizeof; index++)
        {
            remainingVertices[index] = index;
        }
        while(remainingVertices.length != 0)
        {
            int minimumIndex = remainingVertices[0];
            int minimumDegree[] = temp[remainingVertices[0]];
            /*
             * for loop below is to find vertice with lowest number of neighbors
             */
            for(int i : remainingVertices)
            {
                if(temp[i].length < minimumDegree.length)
                {
                    minimumIndex = i;
                    minimumDegree = temp[i];
                }
            }
            int minDegreeSize = 0;
            /*
             * The two for loops below are for creating a list of neighbors 
             * for our vertex that we selected above by including all neighbors 
             * not previously deleted
             * 
             * for loop 1 is to get the size of neighbors list
             * 
             * for loop 2 is to add neighbors that are not already deleted
             */
            for(int i : minimumDegree)
            {
                boolean containing = false;
                for(int d = 0; d < deletedPlace; d++)
                {
                    containing = (i == deleted[d]);
                    if(containing)
                        break;
                }
                if(!containing)
                    minDegreeSize++;
            }
            int neighbors[] = new int[minDegreeSize];
            int test = 0;

            for(int i : minimumDegree)
            {
                boolean in = false;
                if(deletedPlace == 0)
                    break;
                for(int d = 0; d < deletedPlace; d++)
                {
                    if(i == deleted[d])
                    {
                        in = true;
                        break;
                    }
                }
                if(!in)
                {
                    neighbors[test] = i;
                    test++;
                }
            }
            int remainingTemp[] = new int[remainingVertices.length - (1 + neighbors.length)];
            int independentTemp[] = new int[independentSet.length + 1];
            System.arraycopy(independentSet, 0, independentTemp, 0, independentSet.length);
            independentTemp[independentTemp.length - 1] = minimumIndex;
            independentSet = new int[independentTemp.length];
            System.arraycopy(independentTemp, 0, independentSet, 0, independentTemp.length);
            int rTIndex = 0;
            /*
             * for loop below is to add all vertices found in remainingVertices
             * to remainingTemp, remainingVertices will then be set equal to remainingTemp
             */
            for(int i : remainingVertices)
            {
                boolean contains = false;
                for(int j : neighbors)
                {
                    if(i == j)
                    {
                        contains = true;
                        break;
                    }
                }
                if(remainingTemp.length == 0)
                {
                    break;
                }
                boolean indexContain = (i == minimumIndex);
                if((!contains) && (!indexContain))
                {
                    remainingTemp[rTIndex] = i;
                    rTIndex++;
                }
                else
                {
                    deleted[deletedPlace] = i;
                    deletedPlace++;
                }
            }
            remainingVertices = new int[remainingTemp.length];
            System.arraycopy(remainingTemp, 0, remainingVertices, 0, remainingTemp.length);
        }
        System.out.println("IS Length: " + independentSet.length);
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
    
    
    
    /**
     * Tests if a given set of vertices represents an independent set of a given graph
     * @param inputGraph - a graph generated from Graph.java
     * @param verts - a list of vertices seperated by commas
     * @return boolean
     * @author Grossman
     */
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
