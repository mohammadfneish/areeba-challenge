/**
 * 
 */
package com.fneishpro.mobile.validation.model;

/**
 * @author M.Fneish
 *
 */
public class MobileInfo {

	private boolean valid;
	private String countryCode;
	private String countryName;
	private String operatorName;
    
    public MobileInfo() {
		super();
	}
    
	/**
	 * @param valid
	 * @param countryCode
	 * @param countryName
	 * @param operatorName
	 */
	public MobileInfo(boolean valid, String countryCode, String countryName, String operatorName) {
		this.valid = valid;
		this.countryCode = countryCode;
		this.countryName = countryName;
		this.operatorName = operatorName;
	}


	/**
	 * @return the valid
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * @param valid the valid to set
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * @return the operatorName
	 */
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * @param operatorName the operatorName to set
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

}
