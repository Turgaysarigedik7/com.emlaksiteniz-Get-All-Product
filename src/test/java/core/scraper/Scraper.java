package core.scraper;


import com.emlaksiteniz.Product;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.List;
import static core.scraper.JSONHandler.writeJSONToFile;

/**
 * Web kazıma (scraping) işlemleri için soyut temel sınıf.
 * Selenium WebDriver kullanarak web kazıma işlemleri için ortak işlevsellik sağlar.
 */
public abstract class Scraper {
    protected WebDriver driver;          // Web tarayıcı sürücüsü
    protected WebDriverWait wait;        // Sayfa yüklenme bekleme nesnesi
    private static final Duration DEFAULT_WAIT_TIME = Duration.ofSeconds(5);  // Varsayılan bekleme süresi

    /**
     * WebDriver'ı başlıksız (headless) modda başlatan yapıcı metod.
     */
    public Scraper() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Başlıksız modu etkinleştirmek için yorumu kaldırın
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, DEFAULT_WAIT_TIME);
    }

    /**
     * XPath kullanarak bir elementten metin almaya çalışır.
     * Element bulunamazsa boş string döndürür.
     *
     * @param xpath Elementi bulmak için kullanılacak XPath seçici
     * @return Elementin metin içeriği veya bulunamazsa boş string
     */
    protected String getTextIfPresent(String xpath) {
        try {
            return driver.findElement(By.xpath(xpath)).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * WebDriver örneğini varsa kapatır.
     */
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Alt sınıflar tarafından uygulanması gereken soyut metod.
     * Kazıma mantığını tanımlar.
     *
     * @return Kazınan ürünlerin listesi
     */
    public abstract List<Product> scrapeProducts();

    /**
     * Verilen kazıyıcı sınıfı için kazıma işlemini çalıştırır.
     *
     * @param clazz Çalıştırılacak kazıyıcı sınıfı
     * @param <T> Bu temel sınıfı genişleten kazıyıcı tipi
     * @throws NoSuchMethodException Yapıcı metod bulunamazsa
     * @throws InvocationTargetException Yapıcı metod çağrısı başarısız olursa
     * @throws InstantiationException Örnek oluşturma başarısız olursa
     * @throws IllegalAccessException Yapıcı metod erişimi reddedilirse
     * @throws IOException Dosya işlemleri başarısız olursa
     */
    public static <T extends Scraper> void executeScraper(Class<T> clazz)
            throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException, IOException {
        T scraper = clazz.getDeclaredConstructor().newInstance();
        try {
            List<Product> products = scraper.scrapeProducts();
            System.out.println("\nKazınan ürün sayısı: " + products.size());
            writeJSONToFile("compiled.json", products);
        } finally {
            scraper.close();
        }
    }
}
