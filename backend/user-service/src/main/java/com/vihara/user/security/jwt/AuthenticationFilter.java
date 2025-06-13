    package com.vihara.user.security.jwt;

    import com.vihara.user.security.services.UserDetailsServiceImpl;
    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
    import org.springframework.stereotype.Component;
    import org.springframework.util.StringUtils;
    import org.springframework.web.filter.OncePerRequestFilter;

    import java.io.IOException;

    @Component
    public class AuthenticationFilter extends OncePerRequestFilter {
        private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

        private final TokenProvider tokenProvider;
        private final UserDetailsServiceImpl userDetailsService;

        public AuthenticationFilter(TokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService) {
            this.tokenProvider = jwtTokenProvider;
            this.userDetailsService = userDetailsService;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            try {
                String jwt = parseJwt(request); // Panggil parseJwt untuk mendapatkan token
                logger.info("AuthTokenFilter - Parsed JWT: [{}]", jwt); // LOG: Token yang diterima oleh filter
                
                if (jwt != null && tokenProvider.validateToken(jwt)) {
                    String username = tokenProvider.getUsername(jwt);

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                logger.error("AuthTokenFilter - Cannot set user authentication: {}", e.getMessage());
            }

            filterChain.doFilter(request, response);
        }

        private String parseJwt(HttpServletRequest request) {
            String headerAuth = request.getHeader("Authorization");
            logger.info("AuthTokenFilter - Authorization Header: [{}]", headerAuth); // LOG: Header Authorization lengkap

            if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
                // Pastikan substring ini tidak memotong spasi atau karakter lain secara tidak sengaja
                return headerAuth.substring(7); // Mengambil string setelah "Bearer "
            }

            return null;
        }
    }
    