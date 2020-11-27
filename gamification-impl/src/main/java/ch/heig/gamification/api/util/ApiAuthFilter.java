package ch.heig.gamification.api.util;

import ch.heig.gamification.entities.ApplicationEntity;
import ch.heig.gamification.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Order(1)
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

        ApplicationEntity appEntity = applicationRepository.findByApiKey(apiKey);

        if (appEntity == null){

            res.sendError(HttpServletResponse.SC_FORBIDDEN, "API-KEY is invalid");
        } else {

            req.setAttribute("appEntity", appEntity);
            chain.doFilter(request, response);

        }

        //chain.doFilter(request, response);
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
