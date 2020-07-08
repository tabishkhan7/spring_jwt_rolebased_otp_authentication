package io.app.otpgenerator.serviceImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.app.otpgenerator.dto.OtpValidatorResponseDto;
import io.app.otpgenerator.entity.OtpEntity;
import io.app.otpgenerator.repository.OtpRepository;
import io.app.otpgenerator.service.OtpValidatorService;
import io.app.otpgenerator.utils.OtpUtils;

@Service
public class OtpValidatorServiceImpl implements OtpValidatorService {
	@Value("${otp-generator-maxvalidation}")
	String maxvalidation;
	@Value("${otp-generator-expirytime}")
	String expiryTime;
	
	@Autowired
	private OtpRepository otpRepository;
	
	

	/* (non-Javadoc)
	 * @see io.app.otpgenerator.serviceImpl.OtpValidatorService#validateOtp(java.lang.String, java.lang.String)
	 */
	@Override
	public OtpValidatorResponseDto validateOtp(String key, String otp) {
		OtpValidatorResponseDto otpValidatorResponseDto=new OtpValidatorResponseDto();
		
		Optional<OtpEntity> otpEntity = otpRepository.findById(key);
		if (otpEntity.isPresent()) {
			if (timeDifferenceInSeconds(otpEntity.get().getUpdatedDtimes(),
					LocalDateTime.now(ZoneId.of("UTC"))) < Integer.parseInt(expiryTime) || otpEntity.get().getValidationRetryCount()<Integer.parseInt(maxvalidation)) {
				if (OtpUtils.decrypt(otpEntity.get().getOtp(),key).equals(otp)) {
					otpValidatorResponseDto.setMessage("Validated Successfully");
					otpValidatorResponseDto.setStatus(otpEntity.get().getStatusCode());
					
				}

			} else {
				otpEntity.get().setValidationRetryCount(otpEntity.get().getValidationRetryCount()+1);
				otpValidatorResponseDto.setMessage("Validation Unsuccessfull");
				if(otpEntity.get().getValidationRetryCount()>=Integer.parseInt(maxvalidation))
					otpEntity.get().setStatusCode("EXPIRED");
				otpValidatorResponseDto.setStatus(otpEntity.get().getStatusCode());
				otpRepository.save(otpEntity.get());
			}

		}
		else {
			otpValidatorResponseDto.setMessage("User Id Does Not Exist");
			otpValidatorResponseDto.setStatus("FAILED");
		}
		return otpValidatorResponseDto;
	
	}

	public static int timeDifferenceInSeconds(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
		return (int) fromDateTime.until(toDateTime, ChronoUnit.SECONDS);
	}
}
