import java.io.FileNotFoundException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


class Edgee {

    public int source;
    int destination;
    int weight;

    public Edgee(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
    public Edgee() {

        this.weight = 0;
    }
    public int getWeight() {
        return this.weight;
    }


}

class Solution {
    List<Integer> nodesInSet1;
    List<Integer> nodesInSet2;
    int weight;

    public Solution(List<Integer> set1, List<Integer> set2) {
        this.nodesInSet1 = set1;
        this.nodesInSet2 = set2;

    }
}

public class MaxCutAlgorithm {
    static int[][] graph; // Adjacency matrix of the graph
    static int nodeCount; // Number of nodes in the graph
    static int wThreshold; // Threshold for solution weight
    public static int noOfVertices, noOfEdges;
    public static int araX[],araY[];

    static int computeCut(Solution solution,List<Edgee> adjacencyList)
    {
        int totalweight = 0;
        for(int i=0;i<adjacencyList.size();i++)
        {
            if(solution.nodesInSet1.contains(adjacencyList.get(i).source) && solution.nodesInSet2.contains(adjacencyList.get(i).destination))
            {
                totalweight+=adjacencyList.get(i).weight;
            }
            else if(solution.nodesInSet1.contains(adjacencyList.get(i).destination) && solution.nodesInSet2.contains(adjacencyList.get(i).source))
            {
                totalweight+=adjacencyList.get(i).weight;
            }

        }
        return totalweight;
    }
    static Solution SEMI_GREEDY_MAXCUT(List<Edgee> adjacencyList, float alpha) {
        Random rand = new Random();

        //alpha= 0;
        int minweight = adjacencyList.get(0).weight;
        int maxweight = adjacencyList.get(adjacencyList.size() - 1).weight;

        int totalweight1 = 0, totalweight2 = 0;
        float mue = minweight + alpha * (maxweight - minweight);
        List<Edgee> rcl = new ArrayList<>();
        for (Edgee edgee : adjacencyList) {
            if (edgee.weight >= mue) {
                rcl.add(edgee);
            }
        }
//        for (int i = 0; i < rcl.size(); i++) {
//            System.out.println("rcl");
//            System.out.println(rcl.get(i).source + " " + rcl.get(i).destination + " " + rcl.get(i).weight);
//        }
        List<Integer> Xset = new ArrayList<>();
        List<Integer> Yset = new ArrayList<>();
        List<Integer> V = new ArrayList<>();
        for (int i = 0; i < noOfVertices; i++) {
            V.add(i + 1);
        }
        int randIndex = rand.nextInt(rcl.size());
        if (randIndex < rcl.size()) {
            Edgee edgee = rcl.get(randIndex);
            Xset.add(edgee.source);
            Yset.add(edgee.destination);

        }
        araX = new int[noOfVertices];
        araY = new int[noOfVertices];
        for (int i = 0; i < noOfVertices; i++)
        {
            araX[i] = 0;
            araY[i] = 0;
        }
        while(Xset.size() + Yset.size() != noOfVertices) {
            for(int i=0; i<V.size(); i++) {
                for (int j = 0; j < Xset.size(); j++) {
                    if (V.get(i) == Xset.get(j)) {
                        V.remove(i);
                    }
                }
            }
            for(int i=0; i<V.size(); i++) {
                for (int j = 0; j < Yset.size(); j++) {
                    if (V.get(i) == Yset.get(j)) {
                        V.remove(i);
                    }
                }
            }

            for (int i = 0; i < noOfVertices; i++)
            {
                araX[i] = 0;
                araY[i] = 0;
            }
            System.out.println(Xset.size()+Yset.size());
            for (int i = 0; i < V.size(); i++) {
                for (int j = 0; j < Xset.size(); j++) {
                    for(int k=0; k<adjacencyList.size(); k++) {
                        if (((Xset.contains(adjacencyList.get(k).source)) && V.contains(adjacencyList.get(k).destination))) {
                            araY[V.get(i)-1] += adjacencyList.get(k).weight;
                            break;

                        } else if (((Xset.contains(adjacencyList.get(k).destination)) && V.contains(adjacencyList.get(k).source))) {
                            araY[V.get(i)-1] += adjacencyList.get(k).weight;
                            break;
                        }
                    }

                }

                for (int j = 0; j < Yset.size(); j++) {
                    for(int k=0; k<adjacencyList.size(); k++) {
                        if (((Yset.contains(adjacencyList.get(k).source)) && V.contains(adjacencyList.get(k).destination))) {
                            araX[V.get(i)-1] += adjacencyList.get(k).weight;
                            break;

                        } else if (((Yset.contains(adjacencyList.get(k).destination)) && V.contains(adjacencyList.get(k).source))) {
                            araX[V.get(i)-1] += adjacencyList.get(k).weight;
                            break;
                        }
                    }
                }

            }
            int min1 = araX[V.get(0)-1]; // Initialize min with the first element of the array
            for (int i = 1; i < V.size(); i++) {
                if (araX[V.get(i)-1] < min1) {
                    min1 = araX[V.get(i)-1]; // Update min if a smaller element is found
                }
            }
            int min2 = araY[V.get(0)-1]; // Initialize min with the first element of the array
            for (int i = 1; i < V.size(); i++) {
                if (araY[V.get(i)-1] < min2) {
                    min2 = araY[V.get(i)-1]; // Update min if a smaller element is found
                }
            }
            if (min2 < min1) {
                minweight = min2;
            } else
                minweight = min1;

            int max1 = araX[V.get(0)-1];
            for (int i = 1; i <V.size(); i++) {
                if (araX[V.get(i)-1] > max1) {
                    max1 = araX[V.get(i)-1];
                }
            }
            int max2 = araY[V.get(0)-1];
            for (int i = 1; i < V.size(); i++) {
                if (araY[V.get(i)-1] > max2) {
                    max2 = araY[V.get(i)-1];
                }
            }
            if (max2 > max1) {
                maxweight = max2;
            } else
                maxweight = max1;

            mue = minweight + alpha * (maxweight - minweight);
            //System.out.println(" mue is " + mue);
            List<Integer> rclOfVertex = new ArrayList<>();
            int Max = 0;
            for (int i = 0; i < V.size(); i++) {
                if (araX[V.get(i)-1] > araY[V.get(i)-1])
                    Max = araX[V.get(i)-1];
                else
                    Max = araY[V.get(i)-1];
                if (Max >= mue)
                    rclOfVertex.add(V.get(i));
            }

            //if(!rclOfVertex.isEmpty())
            {
                int randIndex2 = rand.nextInt(rclOfVertex.size());
                if (randIndex2 < rclOfVertex.size()) {
                    if (araX[rclOfVertex.get(randIndex2)-1] > araY[rclOfVertex.get(randIndex2)-1]) {
                        //System.out.println("X " + Xset.contains(randIndex2 + 1) + " Y :" + Yset.contains(randIndex2 + 1));
                        Xset.add(rclOfVertex.get(randIndex2));

                    } else {
                        Yset.add(rclOfVertex.get(randIndex2));
                    }

                }
            }

        }
        Solution solution = new Solution(Xset, Yset);

        return solution;
    }

