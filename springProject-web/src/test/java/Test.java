import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {

        System.out.println( "aa601243353643409fa74f69fbba73ff".length());
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

    }
}
