package com.cly.mineworld.service.aspest;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.cly.mineworld.service.service.RedisService;
import com.cly.mineworld.service.thread.ThreadSysExceptionLog;
import com.cly.mineworld.service.thread.ThreadUserLog;
import com.cly.mineworld.service.vo.common.ResultVo;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

@Aspect
@Component
public class WebLogAspect {
	
	@Autowired
	private RedisService redisService;
	
    @Pointcut("execution(public * com.cly.mineworld.service.controller.*.*(..))")//两个..代表所有子目录，最后括号里的两个..代表所有参数
    public void logPointCut() {}

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        System.out.println("请求地址 : " + request.getRequestURL().toString());
        System.out.println("HTTP METHOD : " + request.getMethod());
        System.out.println("IP : " + request.getRemoteAddr());
        System.out.println("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());
        JSONObject jso = new JSONObject();
        jso.put("controllerName", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        jso.put("controllerParam", Arrays.toString(joinPoint.getArgs()));
        jso.put("ip", request.getRemoteAddr());
        jso.put("userId", "0");
        jso.put("controllerUrl", request.getRequestURL().toString());
        jso.put("messageType", "1");
        ThreadUserLog tul = new ThreadUserLog(jso.toString());
        Thread t = new Thread(tul);
        //t.start();
        System.out.println("参数 : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "logPointCut()")// returning的值和doAfterReturning的参数名一致
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
    	System.out.println("返回值 : " + ret);
    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        /*Token 校验*/
        String token = request.getHeader("token");
        String timestamp = request.getHeader("timestamp");
        System.out.println(token);
        System.out.println(timestamp);
        Object ob = null;
        String classMethodName = pjp.getSignature().getDeclaringTypeName() + "."
        		+ pjp.getSignature().getName();
        System.out.println("环绕：" + classMethodName);
        String authorizationGetTokenClassMethodName = "com.cly.mineworld.service.controller.AuthorizationController.getTokenRequest";//获取Token函数
        if(authorizationGetTokenClassMethodName.equals(classMethodName)) {//不检验用户签名
        	ob = pjp.proceed();// ob 为方法的返回值
        }else if(null == token 
        		|| null == timestamp 
        		|| "".equals(token) 
        		|| "".equals(timestamp) 
        		|| false == checkToken(token)) {
			    	/*Token超时*/
			    	ResultVo vo = new ResultVo();
			    	vo.setResult("-3");
			    	vo.setMessage("Token time over!");
			    	vo.setJsonData(new JSONObject());
			    	ob = vo;
        }else {
        	ob = pjp.proceed();
        }
        //return pjp.proceed();
        return ob;
    }
    
    @AfterThrowing(throwing="ex"  
            , pointcut="execution(* com.cly.mineworld.service.controller.*.*(..))")
    public void doAferThrowing(Exception ex){
    	System.out.println(ex.getMessage());
        String sOut = "";
        StackTraceElement[] trace = ex.getStackTrace();
        for (StackTraceElement s : trace) {
            sOut += "\tat " + s + "\r\n";
        }
        System.out.println("====:" + sOut);
        JSONObject jso = new JSONObject();
        jso.put("exceptionType", "1");//系统异常,2=客户端服务
        jso.put("exceptionContent", sOut);
        ThreadSysExceptionLog tsel = new ThreadSysExceptionLog(jso.toString());
        Thread t = new Thread(tsel);
        t.start();
    }
    
    /**
     * 	校验Token
     * @param token
     * @return
     */
    private boolean checkToken(String token) {
    	boolean result = true;
		Jedis jedis0 = redisService.getJedis();
		jedis0.select(0);//Token库
		if(null == jedis0.get(token)) {
			result = false;
		}
		jedis0.close();
		return result;
    }
}
