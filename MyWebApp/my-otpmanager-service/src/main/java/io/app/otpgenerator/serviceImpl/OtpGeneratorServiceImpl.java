package io.app.otpgenerator.serviceImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.app.otpgenerator.dto.OtpGeneratorResponseDto;
import io.app.otpgenerator.dto.OtpRequestdto;
import io.app.otpgenerator.entity.OtpEntity;
import io.app.otpgenerator.exception.OtpException;
import io.app.otpgenerator.repository.OtpRepository;
import io.app.otpgenerator.service.OtpGeneratorService;
import io.app.otpgenerator.utils.OtpUtils;

@Service
public class OtpGeneratorServiceImpl implements OtpGeneratorService {
	@Value("${otp-generator-maxvalidation}")
	String maxvalidation;
	@Value("${otp-generator-expirytime}")
	String expiryTime;
	@Autowired
	private OtpRepository otpRepository;
	
	@Autowired
	OtpUtils otpUtils;

	/* (non-Javadoc)
	 * @see io.app.otpgenerator.serviceImpl.OtpGeneratorService#getOtp(io.app.otpgenerator.dto.OtpRequestdto)
	 */
	@Override
	public OtpGeneratorResponseDto getOtp(OtpRequestdto otpDto) {
		
		OtpGeneratorResponseDto otpGeneratorResponseDto=new OtpGeneratorResponseDto();
		Optional<OtpEntity> otpEntity = otpRepository.findById(otpDto.getKey());
		if(OtpUtils.validate(otpDto.getKey())) {
		if (otpEntity.isPresent()) {
			if(OtpUtils.timeDifferenceInSeconds(otpEntity.get().getUpdatedDtimes(),
		
				LocalDateTime.now(ZoneId.of("UTC"))) > Integer.parseInt(expiryTime) || otpEntity.get().getValidationRetryCount()>=Integer.parseInt(maxvalidation)) {

	
				otpGeneratorResponseDto=otpUtils.getOtp(otpDto);
				
			}
			else {
				otpGeneratorResponseDto.setOtp(OtpUtils.decrypt(otpEntity.get().getOtp(),otpDto.getKey()));
				otpGeneratorResponseDto.setStatus("GENERATION_SUCCESSFUL");
			}
		} else  {
			otpGeneratorResponseDto=otpUtils.getOtp(otpDto);
			
		}
		} else {
			try {
				otpGeneratorResponseDto.setOtp("Email ID INVALID");
				otpGeneratorResponseDto.setStatus("Generation_FAILED");
				throw new OtpException("Email ID INVALID");
			} catch (OtpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return otpGeneratorResponseDto;
	}

}
