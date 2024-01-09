import java.io.IOException;
import java.math.BigDecimal;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.*;
import java.util.Scanner;
import org.json.JSONObject;
public class CurrencyConverter {
	private static HashMap<String,HashMap<String,BigDecimal>> favoriteCurrencies=new HashMap<>();
	private static Scanner scanner=new Scanner(System.in);
    public static void main(String[] args) throws Exception {
    	while (true) {
            try {
                showMenu();
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
    private static void showMenu() throws IOException {
    	System.out.println("Select an option:");
        System.out.println("1. Convert currency");
        System.out.println("2. Manage favorite currencies");
        System.out.println("3. Show favorite currencies");
        System.out.println("4. Exit");

        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                convertCurrency();
                break;
            case 2:
                manageFavoriteCurrencies();
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
    private static void handleExceptions(ExceptionRunnable runnable) {
    	try {
    		runnable.run();
    	}catch(Exception e) {
    		System.out.println("An error occurred: "+e.getMessage());
    	}
    }
    private static JSONObject getCurrency(String convertFrom) throws IOException {
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
        return ratesObject;
    }
    private static void convertCurrency() throws IOException{
    	 System.out.println("Type currency to convert from");
         String convertFrom = scanner.nextLine().toUpperCase();
         System.out.println("Type currency to convert to");
         String convertTo = scanner.nextLine().toUpperCase();
         System.out.println("Type quantity to convert");
         BigDecimal quantity = scanner.nextBigDecimal();
         if(favoriteCurrencies.containsKey(convertFrom)) {
        	 HashMap<String,BigDecimal> map=favoriteCurrencies.get(convertFrom);
        	 BigDecimal result=map.get(convertFrom+convertTo);
        	 result=result.multiply(quantity);
        	 System.out.println(result);
        	 return;
         }
         JSONObject ratesObject=getCurrency(convertFrom);
         BigDecimal rate=ratesObject.getBigDecimal(convertFrom.toUpperCase()+convertTo.toUpperCase());
         BigDecimal result=rate.multiply(quantity);
         System.out.println(result);
    }

    private static void manageFavoriteCurrencies() throws IOException {
        System.out.println("Select an option:");
        System.out.println("1. Add favorite currency");
        System.out.println("2. View favorite currencies");
        System.out.println("3. Update favorite currency");
        System.out.println("4. Delete currency from favorite");
        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option) {
            case 1:
                addFavoriteCurrency();
                break;
            case 2:
                showFavoriteCurrencies();
                break;
            case 3:
                updateFavoriteCurrency();
                break;
            case 4:
            	deletionFromFavoriteCurrency();
            	break;
            default:
                System.out.println("Invalid option. Please choose a valid option.");
        }
    }

    private static void addFavoriteCurrency() throws IOException {
        System.out.println("Enter the currency code to add to favorites:");
        String currencyCode = scanner.nextLine().toUpperCase();
        if (favoriteCurrencies.containsKey(currencyCode)) {
            System.out.println("Currency is already in favorites.");
        } else {
            JSONObject quotesObject=getCurrency(currencyCode);
            if(quotesObject!=null) {
            	HashMap<String,BigDecimal>exchangeRates=new HashMap<>();
            	for(String key:quotesObject.keySet()) {
            		exchangeRates.put(key,quotesObject.getBigDecimal(key));
            	}
            	favoriteCurrencies.put(currencyCode,exchangeRates);
            	System.out.println(currencyCode +"added to favorites.");
            }else {
            	System.out.println("Error fetching conversion rates. Please try again later.");
            }
        }
    }
    private static void updateFavoriteCurrency() throws IOException{
        System.out.println("Enter the currency code to update:");
        String currencyCode = scanner.nextLine().toUpperCase();
        if (favoriteCurrencies.containsKey(currencyCode)) {
        	JSONObject quotesObject=getCurrency(currencyCode);
        	if(quotesObject!=null) {
        		HashMap<String,BigDecimal> updatedRates=new HashMap<>();
        		for(String key:quotesObject.keySet()) {
        			updatedRates.put(key,quotesObject.getBigDecimal(key));
        		}
        		favoriteCurrencies.put(currencyCode, updatedRates);
        		System.out.println(currencyCode + " is successfully updated with the latest exchange rates.");
        	}else {
                System.out.println("Error fetching conversion rates. Please try again later.");
            }
        } else {
            System.out.println("Currency not found in favorites.");
        }
    }

    private static void deletionFromFavoriteCurrency() {
    	System.out.println("Enter the currency which you want to delete");
    	String currencyCode=scanner.nextLine().toUpperCase();
    	if(favoriteCurrencies.containsKey(currencyCode)) {
    		favoriteCurrencies.remove(currencyCode);
    	}else {
    		System.out.println("This Currency is not present in the Favorite Currency List");
    	}
    }
    
    private static void showFavoriteCurrencies() {
        if (favoriteCurrencies.isEmpty()) {
            System.out.println("No favorite currencies added yet.");
        } else {
            System.out.println("Favorite currencies:");
            for(Map.Entry<String,HashMap<String,BigDecimal>>entry:favoriteCurrencies.entrySet()) {
            	System.out.println(entry.getKey());
            }
        }
    }
}
