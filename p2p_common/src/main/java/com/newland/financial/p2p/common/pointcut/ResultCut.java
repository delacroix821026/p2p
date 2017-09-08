package com.newland.financial.p2p.common.pointcut;

import com.newland.financial.p2p.common.entity.ReturnResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author cengdaijuan
 */
@Aspect
@Component
public class ResultCut {
    /**
     * 测试.
     */
    @Pointcut("execution(* com.newland.financial.p2p.client.controller.*.*(..))")
    private void controllerAspect() {
    }

    /**
     * @param proceedingJoinPoint ProceedingJoinPoint
     * @throws Throwable if has error
     * @return object
     */
    @Around("controllerAspect()")
    public final Object around(final ProceedingJoinPoint proceedingJoinPoint)
            throws Throwable {
        try {
            /*Signature signature =  proceedingJoinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            MethodInvocationProceedingJoinPoint
            Class returnType = methodSignature.getReturnType();
            System.out.println("signature:::::::::::::::::::::::::::::::::::"
            + signature.getClass());*/
            return new ReturnResult(proceedingJoinPoint.proceed());
        } catch (Exception ex) {
            StringBuffer stackflow = null;
            StringWriter sw = null;
            PrintWriter pw = null;
            try {
                sw = new StringWriter();
                pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                stackflow = sw.getBuffer();
            } finally {
                try {
                    if (sw != null) {
                        sw.close();
                    }
                } finally {
                    try {
                        if (pw != null) {
                            pw.close();
                        }
                    } finally {
                        return new ReturnResult(ReturnResult.ERROR,
                                ex.getMessage(), stackflow);
                    }
                }

            }
        }
    }

    /*@AfterThrowing("controllerAspect()", throwing="ex")
    public void AfterThrowing(DataAccessException ex) throws Throwable {
        ex.getMessage();
    }*/

    /*@After("controllerAspect()")
    public void after() {
        System.out.println("结束1");
    }*/

    /*@AfterReturning(pointcut = "controllerAspect()", returning="result")
    public void after(JoinPoint joinpoint, Object result) throws Throwable{
        if(result != null) {
            result = new ReturnResult(result);
            //return new ReturnResult(result);
        }
        //return null;
    }*/
}
