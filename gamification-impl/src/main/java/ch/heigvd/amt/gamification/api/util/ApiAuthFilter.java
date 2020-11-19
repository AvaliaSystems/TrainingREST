package ch.heigvd.amt.gamification.api.util;

import ch.heigvd.amt.gamification.entities.ApplicationEntity;
import ch.heigvd.amt.gamification.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Order(1)
public class ApiAuthFilter implements javax.servlet.Filter {

    @Autowired
    ApplicationRepository applicationRepository;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if(req.getRequestURI().startsWith("/swagger") || req.getRequestURI().startsWith("/v3") || req.getRequestURI().startsWith("/applications")) {
            chain.doFilter(request, response);
        }

        String apiKey = req.getHeader("X-API-KEY");


        ApplicationEntity applicationEntity = applicationRepository.findAllByKey(apiKey);

        try {
            if(applicationEntity != null){
                req.setAttribute("applicationEntity", applicationEntity);
                chain.doFilter(request, response);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


        /*
        if(applicationEntity == null){
            //res.setStatus(403);
            chain.doFilter(request, response);
        }
        else{
            req.setAttribute("applicationEntity", applicationEntity);
            chain.doFilter(request, response);
        }
        */
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
