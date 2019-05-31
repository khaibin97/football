import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class PerformanceAnalysisHash implements PerformanceAnalysis {

    // The input data from each file is stored in this/ per file
    private LinkedList<String> inputData;
    private LinkedList<String> dataDetails; //contains the file names for the keys
    private String pathHeader;//"absolute" path to where the key files are located
    private ArrayList<String> report = new ArrayList<String>();//for printing results
    private TreeMap treeMap = new TreeMap();
    private HashTable hashTable = new HashTable(100,0.75);
    
    public PerformanceAnalysisHash(){
    }

    public PerformanceAnalysisHash(String details_filename){
        try {
            loadData(details_filename);
        } catch (IOException e) {
            System.out.println("File Path to data_details.txt invalid");
            System.exit(1); // prevent a multitude of exceptions from an invalid file name
        }
        dataDetails = new LinkedList<String>();
        String [] temp = inputData.removeFirst().split(",");
        pathHeader = new File("").getAbsolutePath();
        String temp1 = temp[1];
        
        if (!(pathHeader.contains("src"))) // if start at project folder
            temp1 = temp1.replace("..", "");//remove the .. from the relative path
        
        pathHeader = pathHeader + temp1 + "/"; //if start at src folder
        
        for (String line : inputData) {
            temp = line.split(",");
            dataDetails.add(temp[0].trim()); //add only the filepath
        }
    }
    
    /**
     * The important function that compares the implemented HashTable with
     * TreeMap of Java and generates the table with all the comparison details
     * Calls - compareInsertion, compareDeletion, CompareSearch
     * for all the test data provided.
     */
    @Override
    public void compareDataStructures() {
        
        for (int i = 0; i<dataDetails.size(); i++) { // all key file paths
            try {
                loadData(pathHeader + dataDetails.get(i));
            } catch (IOException e) {
                System.out.println("Invalid path: " +pathHeader+ dataDetails.get(i));
                return;
            }
            compareInsertion();
            compareSearch();
            compareDeletion();
            
            dataDetails.removeFirst(); // remove the name once done, for the print report
            i--;
        }
    }

    /** 
     * Function used to print out the final report
     */
    @Override
    public void printReport() {
        System.out.println("The report name : Performance Analysis Report");
        System.out.println(String.format("%96s", "-").replace(" ","-"));
        System.out.printf("|%20s|%15s|%15s|%25s|%15s|\n","FileName","Operation","Data Structure","Time Taken (micro sec)","Bytes Used");
        System.out.println(String.format("%96s", "-").replace(" ","-"));
        for (String line : report) {
            System.out.println(line);
        }
        System.out.println(String.format("%96s", "-").replace(" ","-"));
    }

    /**
     * Standalone method for comparing insertion operation
     * across HashTable and TreeMap
     */
    @Override
    public void compareInsertion() {
        compareInsertHelper(hashTable,treeMap);
    }

    /**
     * Standalone method for comparing deletion operation
     * across HashTable and TreeMap
     */
    @Override
    public void compareDeletion() {
        compareRemoveHelper(hashTable,treeMap);
    }

    /**
     * Standalone method for comparing search operation
     * across HashTable and TreeMap
     */
    @Override
    public void compareSearch() {
        compareGetHelper(hashTable,treeMap);
    }

    /*
    An implementation of loading files into local data structure is provided to you
    Please feel free to make any changes if required as per your implementation.
    However, this function can be used as is.
     */
    @Override
    public void loadData(String filename) throws IOException {

        // Opens the given test file and stores the objects each line as a string
        File file = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        inputData = new LinkedList<>();
        String line = br.readLine();
        while (line != null) {
            inputData.add(line);
            line = br.readLine();
        }
        br.close();
    }
    
    /**
     * Private method to avoid clustering the public ones
     * @param hashTable
     * @param treeMap
     */
    private void compareInsertHelper(HashTable hashTable, TreeMap treeMap) {
        long startTime = 0;
        long end = 0;
        long startMem = 0;
        long endMem = 0;
        long usedMem = 0;
        
        //Hash Table
        startMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        startTime = System.nanoTime();
        for (String key : inputData) {
            if (dataDetails.peek().toLowerCase().contains("integer")) { //if Integer
                try {
                    hashTable.put(Integer.parseInt(key), 1); 
                } catch (NullPointerException e2) {
                    System.out.println("Key cannot be null, key not entered");
                } 
            } else { // not Integer
                try {
                    hashTable.put(key, 1); //simply enter as String
                } catch (NullPointerException e1) {
                    System.out.println("Key cannot be null, key not entered");
                } 
            } 
        }
        
        end = (System.nanoTime() - startTime)/1000;
        endMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        usedMem = endMem - startMem;
        report.add(String.format("|%20s|%15s|%15s|%25d|%15d|", dataDetails.peek(), "PUT", "HASHTABLE", end, usedMem));
        
      //TreeMap
        startMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        startTime = System.nanoTime();
        for (String key : inputData) {
            if (dataDetails.peek().toLowerCase().contains("integer")) { //if Integer
                try {
                    treeMap.put(Integer.parseInt(key), 1); 
                } catch (NullPointerException e2) {
                    System.out.println("Key cannot be null, key not entered");
                } 
            } else { // not Integer
                try {
                    treeMap.put(key, 1); //simply enter as String
                } catch (NullPointerException e1) {
                    System.out.println("Key cannot be null, key not entered");
                } 
            } 
        }
        
        end = (System.nanoTime() - startTime)/1000;
        endMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        usedMem = endMem - startMem;
        report.add(String.format("|%20s|%15s|%15s|%25d|%15d|", dataDetails.peek(), "PUT", "TREEMAP", end, usedMem));
    }
    
    /**
     * Private method to avoid clustering the public ones
     * @param hashTable
     * @param treeMap
     */
    private void compareGetHelper(HashTable hashTable, TreeMap treeMap) {
        long startTime = 0;
        long end = 0;
        long startMem = 0;
        long endMem = 0;
        long usedMem = 0;
        
        //Hash Table
        startMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        startTime = System.nanoTime();
        for (String key : inputData) {
            if (dataDetails.peek().toLowerCase().contains("integer")) { //if Integer
                try {
                    hashTable.get(Integer.parseInt(key)); 
                } catch (NoSuchElementException e2) {
                    System.out.println("Key not mapped");
                } 
            } else { // not Integer
                try {
                    hashTable.get(key); //simply enter as String
                } catch (NoSuchElementException e1) {
                    System.out.println("Key not mapped");
                } 
            } 
        }
        
        end = (System.nanoTime() - startTime)/1000;
        endMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        usedMem = endMem - startMem;
        report.add(String.format("|%20s|%15s|%15s|%25d|%15d|", dataDetails.peek(), "GET", "HASHTABLE", end, usedMem));
        
      //TreeMap
        startMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        startTime = System.nanoTime();
        for (String key : inputData) {
            if (dataDetails.peek().toLowerCase().contains("integer")) { //if Integer
                try {
                    treeMap.get(Integer.parseInt(key)); 
                } catch (NoSuchElementException e2) {
                    System.out.println("Key not mapped");
                } 
            } else { // not Integer
                try {
                    treeMap.get(key); //simply enter as String
                } catch (NoSuchElementException e1) {
                    System.out.println("Key not mapped");
                } 
            } 
        }
        
        end = (System.nanoTime() - startTime)/1000;
        endMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        usedMem = endMem - startMem;
        report.add(String.format("|%20s|%15s|%15s|%25d|%15d|", dataDetails.peek(), "GET", "TREEMAP", end, usedMem));
    }
    
    /**
     * Private method to avoid clustering the public ones
     * @param hashTable
     * @param treeMap
     */
    private void compareRemoveHelper(HashTable hashTable, TreeMap treeMap) {
        long startTime = 0;
        long end = 0;
        long startMem = 0;
        long endMem = 0;
        long usedMem = 0;
        
        //Hash Table
        startMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        startTime = System.nanoTime();
        for (String key : inputData) {
            if (dataDetails.peek().toLowerCase().contains("integer")) { //if Integer
                try {
                    hashTable.remove(Integer.parseInt(key)); 
                } catch (NullPointerException e2) {
                    System.out.println("Key cannot be null, key not entered");
                } 
            } else { // not Integer
                try {
                    hashTable.remove(key); //simply enter as String
                } catch (NullPointerException e1) {
                    System.out.println("Key cannot be null, key not entered");
                } 
            } 
        }
        
        end = (System.nanoTime() - startTime)/1000;
        endMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        usedMem = endMem - startMem;
        report.add(String.format("|%20s|%15s|%15s|%25d|%15d|", dataDetails.peek(), "REMOVE", "HASHTABLE", end, usedMem));
        
      //TreeMap
        startMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        startTime = System.nanoTime();
        for (String key : inputData) {
            if (dataDetails.peek().toLowerCase().contains("integer")) { //if Integer
                try {
                    treeMap.remove(Integer.parseInt(key)); 
                } catch (NullPointerException e2) {
                    System.out.println("Key cannot be null, key not entered");
                } 
            } else { // not Integer
                try {
                    treeMap.remove(key); //simply enter as String
                } catch (NullPointerException e1) {
                    System.out.println("Key cannot be null, key not entered");
                } 
            } 
        }
        
        end = (System.nanoTime() - startTime)/1000;
        endMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        usedMem = endMem - startMem;
        report.add(String.format("|%20s|%15s|%15s|%25d|%15d|", dataDetails.peek(), "REMOVE", "TREEMAP", end, usedMem));
    }
}
