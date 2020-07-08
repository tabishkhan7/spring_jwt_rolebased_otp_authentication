package io.app.authmanager.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.app.authmanager.dto.AuthManagerRequestDto;
import io.app.authmanager.entity.Role;
import io.app.authmanager.entity.User;
import io.app.authmanager.repository.UserRepository;


@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;

	/* (non-Javadoc)
	 * @see io.app.authmanager.serviceImpl.UserDetailService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Email " + username +" Not Found"));
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getSecretKey(),getAuthorities(user));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(User user) {
		String[] userRoles=user.getRoles().stream().map((role) -> role.getName()).toArray(String[] :: new);
		Collection<GrantedAuthority> authorities=AuthorityUtils.createAuthorityList(userRoles);
		return authorities;
	}
	
	public void saveDetails(AuthManagerRequestDto authManagerRequestDto) {
		List<Role> roles=new ArrayList<Role>();
	
		User user=new User();
		user.setEmail(authManagerRequestDto.getUsername());
		user.setSecretKey(authManagerRequestDto.getOtp());
		for(String rl:authManagerRequestDto.getRole()) {
		Role role=new Role();
		role.setName(rl);
		roles.add(role);
		user.setName(rl);
		}
		user.setRoles(roles);
		if(!userRepository.findByEmail(authManagerRequestDto.getUsername()).isPresent())
			userRepository.save(user);
			
		
	}

}
