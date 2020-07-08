package io.app.login.serviceimpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.app.login.dto.LoginResponseDto;
import io.app.login.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
	@Value("${io.login.baseUri}")
	private String baseUri;
	
	/* (non-Javadoc)
	 * @see io.app.login.serviceimpl.LoginService#validateUser(java.lang.String, java.lang.String)
	 */
	@Override
	public ResponseEntity<Object> validateUser(String email,String otp) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		LoginResponseDto loginResponseDto=new LoginResponseDto();
		RestTemplate restTemplate =new RestTemplate();
		Map<String, String> params=new HashMap<String, String>();
		params.put("key", email);
		params.put("otp", otp);
		HttpEntity<Object> requestEntity=new HttpEntity<Object>(params,headers);
		ResponseEntity<Object> responseBody= restTemplate.exchange(baseUri, HttpMethod.POST, requestEntity, Object.class);
		return responseBody;
	
		
	}
}
