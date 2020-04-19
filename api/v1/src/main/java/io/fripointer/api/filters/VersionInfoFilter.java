package io.fripointer.api.filters;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.mjamsek.rest.common.HttpHeaders;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VersionInfoFilter implements Filter {
    
    private String serviceName;
    private String serviceVersion;
    private String serviceEnv;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ConfigurationUtil configUtil = ConfigurationUtil.getInstance();
        serviceName = configUtil.get("kumuluzee.name").orElseThrow();
        serviceVersion = configUtil.get("kumuluzee.version").orElseThrow();
        serviceEnv = configUtil.get("kumuluzee.env.name").orElseThrow();
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (response instanceof HttpServletResponse) {
            HttpServletResponse resp = (HttpServletResponse) response;
            
            resp.addHeader(HttpHeaders.X_SERVICE_NAME, serviceName);
            resp.addHeader(HttpHeaders.X_SERVICE_VERSION, serviceVersion);
            resp.addHeader("X-Service-Env", serviceEnv);
    
            chain.doFilter(request, resp);
        } else {
            chain.doFilter(request, response);
        }
    }
    
    @Override
    public void destroy() {
    
    }
}
