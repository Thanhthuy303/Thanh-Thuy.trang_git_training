package Example;

import static java.lang.Thread.sleep;

import java.awt.Desktop.Action;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class BaiTap5 {
	
	static WebDriver driver;
	
	public static void main(String[] args) throws InterruptedException {
		setup();	
		System.out.println("Go to move slider");
		//driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		Thread.sleep(5000);		
        setValue(37,48);      
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//  Setup function
	public static void setup() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",
				"D:\\06_Automation\\WebDriver\\chromedriver_win32v84\\chromedriver.exe");
		
		driver = new ChromeDriver();
//		Maximize window
		driver.manage().window().maximize();
		LocalDate localDate = LocalDate.now();
		String currentDate = localDate.toString();
		String url = "https://all.accor.com/ssr/app/accor/hotels/hhhhh-audugodi-police-quarters-adugodi-bengaluru-karnataka-560030/index.en.shtml?dateIn="+ currentDate +"&nights=1&compositions=1&stayplus=false";
//		1. Open page : Hotel list page
		driver.get(url);
		//sleep(10000);
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
//		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
//		Wait 
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
    }
// 	Set LeftSlider
	 public static void setLowValue(WebElement slider, int value) throws InterruptedException {
	        WebElement sliderBar= driver.findElement(By.xpath ("//div[@class='rangeSlider__picker noUi-target noUi-ltr noUi-horizontal noUi-txt-dir-ltr']"));
	        int curValue = Integer.parseInt(slider.getAttribute("aria-valuetext").trim());
	        Dimension dim = sliderBar.getSize(); // get width - height
	        Point point = slider.getLocation(); // get current location
	        System.out.println("Dim size"+dim);
	        System.out.println("point"+point);
	        System.out.println("slider"+slider);
	        double min = Double.parseDouble(slider.getAttribute("aria-valuemin")); // get min 
			System.out.println("min  "+ min);
	        double max = Double.parseDouble(slider.getAttribute("aria-valuemax")); // get max
			System.out.println("max  "+ max);

	        int distance = (int) ((dim.width) / (int) (max - min + 1)); // calculate from min to max
	        System.out.println("distance  "+ distance);
	        int x = (int) ((dim.width) / (int) (max - min + 1)); // calculate for x => 1 unit = ?px
	        System.out.println("x  "+ x);
	        int y =  dim.height / 2; // calculate  for y
	        System.out.println("y  "+ y);
	        int MAX = dim.width; // get limit-num (263)
	        int count = 0;
	        do {
	            Actions SliderAction = new Actions(driver);
	            SliderAction.clickAndHold(slider);
	            SliderAction.moveByOffset(x,y).build().perform();;
	            sleep(100);
	            curValue = Integer.parseInt(slider.getAttribute("aria-valuetext").trim());
	            if (curValue == value) {
	                SliderAction.clickAndHold(slider).release().build().perform();
	                break;
	            } else if (curValue > value) {
	                x -= (int) (distance / 10);
	                System.out.println("x curValue > value "+x);
					System.out.println("curValue > "+ curValue);
	            } else if (curValue < value) {
	                x += (int) (distance / 10);
	                System.out.println("x curValue < value "+x);
					System.out.println("curValue < "+ curValue);
	            }
	            count--;

	        } while (count < MAX);
	    }
//		Set rightSlider
	    public static void setUperValue(WebElement slider, int value) throws InterruptedException {
	        WebElement sliderBar= driver.findElement(By.xpath ("//div[@class='noUi-connect']"));
	        int curValue = Integer.parseInt(slider.getAttribute("aria-valuetext").trim());
	        Dimension dim = sliderBar.getSize();
	        //Point point = slider.getLocation();
	        System.out.println("slider"+slider);
	        double max = Double.parseDouble(slider.getAttribute("aria-valuemin"));
	        double min = Double.parseDouble(slider.getAttribute("aria-valuemax"));

	        int distance = (int) ((dim.width) / (int) (max - min - 1));
	        int x = (int) ((dim.width) / (int) (max - min - 1));
	        int y =  dim.height / 2;
	        final int MAX = dim.width;
	        int count = 0;
	        do {
	            Actions SliderAction = new Actions(driver);
	            SliderAction.clickAndHold(slider);
	            SliderAction.moveByOffset(x,y).build().perform();;
	            sleep(100);
	            curValue = Integer.parseInt(slider.getAttribute("aria-valuetext").trim());
	            if (curValue == value) {
	                System.out.println("break"+curValue);
	                break;
	            } else if (curValue < value) {
	                System.out.println("x curValue < value "+ x);
	                System.out.println("curValue < "+ curValue);
	                x -= (int) (distance / 10);
	            } else if (curValue > value) {
	                x += (int) (distance / 10);
	                System.out.println("x curValue > value "+x);
	                System.out.println("curValue > "+ curValue);
	            }
	            count++;

	        } while (count < MAX);
	    }
//		Set range for price 
	    public static void setValue (int low, int Uper) throws InterruptedException {
	        WebElement lower = driver.findElement(By.xpath("//div[@class='noUi-handle noUi-handle-lower']"));
	        WebElement upper = driver.findElement(By.xpath("//div[@class='noUi-handle noUi-handle-upper']"));
	        setLowValue(lower,low);
	        sleep(1000);
	        setUperValue(upper,Uper);
	    }

}
