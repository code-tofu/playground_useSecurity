``` java
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
}
```


``` java
    @GetMapping(path="/admin")
    public ResponseEntity<String> mustBeAdmin() throws Exception {
        return ResponseEntity.ok("ADMIN API ACCESS OK");
    }
                            .requestMatchers("/admin").hasRole(Role.ADMIN.name())

```