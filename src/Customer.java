import java.time.*;
import java.util.ArrayList;
import java.sql.*;

public class Customer {
	private int id;
	private String name;
	private Gender gender;
	private LocalDate birthdate;
	private LocalDateTime datestamp;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
	
	public LocalDateTime getDatestamp() {
		return datestamp;
	}

	public Customer() {
	}

	public Customer(String name, Gender gender) {
		this.name = name;
		this.gender = gender;
	}

	public Customer(String name, int gender) {
		this(name, Gender.toGender(gender));
	}

	public Customer(int id, String name, Gender gender, LocalDate birthdate) {
		this(name, gender);
		this.id = id;
		this.birthdate = birthdate;
	}

	public Customer(ResultSet rs) {
		if (rs == null) {
			return;
		}

		try {
			this.id = rs.getInt("Id");
			this.name = rs.getString("Name");
			this.gender = Gender.toGender(rs.getInt("gender"));

			Date dt = rs.getDate("birthdate");
			if (dt != null) {
				this.birthdate = dt.toLocalDate();
			}

			if (rs.getTimestamp("datestamp") != null) {
				this.datestamp = rs.getTimestamp("datestamp").toLocalDateTime();
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getAge() {
		if (birthdate == null) {
			return -1;
		}

		LocalDate now = LocalDate.now();
		int age = now.getYear() - birthdate.getYear();
		if (now.getDayOfYear() < birthdate.getDayOfYear()) {
			age -= 1;
		}

		return age;
	}

	public String toString() {
		return String.format("%s #%d, %s", name, id, gender);
	}

	public static Customer getCustomer(int id) {
		Connection cn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			cn = DBConnection.getConnection();
			if (cn != null) {
				stmt = cn.createStatement();
				rs = stmt.executeQuery("SELECT * FROM Customer WHERE Id = " + id);
				while (rs.next()) {
					return new Customer(rs);
				}
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (rs != null) try {
				rs.close();
			}
			catch (Exception e) {}
			if (stmt != null) try {
				stmt.close();
			}
			catch (Exception e) {}
			if (cn != null) try {
				cn.close();
			}
			catch (Exception e) {}
		}
		return null;
	}

	public static boolean addCustomer(Customer c) {
		if (c == null || c.id > 0) {
			return false;
		}

		Connection cn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			cn = DBConnection.getConnection();
			if (cn != null) {
				stmt = cn.createStatement();
				String sql = String.format("INSERT INTO Customer(Name, Gender, Birthdate) VALUES ('%s', %d, %s)", c.name, c.gender.ordinal(), SQL.getSQLof(c.birthdate));
				int affected = stmt.executeUpdate(sql);
				if (affected > 0) {
					sql = "SELECT SCOPE_IDENTITY() AS Id";
					rs = stmt.executeQuery(sql);
					if (rs.next()) {
						c.id = rs.getInt(1);
					}
				}
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (rs != null) try {
				rs.close();
			}
			catch (Exception e) {}
			if (stmt != null) try {
				stmt.close();
			}
			catch (Exception e) {}
			if (cn != null) try {
				cn.close();
			}
			catch (Exception e) {}
		}
		return c.id > 0;
	}

	public static boolean updateCustomer(Customer c) {
		if (c == null || c.id <= 0) {
			return false;
		}

		String sql = getUpdateSQL(c);
		if (sql.length() == 0) {
			return false;
		}

		Connection cn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int affected = 0;

		try {
			cn = DBConnection.getConnection();
			if (cn != null) {
				stmt = cn.createStatement();
				affected = stmt.executeUpdate(sql);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (rs != null) try {
				rs.close();
			}
			catch (Exception e) {}
			if (stmt != null) try {
				stmt.close();
			}
			catch (Exception e) {}
			if (cn != null) try {
				cn.close();
			}
			catch (Exception e) {}
		}
		return affected > 0;
	}

	private static String getUpdateSQL(Customer c) {
		Customer c0 = getCustomer(c.id);
		ArrayList<String> sql = new ArrayList<String>();
		if (!c.name.equals(c0.name)) {
			sql.add("Name='" + c.name + "'");
		}
		if (!c.gender.equals(c0.gender)) {
			sql.add("Gender=" + c.gender);
		}
		if ((c.birthdate == null && c0.birthdate != null) || (c.birthdate != null && !c.birthdate.equals(c0.birthdate))) {
			sql.add("Birthdate=" + SQL.getSQLof(c.birthdate));
		}
		if (sql.size() > 0) {
			return String.format("UPDATE Customer SET %s WHERE Id = %d", String.join(",", sql), c.id);
		}
		else {
			return "";
		}
	}
}
