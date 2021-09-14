/**
 * 
 */
package com.fneishpro.mobile.validation.model;

/**
 * @author M.Fneish
 *
 */
public class MobileInfo {

	/**
	 * true if the phone is valid else false
	 */
	private boolean valid;
	
	/**
	 * .the country code of the mobile number
	 */
	private String countryCode;
	
	/**
	 * the country name of the mobile number
	 */
	private String countryName;
	
	/**
	 * the operator name of the mobile number if available
	 */
	private String operatorName;
    
	/**
	 * Constructor
	 */
    public MobileInfo() {
		super();
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
