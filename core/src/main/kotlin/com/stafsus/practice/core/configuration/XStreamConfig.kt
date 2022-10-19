package com.stafsus.practice.core.configuration

import com.thoughtworks.xstream.XStream
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class XStreamConfig {

    @Bean
    fun xStream(): XStream {
        val xStream = XStream()
        xStream.allowTypesByWildcard(arrayOf("com.stafsus.**"))
        return xStream
    }
}