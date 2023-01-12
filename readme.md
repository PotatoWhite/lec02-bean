# Dependency Injection

## DI(Dependency Injection)란?
- 의존성 주입이란, 객체 간의 의존성을 외부에서 주입하는 것을 의미한다.
- 의존성 주입은 객체 간의 결합도를 낮추고 유연성을 높이는 방법 중 하나이다.

# sec01 : Spring bean의 접근 방법 예제

## 1. Spring bean의 접근 방법
- Method Bean의 등록 방법 '@Bean'을 이용 : 'name'이라는 이름으로 등록 type은 String이다.
```java
@Bean
public String name() {
        return "Potato";
}

@Bean
public int age() {
        return 40;
}

```
- name() bean을 호출하는 방법
```java
String name = context.getBean(String.class);
```

## 2. Spring Bean 내부의 DI 동작
- Spring bean은 기본적으로 싱글톤으로 동작한다.

### name()과 age()를 사용하는 Person bean 등록
```java
@Bean
public Person person1() {
    return new Person("carrot", 30);
}

@Bean
public Person person2() {
    return new Person(name(), age());
}

@Bean
public Person person3parameters(String a, int b) {
    return new Person(a, b - 10);
}
```


### person(), person2(), person3parameters()를 호출하는 방법
- person() bean을 호출하는 방법 : 실패!
```java
Person person = context.getBean(Person.class);
```
- 동일한 Type의 bean이 2개 이상 존재하기 때문에, bean의 이름을 명시해야 한다.
```java
Person person1 = context.getBean("person1", Person.class);
Person person2 = context.getBean("person2", Person.class);
Person person3 = context.getBean("person3parameters", Person.class);
```

- person1(), person2()의 경우 argument가 없지만, person3parameters()의 경우 argument가 존재한다.
- person3parameters()의 경우, a, b라는 이름의 argument를 가지고 있다.
- Spring은 bean이 필요하는 argument를 찾아서 자동으로 주입하려 노력한다.
- person3parameters()의 경우, a는 String type이고, String type의 bean이 존재하므로, name() bean을 주입된다.
- b는 int type이고, int type의 bean이 존재하므로, age() bean을 주입된다.


## 동일 Type의 bean이 2개 이상 존재하는 경우
- 동일 Type의 bean이 2개 이상 존재하는 경우, @Qualifier를 이용하여 bean을 구분할 수 있다.
```java
@Bean
public Person person4parameters(@Qualifier("name2") String name, int age) {
    return new Person(name, age - 10);
}

@Bean
public String name2() {
    return "tomato";
}
```
- person4parameters()에서 @Qualifier("name2")를 이용하여 bean을 구분하는 경우, bean의 이름을 명시하지 않으면, 동일 Type의 bean이 2개 이상 존재하기 때문에 Spring은 bean을 지정 할 수 없다.

## 동일 Type의 bean이 2개 이상 존재하는 경우 기본 bean 지정
- 동일 Type의 bean이 2개 이상 존재하는 경우, @Primary를 이용하여 기본 bean을 지정할 수 있다.
```java
@Bean
@Primary
public String name() {
    return "Potato";
}

@Bean
public String name2() {
    return "tomato";
}
```
- name() bean에 @Primary를 지정하면, name() bean이 기본 bean이 된다.
- name() bean이 기본 bean이므로, 동일 Type의 bean이 2개 이상 존재하는 경우, name() bean을 주입한다.

## 동일 Type의 bean이 2개 이상 존재하는 경우, @Qualifier와 @Primary를 동시에 사용하는 경우
- @Qualifier와 @Primary를 동시에 사용하는 경우, @Qualifier가 우선순위가 높다.
```java
@Bean
@Primary
public String name() {
    return "Potato";
}

@Bean
public String name2() {
    return "tomato";
}

@Bean
public Person person4parameters(@Qualifier("name2") String name, int age) {
    return new Person(name, age - 10);
}
```
- person4parameters()에서 @Qualifier("name2")를 이용하여 bean을 구분하는 경우, name2() bean을 주입한다.

## 정리
- @Bean을 이용하면 bean을 생성하여 Spring Container에 등록할 수 있다.
- Spring은 bean이 필요한 argument를 찾아서 자동으로 주입하려 노력한다.
- 동일 Type의 bean이 2개 이상 존재하는 경우, @Qualifier를 이용하여 bean을 구분할 수 있다.
- 동일 Type의 bean이 2개 이상 존재하는 경우, @Primary를 이용하여 기본 bean을 지정할 수 있다.

##  숙제
- Bean Scope?

# sec02. Spring DI
## Spring DI
- Spring DI는 Spring Container가 bean을 생성하고, bean이 필요한 argument를 찾아서 자동으로 주입하려 노력한다.
- Spring DI는 3가지 방법으로 bean을 주입할 수 있다.
- @Autowired : Type을 이용하여 bean을 주입한다.
- @Qualifier : Type과 bean의 이름을 이용하여 bean을 주입한다.

## Class를 이용하여 bean을 생성
- Spring Container에 bean을 등록하는 방법은 2가지가 있다.
- @Component를 이용하여 bean을 등록하는 방법
- @Bean을 이용하여 bean을 등록하는 방법 : sec01

### NameService.java
```java
@Component
public class NameService {
    public String getNameFromDatabase1() {
        return "potato";
    }

    public String getNameFromDatabase2() {
        return "carrot";
    }
}
```
- @Component를 이용하여 bean을 등록한다.
- NameService bean은 NameService class의 Type과 동일하다.

#### AgeService.java
```java
@Component
public class AgeService {
    public int getAgeFromDatabase1() {
        return 30;
    }

    public int getAgeFromDatabase2() {
        return 40;
    }
}
```
- @Component를 이용하여 bean을 등록한다.

### PersonService.java
- NameService와 AgeService를 이용하여 PersonService를 생성한다.
- 이를 @Autowired를 이용하여 bean을 주입한다.
```java
@Service
public class PersonService {
    @Autowired
    private NameService nameService;

    @Autowired
    private AgeService ageService;

    public Person getPerson01() {
        return new Person(nameService.getNameFromDatabase1(), ageService.getAgeFromDatabase1());
    }

    public Person getPerson02() {
        return new Person(nameService.getNameFromDatabase2(), ageService.getAgeFromDatabase2());
    }
}
```
- @Service를 이용하여 bean을 등록한다.

#### @Component와 @Service의 차이
- @Component는 Spring Container에 bean을 등록하는 어노테이션이다.
- @Service는 @Component를 상속받은 어노테이션이다.
- @Service는 @Component와 달리 비즈니스 로직을 담고 있는 bean을 등록할 때 사용한다.

### TestRunner.java
```java
@Component
public class TestRunner implements ApplicationRunner {
    @Autowired
    private PersonService personService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(personService.getPerson01());
        System.out.println(personService.getPerson02());
    }
}
```
- 결과
```shell
Person{name='potato', age=30}
Person{name='carrot', age=40}
```