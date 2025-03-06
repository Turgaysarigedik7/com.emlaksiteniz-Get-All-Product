package com.emlaksiteniz;

import core.scraper.Scraper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * emlaksiteniz.com web sitesi için kazıyıcı (scraper) implementasyonu.
 * Web sitesindeki emlak ilanlarından bilgi çıkarır.
 */
public class EmlakScraper extends Scraper {
    private static final String BASE_URL = "https://emlaksiteniz.com/urunler";
    private static final Duration PAGE_LOAD_WAIT = Duration.ofSeconds(3);

    // XPath sabitleri - Web sayfasındaki elementleri bulmak için kullanılan XPath ifadeleri
    private static final String PRODUCT_LINKS_XPATH = "//div[@class='product-item']//div[@class='ratio ratio-product-box']//a";  // Ürün linklerini bulmak için
    private static final String PRODUCT_TITLE_XPATH = "//h1[@class='product-title']";  // Ürün başlığını bulmak için
    private static final String PRODUCT_PRICE_XPATH = "//strong[@class='lbl-price']";  // Ürün fiyatını bulmak için
    private static final String CATEGORY_ELEMENTS_XPATH = "//nav[@class='nav-breadcrumb']//a";  // Kategori elementlerini bulmak için
    private static final String ADVISOR_XPATH = "//div[contains(text(),'Danışman')]//a";  // Danışman bilgisini bulmak için
    private static final String DESCRIPTION_XPATH = "//div[@class='description']";  // Ürün açıklamasını bulmak için
    private static final String LOCATION_XPATH = "//td[contains(text(),'İlan Konumu')]//parent::tr//td[@class='td-right']";  // Konum bilgisini bulmak için
    private static final String IMAGE_ELEMENTS_XPATH = "//div[@id='product_thumbnails_slider']//div[@class='item-inner']//img";  // Resim elementlerini bulmak için
    private static final String FEATURES_TAB_XPATH = "//li[@class='nav-item']//a[contains(text(),'İlan Özellikleri')]";  // Özellikler sekmesini bulmak için
    private static final String LOCATION_TAB_XPATH = "//li[@class='nav-item']//a[contains(text(),'Emlak Konumu')]";  // Konum sekmesini bulmak için
    private static final String FEATURES_TABLE_XPATH = "//table[@class='table table-striped table-product-additional-information']//td";  // Özellikler tablosunu bulmak için

    /**
     * Web sitesindeki tüm ürünleri kazır ve bir liste olarak döndürür.
     * Her ürün için detaylı bilgileri toplar.
     *
     * @return Kazınan tüm ürünlerin listesi
     */
    @Override
    public List<Product> scrapeProducts() {
        List<Product> products = new ArrayList<>();
        List<String> productLinks = collectProductLinks();

        System.out.println("Total Products Found: " + productLinks.size());

        for (String productUrl : productLinks) {
            try {
                Product product = scrapeProductDetails(productUrl);
                if (product != null) {
                    products.add(product);
                    System.out.println("Scraped product: " + product.getName());
                }
            } catch (Exception e) {
                System.err.println("Error scraping product from URL: " + productUrl);
                System.err.println("Error details: " + e.getMessage());
            }
        }

        return products;
    }

    /**
     * Web sitesindeki tüm ürün linklerini toplar.
     * Ana sayfadaki ürün kartlarından linkleri çıkarır.
     *
     * @return Toplanan ürün linklerinin listesi
     */
    private List<String> collectProductLinks() {
        List<String> productLinks = new ArrayList<>();
        try {
            driver.get(BASE_URL);
            Thread.sleep(PAGE_LOAD_WAIT.getSeconds() * 1000);

            List<WebElement> productElements = driver.findElements(By.xpath(PRODUCT_LINKS_XPATH));
            for (WebElement productElement : productElements) {
                String href = productElement.getAttribute("href");
                if (!href.isEmpty() && !productLinks.contains(href)) {
                    productLinks.add(href);
                    System.out.println("Found link: " + href);
                }
            }
        } catch (Exception e) {
            System.err.println("Error collecting product links: " + e.getMessage());
        }
        return productLinks;
    }

