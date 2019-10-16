package model;

public class Income {

	private String country;
	private String city;
	private String gender;
	private Currency currency;
	private double avgIncome;
	
	

	public Income(String country, String city, String gender, Currency currency, double avgIncome) {
		super();
		this.country = country;
		this.city = city;
		this.gender = gender;
		this.currency = currency;
		this.avgIncome = avgIncome;
	}

	@Override
	public String toString() {
		return "Income [country=" + country + ", city=" + city + ", gender=" + gender + ", currency=" + currency
				+ ", avgIncome=" + avgIncome + "]";
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public double getAvgIncome() {
		return avgIncome;
	}

	public void setAvgIncome(double avgIncome) {
		this.avgIncome = avgIncome;
	}

}
