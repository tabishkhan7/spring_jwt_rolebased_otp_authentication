package io.app.authmanager.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.app.authmanager.service.UserValidateService;

@Service
public class UserValidateServiceImpl implements UserValidateService {
	@Value("${io.login.baseUri}")
	private String baseUri;
	
	@Value("{io.login.getOtp}")
	private String getOtp;
	
	/* (non-Javadoc)
	 * @see io.app.login.serviceimpl.LoginService#validateUser(java.lang.String, java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see io.app.authmanager.serviceImpl.UserValidateService#validateUser(java.lang.String, java.lang.String)
	 */
	@Override
	public ResponseEntity<Object> validateUser(String email,String otp) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate =new RestTemplate();
		Map<String, String> params=new HashMap<String, String>();
		params.put("key", email);
		params.put("otp", otp);
		HttpEntity<Object> requestEntity=new HttpEntity<Object>(params,headers);
		ResponseEntity<Object> responseBody= restTemplate.exchange(baseUri, HttpMethod.POST, requestEntity, Object.class);
		return responseBody;
	
		
	}
	
	public ResponseEntity<Object> getOtp(String email){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate =new RestTemplate();
		Map<String, String> params=new HashMap<String, String>();
		params.put("key", email);
		HttpEntity<Object> requestEntity=new HttpEntity<Object>(params,headers);
		ResponseEntity<Object> responseBody= restTemplate.exchange(getOtp, HttpMethod.POST, requestEntity, Object.class);
		return responseBody;
	}
}
