package com.ayuconpon.common.aop.multidatasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

@Aspect
@Order(value = 0)
@Slf4j
public class DataSourceAspect {

    @Around("@annotation(com.ayuconpon.common.aop.multidatasource.DataSource)")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("Method \"{}\" annotated with @Datasource start", joinPoint.getSignature().toShortString());

        DataSource dataSource = findDataSourceAnnotation(joinPoint);
        String currentSettingDataSourceName = dataSource.value();
        String currentDataSourceName = DataSourceNameContextHolder.getDataSourceName();

        if (currentSettingDataSourceName != null &&
                !currentSettingDataSourceName.equals(currentDataSourceName)) {
            DataSourceNameContextHolder.setDataSourceName(currentSettingDataSourceName);
            log.debug("'{}' change datasource", currentSettingDataSourceName);
        }

        try {
            return joinPoint.proceed();
        } finally {
            DataSourceNameContextHolder.clear();
            log.debug("Methods \"{}\" annotated with @Datasource finish", joinPoint.getSignature().toShortString());
        }
    }

    private DataSource findDataSourceAnnotation(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        Method method = findExecutedMethod(joinPoint);
        DataSource dataSource = AnnotationUtils.findAnnotation(method, DataSource.class);

        if (dataSource == null) {
            dataSource = AnnotationUtils.findAnnotation(
                    joinPoint.getTarget().getClass(), DataSource.class);
        }

        return dataSource;
    }

    private Method findExecutedMethod(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        if (method.getDeclaringClass().isInterface()) {
            String methodName = methodSignature.getName();
            method = joinPoint.getTarget().getClass()
                    .getDeclaredMethod(methodName, method.getParameterTypes());
        }

        return method;
    }

}
