package TestMePackage1;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import TestMePackage1.capture;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;



public class OnlineShoppingTest {
	
	
	ExtentReports report;
	ExtentTest test;
	WebDriver driver;
	
	
	//Open chrome browser ---priority is not given, so it will execute first
	@Test
	  public void OpenChrome() {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Training_C2a.05.01\\Desktop\\mona\\chromedriver_win32\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.get("http://10.232.237.143:443/TestMeApp/fetchcat.htm");
	  }
	
	
	//registration process--Screenshot is displayed for failure
  @Test(priority=1)
  public void testRegistration() throws InterruptedException, IOException {
	  driver.findElement(By.partialLinkText("SignUp")).click();
	  driver.findElement(By.name("userName")).sendKeys("haritharaj");
	  driver.findElement(By.name("firstName")).click();
	  Thread.sleep(1000);
	  String text=driver.findElement(By.id("err")).getText();
	 System.out.println(text);
	 String av="Available";
	  if(text.equals(av)){
		  driver.findElement(By.name("firstName")).sendKeys("priyamohana");
		  driver.findElement(By.name("lastName")).sendKeys("s");
		  driver.findElement(By.name("password")).sendKeys("9876543210");
		  driver.findElement(By.name("confirmPassword")).sendKeys("9876543210");
		  driver.findElement(By.xpath("//input[@value='Female']")).click();
		  driver.findElement(By.name("emailAddress")).sendKeys("marvelousmona@gmail.com");
		  driver.findElement(By.name("mobileNumber")).sendKeys("7890646789");
		  driver.findElement(By.name("dob")).sendKeys("29/09/1997");
		  driver.findElement(By.name("address")).sendKeys("Coimbatore");
		  Select a=new Select(driver.findElement(By.id("securityQuestion")));
		  a.selectByIndex(1);
		  driver.findElement(By.id("answer")).sendKeys("Udumalpet");
		  driver.findElement(By.name("Submit")).click();
		  test.log(LogStatus.PASS, "Registration success", "Registered successfully");
		  System.out.println("Registration succesfull");
		  
	  }
	  
	  else {
		  System.out.println("Name already exist");
		  test.log(LogStatus.FAIL,"Name already exist", test.addScreenCapture(capture.takescreenshot(driver)));
		  driver.findElement(By.name("userName")).click();
	  }
  }
 
  
  //Login for TestMe
  @Test(priority=2)
  public void testLogin() throws InterruptedException, IOException {
	  driver.findElement(By.name("userName")).sendKeys("Lalitha");
	  driver.findElement(By.name("password")).sendKeys("Password123");
	  //Thread.sleep(2000);
	  driver.findElement(By.name("Login")).click();
	  //Thread.sleep(3000);
	  //System.out.println(driver.getTitle());
	  if(driver.getTitle().contains("Home")){
		  System.out.println("Login successfull");
		  test.log(LogStatus.PASS, "login Passed", "Login executed successfully");
	  }
	  else {
		  System.out.println("Login unsuccessfull");
		  test.log(LogStatus.FAIL,"Login unsuccessfull", test.addScreenCapture(capture.takescreenshot(driver)));  
	  }
  }
  
  
  //Checking whether item added to cart
  @Test(priority=3)
  public void testCart() throws IOException {
	  WebElement search=driver.findElement(By.name("products"));
	  Actions act=new Actions(driver);
	  act.click(search).sendKeys("HeadPhone").build().perform();
	  //driver.findElement(By.name("val")).click();
	  driver.findElement(By.xpath("//input[@value='FIND DETAILS']")).click();
	  driver.findElement(By.partialLinkText("Add to cart")).click();
	  driver.findElement(By.partialLinkText("Cart")).click();
	  String s=driver.findElement(By.className("nomargin")).getText();
	  System.out.println(s);
		 if(s.equals("Headphone")) {
			 System.out.println("Item Sucessfully added to cart");
			 test.log(LogStatus.PASS, "Add to cart successfull", "Item Sucessfully added to cart");
		 }
		 else{
			 System.out.println("Item not added to cart");
			  test.log(LogStatus.FAIL,"Item not added to cart", test.addScreenCapture(capture.takescreenshot(driver)));
		 }
	 
  }
  
 
  
  //initiation of payment and checking whether the payment is successfull or not 
  @Test(priority=4)
  public void testPayment() throws InterruptedException, IOException {
	  	driver.findElement(By.partialLinkText("Checkout")).click();
		  driver.findElement(By.xpath("//input[@value='Proceed to Pay']")).click();
		  Thread.sleep(10000);
		  driver.findElement(By.cssSelector("#swit > div:nth-child(1) > div > label > i")).click();
		  //driver.findElement(By.xpath("//input[@value='HDFC Bank']")).click();
		  driver.findElement(By.id("btn")).click();
		  driver.findElement(By.name("username")).sendKeys("123456");
		  driver.findElement(By.name("password")).sendKeys("Pass@456");
		  driver.findElement(By.xpath("//input[@value='LOGIN']")).click();
		  //System.out.println(driver.getTitle());
		  driver.findElement(By.xpath("//input[@value='PASSWORD']")).sendKeys("Trans@456");
		  driver.findElement(By.xpath("//input[@value='PayNow']")).click();
		  String lasttitle=driver.getTitle();
		  System.out.println(lasttitle);
		  if(lasttitle.equals("Order Details")) {
			  System.out.println("Payment succesfull");
			  test.log(LogStatus.PASS, "Payment successfull", "Payment done succesfully");
		  }
		  else {
			  System.out.println("payment unsuceesfull");
			  test.log(LogStatus.FAIL,"payment unsuceesfull", test.addScreenCapture(capture.takescreenshot(driver)));
		  }
		  
		   
  }
  

  //Extent report initiation
  @BeforeTest
  public void startReportBeforeTest() {
	  report=new ExtentReports("C:\\Users\\Training_C2a.05.01\\Desktop\\mona\\casestudyreport.html",false);
	  test=report.startTest("OnlineShoppingTest");
  }

  
  
  //End the report
  @AfterTest
  public void afterTest() {
	  report.flush();
	  report.endTest(test);
  } 
  

}