    /**
     * Belirli bir ürün URL'sinden ürün detaylarını kazır.
     * Ürünün tüm özelliklerini ve bilgilerini toplar.
     *
     * @param productUrl Kazınacak ürünün URL'si
     * @return Kazınan ürün bilgilerini içeren Product nesnesi
     */
    private Product scrapeProductDetails(String productUrl) {
        try {
            driver.get(productUrl);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product-item")));

            Product product = new Product();

            // Temel Bilgiler
            product.setName(getTextIfPresent(PRODUCT_TITLE_XPATH));
            product.setPrice(getTextIfPresent(PRODUCT_PRICE_XPATH));
            product.setLocation(getTextIfPresent(LOCATION_XPATH));
            product.setExplanation(getTextIfPresent(DESCRIPTION_XPATH));
            product.setAdvisor(getTextIfPresent(ADVISOR_XPATH));

            // Emlak Detayları
            product.setSquareMeterGross(getFeatureValue("Brüt m²"));
            product.setSquareMeterNet(getFeatureValue("Net m²"));
            product.setNumberRooms(getFeatureValue("Oda Sayısı"));

            // Ek Bilgiler
            product.setAdvertisementNumber(getTextIfPresent("//p[contains(text(),'İlan ID')]"));  // İlan numarasını bulmak için

            // Kategori Bilgileri
            product.setCategories(extractCategories());

            // Diğer Bilgiler
            product.setNumberBathrooms(getTextIfPresent(generateXPathLocator("Banyo Sayısı")));
            product.setKitchen(getTextIfPresent(generateXPathLocator("Mutfak")));
            product.setBalcony(getTextIfPresent(generateXPathLocator("Balkon")));
            product.setLift(getTextIfPresent(generateXPathLocator("Asansör")));
            product.setIsFurnished(getTextIfPresent(generateXPathLocator("Eşyalı")));
            product.setUsageStatus(getTextIfPresent(generateXPathLocator("Kullanım Durumu")));
            product.setDues(getTextIfPresent(generateXPathLocator("Aidat")));
            product.setIsEligibleCredit(getTextIfPresent(generateXPathLocator("Krediye Uygun")));
            product.setDeedStatus(getTextIfPresent(generateXPathLocator("Tapu Durumu")));
            product.setExchangeable(getTextIfPresent(generateXPathLocator("Takaslı")));
            product.setBuildingAge(getTextIfPresent(generateXPathLocator("Bina Yaşı")));
            product.setHeating(getTextIfPresent(generateXPathLocator("Isıtma")));
            product.setFloorLocated(getTextIfPresent(generateXPathLocator("Bulunduğu Kat")));
            product.setNumberFloors(getTextIfPresent(generateXPathLocator("Kat Sayısı")));
            product.setFromWhom(getTextIfPresent(generateXPathLocator("Kimden")));

            // Ek Özellikler
            product.setAdFeatures(extractAdditionalFeatures());
            product.setImageUrl(extractImageUrls());

            return product;
        } catch (Exception e) {
            System.err.println("Error scraping product details: " + e.getMessage());
            return null;
        }
    }

    /**
     * Ürün sayfasındaki kategori bilgilerini çıkarır.
     * Breadcrumb navigasyonundan kategori isimlerini toplar.
     *
     * @return Ürünün kategorilerinin listesi
     */
    private List<String> extractCategories() {
        List<String> categoryNames = new ArrayList<>();
        List<WebElement> categoryElements = driver.findElements(By.xpath(CATEGORY_ELEMENTS_XPATH));
        for (int i = 1; i < categoryElements.size(); i++) {
            categoryNames.add(categoryElements.get(i).getText());
        }
        return categoryNames;
    }

    /**
     * Ürünün ek özelliklerini çıkarır.
     * İlan özellikleri sekmesindeki tablodan bilgileri toplar.
     *
     * @return Özellik adı ve değeri çiftlerini içeren Map
     */
    private Map<String, String> extractAdditionalFeatures() {
        Map<String, String> features = new HashMap<>();
        try {
            if (!driver.findElements(By.xpath(FEATURES_TAB_XPATH)).isEmpty()) {
                driver.findElement(By.xpath(FEATURES_TAB_XPATH)).click();
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(FEATURES_TABLE_XPATH)));

                List<WebElement> tdElements = driver.findElements(By.xpath(FEATURES_TABLE_XPATH));
                for (int i = 0; i < tdElements.size(); i += 2) {
                    if (i + 1 < tdElements.size()) {
                        String key = tdElements.get(i).getText();
                        String value = tdElements.get(i + 1).getText();
                        features.put(key, value);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error extracting additional features: " + e.getMessage());
        }
        return features;
    }

    /**
     * Ürün sayfasındaki tüm resim URL'lerini çıkarır.
     * Ürün galerisindeki tüm resimlerin URL'lerini toplar.
     *
     * @return Ürün resimlerinin URL'lerinin listesi
     */
    private List<String> extractImageUrls() {
        List<String> imageUrls = new ArrayList<>();
        try {
            List<WebElement> imageElements = driver.findElements(By.xpath(IMAGE_ELEMENTS_XPATH));
            for (WebElement imageElement : imageElements) {
                imageUrls.add(imageElement.getAttribute("src"));
            }
        } catch (Exception e) {
            System.err.println("Error extracting image URLs: " + e.getMessage());
        }
        return imageUrls;
    }

    /**
     * Verilen etiket için XPath seçici oluşturur.
     * Özellik değerlerini bulmak için kullanılır.
     *
     * @param label Aranacak özellik etiketi
     * @return Oluşturulan XPath seçici
     */
    private String generateXPathLocator(String label) {
        return "//label[contains(text(),'" + label + "')]//ancestor::div[2]//div[@class='right']//span";
    }

    /**
     * Belirtilen etiket için özellik değerini alır.
     * XPath seçici oluşturup değeri bulmaya çalışır.
     *
     * @param label Aranacak özellik etiketi
     * @return Bulunan özellik değeri veya boş string
     */
    private String getFeatureValue(String label) {
        return getTextIfPresent(generateXPathLocator(label));
    }

    /**
     * Scraper'ı çalıştırmak için ana metod.
     * EmlakScraper sınıfının bir örneğini oluşturup çalıştırır.
     *
     * @param args Komut satırı argümanları (kullanılmıyor)
     * @throws InvocationTargetException Yapıcı metod çağrısı başarısız olursa
     * @throws NoSuchMethodException Yapıcı metod bulunamazsa
     * @throws InstantiationException Örnek oluşturma başarısız olursa
     * @throws IllegalAccessException Yapıcı metod erişimi reddedilirse
     * @throws IOException Dosya işlemleri başarısız olursa
     */
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException, IOException {
        Scraper.executeScraper(EmlakScraper.class);
    }
}
