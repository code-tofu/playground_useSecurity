### Implementation

1. Include Project dependencies
- Spring
	- spring-boot-starter-security
	- spring-boot-starter-web
- Database Implementation
	- mysql-connector-j
	- spring-boot-starter-data-jpa
- JWT libraries
	- jjwt-api
	- jjwt-impl
	- jjwt-jackson
	- java-jwt
- Others
	- jakarta.json
	- lombok
	- slf4j-simple
	- slf4j-api

2. Create WebSecurityConfig Bean
	- provides beans:
		- PasswordEncoder
		- AuthenticationManager ?
		- SecurityFilterChain
		- DAOAuthenticationProvider (requires UserDetailServiceI and PasswordEncoder) - added to fitlerChain
	- Requires
		- UserDetailsServiceImpl
		- JWTAuthenticationFilter to be added to the filterChain
	- Can configure
		- Session management
		- csrf
		- requestmatching

3. Create JWTAuthenticationFilter
- implement doFilterInternal (OncePerRequestFilter)
	- ExtractToken from auth header
	- Validate Token
	- gets the username from the Token and get userDetails from userDetailsService
	- creates the UsernamePasswordAuthenticationToken to be added to the securityContextHolder.context.Authentication
- requires:
	- UserDetailsService
	- JWTService

4. Create JWTService
	- get authorisation bearer token
	- use the signing key to parse claims
		- check username exists from subject
		- check expiry
		- check other claims
	- create new token
		- set claims
		- set subject
		- set expiration dates set
		- sign with secret keys

5. Create Endpoint AuthController
	- requires passwordEncoder
	- signup
		- check if credentials aready exist
		- save credentials, username and password(encoded)
	- signin
		- create a new UsernamePasswordAuthenticationToken using username and password from request
		- use authenticationManager to authenticate, returning an authentication object
		- able to retreive principal (usually userDetails object
		- respond with JWT containing authority claims of user
		- ? do you need to add to the securityContext?

6. Implement UserDetails/service
	- implement loadUserByUsername
	- difference between Roles vs Authorities
### What Happens
- Sign up creates user details in user repository
- The user may have additional roles/authorities and claims (see userDetails.getAuthorities)

- During authentication, AuthenticationManager performs the authentication of the UsernamePasswordAuthToken which contains the raw username and password (.authenticate)

- AuthenticationManager delegates to ProviderManager and DaoAuthenticationProvider, which gets the stored credentials using UserDetailsService and compares it to the provided password that has been hashed by its password encoder.
- Authentication manager returns an Authentication object if successful. The JWT token is signed with a secret key and returned as a response. UserDetails can be retrieved from the authentication object's principal.

- Client side makes request with the JWT from the sign in response included in the Authorization header. During authorisation, Http request intercepted by the OncePerRequestFilter/ JWTAuthorizationFilter which uses JWT service to parse the provided JWT token with the same secret key.
- If the token is valid the principal from the JWT token is used to get the credentials and authorities from the user details service of the user (token subject) and add it to the security context.
- Spring security uses the authorities of the principal added in the security context to check if the request can access the resource.
- If the token is expired, server will ExpiredException and client has to send a refresh token request
- The Security Context
	- stores details of currently authenticated user (principal) - context.getAuthentication returns the userDetailsObject
	- uses a thread local object to store security context = spring takes care of cleaning and thread local memory leaks. security context is always available to the same thread of execution

- EnableWebSecurity annotation applies the secuirty config to the Global Web Security. WebSecurityConfigurerAdaptor is deprecated in spring 3 onwards
- Authentication entry point commence will trigger an expection every time an authorized user accesses a secured http resources. It rejects every unauthenticated requests and sends a 401?
- Method security is applied to the methods of the controller while Httpsecurity is applied to the URLs
- Authentication manager can throw authenticaion exception, disabled exception, locked exception and bad credentials exception

## Questions
- Should OncePerRequestFilter check if token = user details? username?
- Registered vs recommended claims? private vs public claims?
- SecurityContextHolder.getContext().getAuthentication() == null?
- authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request))?
- Is Password encoder is always a new instance?
-  What if we use secureRandom
- SecurityContextHolder.getContext().getAuthentication() == null?
- authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request))?
- Is Password encoder is always a new instance? What if we use secureRandom
- pokemart/dubstep not used? public Boolean validateToken(String jwtStr, UserDetails userDetails)

