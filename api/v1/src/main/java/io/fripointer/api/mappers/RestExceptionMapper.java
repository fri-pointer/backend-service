package io.fripointer.api.mappers;

import com.mjamsek.rest.exceptions.RestException;
import com.mjamsek.rest.services.LocaleService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@RequestScoped
@Provider
public class RestExceptionMapper implements ExceptionMapper<RestException> {
    
    @Inject
    private LocaleService localeService;
    
    @Context
    private HttpServletRequest request;
    
    @Override
    public Response toResponse(RestException exception) {
        if (exception.getResponse().getParams() != null && exception.getResponse().getParams().length > 0) {
            String message = localeService.getTranslation(
                exception.getResponse().getCode(),
                request.getLocale(),
                exception.getResponse().getParams()
            );
            exception.setMessage(message);
        } else {
            String message = localeService.getTranslation(
                exception.getResponse().getCode(),
                request.getLocale()
            );
            exception.setMessage(message);
        }
        return exception.getResponse().createResponse();
    }
}
