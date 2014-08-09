package program;

import api.SolarSystem;

public class Tester
{
  public static SolarSystem solar;
  public static String systemKey = "181938"; public static String apiKey = "821d1e995274b8bcd2431ec72f461381";

  public static void main(String[] args) {
    solar = new SolarSystem(Integer.parseInt(systemKey), apiKey);
  }
}