package be.vdab.util;

import java.math.BigDecimal;

public class Invoercontrole {
	
	public static boolean controleerLong(long param){
		if (param < 0) {
			return false;
		}
		return true;
	}
	
	public static boolean controleerString(String param){
		if (param == null || param.equals("")){
			return false;
		}
		return true;
	}
	
	public static boolean controleerInt(int param){
		if (param < 0) {
			return false;
		}
		return true;
	}
	
	public static boolean controleerBigDecimal(BigDecimal param){
		if (param.signum() == -1) {
			return false;
		}
		return true;
	}
	
}
