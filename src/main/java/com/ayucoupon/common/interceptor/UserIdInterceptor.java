package com.ayucoupon.common.interceptor;

import com.ayucoupon.common.exception.RequireRegistrationException;
import com.ayucoupon.common.exception.UnauthorizedException;
import com.ayucoupon.common.exception.UserIdFormatException;
import com.ayucoupon.user.domain.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class UserIdInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;

    private static final String HEADER_USER_ID = "User-Id";
    private static final String USER_ID = "userId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userId = request.getHeader(HEADER_USER_ID);

        if (userId == null) {
            throw new UnauthorizedException();
        }

        Long userIdLong = convertToLong(userId);
        validateRegisteredUser(userIdLong);
        request.setAttribute(USER_ID, userIdLong);
        return true;
    }

    private Long convertToLong(String userId) {
        try {
            return Long.parseLong(userId);
        } catch (NumberFormatException e) {
            throw new UserIdFormatException();
        }
    }

    private void validateRegisteredUser(Long userId) {
        boolean isExist = userRepository.existsById(userId);
        if (!isExist) throw new RequireRegistrationException();
    }

}
