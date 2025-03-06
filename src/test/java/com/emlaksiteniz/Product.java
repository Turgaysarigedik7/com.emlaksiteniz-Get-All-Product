package com.emlaksiteniz;


import java.util.List;
import java.util.Map;

/**
 * Represents a real estate product with all its properties.
 * This class contains all the information about a property listing.
 */
public class Product {
    // Temel Bilgiler
    private String name;                    // Emlak ilanının başlığı
    private String price;                   // Emlak fiyatı
    private String advertisementNumber;     // İlan numarası
    private String location;                // Emlakın konumu
    private String explanation;             // İlan açıklaması
    private List<String> categories;        // Emlak kategorileri (örn: KONUT>SATILIK>DAIRE)
    private String advisor;                 // Emlak danışmanının adı

    // Emlak Detayları
    private String squareMeterGross;        // Brüt metrekare
    private String squareMeterNet;          // Net metrekare
    private String numberRooms;             // Oda sayısı
    private String buildingAge;             // Bina yaşı
    private String floorLocated;            // Bulunduğu kat
    private String numberFloors;            // Toplam kat sayısı
    private String heating;                 // Isıtma tipi
    private String numberBathrooms;         // Banyo sayısı
    private String kitchen;                 // Mutfak tipi
    private String balcony;                 // Balkon bilgisi
    private String lift;                    // Asansör bilgisi
    private String isFurnished;             // Eşya durumu
    private String usageStatus;             // Kullanım durumu
    private String dues;                    // Aidat bilgisi
    private String isEligibleCredit;        // Kredi uygunluğu
    private String deedStatus;              // Tapu durumu
    private String fromWhom;                // Satıcı tipi
    private String exchangeable;            // Takas imkanı

    // Ek Bilgiler
    private Map<String,String> adFeatures;  // Ek emlak özellikleri
    private List<String> imageUrl;          // Emlak fotoğraf URL'leri

    /**
     * Default constructor for Product class.
     */
    public Product() {
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
    public String getAdvisor() {
        return advisor;
    }

    public void setAdvisor(String advisor) {
        this.advisor = advisor;
    }

    public String getAdvertisementNumber() {
        return advertisementNumber;
    }

    public void setAdvertisementNumber(String advertisementNumber) {
        this.advertisementNumber = advertisementNumber;
    }

    public String getSquareMeterGross() {
        return squareMeterGross;
    }

    public void setSquareMeterGross(String squareMeterGross) {
        this.squareMeterGross = squareMeterGross;
    }

    public String getSquareMeterNet() {
        return squareMeterNet;
    }

    public void setSquareMeterNet(String squareMeterNet) {
        this.squareMeterNet = squareMeterNet;
    }

    public String getNumberRooms() {
        return numberRooms;
    }

    public void setNumberRooms(String numberRooms) {
        this.numberRooms = numberRooms;
    }

    public String getBuildingAge() {
        return buildingAge;
    }

    public void setBuildingAge(String buildingAge) {
        this.buildingAge = buildingAge;
    }

    public String getFloorLocated() {
        return floorLocated;
    }

    public void setFloorLocated(String floorLocated) {
        this.floorLocated = floorLocated;
    }

    public String getNumberFloors() {
        return numberFloors;
    }

    public void setNumberFloors(String numberFloors) {
        this.numberFloors = numberFloors;
    }

    public String getHeating() {
        return heating;
    }

    public void setHeating(String heating) {
        this.heating = heating;
    }

    public String getNumberBathrooms() {
        return numberBathrooms;
    }

    public void setNumberBathrooms(String numberBathrooms) {
        this.numberBathrooms = numberBathrooms;
    }

    public String getKitchen() {
        return kitchen;
    }

    public void setKitchen(String kitchen) {
        this.kitchen = kitchen;
    }

    public String getBalcony() {
        return balcony;
    }

    public void setBalcony(String balcony) {
        this.balcony = balcony;
    }

    public String getLift() {
        return lift;
    }

    public void setLift(String lift) {
        this.lift = lift;
    }

    public String getIsFurnished() {
        return isFurnished;
    }

    public void setIsFurnished(String isFurnished) {
        this.isFurnished = isFurnished;
    }

    public String getUsageStatus() {
        return usageStatus;
    }

    public void setUsageStatus(String usageStatus) {
        this.usageStatus = usageStatus;
    }

    public String getDues() {
        return dues;
    }

    public void setDues(String dues) {
        this.dues = dues;
    }

    public String getIsEligibleCredit() {
        return isEligibleCredit;
    }

    public void setIsEligibleCredit(String isEligibleCredit) {
        this.isEligibleCredit = isEligibleCredit;
    }

    public String getDeedStatus() {
        return deedStatus;
    }

    public void setDeedStatus(String deedStatus) {
        this.deedStatus = deedStatus;
    }

    public String getFromWhom() {
        return fromWhom;
    }

    public void setFromWhom(String fromWhom) {
        this.fromWhom = fromWhom;
    }

    public String getExchangeable() {
        return exchangeable;
    }

    public void setExchangeable(String exchangeable) {
        this.exchangeable = exchangeable;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Map<String,String> getAdFeatures() {
        return adFeatures;
    }

    public void setAdFeatures(Map<String,String> adFeatures) {
        this.adFeatures = adFeatures;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }
    @Override
    public String toString() {
        return "Advertisement{" +
                "name='" + name + '\'' +
                "price='" + price + '\'' +
                "categories='" + categories + '\'' +
                ", advisor='" + advisor + '\'' +
                ", advertisementNumber='" + advertisementNumber + '\'' +
                ", squareMeterGross='" + squareMeterGross + '\'' +
                ", squareMeterNet='" + squareMeterNet + '\'' +
                ", numberRooms='" + numberRooms + '\'' +
                ", buildingAge=" + buildingAge +
                ", floorLocated=" + floorLocated +
                ", numberFloors=" + numberFloors +
                ", heating='" + heating + '\'' +
                ", numberBathrooms='" + numberBathrooms + '\'' +
                ", kitchen='" + kitchen + '\'' +
                ", balcony='" + balcony + '\'' +
                ", lift='" + lift + '\'' +
                ", isFurnished='" + isFurnished + '\'' +
                ", usageStatus='" + usageStatus + '\'' +
                ", dues='" + dues + '\'' +
                ", isEligibleCredit='" + isEligibleCredit + '\'' +
                ", deedStatus='" + deedStatus + '\'' +
                ", fromWhom='" + fromWhom + '\'' +
                ", exchangeable='" + exchangeable + '\'' +
                ", explanation='" + explanation + '\'' +
                ", adFeatures='" + adFeatures + '\'' +
                ", location='" + location + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

}