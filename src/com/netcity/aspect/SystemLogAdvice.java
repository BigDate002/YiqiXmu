package com.netcity.aspect;

import com.alibaba.fastjson.JSON;
import com.netcity.module.entity.SystemLogEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.SystemLogService;
import com.netcity.util.ResponseFlag;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class SystemLogAdvice {
	@Autowired
	SystemLogService logService;

	@Around("@annotation(com.netcity.aspect.SystemLog)")
	public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
		// SystemLogEntity log;
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String requestTime = request.getParameter("requestTime");
		if (StringUtils.isNotEmpty(requestTime)) {
			Session session = SecurityUtils.getSubject().getSession();
			Object sessionTime = session.getAttribute(requestTime);
			if (sessionTime != null) {
				ResponseFlag res = new ResponseFlag();
				res.setFlag(Boolean.valueOf(true));
				res.setMessage("正在操作中请稍候...");
				res.setMsg("正在操作中请稍候...");
				return res;
			}
			session.setAttribute(requestTime, requestTime);
		}

		MethodSignature ms = (MethodSignature) joinPoint.getSignature();
		Method method = ms.getMethod();
		UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
		String action = ((SystemLog) method.<SystemLog>getAnnotation(SystemLog.class)).action();
		Object[] args = joinPoint.getArgs();
		StringBuffer param = new StringBuffer();
		int index = 1;
		if (args.length > 0) {
			byte b;
			int i;
			Object[] arrayOfObject;
			for (i = (arrayOfObject = args).length, b = 0; b < i;) {
				Object arg = arrayOfObject[b];
				param.append("parameter").append(index).append("=");
				index++;
				if (arg instanceof String) {
					param.append(arg.toString());
				} else if (!(args[0] instanceof org.springframework.web.multipart.MultipartFile)) {
					param.append(JSON.toJSONString(arg));
				}
				b++;
			}

		}
		long now = System.currentTimeMillis();
		Object object = null;
		Boolean result = ResponseFlag.Success;
		String error = null;
		try {
			object = joinPoint.proceed();
			if (object instanceof ResponseFlag) {
				ResponseFlag res = (ResponseFlag) object;
				result = res.getFlag();
				if (!result.booleanValue()) {
					error = res.getMessage();
				}
			}
		} catch (Exception e) {
			result = ResponseFlag.Failed;
			error = e.getMessage();
			throw e;
		} finally {
			now = System.currentTimeMillis() - now;
			SystemLogEntity systemLogEntity = new SystemLogEntity();
			systemLogEntity.setAction(action);
			systemLogEntity.setResult(result);
			systemLogEntity.setExecuteMillis(Long.valueOf(now));
			systemLogEntity.setError(error);
			systemLogEntity.setUsercode(user.getUsercode());
			systemLogEntity.setParam(param.toString());
			this.logService.insert(systemLogEntity);
		}
		return object;
	}
}