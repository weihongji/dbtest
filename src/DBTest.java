public class DBTest {

	public static void main(String[] args) {
		Customer c = Customer.getCustomer(1);
		System.out.println(c.toString() + ". Age: " + c.getAge());
//		Customer.addCustomer(new Customer("Susan", Gender.Female));
//		Customer c = new Customer(0, "Lydia", Gender.Female, LocalDate.parse("19830815", DateTimeFormatter.ofPattern("yyyyMMdd")));
//		Customer.addCustomer(c);
//		System.out.println(c);
		
	}

}
