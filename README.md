# useSecurity

## Versions
### V1

### V2

### V3
- Global Exception Handler
- Implement refresh token

## TODO
- Review V1-3 and summarise


## QUESTIONS
- implement unit testing with test.json resources
- AOP token parse methods
- printing all filter chains upon setup
- SecurityContextHolder.getContext().getAuthentication() == null?
- authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request))?
- Is Password encoder is always a new instance? What if we use secureRandom
- pokemart/dubstep not used? public Boolean validateToken(String jwtStr, UserDetails userDetails)
- implement authenticationfilter
