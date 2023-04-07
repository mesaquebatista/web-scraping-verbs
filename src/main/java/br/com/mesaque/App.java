package br.com.mesaque;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import br.com.mesaque.dao.VerbDAO;
import br.com.mesaque.db.ConnectionMySql;
import br.com.mesaque.entities.Verb;

import org.openqa.selenium.WebElement;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        // System.out.println(ConnectionMySql.getConnection());
        fillDatabase();
    }

    public static void fillDatabase() {
        System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);

        List<String> verbs = getVerbs(driver);

        for (String verb : verbs) {
            getVerForms(driver, verb);
        }

        driver.quit();
        // System.out.println( "Hello World!" );
    }

    public static void getVerForms(WebDriver driver, String sVerb) {
        try {
            VerbDAO dao = new VerbDAO();

            Verb verb = dao.getByVerb(sVerb);

            if(verb == null){
                
                verb = new Verb();
    
                driver.get("https://pt.bab.la/verbo/ingles/" + sVerb);
    
                WebElement elementInfinitive = driver
                        .findElement(By.xpath("/html/body/main/div[2]/div[1]/div/div[1]/div/div[2]/div[2]/ul/li"));
                System.out.println("Infinitive: " + elementInfinitive.getText());
                verb.setInfinitive(elementInfinitive.getText());
    
                WebElement elementSimplePast = driver
                        .findElement(By.xpath("/html/body/main/div[2]/div[1]/div/div[1]/div/div[3]/div[2]/ul/li"));
                System.out.println("Simple Past: " + elementSimplePast.getText());
                verb.setSimplePast(elementSimplePast.getText());
    
                WebElement elementPastParticiple = driver
                        .findElement(By.xpath("/html/body/main/div[2]/div[1]/div/div[1]/div/div[4]/div[2]/ul/li"));
                System.out.println("Past participle: " + elementPastParticiple.getText());
                verb.setPastParticiple(elementPastParticiple.getText());

                verb.setVerb(sVerb);

                dao.save(verb);
            }

        } catch (Exception e) {
            System.out.println("ERROR: " + sVerb);
        }

    }

    public static List<String> getVerbs(WebDriver driver) {
        List<String> verbs = new ArrayList<>();

        driver.get("https://www.talkenglish.com/vocabulary/top-1000-verbs.aspx");
        // driver.get("https://cocosteaparty.com/list-of-verbs/");

        List<WebElement> elements = driver.findElements(By.cssSelector("#GridView3 > tbody > tr > td:nth-child(2) > a"));
        // List<WebElement> elements = driver.findElements(By.cssSelector(
        //         "#post-7273 > div > div.post_content_w.jl_sh_link > div.post_content.jl_content > ul > li"));

        for (WebElement e : elements) {
            verbs.add(e.getText());
        }

        return verbs;
    }
}
