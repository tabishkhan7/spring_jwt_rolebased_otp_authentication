package io.app.otpgenerator.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import io.app.otpgenerator.constants.Constants;
import io.app.otpgenerator.dto.OtpGeneratorResponseDto;
import io.app.otpgenerator.dto.OtpRequestdto;
import io.app.otpgenerator.entity.OtpEntity;
import io.app.otpgenerator.exception.OtpException;
import io.app.otpgenerator.repository.OtpRepository;

@Component
public class OtpUtils {
	@Autowired
	private  OtpRepository otpRepository;

	private static SecretKeySpec secretKey;
	private static byte[] key;
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	
	public  OtpGeneratorResponseDto getOtp(OtpRequestdto otpDto) {
		OtpGeneratorResponseDto response = new OtpGeneratorResponseDto();
		OtpEntity otp = new OtpEntity();
		String generatedOtp = "00000";
		otp.setId(otpDto.getKey());
		generatedOtp = generateOtp(otpDto);
		generatedOtp = OtpUtils.encrypt(generatedOtp, otpDto.getKey());
		otp.setValidationRetryCount(0);
		otp.setOtp(generatedOtp);
		otpRepository.save(otp);
		generatedOtp = OtpUtils.decrypt(generatedOtp, otpDto.getKey());
		response.setOtp(generatedOtp);
		response.setStatus("GENERATION_SUCCESSFUL");
		return response;
	}

	public  String generateOtp(OtpRequestdto otpDto) {

		int otp = ThreadLocalRandom.current().nextInt();

		return String.valueOf(Math.abs(otp));
	}

	public static void setKey(String myKey) {
		MessageDigest sha = null;
		try {
			key = myKey.getBytes(Constants.STANDARD_CHARECTERS_SET);
			sha = MessageDigest.getInstance(Constants.ENCRYPTION_ALGO);
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, Constants.ENCRYPTION_STANDARD);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static String encrypt(String strToEncrypt, String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance(Constants.CIPHER_INSTANCE_STANDARD);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			try {
				throw new OtpException(e.getMessage());
			} catch (OtpException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}

	public static String decrypt(String strToDecrypt, String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance(Constants.CIPHER_INSTANCE_STANDARD);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			try {
				throw new OtpException(e.getMessage());
			} catch (OtpException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
		
	}
	public static int timeDifferenceInSeconds(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
		return (int) fromDateTime.until(toDateTime, ChronoUnit.SECONDS);
	}
	
	public  SimpleMailMessage sendEmail(String otp,String recipient) {
		 SimpleMailMessage msg = new SimpleMailMessage();
	        msg.setTo(recipient);

	        msg.setSubject("Authentiation Code");
	        msg.setText("Authorization Code is :: "+otp);
	        return msg;
	        //javaMailSender.send(msg);
	}
	
	

		public static boolean validate(String emailStr) {
		        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		        return matcher.find();
		}
}
