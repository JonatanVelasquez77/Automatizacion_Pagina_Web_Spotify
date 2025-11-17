package utils;

import driver.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static void takeScreenshot(String testName) {
        if (System.getenv("RUN_ENV") != null) {
            try {
                TakesScreenshot ts = (TakesScreenshot) DriverFactory.getDriver();
                File source = ts.getScreenshotAs(OutputType.FILE);
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String fileName = "screenshot_" + testName + "_" + timestamp + ".png";
                FileUtils.copyFile(source, new File("./screenshots/" + fileName));
                System.out.println("Screenshot tomada: " + fileName);
            } catch (IOException e) {
                System.out.println("Error tomando screenshot: " + e.getMessage());
            }
        }
    }
}