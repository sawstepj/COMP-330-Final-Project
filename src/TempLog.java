package src;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TempLog {
  String filename = "src/TempLog.txt";

  public TempLog(){}

  public void createLogFile(){
    File f = new File(filename);
  }
  public void addTemp(double entry, char units){
    //createLogFile();
    try {
      FileWriter myWriter = new FileWriter(filename, true);
      myWriter.write(" " + String.valueOf(entry) + units);
      myWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void getTemps(){
    try {
      String logContent = " ";
      File myObj = new File(filename);
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        logContent += data;
      }
      List<String> content = Arrays.asList(logContent.split(" "));
      if (content.size() == 0){
        System.out.println("++++++++++++++++");
        System.out.println("| log is empty |");
        System.out.println("++++++++++++++++");
      }else if (content.size() < 10){
        System.out.println("+++++++++++++++++++++++++++++++++");
        System.out.println("| log entries");
        System.out.print("|");
        int i;
        for (i = 0; i < content.size(); i++){
          System.out.print(content.get(i) + " ");
          if (i == content.size()/2){
            System.out.println();
            System.out.print("|");
          }
        }
        System.out.println("");
        System.out.println("+++++++++++++++++++++++++++++++++");
      } else {
        System.out.println("+++++++++++++++++++++++++++++++++");
        System.out.println("| last 10 log entries");
        String[] last10 = content.subList(content.size() - 10, content.size()).toArray(new String[0]);
        System.out.print("| ");
        int i;
        for (i = 0; i < last10.length; i++){
          System.out.print(last10[i] + " ");
          if (i == 4){
            System.out.println();
            System.out.print("| ");
          }
        }
        System.out.println("");
        System.out.println("+++++++++++++++++++++++++++++++++");
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public void clearLog(){
    try {
      FileWriter myWriter = new FileWriter(filename, false);
      myWriter.write("");
      myWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}