    static Solution LOCAL_SEARCH_MAXCUT(Solution solution,List<Edgee> adjacencyList) throws FileNotFoundException {

        List<Integer> nodes1 = solution.nodesInSet1;
        List<Integer> nodes2 = solution.nodesInSet2;
        int i=0;
        for (int i = 0; i < noOfVertices; i++)
        {
            araX[i] = 0;
            araY[i] = 0;
        }

        boolean change = true;
        while (change) {
            change = false;
                i++;
                for (int i = 0; i < noOfVertices; i++) {
                   deltaCount(i+1,nodes1,nodes2,adjacencyList);
                    if ((nodes1.contains(i + 1)) && (araX[i] - araY[i] > 0)) {
                        nodes1.remove(nodes1.indexOf(i + 1));
                        nodes2.add(i + 1);
                        change = true;
                        break;
                    }
                    if ((nodes2.contains(i + 1)) && (araY[i] - araX[i] > 0)) {
                        nodes2.remove(nodes2.indexOf(i + 1));
                        nodes1.add(i + 1);
                        change = true;
                        break;
                    }
                }
            //System.out.println("Change");

            }

        System.out.println("Total Number of Iterations "+i);
        Solution soln = new Solution(nodes1,nodes2);
        System.out.println("Best Value in Local Search "+computeCut(soln,adjacencyList));
        return soln;
    }

