package core.scraper;


import com.emlaksiteniz.Product;
import com.google.gson.*;

import java.io.*;
import java.util.List;

public class JSONHandler {

    /**
     * Verilen JSON dizisini belirtilen dosyaya yazar.
     *
     * @param fileName Type: String Info: JSON verisinin yazılacağı dosyanın adı
     * @param jsonArray Type: JsonArray Info: Yazılacak JSON verisini içeren dizi
     * @throws IOException Eğer dosya yazma sırasında bir hata oluşursa
     */
    public static void writeJSONToFile(String fileName, List<Product> jsonArray) throws IOException {
        // Dosyayı aç ve var olan içeriğe ekleme yap (append) için "true" kullanılır
        try (FileWriter writer = new FileWriter(fileName, true)) {
            // JSON verisini yazarken daha güzel formatlanmış (pretty-print) olmasını sağlarız
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonArray, writer);
        }
    }

    /**
     * Belirtilen dosyadan JSON verisini okur ve bir JsonArray olarak döndürür.
     *
     * @param fileName Type: String Info: JSON verisini içeren dosyanın adı
     * @return Type: JsonArray Info: Dosyadaki JSON verisini bir JsonArray olarak döndürür. Eğer dosya boşsa veya mevcut değilse boş bir JsonArray döner.
     * @throws IOException Eğer dosya okuma sırasında bir hata oluşursa
     */
    public static JsonArray readJSONFromFile(String fileName) throws IOException {
        File file = new File(fileName);

        // Dosya yoksa yeni bir JsonArray döndür
        if (!file.exists()) {
            return new JsonArray(); // Dosya yoksa yeni boş bir JsonArray döndürüyoruz
        }

        // Dosyayı FileReader ve BufferedReader ile oku
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }

        // Dosya boş ise, yeni bir JsonArray döndür
        if (content.length() == 0) {
            return new JsonArray();  // Boş dosya olduğunda yeni bir JsonArray döndürüyoruz
        }

        // JSON'u JsonArray'e dönüştür
        return JsonParser.parseString(content.toString()).getAsJsonArray();
    }


}
