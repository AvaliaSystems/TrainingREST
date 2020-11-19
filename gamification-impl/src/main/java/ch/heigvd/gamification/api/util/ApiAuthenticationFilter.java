package ch.heigvd.gamification.api.util;

import ch.heigvd.gamification.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Objects;

@Order(1)
@Component
public class ApiAuthenticationFilter implements Filter {

    @Autowired
    ApplicationRepository applicationRepository;

    private static final String HEADER = "X-API-KEY";
    private static final String KEY = "pass1234";

    private final List<String> keys = new ArrayList<>();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String path = req.getRequestURI();
        String contentType = req.getContentType();
        String header = Objects.requireNonNullElse(req.getHeader(HEADER),"") ;
        System.out.println("Request URL path : "+ path +", Request content type: " + contentType + " Request header : " + header);

        // we check if the api-Key is valid

        if (path.startsWith("/applications") && !HttpMethod.POST.matches(req.getMethod()))
        {
            if (!header.equals(KEY)){
                res.setStatus(HttpStatus.UNAUTHORIZED.value());
                // res.getWriter().write("{\"error\":\"You have not the correct API key\" }");
                filterChain.doFilter(req,res);
            }

        }

        filterChain.doFilter(req,servletResponse);
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
