# LI61N

Lecturer: Paulo Pereira

## Week 1
### 15/03/2021 - Course introduction
* Syllabus, teaching methodology and bibliography.
  * [Calendar](../docs/calendar.md)
  * [Evaluation](../docs/evaluation.md)
  * [Resources](../docs/resources.md)

[Lecture Video (in Portuguese)](https://www.youtube.com/watch?v=67QZBwLS5tE&list=PL8XxoCaL3dBiBwOrdGKPJ7SqZwl29xO6K&index=1)

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

[Lecture Video (in Portuguese)](https://www.youtube.com/watch?v=fgEyCBWTM9k&list=PL8XxoCaL3dBiBwOrdGKPJ7SqZwl29xO6K&index=2)

### 22/03/2021 - Spring and Spring MVC
* Spring
  * Main elements of the programming model 
    * [Application Context](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-basics) and [AnnotationConfigApplicationContext](https://docs.spring.io/spring-framework/docs/5.3.5/javadoc-api/org/springframework/web/context/support/AnnotationConfigWebApplicationContext.html)
    * [Beans](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-definition) and [Bean scopes](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes)
  * [Constructor based dependency injection](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-constructor-injection)
  * [Resolving dependencies through classpath scanning](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-classpath-scanning)
* Demo illustrating the use of `AnnotationConfigApplicationContext` in contrast to `@SpringBootApplication`
  * The demo codebase in its final form is [here](https://github.com/isel-leic-daw/2021v-public/tree/main/LI61N/demos/hvac-spring)
* Spring MVC, introduction
  * Servlet container architecture (overview): servlets and filters
  * Spring MVC architecture overview ([diagram](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-servlet))
    * [Spring MVC application context](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-servlet-context-hierarchy)
    * [DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-servlet)
    * [MVC Controllers](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-controller)
* Demo with the creation of an HTTP API for the HVAC controller application ([here](https://github.com/isel-leic-daw/2021v-public/tree/7901b94efec84bd66587bf6ab40fa538e1b4be81/LI61N/demos/hvac-spring-mvc))

### 22/03/2021 - Spring and Spring MVC
* Spring
  * Main elements of the programming model 
    * [Application Context](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-basics) and [AnnotationConfigApplicationContext](https://docs.spring.io/spring-framework/docs/5.3.5/javadoc-api/org/springframework/web/context/support/AnnotationConfigWebApplicationContext.html)
    * [Beans](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-definition) and [Bean scopes](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes)
  * [Constructor based dependency injection](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-constructor-injection)
  * [Resolving dependencies through classpath scanning](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-classpath-scanning)
* Demo illustrating the use of `AnnotationConfigApplicationContext` in contrast to `@SpringBootApplication`
  * The demo codebase in its final form is [here](https://github.com/isel-leic-daw/2021v-public/tree/main/LI61N/demos/hvac-spring)
* Spring MVC, introduction
  * Servlet container architecture (overview): servlets and filters
  * Spring MVC architecture overview ([diagram](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-servlet))
    * [Spring MVC application context](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-servlet-context-hierarchy)
    * [DispatcherServlet](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-servlet)
    * [MVC Controllers](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-controller)
* Demo with the creation of an HTTP API for the HVAC controller application ([here](https://github.com/isel-leic-daw/2021v-public/tree/7901b94efec84bd66587bf6ab40fa538e1b4be81/LI61N/demos/hvac-spring-mvc))

[Lecture Video (in Portuguese)](https://www.youtube.com/watch?v=zknHDRuc91s&list=PL8XxoCaL3dBiBwOrdGKPJ7SqZwl29xO6K&index=3)

### 07/04/2021 - Web API design, introduction
* Web APIs (or HTTP APIs): Concept and Motivation
* Distributed systems' basic concepts
  * ["Fallacies of distributed computing"](https://www.researchgate.net/publication/322500050_Fallacies_of_Distributed_Computing_Explained)
  * Failure model: nodes and links failures
    * Detection and recovery
  * Replication: purpose and consequences
    * Data and service replication
* The [Architecture of the World Wide Web](https://www.w3.org/TR/webarch/)
  * Resources: identification (URI), interactions (links) and representations (external data formats)
* The HTTP protocol
  * Request-response message exchange semantics and pattern
  * Participants in HTTP based systems: client (e.g. user agent), server and proxies
  * Request methods [semantics](https://tools.ietf.org/html/rfc7231#section-4.3) and [properties](https://tools.ietf.org/html/rfc7231#section-4.2)
  * ["HTTP Method selection"](https://github.com/isel-leic-daw/1819v-public/wiki/HTTP-method-selection)
* Documentation:
  * ["Introduction to Web APIs"](https://github.com/isel-leic-daw/1819v-public/wiki/Web-APIs)
  * ["Designing evolvable Web APIs: Chapter 1"](https://www.oreilly.com/library/view/designing-evolvable-web/9781449337919/ch01.html)
  * ["Designing evolvable Web APIs: Chapter 2"](https://www.oreilly.com/library/view/designing-evolvable-web/9781449337919/ch02.html)
* For reference:
  * [HTTP protocol](https://tools.ietf.org/html/rfc7230)

### 12/04/2021 - Web API design, continued
* The HTTP protocol, continued
  * Response status code [semantics](https://tools.ietf.org/html/rfc7231#section-6)
* Error representation with [Problem-Json](https://tools.ietf.org/html/rfc7807)
* Leveraging HTTP as an application level protocol
  * [Content negotiation](https://tools.ietf.org/html/rfc7231#section-5.3)
  * [HTTP Authentication](https://tools.ietf.org/html/rfc7235)
* Hypermedia, introduction
  * "Hypermedia is defined by the presence of application control information embedded within, or as a layer above, the presentation of information.", in [Principle Design of the Modern Web Architecture](https://www.ics.uci.edu/~taylor/documents/2002-REST-TOIT.pdf) 
* The Spring MVC framework, revisited:
  * [Handler methods, argument binding and message conversion](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-methods)
  * [Exception Handling](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-exceptionhandler)
* Hypermedia types, introduction
  * Purpose and motivation
  * An example: [Siren](https://github.com/kevinswiber/siren)
* Demo: Moving towards an Hypermedia HVAC Web API
* Documentation:
  * ["How to Think About HTTP Status Codes"](https://www.mnot.net/blog/2017/05/11/status_codes)
  * ["How to fail in HTTP APIs"](https://github.com/isel-leic-daw/1819v-public/wiki/How-to-fail-in-HTTP-APIs)
  * ["Designing evolvable Web APIs: Appendice C"](https://www.oreilly.com/library/view/designing-evolvable-web/9781449337919/apc.html)
* For reference:
  * [HTTP protocol](https://tools.ietf.org/html/rfc7230)
  * [HTTP Status Code registry](http://www.iana.org/assignments/http-status-codes/http-status-codes.xhtml)
  * [IANA Media Types Registry](https://www.iana.org/assignments/media-types/media-types.xhtml)
  * ["REST"](https://www.ics.uci.edu/~fielding/pubs/dissertation/rest_arch_style.htm)

### 19/04/2021 - Web API design, continued
* Hypermedia, continuation
  * Concept and motivation, revisited
  * [Web Linking](https://tools.ietf.org/html/rfc8288), revisited
  * Hypermedia types, continued
    * [Siren](https://github.com/kevinswiber/siren), revisited
    * [JSON Home](https://mnot.github.io/I-D/json-home/): purpose and motivation
    * Other media types (for reference):
      * [HAL](https://tools.ietf.org/html/draft-kelly-json-hal-08), [HAL-forms](https://rwcbook.github.io/hal-forms/), [Collection+JSON](http://amundsen.com/media-types/collection/), [JSON API](https://jsonapi.org/), [JSON Patch](http://jsonpatch.com/)
* The HTTP Protocol, revisited
  * [Allow header](https://tools.ietf.org/html/rfc7231#section-7.4.1)
  * HTTP Caching
    * Headers [Cache-Control](https://tools.ietf.org/html/rfc7234#section-5.2), [ETag](https://tools.ietf.org/html/rfc7232#section-2.3), [Vary](https://tools.ietf.org/html/rfc7231#section-7.1.4)
    * Conditional requests: headers [If-None-Match](https://tools.ietf.org/html/rfc7232#section-3.2) e [If-Modified-Since](https://tools.ietf.org/html/rfc7232#section-3.3)
* Documentation:
  * ["Designing evolvable Web APIs: Chapter 6"](https://www.oreilly.com/library/view/designing-evolvable-web/9781449337919/ch06.html)
  * ["Designing evolvable Web APIs: Chapter 8"](https://www.oreilly.com/library/view/designing-evolvable-web/9781449337919/ch08.html)
  * ["REST APIs must be hypertext-driven"](https://roy.gbiv.com/untangled/2008/rest-apis-must-be-hypertext-driven)
* For reference:
  * [IANA Link Relations Registry](https://www.iana.org/assignments/link-relations/link-relations.xhtml)
  * [HTTP Caching](https://tools.ietf.org/html/rfc7234)
  * [HTTP Conditional Requests](https://tools.ietf.org/html/rfc7232)
