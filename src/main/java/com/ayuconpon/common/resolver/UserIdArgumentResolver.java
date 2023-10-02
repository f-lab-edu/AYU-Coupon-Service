package com.ayuconpon.common.resolver;

import com.ayuconpon.common.exception.UnauthorizedException;
import com.ayuconpon.common.exception.UserIdFormatException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final String USER_ID = "User-Id";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String userId = request.getHeader(USER_ID);

        if (userId == null) {
            throw new UnauthorizedException();
        }

        try {
            return Long.parseLong(userId);
        } catch (NumberFormatException e) {
            throw new UserIdFormatException();
        }

    }

}
