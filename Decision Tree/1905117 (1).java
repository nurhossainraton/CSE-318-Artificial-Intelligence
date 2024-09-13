import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Random;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

class Node{
     ArrayList<Node> children = new ArrayList<>();
     Node parent;
     Boolean leaf ;
     String ans;
     int attr;

    public Node() {
        this.parent = null;
        this.leaf = false;
        this.ans = " ";
    }

}

class Dataset{
    ArrayList<String> properties = new ArrayList<>();
    boolean validity;
    String result;
    public Dataset(ArrayList<String> properties, boolean validity, String result) {
        this.properties = properties;
        this.validity = validity;
        this.result = result;
    }
    public Dataset() {

    }

}
class global{
    static int count = 0;
    static ArrayList<Dataset> totalDataSet = new ArrayList<>();
    static ArrayList<Dataset> testDataSet = new ArrayList<>();
    static ArrayList<Dataset> trainningDataSet = new ArrayList<>();
    static ArrayList<Double> accList = new ArrayList<>();
    static String[][] str = new String[6][4];
     static String results[] = new String[4];

}
class Pair{
    int index;
    double value;

    public Pair(int index, double value) {
        this.index = index;
        this.value = value;
    }

    public Pair() {
        this.index = 0;
        this.value = 0;
    }
}
public class Main {



    public static void setUp()
    {
        global.results = new String[]{"unacc", "acc", "good", "vgood"};
        global.str[0]= new String[]{"v-high", "high", "med", "low"};
        global.str[1]= new String[]{"v-high", "high", "med", "low"};
        global.str[2]= new String[]{"2", "3", "4", "5more"};
        global.str[3]= new String[]{"2", "4", "more"};
        global. str[4]= new String[]{"small", "med", "big"};
        global.str[5]= new String[]{"low", "med", "high"};
    }
    public static int bestIndex(int attr)
    {
        int index = -1;
        double min = 1000000;
        for(int i=0;i<6;i++)
        {
            if((attr & (1<<i)) == 0)
            {
                double entropy = entropyCalculation(i);
                if(entropy<min)
                {
                    index = i;
                    min = entropy;
                }
            }

        }
        return index;
    }

    private static double entropyCalculation(int idx) {
       // System.out.println("tot entrpy ");
       ArrayList<Pair> pairArrayList = new ArrayList<>();
       int total = 0;
       for(int i=0;i<global.str[idx].length;i++)
       {
           String atrr = global.str[idx][i];
           Pair p = new Pair();
           p = calSepEntropy(atrr,idx);
           System.out.println("P returned "+p.index+" "+p.index);
           pairArrayList.add(p);
           total += p.index;
       }
       double entrpy = 0;
       for(int i=0;i<pairArrayList.size();i++)
       {
           entrpy += ((pairArrayList.get(i).index * 1.00)/total * pairArrayList.get(i).value);
       }
       return entrpy;
    }

    private static Pair calSepEntropy(String atrr, int idx) {
        System.out.println("separate entroy ");
        int total =0;
        double entropy = 0;
        int res[] = new int[4];
        for(int i=0;i<4;i++)
        {
            res[i] = 0;
        }
        for(int i=0;i<global.trainningDataSet.size();i++)
        {
            if(global.trainningDataSet.get(i).validity)
            {
                if(global.trainningDataSet.get(i).properties.get(idx) == atrr)
                {
                  //  int j = getResultIndex(trainningDataSet.get(i).result);
                    res[getResultIndex(global.trainningDataSet.get(i).result)]++;
                }
            }
        }

        for(int i=0;i<4;i++)
        {
            total+= res[i];
        }
        if(total == 0)
        {
            return new Pair();
        }
        for(int i=0;i<4;i++)
        {
            double p = 1.00*res[i]/total;
            if(p != 0)
                entropy+= p*(Math.log(p)/Math.log(2));
        }
        Pair p = new Pair(total,-entropy);
        return p;


    }

    private static int getResultIndex(String attr) {
        for(int i=0;i<global.results.length;i++)
        {
            if(global.results[i]== attr)
            {
                System.out.println("Result and attr same "+ attr);
                return i;
            }

        }
        return -1;
    }

    private static String checkSimilarity() {
        String str = "";
        for(int i=0;i<global.trainningDataSet.size();i++)
        {
            //System.out.println("I "+i + trainningDataSet.get(i).result+ " "+str);
            //System.out.println(trainningDataSet.get(i).result);
            if(global.trainningDataSet.get(i).validity == false)
            {
                continue;
            }
            if(str.length() == 0)
            {
                str = global.trainningDataSet.get(i).result;
               // System.out.println("length 0");
            }
            else if(str != global.trainningDataSet.get(i).result)
                return " ";
        }
        //System.out.println("from checksimilarity "+str);
        return str;
    }

