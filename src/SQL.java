import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SQL {
	public static String getSQLof(LocalDate date) {
		return date == null ? "NULL" : "'" + date.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "'";
	}
}
