package br.com.currencyConverter;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Currency {

	private String originCurrencyCode;

	private String destinationCurrencyCode;

	private double amountOriginCurrency;

	private double amountDestinationCurrency;

	public Currency(String originCurrencyCode, double amountOriginCurrency, String destinationCurrencyCode, double exchangeRate) {
		this.originCurrencyCode = originCurrencyCode;
		this.amountOriginCurrency = amountOriginCurrency;
		this.destinationCurrencyCode = destinationCurrencyCode;
		this.amountDestinationCurrency = converter(exchangeRate);
	}

	public String getOriginCurrencyCode() {
		return originCurrencyCode;
	}

	public String getDestinationCurrencyCode() {
		return destinationCurrencyCode;
	}

	public double getAmountOriginCurrency() {
		return amountOriginCurrency;
	}

	public double converter(double exchangeRate) {
		return this.amountOriginCurrency * exchangeRate;
	}

	public double getAmountDestinationCurrency() {
		return amountDestinationCurrency;
	}

	public static int checkAnswer(int num, String msg) {
		Scanner input = new Scanner(System.in);
		while (num != 0 && num != 1) {
			System.out.println("O número digitado por você não é uma opção válida. Por favor, Digite um número válido.");
			System.out.println(msg);
			num = input.nextInt();
		}
		return num;
	}

	public static boolean checkFile(String path) {
		try {
			Path FilePath = Paths.get(path);

			if (Files.exists(FilePath)) {
				String fileContent = new String(Files.readAllBytes(FilePath));
				JsonParser parser = new JsonParser();
				JsonElement element = parser.parse(fileContent);
				return true;
			}
			return false;
		} catch (IOException error) {
			System.err.println("Erro ao ler o arquivo: " + error.getMessage());
			return false;
		} catch (JsonSyntaxException error) {
			System.err.println("Erro ao analisar o conteúdo como JSON: " + error.getMessage());
			return false;
		}
	}

	public static void displayConversions(String path) throws IOException {
		Path filePath = Paths.get(path);

		try {
			String fileContent = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
			Gson gson = new Gson();
			Type listType = new TypeToken<List<Currency>>() {
			}.getType();
			List<Currency> conversions = gson.fromJson(fileContent, listType);
			Currency conversion;

			if (conversions.size() < 2) {
				conversion = conversions.get(0);
				System.out.println("Conversão realizada:");
				System.out.println("Moeda de origem: " + conversion.getOriginCurrencyCode() + ",");
				System.out.println("Moeda de destino: " + conversion.getDestinationCurrencyCode() + ",");
				System.out.printf("Valor em %s: %.2f,\n", conversion.getOriginCurrencyCode(), conversion.getAmountOriginCurrency());
				System.out.printf("Valor em %s: %.2f.\n", conversion.getDestinationCurrencyCode(), conversion.getAmountDestinationCurrency());
			} else {
				for (int i = 0; i < conversions.size(); i++) {
					conversion = conversions.get(i);
					System.out.println(i + 1 + "a" + " conversão");
					System.out.println("Moeda de origem: " + conversion.getOriginCurrencyCode() + ",");
					System.out.println("Moeda de destino: " + conversion.getDestinationCurrencyCode() + ",");
					System.out.printf("Valor em %s: %.2f,\n", conversion.getOriginCurrencyCode(), conversion.getAmountOriginCurrency());
					System.out.printf("Valor em %s: %.2f.\n", conversion.getDestinationCurrencyCode(), conversion.getAmountDestinationCurrency());
				}
			}
		} catch (IOException error) {
			System.err.println("Erro ao ler o arquivo: " + error.getMessage());
		}
	}

	public String toString() {
		return "Código da moeda: " + destinationCurrencyCode + "\nValor: " + amountDestinationCurrency;
	}


}
