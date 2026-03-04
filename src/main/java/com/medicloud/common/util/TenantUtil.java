package com.medicloud.common.util;

import com.medicloud.common.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class TenantUtil {

    /**
     * Reads shopId from request attribute set by JwtAuthFilter.
     * No DB call needed — shopId is extracted from JWT token.
     */
    public Long getCurrentShopId() {
        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            throw new BusinessException("No request context available");
        }
        HttpServletRequest request = attrs.getRequest();
        Object shopId = request.getAttribute("currentShopId");
        if (shopId == null) {
            throw new BusinessException("Shop ID not found in token");
        }
        return (Long) shopId;
    }
}