    private static void deltaCount(int index,List<Integer> nodes1, List<Integer> nodes2,List<Edgee>adjacencyList) {
        if(nodes1.contains(index)) {
            for (int i = 0; i < nodes1.size(); i++) {
                for (int j = 0; (j != i) && (j < nodes1.size()); j++) {
                    for (int k = 0; k < adjacencyList.size(); k++) {
                        if (((nodes1.get(i) == adjacencyList.get(k).source) && (nodes1.get(j) == adjacencyList.get(k).destination))) {
                            araX[nodes1.get(i) - 1] += adjacencyList.get(k).weight;
                        } else if (((nodes1.get(i) == adjacencyList.get(k).destination) && (nodes1.get(j) == adjacencyList.get(k).source))) {
                            araX[nodes1.get(i) - 1] += adjacencyList.get(k).weight;
                        }
                    }
                }
                for (int j = 0;  j< nodes2.size(); j++) {
                    for (int k = 0; k < adjacencyList.size(); k++) {
                        if (((nodes1.get(i) == adjacencyList.get(k).source) && (nodes2.get(j) == adjacencyList.get(k).destination))) {
                            araY[nodes1.get(i) - 1] += adjacencyList.get(k).weight;
                        } else if (((nodes1.get(i) == adjacencyList.get(k).destination) && (nodes2.get(j) == adjacencyList.get(k).source))) {
                            araY[nodes1.get(i) - 1] += adjacencyList.get(k).weight;
                        }
                    }
                }
            }
        }
        else
        {
            for (int i = 0; i < nodes2.size(); i++) {
                for (int j = 0; (j != i) && (j < nodes2.size()); j++) {
                    for (int k = 0; k < adjacencyList.size(); k++) {
                        if (((nodes2.get(i) == adjacencyList.get(k).source) && (nodes2.get(j) == adjacencyList.get(k).destination))) {
                            araY[nodes2.get(i) - 1] += adjacencyList.get(k).weight;
                        } else if (((nodes2.get(i) == adjacencyList.get(k).destination) && (nodes2.get(j) == adjacencyList.get(k).source))) {
                            araY[nodes2.get(i) - 1] += adjacencyList.get(k).weight;
                        }
                    }
                }
                for (int j = 0;  j< nodes1.size(); j++) {
                    for (int k = 0; k < adjacencyList.size(); k++) {
                        if (((nodes2.get(i) == adjacencyList.get(k).source) && (nodes1.get(j) == adjacencyList.get(k).destination))) {
                            araX[nodes2.get(i) - 1] += adjacencyList.get(k).weight;
                        } else if (((nodes2.get(i) == adjacencyList.get(k).destination) && (nodes1.get(j) == adjacencyList.get(k).source))) {
                            araX[nodes2.get(i) - 1] += adjacencyList.get(k).weight;
                        }
                    }
                }
            }
        }

    }


    static Solution GRASP_PR_MAXCUT(List<Edgee> adjacencyList, float alpha) throws FileNotFoundException {
        List<Solution> solutionPool = new ArrayList<>();
        Solution bestSolution = null;
        Solution temp=null;
        for(int i=0; i<10; i++) {
            System.out.println("I = "+ i);
            temp= SEMI_GREEDY_MAXCUT(adjacencyList,alpha);
            System.out.println("Successful semi ");
            temp= LOCAL_SEARCH_MAXCUT(temp, adjacencyList);
            System.out.println("Successful local");
            if(i==0){
                bestSolution=temp;
            }
            else {
                if (computeCut(temp, adjacencyList) > computeCut(bestSolution, adjacencyList)) {
                    bestSolution = temp;
                }
            }
        }
        return bestSolution;
    }

    public static void main(String[] args) throws FileNotFoundException {
        MaxCutAlgorithm maxCutAlgorithm = new MaxCutAlgorithm();
        String filePath = "abc.txt";
        String line;
        List<Edgee> adjacencyList = new ArrayList<Edgee>();

        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        try {
            String firstLine = bufferedReader.readLine();
            String[] numbers = firstLine.split(" ");
            noOfVertices = Integer.parseInt(numbers[0]);
            noOfEdges = Integer.parseInt(numbers[1]);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] numbers = line.split(" ");
                Edgee edgee = new Edgee(Integer.parseInt(numbers[0]),Integer.parseInt(numbers[1]),Integer.parseInt(numbers[2]));
                adjacencyList.add(edgee);

            }
            for(int i=0;i<adjacencyList.size();i++)
            {
                System.out.print(adjacencyList.get(i).source + " ");
                System.out.print(adjacencyList.get(i).destination + " ");
                System.out.println(adjacencyList.get(i).weight);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Collections.sort(adjacencyList, new Comparator<Edgee>(){
            public int compare(Edgee o1, Edgee o2){
                return o1.getWeight() - o2.getWeight();
            }
        });
        float alpha;
        Solution bestSolution;
        alpha = 0;//Randomized
        bestSolution = GRASP_PR_MAXCUT(adjacencyList,alpha);
        System.out.println("Randomized ");
        System.out.println("Best solution weight: " + computeCut(bestSolution,adjacencyList));
        System.out.println("Nodes in set 1: " + bestSolution.nodesInSet1);
        System.out.println("Nodes in set 2: " + bestSolution.nodesInSet2);

        alpha = 1;//Greedy
        bestSolution = GRASP_PR_MAXCUT(adjacencyList,alpha);
        System.out.println("Greedy ");
        System.out.println("Best solution weight: " + computeCut(bestSolution,adjacencyList));
        System.out.println("Nodes in set 1: " + bestSolution.nodesInSet1);
        System.out.println("Nodes in set 2: " + bestSolution.nodesInSet2);
        Random rand = new Random();

        alpha = rand.nextFloat();//semigreedy
        bestSolution = GRASP_PR_MAXCUT(adjacencyList,alpha);
        System.out.println("Semi Greedy ");
        System.out.println("Best solution weight: " + computeCut(bestSolution,adjacencyList));
        System.out.println("Nodes in set 1: " + bestSolution.nodesInSet1);
        System.out.println("Nodes in set 2: " + bestSolution.nodesInSet2);


    }
}
