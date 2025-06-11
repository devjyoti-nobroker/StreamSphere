package com.nobroker.streamSphere.security;

import com.nobroker.streamSphere.util.UserContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserContext userContext;

    private boolean isPublicRoute(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals("/auth/login") || path.equals("/api/register");
    }

    private boolean isProfileOnlyRoute(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();
        return (path.equals("/api/profiles") && (method.equals("GET") || method.equals("POST"))) || (path.matches("^/api/profiles/\\d+$") && method.equals("GET") || method.equals("DELETE") || method.equals("PUT") || method.equals("POST")   );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        String username = null;
        String jwt = null;
        Long profileId = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
            profileId = jwtUtil.extractProfileId(jwt);

            userContext.setEmail(username);
            userContext.setProfileId(profileId);
        }

        if (!isPublicRoute(request)) {
            if (jwt == null || !jwtUtil.validateToken(jwt)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid JWT");
                return;
            }

            if (!isProfileOnlyRoute(request) && !jwtUtil.validateTokenWithProfile(jwt)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Profile ID required for this route");
                return;
            }
        }


        // Set Spring Security Context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
