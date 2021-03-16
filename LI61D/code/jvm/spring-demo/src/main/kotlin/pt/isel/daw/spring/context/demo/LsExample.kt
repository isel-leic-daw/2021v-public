/*
 * A small example illustrating how a software design similar to the one used in the LS course
 * could take advantage of IoC/DI using the Spring context support
 */
package pt.isel.daw.spring.context.demo

import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.*
import org.springframework.stereotype.Component

class Request
class Response

/*
 * Component interfaces
 */
interface CommandHandler {
    fun handle(request: Request): Response
}
interface DemoDataSource
interface TransactionManager

/*
 * Concrete component types
 */
@Component
class DefaultTransactionManager(private val ds: DemoDataSource) : TransactionManager

@Component
class GetStudents(private val dataSource: DemoDataSource) : CommandHandler {
    override fun handle(request: Request) = Response()
}

@Component
class PostStudents(val transactionManager: TransactionManager) : CommandHandler {
    override fun handle(request: Request) = Response()
}

@Component
class PostClasses(val transactionManager: TransactionManager) : CommandHandler {
    override fun handle(request: Request) = Response()
}

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class Router(val handlers: List<CommandHandler>) {
    fun route(request: Request): CommandHandler {
        throw NotImplementedError()
    }
}

/**
 * Configuration class, used to configure the DI process done by the Spring context
 * - Via annotations, namely the [@ComponentScan] that enables the scan of @Component annotated classes
 * on the class path.
 * - Via [@Bean] annotated methods that define "recipies" for bean creation.
 */
@Configuration
@ComponentScan
class MyConfig {

    @Bean
    open fun createDataSource() : DemoDataSource {
        // lets pretend a good data source is created and initialized
        return object : DemoDataSource{}
    }
}


fun main() {
    println("Hello Spring Context Example")
    val context = AnnotationConfigApplicationContext(
        MyConfig::class.java,

        /* Without component scan
        Router::class.java,
        DefaultTransactionManager::class.java,

        GetStudents::class.java,
        PostStudents::class.java,
        PostClasses::class.java
         */
    )

    val router: Router = context.getBean(Router::class.java)
    println("Got router instance with ${router.handlers.size} handlers")

    val anotherRouter: Router = context.getBean(Router::class.java)
    println("Are both router references pointing to the same object? ${router === anotherRouter}")

    context.beanDefinitionNames.forEach {
        val def = context.getBeanDefinition(it)
        println("Bean def: name='$it', class='${def.beanClassName}', scope='${def.scope}'")
    }
}