    private static void learingOfDecisionTree(Node currentNode, String parentPlurality, int attr, int cnt) {
        if(cnt == 0)
        {
            currentNode.ans = parentPlurality;
            currentNode.leaf = true;
            return;
        }
      //  System.out.println("Lerning ");
        String answer = checkSimilarity();
        //System.out.println(" answer is "+ answer);
        if(answer.length() !=0)
        {
            currentNode.ans = answer;
            currentNode.leaf = true;
            return;
        }
        String plurality = getPlurality();
        System.out.println("attr value is "+attr);
        if(attr == 63)
        {
            currentNode.leaf = true;
            currentNode.ans = plurality;
            return ;
        }
        int index = bestIndex(attr);
        currentNode.attr = index;
        attr = attr | (1<<index);
        for(int i =0;i<global.str[index].length;i++)
        {
            Node node = new Node();
            node.parent = currentNode;
            currentNode.children.add(node);
            ArrayList<Integer> invalid =invalidate(index,global.str[index][i]);
            learingOfDecisionTree(node,plurality,attr, cnt);
            revalidate(invalid);
            
        }


    }

    private static void revalidate(ArrayList<Integer> container) {
        System.out.println("Revalidate ");
        for(int i=0;i<container.size();i++)
        {
            global.trainningDataSet.get(container.get(i)).validity = true;
            global.count++;
        }
    }

    private static ArrayList<Integer> invalidate(int index, String s) {
        System.out.println("invalidate ");
        ArrayList<Integer> container = new ArrayList<>();
        for(int i=0;i<global.trainningDataSet.size();i++)
        {

            if(global.trainningDataSet.get(i).validity == true)
            {
                if(global.trainningDataSet.get(i).properties.get(index) != s)
                {
                    global.trainningDataSet.get(i).validity = false;
                    global.count--;
                    container.add(i);
                }
            }
        }
        return container;

    }

    private static String getPlurality() {
        System.out.println("getplu");
        int max = -1000000;
        int index = 0;
        int res[] = new int[4];
        for(int i=0;i<4;i++)
        {
            res[i] = 0;
        }
        for(int i=0;i<global.trainningDataSet.size();i++)
        {
            if(global.trainningDataSet.get(i).validity == true)
            {
                res[getResultIndex(global.trainningDataSet.get(i).result)]++;
            }
        }

        for(int i=0;i<4;i++)
        {
            if(res[i] > max)
            {
                max = res[i];
                index = i;
            }
        }
        return global.results[index];
    }
    private static String walkTree(Node currNode, Dataset data) {

        if(currNode.leaf)
        {
            return currNode.ans;
        }
        for(int i=0;i<currNode.children.size();i++) {
            System.out.println("walk tree "+global.str[currNode.attr][i]+" "+data.properties.get(i));
            if (global.str[currNode.attr][i] == data.properties.get(i))
            {
                return walkTree(currNode.children.get(i),data);
            }
        }
        return " ";
    }
    public static void main(String[] args) throws FileNotFoundException {
        setUp();
        String filePath = "car.data";
        String line;
        Dataset dataset = new Dataset();
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        try {
            while ((line = bufferedReader.readLine()) != null) {
                String[] numbers = line.split(",");
                for (int i = 0; i < numbers.length; i++) {
                    //System.out.print(numbers[i]);
                    dataset.properties.add(numbers[i]);
                }
                dataset.validity = true;
                dataset.result = numbers[6];
                global.totalDataSet.add(dataset);
//              for(int i=0;i<global.totalDataSet.size();i++)
//              {                 System.out.println("res in totalset "+global.totalDataSet.get(i).result);
//              }
              //  numbers = new String[7];
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("total size "+global.totalDataSet.size() );

        double accCount = 0;
        Random random = new Random();
        int iteration = 20;
        while (iteration != 0) {
            global.count=0;
            global.trainningDataSet.clear();
            global.testDataSet.clear();
            for(int j=0;j<global.totalDataSet.size();j++)
            {
               // System.out.println("tot set "+global.totalDataSet.get(j).result);
            int randNumber = random.nextInt(11);
            if (randNumber >= 8)
                global.testDataSet.add(global.totalDataSet.get(j));
            else
            {
                global.trainningDataSet.add(global.totalDataSet.get(j));
                global.count++;
            }
               // System.out.println("count is "+global.count);

            }
           // System.out.println(global.testDataSet.size()+ " "+global.trainningDataSet.size()+" "+global.totalDataSet.size());
            Node startNode = new Node();
           // System.out.println("Before ");
            learingOfDecisionTree(startNode, "", 0,global.count);
            int success = 0;
            for (int j = 0; j < global.testDataSet.size(); j++) {
                String result = walkTree(startNode, global.testDataSet.get(j));
                //System.out.println("test set "+ result+ " "+testDataSet.get(j).result);
                if (result == global.testDataSet.get(j).result)
                    success++;
            }
          // System.out.println("Success "+ success);
            double accuracy = 100 * success / global.testDataSet.size();
            //System.out.println(accuracy);
            global.accList.add(accuracy);
            accCount += accuracy;
            iteration--;

        }
        //System.out.println("acccC " +accCount);

        double mean = accCount / global.accList.size();
        System.out.println("Mean is " + mean);
        double std = 0;
        for (int ii = 0; ii < global.accList.size(); ii++) {
            std += (global.accList.get(ii) - mean) * (global.accList.get(ii) - mean);
        }
        std = Math.sqrt(std / global.accList.size());
        System.out.println("Standard Deviation is " + std);
    }


}

