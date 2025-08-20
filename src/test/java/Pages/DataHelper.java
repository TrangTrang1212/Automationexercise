package Pages;

public class DataHelper {
    public static String generateUniqueEmail() {
        return "demo" + System.currentTimeMillis() % 10000 + "@test.vn";
    }
}
