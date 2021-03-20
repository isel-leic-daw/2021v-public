package pt.isel.daw.spring.demo.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import pt.isel.daw.spring.demo.pipeline.argumentresolvers.ClientIpArgumentResolver
import pt.isel.daw.spring.demo.pipeline.messageconverters.UriToPngMessageConverter

/*
 * Example MVC configuration
 */
// TODO show how to use DI here as well
@Configuration
class MvcConfig : WebMvcConfigurer {

    override fun addArgumentResolvers(resolverList: MutableList<HandlerMethodArgumentResolver>) {
        resolverList.add(ClientIpArgumentResolver())
    }

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        converters.add(UriToPngMessageConverter())
    }
}