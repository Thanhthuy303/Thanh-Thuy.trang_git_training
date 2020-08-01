package Example;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaiTap2 {
	
	static WebDriver driver;
	
	public static void main(String[] args) throws InterruptedException {
// 		Go to Homepage
		goToHomepage();		
//      2. Add at least 3 different products to CART
////	--	Define var
		String[] productListSelector = new String[] {"document.querySelectorAll('ul#homefeatured > li:nth-of-type(1)')[0].getElementsByClassName('product-name')[0].click()",
										"document.querySelectorAll('ul#homefeatured > li:nth-of-type(2)')[0].getElementsByClassName('product-name')[0].click()",
										"document.querySelectorAll('ul#homefeatured > li:nth-of-type(3)')[0].getElementsByClassName('product-name')[0].click()",""};
		String[] sizeForEachProduct = new String[] {"2","3","1",""};
		int numWantToAdd = 3;
		String addedProductName [] = new String[] {"","","","",""};
		String addedProductSize [] = new String[] {"","","","",""}; 
//		2. Add at least 3 different products to CART
		for(int i = 0; i <= numWantToAdd-1; i++) {
			addProductToCart(productListSelector[i],sizeForEachProduct[i]);
			System.out.println("Finish adding product " + i + ":\n");
//			get Name i
			addedProductName [i] = getNameProduct();
			System.out.println("Product name "+ (i+1) + ":\n" + addedProductName[i]);
//			get Size i
			addedProductSize[i] = getSizeProduct(); 
			System.out.println("Size Product "+(i+1)+ ":\n"+ addedProductSize[i]);
//			Back to Homepage 
			backToHomepage();
		}
//		Navigate to Summary page
		navigateToSummaryPage();
//		Verify the current URL of summary page
		compareToCurrentURL();
//		5. Verify the list of product match with NAME noted data
		compareToProductName(numWantToAdd,addedProductName);
//		5. Verify the list of product match with SIZE noted data		
		compareToProductSize(numWantToAdd,addedProductSize);
//		6. Quit the browser after finishing the case
		finishTesting();
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//  Setup function
	public static void goToHomepage() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",
				"D:\\06_Automation\\WebDriver\\chromedriver_win32v84\\chromedriver.exe");
		
		driver = new ChromeDriver();
//		Maximize window
		driver.manage().window().maximize();
		String url = "http://automationpractice.com/index.php";
//		1. Open page : http://automationpractice.com/index.php
		driver.get(url);
		//sleep(10000);
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
//		Wait 
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
    }
//	 Add product function
	public static void addProductToCart (String selector, String productNum ) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(selector); // CLick on any Product
		Thread.sleep(5000);	
//		Elements product detail page -> choose num and click 'Add to cart' 
		String addNumSelector = "document.querySelectorAll(\"input#quantity_wanted \")[0].value=" + "'" + productNum +"'";
		js.executeScript(addNumSelector);
		js.executeScript("document.querySelector(\"button[name='Submit']\").click()");
//	    Click on 'add to cart' button
		Thread.sleep(5000);			
//	    Click on 'continue shopping' button			
		js.executeScript("document.querySelector(\"span[title='Continue shopping']\").click()");		 
	}
	public static String getNameProduct () throws InterruptedException {
		String titleXPath = "//h1[@itemprop='name']";
		String titleProduct = driver.findElement(By.xpath(titleXPath)).getText().trim();
		return titleProduct;				 
	}
	public static String getSizeProduct () throws InterruptedException {
		String sizeSelector = "return document.querySelector('input#quantity_wanted').value";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String sizeProduct =(String) js.executeScript(sizeSelector);
		return sizeProduct;		 
	}
	public static void backToHomepage() throws InterruptedException {
		String backHomeselector = "document.querySelector(\"img[class='logo img-responsive']\").click()";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(backHomeselector);
		Thread.sleep(5000);
	}
//  Go to Summary Page
	public static void navigateToSummaryPage() throws InterruptedException {
		String naviagateSummarylector = "document.querySelector(\"a[title='View my shopping cart']\").click()";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(naviagateSummarylector);
		Thread.sleep(5000);
	}
// 	Verify the current URL of summary page
	public static void compareToCurrentURL() throws InterruptedException {
		String currentURLselector = "return document.URL";
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String currentURL = (String)js.executeScript(currentURLselector);
		String urlToCompare = "http://automationpractice.com";
		if (currentURL.contains(urlToCompare)) {
			System.out.println("URL matched --> PASSED");			
		}
		else {
			System.out.println("URL NOT matched --> FAILED");
		}
		Thread.sleep(1000);		 
	}
//	Compare To ProductName
	public static void compareToProductName(int numProduct, String[] nameProduct) throws InterruptedException {
		String selector1 = "table#cart_summary > tbody > tr:nth-of-type(";
		String selector2 =	") > td[class='cart_description'] > p[class='product-name']";
		String[] summaryProductList = new String[] {"","","",""};
		
		for (int i = 0; i < numProduct;i++) {
			summaryProductList[i]= selector1 + (i+1) + selector2;
			String nameProductSelector = "return document.querySelector(\""+ summaryProductList[i] + "\").textContent";
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String sumNameProduct =(String) js.executeScript(nameProductSelector);
			if (sumNameProduct.equals(nameProduct[i])) {
				System.out.println("Name Product "+(i+1)+" matched --> PASSED");
				System.out.println("Name Product before: " + nameProduct[i]); 
				System.out.println("Name Product after: " + sumNameProduct); 
			}
			else {
				System.out.println("Name Product "+(i+1)+" NOT matched --> FAILED");
				System.out.println("Name Product before: " + nameProduct[i]); 
				System.out.println("Name Product after: " + sumNameProduct);
			}
		}
		
	}
//	Compare To Product Size	
	public static void compareToProductSize(int numProduct,String[] sizeProduct ) throws InterruptedException {
		String selector1 = "table#cart_summary > tbody > tr:nth-of-type(";
		String selector2 =	") > td[class='cart_quantity text-center'] > input[type='hidden']";
		String[] summaryProductList = new String[] {"","","",""};
		
		for (int i = 0; i < numProduct;i++) {
			summaryProductList[i]= selector1 + (i+1) + selector2;
			String sizeProductSelector = "return document.querySelector(\""+ summaryProductList[i] + "\").value";
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String sumSizeProduct =(String) js.executeScript(sizeProductSelector);
			if (sumSizeProduct.equals(sizeProduct[i])) {
				System.out.println("Size Product "+(i+1)+" matched --> PASSED");
				System.out.println("Size Product before: " + sizeProduct[i]); 
				System.out.println("Size Product after: " + sumSizeProduct);
			}
			else {
				System.out.println("Size Product "+(i+1)+" NOT matched --> FAILED");
				System.out.println("Size Product before: " + sizeProduct[i]); 
				System.out.println("Size Product after: " + sumSizeProduct);
			}
		}
		 
	}
	
	public static void finishTesting() {
	        driver.quit();
	    }
}

/* 
1. Open page : http://automationpractice.com/index.php
2. Add at least 3 different products to CART
3. Note the size and name of added product
4. Verify the current URL of summary page
5. Verify the list of product match with noted data
5. Quit the browser after finishing the case
*/
