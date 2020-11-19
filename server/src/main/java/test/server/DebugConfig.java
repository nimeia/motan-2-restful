package test.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class DebugConfig {

    @Bean
    public Filter f() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
                System.out.println(httpServletRequest.getRequestURI());

                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        };
    }

}
