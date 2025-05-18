# TODO

## V0
- [x] Default Spring Security AutoConfig

## V1
- [ ] Follow JWT Implementation from previous projects
	- [ ] Security config for authentication  and authorisation for protected resources
	- [ ] Signup, sign in endpoints and protected endpoints
	- [ ] Simple userDetails and service
	- [ ] Authorities is a string field on userDetails
	- [ ] DAOAuthProvider with Default Encoder
	- [ ] JWT Filter and Services
		- [ ] Token Generation using Role claims
		- [ ] Validate Token based on Signing Key and Expiration Time

## V2
- [ ] Global Exception Handler
- [ ] Add CustomAccessDeniedHandler and CustomAuthenticationEntryPoint
- [ ] Throw exceptions for invalid JWTs instead and use the handler/authentry point
- [ ] Printing all filter chains upon startup for reference




- [ ] Implement refresh token
- [ ] AOP token parse methods
- [ ] Implement authenticationfilter
- [ ] Implement roles and authorities
- [ ] Implement unit testing with test.json resources