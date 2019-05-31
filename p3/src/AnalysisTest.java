import java.io.File;
import java.util.Scanner;

public class AnalysisTest {
    public static void main(String[] args)  {

                Scanner s = new Scanner (System.in);
                String input = s.nextLine();
                String path = new File("").getAbsolutePath() + "/data/" + input;
                
                PerformanceAnalysisHash ana = new PerformanceAnalysisHash(path);
                ana.compareDataStructures();
                ana.printReport();
                  
            }
}
