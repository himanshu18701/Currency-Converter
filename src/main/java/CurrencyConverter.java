import java.io.IOException;
import java.math.BigDecimal;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.*;
import java.util.Scanner;
import org.json.JSONObject;
public class CurrencyConverter {
	private static Set<String> favoriteCurrencies=new HashSet<>();
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select an option:");
            System.out.println("1. Convert currency");
            System.out.println("2. Manage favorite currencies");
            System.out.println("3. Show favorite currencies");
            System.out.println("4. Exit");

            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    convertCurrency(scanner);
                    break;
                case 2:
                    manageFavoriteCurrencies(scanner);
                    break;
                case 3:
                    showFavoriteCurrencies();
                    break;
                case 4:
                    System.out.println("Exiting. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }
    private static void convertCurrency(Scanner scanner) throws IOException {
        System.out.println("Type currency to convert from");
        String convertFrom = scanner.nextLine();
        System.out.println("Type currency to convert to");
        String convertTo = scanner.nextLine();
        System.out.println("Type quantity to convert");
        BigDecimal quantity = scanner.nextBigDecimal();
//        String dumpURL="https://v6.exchangerate-api.com/v6/a3b549dd4446eec760918b2d/latest/"+convertFrom.toUpperCase(); -----> 2nd API
        String urlString ="http://api.exchangerate.host/live?access_key=ddf5839c29e8afee559db99bb654431f&source="+convertFrom.toUpperCase();
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
        		.url(urlString)
        		.get()
        		.build();
        Response response=client.newCall(request).execute();
        String stringResponse=response.body().string();
        JSONObject jsonObject=new JSONObject(stringResponse);
        JSONObject ratesObject=jsonObject.getJSONObject("quotes");
        BigDecimal rate=ratesObject.getBigDecimal(convertFrom.toUpperCase()+convertTo.toUpperCase());
        BigDecimal result=rate.multiply(quantity);
        System.out.println(result);
    }
    private static void manageFavoriteCurrencies(Scanner scanner) {
        System.out.println("Select an option:");
        System.out.println("1. Add favorite currency");
        System.out.println("2. View favorite currencies");
        System.out.println("3. Update favorite currency");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                addFavoriteCurrency(scanner);
                break;
            case 2:
                showFavoriteCurrencies();
                break;
            case 3:
                updateFavoriteCurrency(scanner);
                break;
            default:
                System.out.println("Invalid option. Please choose a valid option.");
        }
    }

    private static void addFavoriteCurrency(Scanner scanner) {
        System.out.println("Enter the currency code to add to favorites:");
        String currencyCode = scanner.nextLine().toUpperCase();
        if (favoriteCurrencies.contains(currencyCode)) {
            System.out.println("Currency is already in favorites.");
        } else {
            favoriteCurrencies.add(currencyCode);
            System.out.println(currencyCode +"added to favorites.");
        }
    }
    private static void updateFavoriteCurrency(Scanner scanner) {
        System.out.println("Enter the currency code to update:");
        String currencyCode = scanner.nextLine().toUpperCase();

        if (favoriteCurrencies.contains(currencyCode)) {
            System.out.println("Enter the new exchange rate for the currency:");
            BigDecimal exchangeRate = scanner.nextBigDecimal();
            favoriteCurrencies.add(currencyCode);
            System.out.println(currencyCode + " updated with new exchange rate: " + exchangeRate);
        } else {
            System.out.println("Currency not found in favorites.");
        }
    }

    private static void showFavoriteCurrencies() {
        if (favoriteCurrencies.isEmpty()) {
            System.out.println("No favorite currencies added yet.");
        } else {
            System.out.println("Favorite currencies:");
            for(String key:favoriteCurrencies) {
            	System.out.println(key);
            }
        }
    }
}
