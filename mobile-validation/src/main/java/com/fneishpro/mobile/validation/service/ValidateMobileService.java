package com.fneishpro.mobile.validation.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fneishpro.mobile.validation.model.MobileInfo;

@Service
public class ValidateMobileService {

	private static final Logger log = LoggerFactory.getLogger(ValidateMobileService.class);

	/**
	 * Read the object from URL
	 * @param rd
	 * @return String the json string from URL
	 * @throws IOException
	 */
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	/**
	 * Read the URL
	 * @param url
	 * @return JSONObject
	 * @throws IOException
	 * @throws JSONException
	 */
	public static MobileInfo readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		MobileInfo mobileInfo = new MobileInfo();
		
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			
			mobileInfo.setValid(json.getBoolean("valid"));
			mobileInfo.setCountryCode(json.getString("country_prefix"));
			mobileInfo.setCountryName(json.getString("country_name"));
			mobileInfo.setOperatorName(json.getString("carrier"));
		} finally {
			is.close();
		}
		
		return mobileInfo;
	}

	/**
	 * Get the phone info from the API
	 * @reference https://numverify.com/dashboard
	 * @param number
	 * @return
	 */
	public MobileInfo getPhoneInfo(String number) {
		MobileInfo mobileInfo = new MobileInfo();
		try {
			mobileInfo = readJsonFromUrl("http://apilayer.net/api/validate?access_key=ac5c6b3772430c1154575b8ce8b77a7b&number="+number+"&country_code=&format=1");
		} catch (Exception e) {
			log.error("", e);
		}
		
		return mobileInfo;
	}
	
	/**
	 * check if the phone is valid
	 * @param number
	 * @return
	 */
	public boolean isPhoneValid(String number) {
		MobileInfo res = getPhoneInfo(number);
		return res.isValid();
	}

}
