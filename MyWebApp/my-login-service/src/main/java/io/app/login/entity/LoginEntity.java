package io.app.login.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="login_entity")
public class LoginEntity {
	@Id
	@Column(name="email")
	public String email;
	
	@Column(name = "updated_Dtimes")
	private LocalDateTime updatedDtimes;
	
	@Column(name = "cr_times")
	private LocalDateTime crTimes;

	@Column(name = "del_times")
	private LocalDateTime delTimes;

	@Column(name = "is_deleted")
	private boolean isDeleted;

	@Column(name ="is_authorised")
	private boolean isAuthorised;

}
