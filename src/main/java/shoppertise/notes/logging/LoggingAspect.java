package shoppertise.notes.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut(value = "execution(* shoppertise.notes.*.*.*(..) )")
    public void pointCutLogging() {
        /** No implementation required **/
    }

    @Around("pointCutLogging()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
        var objMapper = new ObjectMapper();
        objMapper.findAndRegisterModules();
        objMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        
        var methodName = pjp.getSignature().getName();
        var className = pjp.getTarget().getClass().getTypeName();

        Object[] array = pjp.getArgs();

        /** Logging happen when method is called **/
        LOGGER.info("Request  -> {}.{}() : parameters -> {}", className, methodName, objMapper.writeValueAsString(array));
        
        var object = pjp.proceed();

        /** Logging happen when method is done **/
        LOGGER.info("Response -> {}.{}() : return -> {}", className, methodName, objMapper.writeValueAsString(object));

        return object;
    }
}
