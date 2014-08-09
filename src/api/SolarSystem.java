package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class SolarSystem
{
  private int systemId;
  private String apiKey;
  private String tempS;
  private URL url;
  private BufferedReader in;
  public int[] production;
  public int interval_length;
  public String first_interval_end_date;
  public int energy_month;
  public int current_power;
  public int modules;
  public int energy_today;
  public int energy_week;
  public int energy_lifetime;
  public String summary_date;
  public String alert_name;
  public int num_devices;
  public String alert_start;
  public String level;
  public String start_date;
  public String end_date;
  public int production_wh;
  public String meter_readings;
  public String source;

  public SolarSystem(int id, String api)
  {
    this.systemId = id;
    this.apiKey = api;
  }

  private void makeUrl(String txt) {
    try {
      this.url = new URL("https://api.enphaseenergy.com/api/systems/" + this.systemId + "/" + txt + "?key=" + this.apiKey);
      System.out.println(url);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  private boolean getData() {
    try {
      this.in = new BufferedReader(new InputStreamReader(this.url.openStream()));
      this.tempS = this.in.readLine();
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public void power_today(){
    makeUrl("power_today");
    if (getData())
      StingtoVar(this.tempS);
  }

  public void power_week(){
    makeUrl("power_week");
    if (getData())
      StingtoVar(this.tempS);
  }

  public void summery(){
    makeUrl("summary");
    if (getData()) {
      System.out.println(this.tempS);
      StingtoVar(this.tempS);
    }
  }

  private void StingtoVar(String txt){
    String s3 = "";
    boolean first = true;
    String firstS = ""; String secondS = "";

    int i1 = 0;
    do{
      String s2 = txt.substring(i1, i1 + 1);
      if ((s2.equals("{")) || (s2.equals("}"))) {
        System.out.println("{ of } detected");
      }
      else if (s2.equals("\"")) {
        int i2 = i1;
        while (true) {
          i2++;

          if ((txt.substring(i2, i2 + 1).equals("\"")) || (txt.substring(i2, i2 + 1).equals("]")) || (txt.substring(i2, i2 + 1).equals("}"))) {
            if (s3.endsWith(",")) {
              s3 = s3.substring(0, s3.length() - 1);
            }
            if (s3.startsWith(":")) {
              s3 = s3.substring(1, s3.length());
            }
            if (s3.startsWith("[")) {
              s3 = s3.substring(1, s3.length());
            }
            if (s3.isEmpty()) {
              break;
            }
            System.out.println("Breaking from \"\" loop, the result: " + s3);

            if (first) {
              firstS = s3;
              first = false;
              break;
            }secondS = s3;
            addToVar(firstS, secondS);
            first = true;

            break;
          }
          if (txt.substring(i2, i2 + 1).equals("}")) {
            break;
          }
          s3 = txt.substring(i1 + 1, i2 + 1);
        }
      }

      i1++;
    }while (i1 != txt.length());
  }

  private void addToVar(String s1, String s2){
    if (s1.equals("production")) {
      String[] temp = s2.split(",");

      if (temp.length > 300)
        this.production = new int[2016];
      else {
        this.production = new int[288];
      }
      for (int i = 1; 
        i < temp.length; i++)
      {
        this.production[i] = Integer.parseInt(temp[i]);
        System.out.println(this.production[i]);
      }

    }

    if (s1.equals("interval_length")) {
      this.interval_length = Integer.parseInt(s2);
    }
    else if (s1.equals("first_interval_end_date")) {
      this.first_interval_end_date = s2;
    }
    else if (s1.equals("energy_month")) {
      this.energy_month = Integer.parseInt(s2);
    }
    else if (s1.equals("current_power")) {
      this.current_power = Integer.parseInt(s2);
    }
    else if (s1.equals("modules")) {
      this.modules = Integer.parseInt(s2);
    }
    else if (s1.equals("energy_today")) {
      this.energy_today = Integer.parseInt(s2);
    }
    else if (s1.equals("energy_week")) {
      this.energy_week = Integer.parseInt(s2);
    }
    else if (s1.equals("energy_lifetime")) {
      this.energy_lifetime = Integer.parseInt(s2);
    }
    else if (s1.equals("summary_date")) {
      this.summary_date = s2;
      System.out.println("This is SummaryDate");
    }
    else if (s1.equals("alert_name")) {
      this.alert_name = s2;
    }
    else if (s1.equals("alert_start")) {
      this.alert_start = s2;
    }
    else if (s1.equals("level")) {
      this.level = s2;
    }
    else if (s1.equals("start_date")) {
      this.start_date = s2;
    }
    else if (s1.equals("end_date")) {
      this.end_date = s2;
    }
    else if (s1.equals("production_wh")) {
      this.production_wh = Integer.parseInt(s2);
    }
    else if (s1.equals("meter_readings")) {
      this.meter_readings = s2;
    }
    else if (s1.equals("num_devices")) {
      this.num_devices = Integer.parseInt(s2);
    }
    else if (s1.equals("source")) {
      this.source = s2;
    }
  }
}