package com.github.qq120011676.credential.aop.autoconfigure;

import com.github.qq120011676.credential.aop.CredentialInterceptor;
import com.github.qq120011676.credential.aop.properties.CredentialAOPProperties;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Configuration
@ConditionalOnClass(CredentialInterceptor.class)
@EnableConfigurationProperties(CredentialAOPProperties.class)
public class CredentialAOPAutoconfigure {
    @Resource
    private CredentialAOPProperties credentialAOPProperties;
    @Resource
    private CredentialInterceptor credentialInterceptor;

    @Bean
    public AspectJExpressionPointcutAdvisor aspectJExpressionPointcutAdvisor() {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        if (StringUtils.hasText(this.credentialAOPProperties.getBefore())) {
            advisor.setExpression(this.credentialAOPProperties.getBefore());
            advisor.setAdvice(this.credentialInterceptor);
        }
        return advisor;
    }
}
