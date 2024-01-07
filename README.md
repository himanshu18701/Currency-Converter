# Currency Converter

## Introduction

Welcome to the Currency Converter application! This tool allows you to convert between different currencies using real-time exchange rates. It also provides features for managing your favorite currencies for quick access. The Currency Converter is a simple Java program that enables users to convert currencies, manage their favorite currencies, and view a list of their favorite currencies. The program utilizes the ExchangeRate API to fetch real-time currency exchange rates. This documentation provides an overview of the program's functionality and usage.

## Features

- Convert currencies using live exchange rates from a third-party API.
- Manage favorite currencies to save frequently used ones.
- View a list of favorite currencies.
- Update the exchange rate for favorite currencies manually.

## Requirements

- Java 8 or higher
- Internet connection (for fetching exchange rates)

## Getting Started

### Running the Application

1. Download the `CurrencyConverter.java` file.
2. Open a terminal or command prompt in the directory where you saved the file.
3. Compile the code using the following command:

   ```bash
   javac CurrencyConverter.java
   ```

4. Run the application using the following command:

   ```bash
   java CurrencyConverter
   ```

## Using the Application

- The application will present you with a menu of options.
- To convert currency, select option 1 and follow the prompts.
- To manage favorite currencies, select option 2 and choose from sub-options to add, view, or update favorite currencies.
- To exit the application, select option 4.

## Code Structure

### Classes

- **CurrencyConverter (main class):**
  - Contains the main method to run the application.
  - Manages user interaction and calls other methods for currency conversion and favorite currency management.
  
- **JSONObject (from org.json library):**
  - Used for parsing JSON responses from the exchange rate API.

- **OkHttpClient (from okhttp3 library):**
  - Used for making HTTP requests to the exchange rate API.

### Methods

- `main()`: Starts the application and presents the user with the menu.
- `convertCurrency()`: Performs currency conversion using the exchange rate API.
- `manageFavoriteCurrencies()`: Provides options to manage favorite currencies.
- `addFavoriteCurrency()`: Adds a currency to the favorites list.
- `showFavoriteCurrencies()`: Displays the list of favorite currencies.
- `updateFavoriteCurrency()`: Updates the exchange rate for a favorite currency.

## Currency Conversion

### Input

- Users are prompted to enter the source currency code (`convertFrom`).
- Users are prompted to enter the target currency code (`convertTo`).
- Users are prompted to enter the quantity to convert (`quantity`).

### API Integration

- The program uses the ExchangeRate API to fetch real-time exchange rates.
- The API key used is provided within the program (`ddf5839c29e8afee559db99bb654431f`).

### Output

- The program calculates and displays the converted result based on the entered inputs.

## Managing Favorite Currencies

### Conclusion

The CurrencyConverter program offers a simple yet effective way for users to perform currency conversions and manage their favorite currencies. The integration with the ExchangeRate API ensures accurate and up-to-date exchange rates, enhancing the reliability of the currency conversion process. Users can easily navigate through the menu options to perform desired tasks, making it a user-friendly and accessible tool for currency-related activities.

### Additional Information

- The API key used for accessing the exchange rate API is hardcoded in the code. If you have your own API key, replace the existing one with your key.
- The application currently only supports updating exchange rates for favorite currencies manually.
