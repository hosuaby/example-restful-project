package io.hosuaby.restful;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * Simple CORS filter that autorizes all origins. Because Undertow don't have
 * one.
 */
@Component
// TODO: check CORS filter of Spring MVC
public class SimpleCorsFilter implements Filter {

    /** {@inheritDoc}} */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    /** {@inheritDoc}} */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        chain.doFilter(request, response);
    }

    /** {@inheritDoc}} */
    @Override
    public void destroy() {}

}
