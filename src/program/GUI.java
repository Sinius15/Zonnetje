package program;

import gui.LButton;
import gui.LButtonGroup;
import gui.LCheckBox;
import gui.LFrame;
import gui.LRadioButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

public class GUI
{
  public static LFrame f = new LFrame();
  public static LCheckBox c1 = new LCheckBox();
  public static LCheckBox c2 = new LCheckBox();
  public static LCheckBox c3 = new LCheckBox();
  public static LRadioButton r1 = new LRadioButton();
  public static LRadioButton r2 = new LRadioButton();
  public static LButtonGroup bg = new LButtonGroup();
  public static LButton b1 = new LButton();
  public static LButton b2 = new LButton();
  public static boolean openMap = false;
  public static ProcessBuilder openMapProcess = new ProcessBuilder(new String[] { "explorer.exe", "/root,C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\Zonnetje" });
  public static String type;

  public static void start()
  {
    r1.setSize(130, 25);
    r1.setPlace(2, 5);
    r1.setText("Current Day");
    r1.setGridSize(2, 1);
    r1.get().setSelected(true);

    r2.setSize(130, 25);
    r2.setPlace(2, 6);
    r2.setText("Last 7 Days");
    r2.setGridSize(2, 1);

    bg.add(r1);
    bg.add(r2);

    c1.setSize(130, 25);
    c1.setPlace(2, 10);
    c1.setText("Save in a .txt");
    c1.setGridSize(2, 1);

    c2.setSize(130, 25);
    c2.setPlace(2, 11);
    c2.setText("Save in a .xls");
    c2.setGridSize(2, 1);

    c3.setSize(130, 25);
    c3.setPlace(2, 12);
    c3.setText("Save in a .csv");
    c3.setGridSize(2, 1);

    b1.setSize(130, 30);
    b1.setPlace(2, 13);
    b1.setText("Save");

    b2.setSize(130, 30);
    b2.setPlace(2, 14);
    b2.setText("Show Summery");

    f.add(r1);
    f.add(r2);
    f.add(c1);
    f.add(c2);
    f.add(c3);
    f.add(b1);
    f.add(b2);
    f.get().pack();

    b1.get().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) { if (GUI.r1.get().isSelected()) {
          MainProgram.solar.power_today();
          GUI.type = "zonnetje productie op dag ";
        }
        else if (GUI.r2.get().isSelected()) {
          MainProgram.solar.power_week();
          GUI.type = "zonnetje productie op week voor ";
        }

        if (GUI.c1.get().isSelected()) {
          Saving.txt(GUI.type);
          GUI.openMap = true;
        }
        if (GUI.c2.get().isSelected()) {
          Saving.xls(GUI.type);
          GUI.openMap = true;
        }
        if (GUI.c3.get().isSelected()) {
          Saving.csv(GUI.type);
          GUI.openMap = true;
        }

        if (GUI.openMap)
          try {
            GUI.openMapProcess.start();
          } catch (IOException e) {
            e.printStackTrace();
          }
      }
    });
    
    b2.get().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) { MainProgram.solar.summery();

        String temp = "Current Power: " + MainProgram.solar.current_power + "W" + "\n";
        temp = temp + "Energy Lifetime: " + MainProgram.solar.energy_lifetime + "WH" + "\n";
        temp = temp + "energy_month: " + MainProgram.solar.energy_month + "WH" + "\n";
        temp = temp + "energy_today: " + MainProgram.solar.energy_today + "WH" + "\n";
        temp = temp + "energy_week: " + MainProgram.solar.energy_week + "WH" + "\n";
        temp = temp + "modules: " + MainProgram.solar.modules + "\n";
        temp = temp + "source: " + MainProgram.solar.source + "\n";
        temp = temp + "Summary Date: " + MainProgram.solar.summary_date + "\n";

        JOptionPane.showMessageDialog(GUI.f.get(), temp, "Summery", 1);
      }
    });
  }
}