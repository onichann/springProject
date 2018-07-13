import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {

        System.out.println( "aa601243353643409fa74f69fbba73ff".length());
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

    }

    @org.junit.Test
    public void filterTest(){
        List<Person> persons = new ArrayList();
        List<Integer> ids = new ArrayList<>();//用来临时存储person的id
        persons.add(new Person(1, "name1", 10));
        persons.add(new Person(2, "name2", 21));
        persons.add(new Person(5, "name5", 55));
        persons.add(new Person(3, "name3", 34));
        persons.add(new Person(1, "name1", 10));

        List<Person> personList = persons.stream().filter(// 过滤去重
                v -> {
                    boolean flag = !ids.contains(v.getId());
                    if(flag) ids.add(v.getId());
                    return flag;
                }
        ).sorted((o1, o2) ->{return o1.getId()-o2.getId();} ).collect(Collectors.toList());
        System.out.println(personList);
        System.out.println(ids);
    }
}
