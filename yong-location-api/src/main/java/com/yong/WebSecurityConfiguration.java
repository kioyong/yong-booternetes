package com.yong;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .anyRequest().authenticated()
                ).oauth2ResourceServer(oauth2ResourceServer ->
                        oauth2ResourceServer.jwt().jwkSetUri("http://localhost:8087/.well-known/jwks.json")
//                .jwt(jwt ->
//                    jwt.decoder(jwtDecoder())
//                )
        );
    }

//    @Bean
//    JwtDecoder jwtDecoder() {
//        JWSKeySelector<SecurityContext> jwsKeySelector =
//            new SingleKeyJWSKeySelector<>(this.jwsAlgorithm, this.secretKey);
//        DefaultJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
//        jwtProcessor.setJWSKeySelector(jwsKeySelector);
//
//        // Spring Security validates the claim set independent from Nimbus
//        jwtProcessor.setJWTClaimsSetVerifier((claims, context) -> { });

//        SecretKey secretKey = ((SecretKey) (new SecretKeySpec("mcegemsverysecret".getBytes(), "HMACSHA256")));
//        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
//        return new NimbusJwtDecoder(withoutSigning());
}

//    private static JWTProcessor<SecurityContext> withoutSigning() {
//        return new MockJwtProcessor();
//    }
//
//    private static class MockJwtProcessor extends DefaultJWTProcessor<SecurityContext> {
//        @Override
//        public JWTClaimsSet process(SignedJWT signedJWT, SecurityContext context)
//            throws BadJOSEException, JOSEException {
//
//            try {
//                return signedJWT.getJWTClaimsSet();
//            } catch (ParseException e) {
//                throw new BadJWTException(e.getMessage(), e);
//            }
//        }
//    }
//}
