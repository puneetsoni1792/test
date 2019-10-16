package junit5.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Currency;
import model.Income;
import parser.GenerateReport;

class GenerateReportTest {
	


	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		GenerateReport reader= new GenerateReport();
		List<Income> incomeFromFile = reader.readFile();
		System.out.println(incomeFromFile);
		assertNotNull(incomeFromFile);
	}
	
	@Test
	void test1() {
		GenerateReport writer= new GenerateReport();
		Income obj1 = new Income("IND", "Delhi", "M", Currency.INR, 30000.0);
		List<Income> list= new ArrayList<>();
		list.add(obj1);
		File f = new File("test.csv");
		writer.writeReport(list,"test.csv" );
		assertTrue(f.exists());
	}

}
