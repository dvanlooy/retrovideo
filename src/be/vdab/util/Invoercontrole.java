package be.vdab.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class Invoercontrole {

	public static boolean isLongPositive(long param) {
		if (param < 0) {
			return false;
		}
		return true;
	}

	public static boolean isStringNotNullOrEmpty(String param) {
		if (param == null || param.equals("")) {
			return false;
		}
		return true;
	}

	public static boolean isIntPositive(int param) {
		if (param < 0) {
			return false;
		}
		return true;
	}

	public static boolean isBigDecimalPositive(BigDecimal param) {
		Objects.requireNonNull(param, "parameter mag niet null zijn");
		if (param.signum() == -1) {
			return false;
		}
		return true;
	}

}
