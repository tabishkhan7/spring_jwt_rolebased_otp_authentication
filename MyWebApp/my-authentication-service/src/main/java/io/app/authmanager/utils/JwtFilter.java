package io.app.authmanager.utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.app.authmanager.serviceImpl.UserDetailServiceImpl;

@Component
public class JwtFilter extends OncePerRequestFilter {
	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authorizationHeader=request.getHeader("Authorization");
		String username=null;
		if(authorizationHeader !=null) {
			username=jwtUtil.extractUsername(authorizationHeader);
		}
		
		if(username !=null && SecurityContextHolder.getContext().getAuthentication() ==null) {
			UserDetails userDetails=this.userDetailServiceImpl.loadUserByUsername(username);
			if(jwtUtil.validateTaken(authorizationHeader, userDetails)) {
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new 
					UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
