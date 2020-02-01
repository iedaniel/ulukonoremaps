package ru.ulukomore.maps.ulukomoremaps.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import io.swagger.jaxrs.listing.ApiListingResource;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationInInterceptor;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharingFilter;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.apache.cxf.validation.BeanValidationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.validation.ValidatorFactory;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@ImportResource({"classpath:META-INF/cxf/cxf.xml"})
public class CxfConfiguration {

    @Autowired
    private ApplicationContext ctx;

    @Value("${cxf.log.requests:true}")
    private boolean logRequests;

    @Autowired
    private LoggingInInterceptor loggingInInterceptor;

    @Autowired
    private LoggingOutInterceptor loggingOutInterceptor;

    @Autowired
    ValidatorFactory validatorFactory;

    @Bean
    public BeanValidationProvider beanValidationProvider() {
        return new BeanValidationProvider(validatorFactory);
    }

    @Bean
    public JAXRSBeanValidationInInterceptor jaxrsBeanValidationInInterceptor() {
        JAXRSBeanValidationInInterceptor validationInInterceptor = new JAXRSBeanValidationInInterceptor();
        validationInInterceptor.setProvider(beanValidationProvider());
        return validationInInterceptor;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        CXFServlet servlet = new CXFServlet();
        servlet.setBus(bus());
        ServletRegistrationBean<CXFServlet> servletRegistrationBean = new ServletRegistrationBean<>(servlet, "/api/*");
        servletRegistrationBean.setName("api");
        return servletRegistrationBean;
    }

    @Bean
    public SpringBus bus() {
        return new SpringBus();
    }

    @Bean
    public ApiListingResource apiListingResource() {
        return new ApiListingResource();
    }

    @Bean
    public Server jaxRsServer() {
        List<Object> serviceBeans = new ArrayList<>(ctx.getBeansWithAnnotation(Path.class).values());
        List<String> packs = Collections.singletonList("ru.ulukomore.maps.controller");
        serviceBeans = serviceBeans.stream()
                .filter(o -> packs.contains(o.getClass().getPackage().getName()))
                .collect(Collectors.toList());
        ApiListingResource apiListingResource = ctx.getBean(ApiListingResource.class);
        serviceBeans.add(apiListingResource);
        return getServer(serviceBeans, bus());
    }

    private Server getServer(List<Object> serviceBeans, SpringBus bus) {
        List<Object> providers = new ArrayList<>(ctx.getBeansWithAnnotation(Provider.class).values());
        providers.add(new CrossOriginResourceSharingFilter());
        JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
        factory.setBus(bus);
        factory.setAddress("/");
        factory.setServiceBeans(serviceBeans);
        factory.setProviders(providers);
        Server server = factory.create();

        if (logRequests) {
            server.getEndpoint().getInInterceptors().add(loggingInInterceptor);
            server.getEndpoint().getInInterceptors().add(jaxrsBeanValidationInInterceptor());
            server.getEndpoint().getOutInterceptors().add(loggingOutInterceptor);
            server.getEndpoint().getOutFaultInterceptors().add(loggingOutInterceptor);
        }

        return server;
    }

    @Bean
    @ConditionalOnMissingBean
    public JacksonJsonProvider jsonProvider(ObjectMapper objectMapper) {
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.setMapper(objectMapper);
        return provider;
    }

    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    @Bean
    public JAXBElementProvider jaxbElementProvider() {
        JAXBElementProvider<Object> jaxbElementProvider = new JAXBElementProvider<>();
        Map<String, Object> properties = new HashMap<>();
        properties.put("jaxb.formatted.output", true);
        jaxbElementProvider.setMarshallerProperties(properties);
        return jaxbElementProvider;
    }
}