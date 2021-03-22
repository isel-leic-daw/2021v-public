# LI61N

Lecturer: Paulo Pereira

## Week 1
### 15/03/2021 - Course introduction
* Syllabus, teaching methodology and bibliography.
  * [Calendar](../docs/calendar.md)
  * [Evaluation](../docs/evaluation.md)
  * [Resources](../docs/resources.md)

### 17/03/2021 - Spring, introduction
* Considerations on the subjective nature of software design
* Dependency Injection (DI): Motivation and consequences
  * Through construction parameters
  * Done explicitely by a wiring up procedure that constructs the solution's object graph 
* Materialization on the [HVAC controller application](demos/hvac)
* DI containers: [Spring](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#spring-core)
  * Motivation
  * Using Spring through [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
  * [Spring Initializr](https://start.spring.io/)
* Refactoring the [HVAC controller application to use Spring](demos/hvac-spring)
* Complementary references, for the inquisitive minds
  * [Software quality](https://en.wikipedia.org/wiki/Software_quality)
    * [Seriously Good Software, by Marco Faella](https://www.manning.com/books/seriously-good-software)
    * Assessment tools (i.e. [SonarQube](https://www.sonarqube.org/))
  * Software design principles, some advocates in no particular order
    * Mark Seeman ([Ploeh Blog](https://blog.ploeh.dk/))
    * Robert Martin (a.k.a Uncle Bob) ([The Clean Code Blog](https://blog.cleancoder.com/))
    * Martin Fowler ([Home page](https://martinfowler.com/))

### 22/03/2021 - Spring and Spring MVC
* Spring
  * Main elements of the programming model 
    * [Application Context](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-basics) and [AnnotationConfigApplicationContext](https://docs.spring.io/spring-framework/docs/5.3.5/javadoc-api/org/springframework/web/context/support/AnnotationConfigWebApplicationContext.html)
    * [Beans](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-definition) and [Bean scopes](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes)
  * [Constructor based dependency injection](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-constructor-injection)
  * [Resolving dependencies through classpath scanning](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-classpath-scanning)
* Demo illustrating the use of `AnnotationConfigApplicationContext` in contrast to `@SpringBootApplication`
  * The commit corresponding to the demo codebase in its final form is [here]() (TODO: commit after lecture)
* Spring MVC, introduction
  * Servlet container architecture (overview): servlets and filters
  * Spring MVC architecture overview ([diagram](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-servlet))
    * [Spring MVC application context](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-servlet-context-hierarchy)
    * [DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-servlet)
    * [MVC Controllers](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-controller)
* Demo with the creation of an HTTP API for the HVAC controller application ([here]()) (TODO: point to the commit)