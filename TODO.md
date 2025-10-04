# TO CODE
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

## V4
- Spring AOP Parse Token
- Spring Annotations for Method Security
- LDAP/SSO
- Implement authentication filter/review authorization filter 
- Implement role and pre-authorize
- Role Heirachy


# TO VIDEO
- [ ] Review https://github.com/code-tofu/archive_useSecurity/tree/main/useSecurityV3
- [ ] Spring boot 3 & Spring security 6 - Roles and Permissions Based Authorization Explained  https://www.youtube.com/watch?v=mq5oUXcAXL4 
- [ ] How to Logout from Spring Security - JWT https://www.youtube.com/watch?v=0GGFZdYe-FY 
- [ ] OAuth2 & Spring boot 3 & Social login | never been easier https://www.youtube.com/watch?v=2WNjmT2z7c4 



