package pt.isel.daw.spring.demo.config

import org.springframework.boot.web.server.MimeMappings
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.stereotype.Component
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import pt.isel.daw.spring.demo.pipeline.argumentresolvers.ClientIpArgumentResolver
import pt.isel.daw.spring.demo.pipeline.interceptors.ExampleInterceptor
import pt.isel.daw.spring.demo.pipeline.messageconverters.UriToPngMessageConverter


/*
 * Example MVC configuration
 */
// TODO show how to use DI here as well
@Component
class MvcConfig : WebMvcConfigurer, WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    override fun addArgumentResolvers(resolverList: MutableList<HandlerMethodArgumentResolver>) {
        resolverList.add(ClientIpArgumentResolver())
    }

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        converters.add(UriToPngMessageConverter())
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(ExampleInterceptor())
    }

    // Because the extension to media-type mapping is defined by the servlet
    // See ResourceHttpRequestHandler#getMediaType
    override fun customize(factory: ConfigurableServletWebServerFactory) {
        val mappings = MimeMappings(MimeMappings.DEFAULT)
        mappings.remove("html")
        mappings.add("html", "text/html;charset=utf-8")
        factory.setMimeMappings(mappings)
    }
}