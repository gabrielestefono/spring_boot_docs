# Username/Password Authentication

Uma das formas mais comuns de autenticar um usuário é validando o username e a senha. Spring Security provê um suporta compreensivo para a autenticação com usuário e senha.
Você pode configurar a autenticação username e senha assim: 

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
				.anyRequest().authenticated()
			)
			.httpBasic(Customizer.withDefaults())
			.formLogin(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails userDetails = User.withDefaultPasswordEncoder()
			.username("user")
			.password("password")
			.roles("USER")
			.build();

		return new InMemoryUserDetailsManager(userDetails);
	}

}
```

A configuração anterior registra automaticamente um `UserDetailsService` em memória com o `SecurityFilterChain`, registra o `DaoAuthenticationProvider` com o `AuthenticationManager` padrão e habilita o login via formulário e a autenticação HTTP Basic.


Para aprender mais sobre a autenticação username/password, considere os links a seguir

* Eu quero aprender como o formulário de Login funciona
* Eu quero aprender como a autenticação básica HTTP funciona
* Eu quero aprender como DaoAuthenticationProvider funciona
* Eu quero gerenciar usuários na memória
* Eu quero gerenciar usuários em um banco de dados
* Eu quero gerenciar usuários em LDAP
* Eu quero publicar um bean AuthenticationManager para customizar a autenticação
* Eu quero customizar um global AuthenticationManager

# Publish an AuthenticationManager bean

A fairly common requirement is publishing an AuthenticationManager bean to allow for custom authentication, such as in a @Service or Spring MVC @Controller. For example, you may want to authenticate users via a REST API instead of using Form Login.

You can publish such an AuthenticationManager for custom authentication scenarios using the following configuration:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/login").permitAll()
				.anyRequest().authenticated()
			);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(
			UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(authenticationProvider);
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails userDetails = User.withDefaultPasswordEncoder()
			.username("user")
			.password("password")
			.roles("USER")
			.build();

		return new InMemoryUserDetailsManager(userDetails);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
```

With the preceding configuration in place, you can create a @RestController that uses the AuthenticationManager as follows:

```java
@RestController
public class LoginController {

	private final AuthenticationManager authenticationManager;

	public LoginController(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
		Authentication authenticationRequest =
			UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
		Authentication authenticationResponse =
			this.authenticationManager.authenticate(authenticationRequest);
		// ...
	}

	public record LoginRequest(String username, String password) {
	}

}
```

# Customize the AuthenticationManager

Normally, Spring Security builds an AuthenticationManager internally composed of a DaoAuthenticationProvider for username/password authentication. In certain cases, it may still be desired to customize the instance of AuthenticationManager used by Spring Security. For example, you may need to simply disable credential erasure for cached users.

The recommended way to do this is to simply publish your own AuthenticationManager bean, and Spring Security will use it. You can publish an AuthenticationManager using the following configuration:

Publish AuthenticationManager bean for Spring Security

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/login").permitAll()
				.anyRequest().authenticated()
			)
			.httpBasic(Customizer.withDefaults())
			.formLogin(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(
			UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		ProviderManager providerManager = new ProviderManager(authenticationProvider);
		providerManager.setEraseCredentialsAfterAuthentication(false);

		return providerManager;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails userDetails = User.withDefaultPasswordEncoder()
			.username("user")
			.password("password")
			.roles("USER")
			.build();

		return new InMemoryUserDetailsManager(userDetails);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
```

Alternatively, you can take advantage of the fact that the AuthenticationManagerBuilder used to build Spring Security’s global AuthenticationManager is published as a bean. You can configure the builder as follows:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// ...
		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		// Return a UserDetailsService that caches users
		// ...
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder builder) {
		builder.eraseCredentials(false);
	}

}
```