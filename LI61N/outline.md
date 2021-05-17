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

[Lecture Video (in Portuguese)](https://www.youtube.com/watch?v=zknHDRuc91s&list=PL8XxoCaL3dBiBwOrdGKPJ7SqZwl29xO6K&index=4)

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

[Lecture Video (in Portuguese)](https://www.youtube.com/watch?v=zknHDRuc91s&list=PL8XxoCaL3dBiBwOrdGKPJ7SqZwl29xO6K&index=5)

### 19/04/2021 - Web API design, continued
* Hypermedia, continuation
  * Concept and motivation, revisited
  * [Web Linking](https://tools.ietf.org/html/rfc8288), revisited
  * Hypermedia types, continued
    * [Siren](https://github.com/kevinswiber/siren), revisited
    * [JSON Home](https://mnot.github.io/I-D/json-home/): purpose and motivation
    * Other media types (for reference):
      * [HAL](https://stateless.group/hal_specification.html), [HAL-forms](https://rwcbook.github.io/hal-forms/), [Collection+JSON](http://amundsen.com/media-types/collection/), [JSON API](https://jsonapi.org/), [JSON Patch](http://jsonpatch.com/)
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

[Lecture Video (in Portuguese)](https://www.youtube.com/watch?v=zknHDRuc91s&list=PL8XxoCaL3dBiBwOrdGKPJ7SqZwl29xO6K&index=6)

### 03/05/2021 - Creating a Web UI, introduction
* Rendering approaches for web based UIs, introduction
  * Static Rendering, Server Side Rendering, Client Side Rendering, Hybrid approaches
  * Motivations and tradeoffs
  * ["Rendering on the Web"](https://developers.google.com/web/updates/2019/02/rendering-on-the-web)
* The React framework: Introduction
  * Motivation: creating Single-Page applications (SPA)
  * [Getting started](https://reactjs.org/docs/create-a-new-react-app.html)
  * Bootstrapping a React based frontend
    * [Create React App](https://create-react-app.dev/docs/getting-started/)
    * [TypeScript for JavaScript Programmers](https://www.typescriptlang.org/docs/handbook/typescript-in-5-minutes.html)
    * [TypeScript fpr Java/C# Programmers](https://www.typescriptlang.org/docs/handbook/typescript-in-5-minutes-oop.html)
* React programming model and core concepts:
  * [JSX](https://reactjs.org/docs/introducing-jsx.html) (or [TSX](https://www.typescriptlang.org/docs/handbook/jsx.html))
  * [Elements](https://reactjs.org/docs/rendering-elements.html)
  * [Components and props](https://reactjs.org/docs/components-and-props.html)
* For reference:
  * [Typescript Playground](https://www.typescriptlang.org/play)
  * [Tutorial: Intro to React](https://reactjs.org/tutorial/tutorial.html)
  * [Using React in VS Code](https://code.visualstudio.com/docs/nodejs/reactjs-tutorial)
  * [Semantic UI](https://semantic-ui.com/)
* Additional resources:
  * [Semantic UI React](https://react.semantic-ui.com/usage)
  * [Storybook](https://storybook.js.org/docs/react/get-started/introduction)
  * [Chrome React Dev Tools](https://chrome.google.com/webstore/detail/react-developer-tools/fmkadmapgofadopljbjfkapdkoienihi)

[Lecture Video (in Portuguese)](https://www.youtube.com/watch?v=zknHDRuc91s&list=PL8XxoCaL3dBiBwOrdGKPJ7SqZwl29xO6K&index=7)


### 10/05/2021 - Creating a Web UI with React Hooks
* The React framework, continued
  * React's execution model
    * For stateless components
    * For statefull components, using React Hooks 
    * Rendering, revisited
      * [Reconciliation](https://reactjs.org/docs/reconciliation.html)
  * React Hooks:
    * Purpose and [motivation](https://reactjs.org/docs/hooks-intro.html#motivation)
    * Hooks [useState](https://reactjs.org/docs/hooks-state.html), [useRef](https://reactjs.org/docs/hooks-reference.html#useref), [useEffect](https://reactjs.org/docs/hooks-effect.html) and [useCallback](https://reactjs.org/docs/hooks-reference.html#usecallback)
    * Constraints in the use of Hooks: [Rules of Hooks](https://reactjs.org/docs/hooks-rules.html)
* For reference:
  * [A Complete Guide to useEffect](https://overreacted.io/a-complete-guide-to-useeffect/)
  * [React as a UI Runtime](https://overreacted.io/react-as-a-ui-runtime/)
  * [Fragments](https://reactjs.org/docs/fragments.html)
* Additional resources:
  * [Content on Declarative UI](http://intelligiblebabble.com/content-on-declarative-ui/)

[Lecture Video (in Portuguese)](__coming soon__)

### 17/05/2021 - Creating a Web UI with React Hooks (continued)
* State management in React, continued: revisiting props and state
  * ["Thinking in React"](https://reactjs.org/docs/thinking-in-react.html)
  * ["Lifting State Up](https://reactjs.org/docs/lifting-state-up.html)
  * [Handling user input with controlled components](https://reactjs.org/docs/forms.html#controlled-components)
* Client side routes
  * Concept and motivation
  * Declarative routing in React: [React Router](https://reactrouter.com/web/guides/quick-start)
* Considerations on React application's architecure
  * [Favoring composition](https://reactjs.org/docs/composition-vs-inheritance.html)

[Lecture Video (in Portuguese)](__coming soon__)
