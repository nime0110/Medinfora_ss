package com.spring.app.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.spring.app.main.domain.HospitalDTO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Myutil {

	/**
	 * include parameter URL
	 * @param {HttpSevletRequest} SevletRequest
	 * @return {String} inculde request parameter URL
	*/
	public static String getCurrentURL(HttpServletRequest request) {
		
		String currentURL = request.getRequestURL().toString();
		
		String queryString = request.getQueryString();

		if(queryString != null) {
			currentURL += '?' + queryString;
		}
		
		String ctxPath = request.getContextPath();
		
		int beginIndex = currentURL.indexOf(ctxPath) + ctxPath.length();	
		
		currentURL = currentURL.substring(beginIndex);

		return currentURL;
	}
	
	/**
	 * Hospital API Inputer (MADE BY SEO DONGHYEOK)
	 * Local Json File Insert into My DB
	 * Json File Type must be JSONArray
	 * @param {String} Loacl JsonFile Address
	 * @return {List<HospitalDTO>} Parsering List DATA
	 * @throws FileNotFoundException
	 * @throws ParseException
	 * @throws IOException
	 */
	public static List<HospitalDTO> hpApiInputer(String localaddr) throws FileNotFoundException, IOException, ParseException {
		
		List<HospitalDTO> hpdtoList = null;
		
		JSONParser parser = new JSONParser();
		
		Reader reader = new FileReader(localaddr);
		JSONArray jsonArr = (JSONArray) parser.parse(reader);
		
		if(jsonArr.size()>0) {
			
			hpdtoList = new ArrayList<HospitalDTO>();
			
			for(int i=0;i<jsonArr.size();i++) {
				JSONObject jsonObj = (JSONObject) jsonArr.get(i);
				
				HospitalDTO hpdto = new HospitalDTO();
				
				hpdto.setHpname((String)jsonObj.get("hpname"));
				hpdto.setHpaddr((String)jsonObj.get("hpaddr"));
				hpdto.setHptel((String)jsonObj.get("hptel"));
				hpdto.setClasscode((String)jsonObj.get("classcode"));
				hpdto.setAgency((String)jsonObj.get("agency"));
				hpdto.setWgs84lon((String)jsonObj.get("wgs84lon"));
				hpdto.setWgs84lat((String)jsonObj.get("wgs84lat"));
				hpdto.setStarttime1((String)jsonObj.get("starttime1"));
				hpdto.setStarttime2((String)jsonObj.get("starttime2"));
				hpdto.setStarttime3((String)jsonObj.get("starttime3"));
				hpdto.setStarttime4((String)jsonObj.get("starttime4"));
				hpdto.setStarttime5((String)jsonObj.get("starttime5"));
				hpdto.setStarttime6((String)jsonObj.get("starttime6"));
				hpdto.setStarttime7((String)jsonObj.get("starttime7"));
				hpdto.setStarttime8((String)jsonObj.get("starttime8"));
				hpdto.setEndtime1((String)jsonObj.get("endtime1"));
				hpdto.setEndtime2((String)jsonObj.get("endtime2"));
				hpdto.setEndtime3((String)jsonObj.get("endtime3"));
				hpdto.setEndtime4((String)jsonObj.get("endtime4"));
				hpdto.setEndtime5((String)jsonObj.get("endtime5"));
				hpdto.setEndtime6((String)jsonObj.get("endtime6"));
				hpdto.setEndtime7((String)jsonObj.get("endtime7"));
				hpdto.setEndtime8((String)jsonObj.get("endtime8"));
				
				hpdtoList.add(hpdto);
			}
			
		}
		
		return hpdtoList;
	}
}
