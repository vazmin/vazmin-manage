package com.github.vazmin.manage.simple.mat.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * Created by Chwing on 2019/8/7.
 */
@Aspect
@Configuration
public class ManageTransactionAdviceConfig {

    private static final String AOP_POINTCUT_EXPRESSION =
            "(execution (* com.github.vazmin.manage.component.*.service..*.*(..)))" +
                    " || (execution (* com.github.vazmin.manage.log.context.*.service..*.*(..)))";

    private final TransactionInterceptor platformTxAdvice;

    @Autowired
    public ManageTransactionAdviceConfig(TransactionInterceptor platformTxAdvice) {
        this.platformTxAdvice = platformTxAdvice;
    }

    @Bean
    public Advisor manageTxAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, platformTxAdvice);
    }
}
