
import br.com.currencyConverter.Currency;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.*;

public class Main {
	public static void main(String[] args) throws IOException, InterruptedException, JsonSyntaxException {
		System.out.println("Olá, seja bem-vindo(a)!");
		Scanner input = new Scanner(System.in);
		int counter = 0;
		int answer = -1;
		boolean invalidValue = false;
		List<Currency> currencies = new ArrayList<>();
		Properties properties = new Properties();

		try {
			properties.load(new FileInputStream("config.properties"));
		} catch (IOException error) {
			System.err.println("Opa, parece que faltam algumas propriedades de configuração por aqui. " + error.getMessage());
		}
		String apiKey = properties.getProperty("api.key");
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		String absolutePath = new File("conversions.json").getAbsolutePath();

		if (Currency.checkFile(absolutePath)) {
			System.out.println("Você possui registros armazenados de conversões realizadas anteriormente. Gostaria de consultá-los novamente? (digite 1 para verificar essas conversões ou 0 para ir direto para próximas conversões)");
			answer = input.nextInt();
			answer = Currency.checkAnswer(answer, "Digite 1 para revisar as conversões salvas existentes ou 0 para continuar");
			if (answer == 1) {
				Currency.displayConversions(absolutePath);
				System.out.println("Digite 0 para sair do programa ou 1 para continuar. Ao continuar você poderá realizar novas conversões de moedas.");
				answer = input.nextInt();
				answer = Currency.checkAnswer(answer, "Digite 0 para sair do programa ou 1 para continuar");

				if (answer == 0) {
					System.out.println("Saindo do programa...");
					System.exit(0);
				}
			}
		}

		do {
			if (counter < 1) {
				System.out.println("Insira o código ISO da  moeda  de origem (por exemplo, o código do Real Brasileiro é 'BRL' e o do Euro é 'EUR')");
				counter++;
			} else
				System.out.println("Insira o código da moeda de origem");
			String currency1 = input.next().toUpperCase();


			try {
				System.out.println("Quantos " + currency1 + " você deseja converter?");
				double currencyValue1 = input.nextDouble();

				if (currencyValue1 < 0) {
					System.out.println("Você não pode digitar um valor negativo aqui. Tente novamente.");
					invalidValue = true;
					continue;
				}

				System.out.println("Insira o código da moeda de destino");
				String currency2 = input.next().toUpperCase();
				String url = "https://api.currencyapi.com/v3/latest?apikey=" + apiKey + "&base_currency=" + currency1 + "&currencies=" + currency2;
				HttpClient client = HttpClient.newHttpClient();
				HttpRequest request = HttpRequest.newBuilder()
						.uri(URI.create(url))
						.build();
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

				if (response.statusCode() == 422) {
					System.out.println("Parece que você inseriu um código de moeda inválido. Por favor, tente novamente.");
					invalidValue = true;
					continue;
				}

				String json = response.body();

				Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
				}.getType());

// Acessando o objeto 'data'
				Map<String, Object> data = (Map<String, Object>) map.get("data");

// Acessando o objeto da moeda dentro de 'data'
				Map<String, Object> currencyApi = (Map<String, Object>) data.get(currency2);

// Acessando o valor da moeda
				double value = (Double) currencyApi.get("value");
				Currency currency = new Currency(currency1, currencyValue1, currency2, value);
				System.out.printf("1 %s equivale a %.2f %s\n", currency1, value, currency2);
				currencies.add(currency);
				System.out.println("""
						Escolha uma opção:
						1 - Fazer mais conversões
						0 - Sair
						""");
				answer = input.nextInt();
				answer = Currency.checkAnswer(answer, "Digite 1 para fazer mais conversões ou 0 para sair");
			} catch (InputMismatchException error) {
				System.err.println("Erro: verifique se você digitou um número válido" + error.getMessage());
			} catch (NullPointerException error) {
				System.err.println("Opa, temos algo nulo por aqui." + error.getMessage());
			}

			if (answer == 0)
				break;
		}
		while (answer != 0);

		if (!invalidValue) {
			for (Currency currency : currencies) {
				System.out.printf("%.2f %s equivale a %.2f %s\n", currency.getAmountOriginCurrency(), currency.getOriginCurrencyCode(), currency.getAmountDestinationCurrency(), currency.getDestinationCurrencyCode());
			}

			System.out.println("Você deseja salvar estas conversões para  consultá-las futuramente? (1 para sim, 0 para não). Lembrando que se salvar as conversões atuais, você perderá as queforam salvas anteriormente.");
			answer = Currency.checkAnswer(answer, "digite 1 para salvar ou 0 para sair sem salvar as conversões");
			answer = input.nextInt();

			if (answer == 1) {
				FileWriter file = new FileWriter("conversions.json");
				file.write(gson.toJson(currencies));
				file.close();
			}

			System.out.println("Então é isso. Obrigado, volte sempre!");
		}
		input.close();
	}


}