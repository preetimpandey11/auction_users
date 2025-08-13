/**
 * 
 */
package com.ba.users.controllers;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ba.users.model.LoginRequest;
import com.ba.users.security.CustomUserDetails;
import com.ba.users.security.CustomUserDetailsService;
import com.ba.users.service.TokenServiceFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Preeti Pandey
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AuthTokenControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@BeforeEach
	void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@MockitoBean
	private TokenServiceFactory tokenServiceFactory;

	@MockitoBean
	private CustomUserDetailsService userDetailsService;

	private ObjectMapper mapper = new ObjectMapper();

//	@Test()
	public void test_generateToken() throws Exception {
		LoginRequest request = new LoginRequest("testuser", "{noop}pass");

		Mockito.when(userDetailsService.loadUserByUsername("testuser")).thenReturn(new CustomUserDetails("testuser",
				"{noop}pass", Stream.of("user:read_contact").collect(Collectors.toSet())));
		mvc.perform(post("/tokens").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
				.andExpect(status().isCreated());
	}

}
