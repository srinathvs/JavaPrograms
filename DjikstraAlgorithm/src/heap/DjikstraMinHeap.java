package heap;

import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class DjikstraMinHeap {
    static class Edge {
        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    static class Graph {
        int vertices;
        LinkedList<Edge>[] adjacencylist;

        Graph(int vertices) {
            this.vertices = vertices;
            adjacencylist = new LinkedList[vertices];
            //initialize adjacency lists for all the vertices
            for (int i = 0; i < vertices; i++) {
                adjacencylist[i] = new LinkedList<>();
            }
        }

        public void printGraph(){

        }
        public String getCharForNumber(int i) {
            return i > -1 && i < 27 ? String.valueOf((char)(i + 65)) : null;
        }

        public void addEdge(int source, int destination, int weight) {
            Edge edge = new Edge(source, destination, weight);
            adjacencylist[source].addFirst(edge);

            edge = new Edge(destination, source, weight);
            adjacencylist[destination].addFirst(edge);
        }

        public void dijkstra_PrintPaths(int sourceVertex) {

            boolean[] SPT = new boolean[vertices];

            int[] distance = new int[vertices];

            int[] parentVertex = new int[vertices];

            parentVertex[0] = -1;


            for (int i = 0; i < vertices; i++) {
                distance[i] = Integer.MAX_VALUE;
            }

            PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(vertices, new Comparator<Pair<Integer, Integer>>() {
                @Override
                public int compare(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
                    //sort using distance values
                    int key1 = p1.getKey();
                    int key2 = p2.getKey();
                    return key1 - key2;
                }
            });

            distance[0] = 0;
            Pair<Integer, Integer> p0 = new Pair<>(distance[0], 0);
            //add it to pq
            pq.offer(p0);

            while (!pq.isEmpty()) {
                //extract the min
                Pair<Integer, Integer> extractedPair = pq.poll();


                int extractedVertex = extractedPair.getValue();
                if (SPT[extractedVertex] == false) {
                    SPT[extractedVertex] = true;


                    LinkedList<Edge> list = adjacencylist[extractedVertex];
                    for (int i = 0; i < list.size(); i++) {
                        Edge edge = list.get(i);
                        int destination = edge.destination;
                        //only if edge destination is not present in mst
                        if (SPT[destination] == false) {
                            ///check if distance needs an update or not
                            //means check total weight from source to vertex_V is less than
                            //the current distance value, if yes then update the distance
                            int newKey = distance[extractedVertex] + edge.weight;
                            int currentKey = distance[destination];
                            if (currentKey > newKey) {
                                Pair<Integer, Integer> p = new Pair<>(newKey, destination);
                                pq.offer(p);
                                distance[destination] = newKey;
                                parentVertex[destination] = extractedVertex;
                            }
                        }
                    }
                }
            }

            printDijkstra(parentVertex, distance, sourceVertex);
        }

        public void printDijkstra(int[] parent, int[] distance, int sourceVertex) {

            for (int i = 0; i <=i+1; i++) {
                if(i==1) {
                    String sourcestr=getCharForNumber(sourceVertex);
                    String destinationstr=getCharForNumber(i);
                    System.out.print(distance[i]+"\n");
                  //  System.out.print(" " + sourcestr + "--> " + destinationstr + ": distance=" + distance[i] );
                    printPathUtil(parent, i);
                    System.out.println();
                }
            }
        }

        public void printPathUtil(int parent[], int destination) {
            //if vertex is source then stop recursion
            if (parent[destination] == -1) {
                System.out.print("A ");
                return;
            }
            printPathUtil(parent, parent[destination]);
           var destinationstr=getCharForNumber(destination);
            System.out.print(destinationstr + " ");
        }
    }


        public static int GetIntegerFromCharacter(String c){
        int i=0;
        i=c.charAt(0);
        return i-65;
        }
        public static void main(String[] args) throws IOException {

            System.out.println("Enter the required file path here :");
            Scanner filescanner=new Scanner(System.in);
            String filePath=filescanner.nextLine();

            List<String> allLines = Files.readAllLines(Paths.get(filePath));

            int x=0;
            x=Integer.parseInt(allLines.get(0));
            List<String> Nodes=new ArrayList<>();


            Graph graph = new Graph(allLines.size()-1);
            for(int i=1;i<allLines.size()-1;i++) {
                String phrase = allLines.get(i);
                String delimiter = "[ ]+";
                String[] tokensizer = phrase.split(delimiter);

               int v1=GetIntegerFromCharacter(tokensizer[0]);

               int v2=GetIntegerFromCharacter(tokensizer[1]);

               int wt=Integer.parseInt(tokensizer[2]);

               graph.addEdge(v1,v2,wt);
            }
            graph.dijkstra_PrintPaths(0);


        }

}

