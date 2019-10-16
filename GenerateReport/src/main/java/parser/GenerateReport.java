package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import model.Currency;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toMap;

import model.Income;

public class GenerateReport {

	public List<Income> readFile() {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Sample_Input.csv");
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		// skip the header of the csv
		List<Income> inputList = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
		return inputList;
	}

	private Function<String, Income> mapToItem = (line) -> {
		String[] p = line.split(",");// a CSV has comma separated lines
		Income item = new Income(p[0], p[1], p[2], Currency.valueOf(p[3]), Long.parseLong(p[4]));
		return item;
	};
	
	
	private static final String OUTPUT_HEAD_COLUMNS = "Country\\City,Gender,Average Income(USD)";

	  public void writeReport(List<Income> incomeList, String reportName) {
	    try (FileWriter fw = new FileWriter(new File(reportName))) {
	      fw.append(OUTPUT_HEAD_COLUMNS).append("\n");
	      LinkedHashMap<String, Map<String, Double>> avgGenderIncomeByCountry =
	          calculateAvgIncomePerCountryByGender(incomeList);

	      fw.append(joinCountryGenderAvgIncome(avgGenderIncomeByCountry));
	    } catch (IOException e) {
	      System.out.println("Can't generate report: " + e.getMessage());
	    }
	  }

	  private String joinCountryGenderAvgIncome(
	      final LinkedHashMap<String, Map<String, Double>> avgGenderIncomeByCountry) {
	    return avgGenderIncomeByCountry.entrySet()
	        .stream()
	        .map(countryEntry -> countryEntry.getValue().entrySet()
	                .stream()
	                .map(genderEntry -> joinKeyValues(countryEntry, genderEntry))
	                .collect(joining("\n")))
	        .collect(joining("\n"));
	  }

	  private LinkedHashMap<String, Map<String, Double>> calculateAvgIncomePerCountryByGender(
	      final List<Income> incomeList) {
	    return incomeList.stream()
	        .collect(groupingBy(Income::getCountry,
	            groupingBy(Income::getGender,
	                mapping(this::getIncomeInUSDollars,
	                    averagingDouble(Double::valueOf)))))
	        .entrySet()
	        .stream()
	        .sorted(Entry.comparingByKey())
	        .collect(toMap(Entry::getKey, Entry::getValue,
	            (m1, m2) -> m1,
	            LinkedHashMap::new));
	  }

	  private double getIncomeInUSDollars(final Income income) {
	    return income.getAvgIncome() / income.getCurrency().getConversionRate();
	  }

	  private String joinKeyValues(final Entry<String, Map<String, Double>> countryEntry,
	      final Entry<String, Double> genderEntry) {
	    return countryEntry.getKey() + "," + genderEntry.getKey() + "," + genderEntry.getValue();
	  }

	public static void main(String args[]) {
		GenerateReport reader = new GenerateReport();
		List<Income> inputList = reader.readFile();
        System.out.println(inputList);
        reader.writeReport(inputList,"output.csv");
	}

}
