package ch.heig.gamification.api.util;

import ch.heig.gamification.entities.ApplicationEntity;
import ch.heig.gamification.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ApiAuthFilter implements Filter {

    @Autowired
    ApplicationRepository applicationRepository;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        //Fetch API-Key
        String apiKey = req.getHeader("X-API-KEY");
        boolean errorFlag = false;

        System.out.println(apiKey);


        if(apiKey != null) {
            ApplicationEntity appEntity = applicationRepository.findByApiKey(UUID.fromString(apiKey));
            if (appEntity != null) {
                req.setAttribute("appEntity", appEntity);
                chain.doFilter(request, response);
            } else {
                errorFlag = true;
            }
        } else {
            errorFlag = true;
        }
        if(errorFlag) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "API-KEY is invalid");
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Bean
    public FilterRegistrationBean<ApiAuthFilter> urlFilter() {
        FilterRegistrationBean<ApiAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ApiAuthFilter());
        registrationBean.addUrlPatterns("/scoreScales/*"); // badge ?
        return registrationBean;
    }
}
