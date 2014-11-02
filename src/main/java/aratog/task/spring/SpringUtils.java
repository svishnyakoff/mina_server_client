package aratog.task.spring;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringUtils {
    private static SpringUtils ourInstance = new SpringUtils();

    public static SpringUtils getInstance() {
        return ourInstance;
    }

    private final ConfigurableApplicationContext context;

    private SpringUtils() {
        context = new ClassPathXmlApplicationContext("spring-context.xml");
    }

    public <T> T get(Class<T> clas) {
        return context.getBean(clas);
    }
}
