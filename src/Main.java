package src;

public class Main
{
  public static void main(String[] args) throws InterruptedException {
    TempLog tempLog = new TempLog();
    tempLog.createLogFile();
    ThermometerSystem thermometer = new ThermometerSystem();
    thermometer.clearConsole();
    thermometer.mainMenu();

  }
}