package com.ssafy;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

import javax.servlet.MultipartConfigElement;

import org.kurento.client.KurentoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import com.ssafy.kurento.CallHandler;
import com.ssafy.kurento.RoomManager;
import com.ssafy.kurento.UserRegistry;

@SpringBootApplication
@EnableWebSocket
@Configuration
public class GroupCallApplication implements WebSocketConfigurer{
	
	public static void main(String[] args) throws IOException {
        SpringApplication.run(GroupCallApplication.class, args);
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }
    @Bean
    public UserRegistry registry() {
      return new UserRegistry();
    }

    @Bean
    public RoomManager roomManager() {
      return new RoomManager();
    }

    @Bean
    public CallHandler groupCallHandler() {
      return new CallHandler();
    }

    @Bean
    public KurentoClient kurentoClient() {
      return KurentoClient.create();
    }

    @Bean
    public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
      ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
      container.setMaxTextMessageBufferSize(32768);
      return container;
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
      registry.addHandler(groupCallHandler(), "/groupcall").setAllowedOrigins("*"); // crossorigin?????? ??????
    }
//    //MultipartResolver Bean ??????
//    @Bean
//    public MultipartResolver multipartResolver() {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(2000000000);
//        return multipartResolver;
//    }
//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//    MultipartConfigFactory factory = new MultipartConfigFactory();
//    factory.setMaxFileSize("512MB");
//    factory.setMaxRequestSize("512MB");
//    return factory.createMultipartConfig();
//    }
}
