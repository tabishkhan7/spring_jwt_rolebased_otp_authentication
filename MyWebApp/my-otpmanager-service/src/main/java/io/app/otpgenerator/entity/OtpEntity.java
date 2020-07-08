package io.app.otpgenerator.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;


@Entity
@Data
@Table(name = "otp_entity")
public class OtpEntity {
	/**
	 * The variable that holds the unique ID.
	 */
	@Id
	@Column(name = "id")
	private String id;

	/**
	 * The variable that holds the time at which the OTP validation was last
	 * attempted. The default value is the generation time.
	 */
	@Column(name = "updated_Dtimes")
	private LocalDateTime updatedDtimes;

	/**
	 * The variable that holds the generated OTP.
	 */
	@Column(name = "otp")
	private String otp;

	/**
	 * The variable that holds the number of validation attempts.
	 */
	@Column(name = "validation_retry_count")
	private int validationRetryCount;

	/**
	 * The variable that holds the time at which the OTP was generated.
	 */
	@Column(name = "generated_dtimes")
	private LocalDateTime generatedDtimes;

	/**
	 * The variable that holds the status of the OTP.
	 */
	@Column(name = "status_code")
	private String statusCode;

	@Column(name = "ref_id")
	private String refId;

	@Column(name = "ref_id_type")
	private String refIdType;

	@Column(name = "expiry_times")
	private LocalDateTime expiryTimes;

	@Column(name = "lang_code", length = 3)
	private String langCode;

	@Column(name = "created_by", length = 256)
	private String createdBy;

	@Column(name = "updated_by", length = 256)
	private String updatedBy;

	@Column(name = "cr_times")
	private LocalDateTime crTimes;

	@Column(name = "del_times")
	private LocalDateTime delTimes;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	/**
	 * The default constructor for OtpEntity.
	 */
	public OtpEntity() {
		generatedDtimes = LocalDateTime.now(ZoneId.of("UTC"));
		updatedDtimes = generatedDtimes;
		statusCode = "Active";
	}
}
