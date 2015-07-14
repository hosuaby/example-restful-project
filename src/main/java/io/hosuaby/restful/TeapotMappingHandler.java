package io.hosuaby.restful;

import io.hosuaby.restful.controllers.Mapped;
import io.hosuaby.restful.domain.Teapot;
import io.hosuaby.restful.mappers.TeapotMapper;
import io.hosuaby.restful.mappings.TeapotMapping;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

/**
 * Handler of requests and responses from controller that implicitly converts
 * {@link Teapot} domain objects to {@link TeapotMapping}s and from mappings.
 */
public class TeapotMappingHandler extends
        RequestResponseBodyMethodProcessor {

    /** Teapot mapper */
    private TeapotMapper mapper;

    /**
     * Constructor from mapper object and list of message converters.
     *
     * @param mapper               {@link TeapotMapper} bean
     * @param messageConverters    list of registered message converters
     */
    public TeapotMappingHandler(final TeapotMapper mapper,
            final List<HttpMessageConverter<?>> messageConverters) {
        super(messageConverters);
        this.mapper = mapper;
    }

    /**
     * Constructor from mapper object, list of message converters and content
     * negotiation manager.
     *
     * @param mapper                       {@link TeapotMapper} bean
     * @param messageConverters            list of registered message converters
     * @param contentNegotiationManager    content negotiation manager
     */
    public TeapotMappingHandler(final TeapotMapper mapper,
            final List<HttpMessageConverter<?>> messageConverters,
            final ContentNegotiationManager contentNegotiationManager) {
        super(messageConverters, contentNegotiationManager);
        this.mapper = mapper;
    }

    /**
     * Supports parameters of {@link Teapot} type.
     */
    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Mapped.class);
    }

    /**
     * Handler supports {@link Teapot} return type.
     */
    @Override
    public boolean supportsReturnType(final MethodParameter returnType) {
        return returnType.getMethodAnnotation(Mapped.class) != null;
    }

    /**
     * Converts {@link TeapotMapping} to {@link Teapot} domain object. Apply
     * binding on {@link Teapot}.
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        /* Read mapping from request */
        TeapotMapping mapping = (TeapotMapping) readWithMessageConverters(
                webRequest, parameter, TeapotMapping.class);
        String name = Conventions.getVariableNameForParameter(parameter);

        /* Convert mapping to domain object */
        Teapot teapot = mapper.fromMapping(mapping);

        WebDataBinder binder = binderFactory
                .createBinder(webRequest, teapot, name);

        if (teapot != null) {
            validateIfApplicable(binder, parameter);
            if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, parameter)) {
                throw new MethodArgumentNotValidException(parameter, binder.getBindingResult());
            }
        }

        mavContainer.addAttribute(BindingResult.MODEL_KEY_PREFIX + name, binder.getBindingResult());

        return teapot;
    }

    /**
     * Converts returned {@link Teapot} domain object to {@link TeapotMapping}.
     */
    @Override
    public void handleReturnValue(final Object returnValue,
            final MethodParameter returnType,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest)
                    throws HttpMediaTypeNotAcceptableException, IOException {
        boolean isTeapot = Teapot.class.isAssignableFrom(returnType.getParameterType());
        boolean isTeapotCollection = Collection.class.isAssignableFrom(returnType.getParameterType());

        if (isTeapot) {
            TeapotMapping mapping = mapper.toMapping((Teapot) returnValue);
            super.handleReturnValue(mapping, returnType, mavContainer, webRequest);
        } else if (isTeapotCollection) {
            Collection<TeapotMapping> mappings = mapper.toMappings((Collection<Teapot>) returnValue);
            super.handleReturnValue(mappings, returnType, mavContainer, webRequest);
        }
    }

}
