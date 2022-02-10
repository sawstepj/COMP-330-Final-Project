package src;

import java.awt.*;

public class Thermometer
{
  public void selfTest(int batteryStat) throws InterruptedException {
    if(batteryStat > 20){
      beep(2);
    }
  }

  public void feverCalc(double avgTemp, double feverTemp) throws InterruptedException {
    if(avgTemp >= feverTemp) {
      beep(1);
    }
  }

  public void beep(int numBeeps) throws InterruptedException {
    Toolkit play = Toolkit.getDefaultToolkit();
    play.beep();
    if (numBeeps == 2) {
      Thread.sleep(300);
      play.beep();
    }
  }
}