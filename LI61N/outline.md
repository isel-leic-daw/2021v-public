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
