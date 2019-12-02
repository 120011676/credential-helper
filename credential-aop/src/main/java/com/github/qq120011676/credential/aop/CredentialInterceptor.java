package com.github.qq120011676.credential.aop;

import com.github.qq120011676.credential.aop.properties.CredentialAOPProperties;
import com.github.qq120011676.credential.client.CredentialClient;
import com.github.qq120011676.ladybird.web.conntroller.ControllerHelper;
import com.github.qq120011676.ladybird.web.exception.RestfulExceptionHelper;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class CredentialInterceptor implements MethodInterceptor {
    @Resource
    private CredentialAOPProperties credentialAOPProperties;
    @Resource
    private CredentialClient credentialClient;
    @Resource
    private RestfulExceptionHelper restfulExceptionHelper;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        HttpServletRequest request = ControllerHelper.getHttpServletRequest();
        String token = request.getParameter(this.credentialAOPProperties.getParameterName());
        String headerToken = request.getHeader(this.credentialAOPProperties.getHeaderName());
        if (StringUtils.hasText(headerToken)) {
            token = headerToken;
        }
        if (!StringUtils.hasText(token)) {
            throw this.restfulExceptionHelper.getRestfulRuntimeException("credential_token_not_null");
        }
        boolean bol = this.credentialClient.verify(token);
        if (bol) {
            throw this.restfulExceptionHelper.getRestfulRuntimeException("credential_error");
        }
        return invocation.proceed();
    }
}
