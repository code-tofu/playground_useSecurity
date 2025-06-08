# TODO

- [x] Default Spring Security AutoConfig
- [ ] Follow JWT Implementation from previous projects
	- [x] Security config for authentication and authorisation for protected resources
	- [x] Signup, sign in endpoints and protected endpoints
	- [x] Simple userDetails and service
	- [x] Authorities is a string field on userDetails
	- [x] DAOAuthProvider with Default Encoder
	- [x] JWT Filter and Services
		- [x] Token Generation using Authority claims
		- [x] Validate Token based on Signing Key and Expiration Time
- [x] Global Exception Handler
- [x] Add CustomAccessDeniedHandler and CustomAuthenticationEntryPoint
- [x] Throw exceptions for invalid JWTs instead and use the handler/authentry point
- [x] Printing all filter chains upon startup for reference

- [x] Implement Role Based Authorisation
- [x] Implement refresh token
- [ ] Add authorisation endpoints based on role rather than authorities
- [ ] AOP token parse methods
- [ ] Implement authenticationfilter?
- [ ] Implement unit testing with test.json resources