package program;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import fileHandling.LFile;

public class Saving extends MainProgram
{
  public static LFile data;
  public static String reminder;
  public static final String DAY = "zonnetje productie op dag ";
  public static final String WEEK = "zonnetje productie op week voor ";

  public static void txt(String type)
  {
    String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

    data = new LFile(new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\Zonnetje\\" + type + timeStamp + ".txt"));
    if (!data.exist()) {
      try {
		data.create();
      } catch (IOException e) {}
      for (int i = 0; i < solar.production.length; i++)
		data.append(String.valueOf(solar.production[i]));
    }
  }

  public static void xls(String type)
  {
    String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    try
    {
      FileOutputStream fileOut = new FileOutputStream("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\Zonnetje\\" + type + timeStamp + ".xls");
      HSSFWorkbook workbook = new HSSFWorkbook();
      HSSFSheet worksheet = workbook.createSheet("Worksheet");

      HSSFRow[] row = new HSSFRow[289];
      HSSFCell[][] cell = new HSSFCell[289][11];

      for (int i = 0; i <= 288; i++) {
        row[i] = worksheet.createRow(i);
      }

      for (int x = 1; x <= 288; x++) {
        int hours = x * solar.interval_length % 86400 / 3600;
        int minutes = x * solar.interval_length % 86400 % 3600 / 60;
        cell[x][0] = row[x].createCell(0);
        cell[x][0].setCellValue(twoDigitString(hours) + ":" + twoDigitString(minutes));
      }

      int teller = 7;
      for (int y = 1; y <= 7; y++) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.add(5, -teller);
        cell[0][y] = row[0].createCell(y);
        cell[0][y].setCellValue(dateFormat.format(cal.getTime()));
        teller--;
      }

      int x = 1; int y = 1;
      for (int i = 1; i < solar.production.length; i++) {
        cell[x][y] = row[x].createCell(y);
        cell[x][y].setCellValue(solar.production[i]);
        x++;
        if (x > 288) {
          x = 1;
          y++;
        }

      }

      workbook.write(fileOut);
      fileOut.flush();
      fileOut.close();
    }
    catch (Exception e) {
      System.out.println(e);
    }
  }

  public static void csv(String type){
    String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    data = new LFile(new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\Zonnetje\\" + type + timeStamp + ".csv"));
    if (!data.exist()) {
      try {
		data.create();
	} catch (IOException e) {}

      for (int i = 0; i < solar.production.length; i++) {
        reminder = reminder + solar.production[i] + ",";
      }
      reminder = reminder.substring(4, reminder.length() - 1);
      data.append(reminder);
    }
  }
}