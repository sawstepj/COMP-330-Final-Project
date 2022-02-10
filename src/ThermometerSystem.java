package src;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ThermometerSystem {
  Thermometer thermometer = new Thermometer();
  Scanner in = new Scanner(System.in);
  boolean isOn = false;
  double avgTemp;
  double feverTemp = 37.0;
  int batteryStat = 80;
  char tempUnits = 'C';
  TimerTask task;
  Timer timer;
  TempLog tempLog = new TempLog();


  public void mainMenu() throws InterruptedException {
    int input = 0;
    in = new Scanner(System.in);
    boolean timerRun = false;
    while (input >= 0) {
      tempLog.getTemps();
      printScreen();
      if (isOn){
        if (!timerRun) {
          timerRun = true;
        } else {
          timer.cancel();
        }
        setInactivityTimer();
      }
      System.out.println("==================");
      System.out.println("| 0 on/off       |");
      System.out.println("| 1 C/F          |");
      System.out.println("| 2 set fever    |");
      System.out.println("| 3 measure temp |");
      System.out.println("| 4 clear log    |");
      System.out.println("==================");
      System.out.print("Enter option: ");
      input = in.nextInt();
      clearConsole();
      switch (input) {
        case 0:
          turnOnOff();
          break;
        case 1:
          if(isOn) {
            convertTemp();
          }else{
            printIsOffMsg();
          }
          break;
        case 2:
          if(isOn) {
            int input2 = 0;
            while (input2 < 3) {
              printScreen();
              System.out.println("==========");
              System.out.println("| 1 up   |");
              System.out.println("| 2 down |");
              System.out.println("| 3 save |");
              System.out.println("==========");
              System.out.print("Enter option: ");
              input2 = in.nextInt();
              clearConsole();
              switch (input2) {
                case 1:
                  feverTemp = feverTemp + 0.5;
                  break;
                case 2:
                  feverTemp = feverTemp - 0.5;
                  break;
                default:
                  break;
              }
            }
          } else {
            printIsOffMsg();
          }
          break;
        case 3:
          if(isOn) {
            printScreen();
            measureTemp();
          }else{
            printIsOffMsg();
          }
          break;
        case 4:
          if(isOn){
            tempLog.clearLog();
          }else{
            printIsOffMsg();
          }
        default:
          break;
      }
    }
  }

  public void setInactivityTimer() {
    task = new TimerTask() {
      public void run() {
        isOn = false;
        clearConsole();
        try {
          mainMenu();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };
    timer = new Timer("Timer");
    int delay = 30000;
    timer.schedule(task, delay);
  }

  public void turnOnOff() throws InterruptedException {
    if(isOn){
      isOn = false;
    } else{
      thermometer.selfTest(batteryStat);
      isOn = true;
    }
  }

  public void measureTemp() throws InterruptedException {
    //random generated temps
    double [][]tempReadings = {{37.1, 37.2, 37.1, 37.1, 37.1, 37.2, 37.1, 37.2, 37.1, 37.2},
                               {35.3, 35.3, 35.4, 35.3, 35.3, 35.4, 35.4, 35.4, 35.4, 35.3}};
    DecimalFormat df = new DecimalFormat("#");
    int index = Integer.parseInt(String.valueOf(df.format(Math.random()*1)));
    double sum = 0;
    for (double Reading : tempReadings[index]) {
      sum += Reading;
    }
    avgTemp = sum/tempReadings[index].length;
    thermometer.feverCalc(avgTemp, feverTemp);
    df = new DecimalFormat("#.#");
    tempLog.addTemp(Double.parseDouble(df.format(avgTemp)), tempUnits);
  }

  public void convertTemp() {
    if(tempUnits == 'C') {
      tempUnits = 'F';
      avgTemp = (9.0/5.0) * avgTemp + 32;
      feverTemp = (9.0/5.0) * feverTemp + 32;
    } else {
      tempUnits = 'C';
      avgTemp = (5.0/9.0) * (avgTemp - 32);
      feverTemp = (5.0/9.0) * (feverTemp - 32);
    }
  }

  public void clearConsole(){
    int blanks = 30;
    int i = 0;
    while (i < blanks){
      System.out.println();
      i++;
    }
  }

  public void printIsOffMsg(){
    System.out.println("************************************");
    System.out.println("* Turn thermometer on before using *");
    System.out.println("************************************");
  }

  public void printScreen(){
    int screenWidth = 26;
    System.out.println("--------------------------");
    if(!isOn){
      System.out.println("|                        |");
      System.out.println("|                        |");
    }
    else {
      DecimalFormat df = new DecimalFormat("#.#");
      String[] lines = {"| Battery: " + batteryStat + "% ",
        "| fever: " + df.format(feverTemp) + " temp: " +
          df.format(avgTemp) + " " + tempUnits};
      for (int i = 0; i < lines.length; i++) {
        int len = lines[i].length();
        int dif = screenWidth - len - 1;
        int x;
        for (x = 0; x < dif; x++) {
          lines[i] = lines[i] + " ";
        }
        lines[i] = lines[i] + "|";
        System.out.println(lines[i]);
      }
    }
    System.out.println("--------------------------");
  }
}