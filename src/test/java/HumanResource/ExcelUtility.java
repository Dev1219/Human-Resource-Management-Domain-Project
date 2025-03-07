package HumanResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ExcelUtility {
    String fPath = System.getProperty("user.dir") + "\\ExcellFile\\LoginData.xlsx";
    File file;
    FileOutputStream fos;
    FileInputStream fis;
    XSSFWorkbook wb;
    XSSFSheet sheet;
    int index = 0;

    @Test(dataProvider = "getLoginData")
    public void createData(String un, String ps) {
        XSSFRow row = sheet.createRow(index);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue(un);

        cell = row.createCell(1);
        cell.setCellValue(ps);

        index++;
    }

    @Test(dependsOnMethods = "createData") // This test will run after createData test
    public void readData() {
        try {
            fis = new FileInputStream(file);
            wb = new XSSFWorkbook(fis);
            sheet = wb.getSheet("LoginData"); // Ensure we read the correct sheet
            
            int rowCount = sheet.getPhysicalNumberOfRows(); // Get total rows
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells(); // Get total columns
            
            System.out.println("Reading data from Excel:");
            for (int i = 0; i < rowCount; i++) {
                XSSFRow row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    XSSFCell cell = row.getCell(j);
                    
                    // Use DataFormatter to handle different cell types (string, number, etc.)
                    DataFormatter formatter = new DataFormatter();
                    String cellValue = formatter.formatCellValue(cell);
                    
                    System.out.print(cellValue + "  |  ");
                }
                System.out.println(); // Move to next line after reading one row
            }

            wb.close();
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DataProvider
    public Object[][] getLoginData() {
        return new Object[][]{
            {"Admin", "admin123"},
            {"HR_Manager", "HR@2024"},
            {"Data_Manager", "Emp@12345"},
            {"Admin", "admin123"},
            {"Operation_Manager", "WrongPass123"},
        };
    }

    @BeforeTest
    public void beforeTest() {
        try {
            file = new File(fPath);
            file.getParentFile().mkdirs(); // Ensure folder exists

            wb = new XSSFWorkbook();
            sheet = wb.createSheet("LoginData"); // Sheet name added

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterTest
    public void afterTest() {
        try {
            fos = new FileOutputStream(file);
            wb.write(fos);
            fos.close();
            wb.close();
            System.out.println("Excel file written successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
