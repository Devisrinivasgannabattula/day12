package Spring.EmployeeDetails;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context=new ClassPathXmlApplicationContext("Spring/EmployeeDetails/application-context.xml");
        Employee bean=context.getBean("Employee",Employee.class);
        System.out.println(bean);
    }
}
