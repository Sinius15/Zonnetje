package program;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import api.SolarSystem;
import fileHandling.LFile;

public class MainProgram extends GUI
{
  public static SolarSystem solar;
  public static String systemKey;
  public static String apiKey;
  public static LFile config = new LFile(new File("config.ini"));

  public static void main(String[] args)
  {
    if (config.exist()) {
      try {
		systemKey = config.open(true)[1];
		apiKey = config.open(true)[2];
      } catch (IOException e) {}
      
    } else {
      JOptionPane.showMessageDialog(null, "je moet in je config file een api key en system code invullen", "STOP", 0);
      System.exit(0);
    }

    solar = new SolarSystem(Integer.parseInt(systemKey), apiKey);

    GUI.start();

    if (args.length != 0) {
        GUI.start();
        if (args[0].equals("txt")) {
          c1.get().setSelected(true);
        }
        if (args[0].equals("xls")) {
          c2.get().setSelected(true);
        }
        if (args[0].equals("csv")) {
          c3.get().setSelected(true);
        }
        b1.get().doClick();
      }
    }

    public static String twoDigitString(int number){
      if (number == 0) {
        return "00";
      }
      if (number / 10 == 0) {
        return "0" + number;
      }
      return String.valueOf(number);
    }
  }