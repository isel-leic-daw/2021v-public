package pt.isel.daw.spring.demo.pipeline.messageconverters

import net.glxn.qrgen.QRCode
import net.glxn.qrgen.image.ImageType
import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.converter.AbstractGenericHttpMessageConverter
import org.springframework.stereotype.Component
import java.lang.reflect.Type
import java.net.URI

@Component
class UriToPngMessageConverter : AbstractGenericHttpMessageConverter<URI>(MediaType("image","png")) {

    override fun supports(clazz: Class<*>) = URI::class.java.isAssignableFrom(clazz)

    override fun writeInternal(uri: URI, type: Type?, outputMessage: HttpOutputMessage) {
        val stream = QRCode.from(uri.toASCIIString())
                .to(ImageType.PNG)
                .withSize(250, 250)
                .stream()
        stream.writeTo(outputMessage.body)
    }

    override fun canRead(mediaType: MediaType?) = false

    override fun read(type: Type, contextClass: Class<*>?, inputMessage: HttpInputMessage): URI {
        throw UnsupportedOperationException()
    }

    override fun readInternal(clazz: Class<out URI>, inputMessage: HttpInputMessage): URI {
        throw UnsupportedOperationException()
    }

}