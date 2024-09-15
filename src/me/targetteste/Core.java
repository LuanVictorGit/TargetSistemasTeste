package me.targetteste;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class Core {

	public static void main(String[] args) {
		
		// Questão - 1
		int soma = 0;
		int k = 0;
		for (int indice = 13; indice > 0; indice--) {
			k+=1;
			soma+=k;
		}
		System.out.println("1) soma final: " + soma);
		
		
		// Questão - 2
		Integer[] fibo = new Integer[20];
		for (int i = 0; i < fibo.length; i++) {
			if (i>=2 && fibo[i-1] != null && fibo[i-2] != null) {
				fibo[i] = fibo[i-1] + fibo[i-2];
				continue;
			}
			fibo[i] = i;
		}
		int number = 3; // definindo aqui o numero que vai verificar se encontra na lista
		if (!hasNumberInFibo(number, fibo)) {
			System.out.println("2) Não contem o numero " + number + " na lista.");
		} else {
			System.out.println("2) O numero " + number + " foi encontrado na lista.");
		}
		
		
		// Questão - 3
		try {
            List<Faturamento> dadosFaturamento = carregarDadosJson("dados.json");
            List<Double> diasComFaturamento = new ArrayList<>();
            for (Faturamento dia : dadosFaturamento) {
                if (dia.getValor() > 0) {
                    diasComFaturamento.add(dia.getValor());
                }
            }
            if (diasComFaturamento.isEmpty()) {
                System.out.println("3) Não há dias com faturamento para análise.");
                return;
            }

            double menorFaturamento = diasComFaturamento.stream().min(Double::compare).orElse(0.0);
            double maiorFaturamento = diasComFaturamento.stream().max(Double::compare).orElse(0.0);

            double somaFaturamento = diasComFaturamento.stream().mapToDouble(Double::doubleValue).sum();
            double mediaFaturamento = somaFaturamento / diasComFaturamento.size();

            long diasAcimaDaMedia = diasComFaturamento.stream().filter(valor -> valor > mediaFaturamento).count();

            System.out.println("3) Menor valor de faturamento: R$ " + String.format("%.2f", menorFaturamento));
            System.out.println("3) Maior valor de faturamento: R$ " + String.format("%.2f", maiorFaturamento));
            System.out.println("3) Número de dias com faturamento acima da média: " + diasAcimaDaMedia);

        } catch (IOException e) {
            System.out.println("3) Erro ao ler o arquivo JSON: " + e.getMessage());
        }
		
		
		// Questão - 4
		Map<String, Double> faturamentos = new HashMap<>();
		faturamentos.put("SP", 67836.43);
		faturamentos.put("RJ", 36678.66);
		faturamentos.put("MG", 29229.88);
		faturamentos.put("ES", 27165.48);
		faturamentos.put("Outros", 19849.53);

        double totalFaturado = faturamentos.values().stream().mapToDouble(Double::doubleValue).sum();

        System.out.println("4) Percentual de representação por estado:");
        for (Map.Entry<String, Double> entry : faturamentos.entrySet()) {
            String estado = entry.getKey();
            double faturamento = entry.getValue();
            double percentual = (faturamento / totalFaturado) * 100;
            System.out.printf("%s: %.2f%%\n", estado, percentual);
        }
        
        
        // Questão - 5
        String line = "TargetSistemas";
        String newLine = new String();
        for (int i = line.toCharArray().length; i > 0; i--) {
			newLine+=line.toCharArray()[i-1];
		}
        System.out.println("5) " + line);
        System.out.println("5) " + newLine);
	}
	
	// verificando se o numero existe no vetor
	private static Boolean hasNumberInFibo(int number, Integer[] fibo) {
		for(int n : fibo) {
			if (n == number) return true;
		}
		return false;
	}
	
	// carregando o arquivo json com os dias.
	private static List<Faturamento> carregarDadosJson(String filePath) throws IOException {
        Gson gson = new Gson();
        Type tipoLista = new TypeToken<List<Faturamento>>() {}.getType();
        FileReader reader = new FileReader(filePath);
        List<Faturamento> dadosFaturamento = gson.fromJson(reader, tipoLista);
        reader.close();
        return dadosFaturamento;
    }
	
}
