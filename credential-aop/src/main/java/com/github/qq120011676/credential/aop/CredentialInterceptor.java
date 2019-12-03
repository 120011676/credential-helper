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
        String token = request.getParameter(this.credentialAOPProperties.getParameterTokenName());
        String headerToken = request.getHeader(this.credentialAOPProperties.getHeaderTokenName());
        if (StringUtils.hasText(headerToken)) {
            token = headerToken;
        }
        String unique = request.getParameter(this.credentialAOPProperties.getParameterUniqueName());
        String headerUnique = request.getHeader(this.credentialAOPProperties.getHeaderUniqueName());
        if (StringUtils.hasText(unique)) {
            unique = headerUnique;
        }
        if (!StringUtils.hasText(token) && !StringUtils.hasText(unique)) {
            throw this.restfulExceptionHelper.getRestfulRuntimeException("credential_not_null");
        }
        if (StringUtils.hasText(token)) {
            boolean bol = this.credentialClient.verify(token);
            if (!bol) {
                throw this.restfulExceptionHelper.getRestfulRuntimeException("credential_token_error");
            }
        } else if (StringUtils.hasText(unique)) {
            boolean bol = this.credentialClient.use(unique);
            if (!bol) {
                throw this.restfulExceptionHelper.getRestfulRuntimeException("credential_unique_error");
            }
        }
        return invocation.proceed();
    }
}
