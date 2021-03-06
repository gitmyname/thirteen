package org.thirteen.authorization.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.thirteen.authorization.interceptor.JwtInterceptor;
import org.thirteen.authorization.redis.service.RedisTokenService;
import org.thirteen.authorization.service.AuthorityService;
import org.thirteen.authorization.service.SysPermissionService;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aaron.Sun
 * @description swagger2配置
 * @date Created in 10:45 2018/1/15
 * @modified by
 */
@EnableSwagger2
@Configuration
public class Swagger2Config extends WebMvcConfigurationSupport {

    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private RedisTokenService redisTokenService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
            .addPathPatterns("/**");
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor(sysPermissionService, authorityService, redisTokenService);
    }

    /**
     * 这个地方要重新注入一下资源文件，不然不会注入资源的，也没有注入requestHandlerMappping,相当于xml配置的
     * <!--swagger资源配置-->
     * <mvc:resources location="classpath:/META-INF/resources/" mapping="swagger-ui.html"/>
     * <mvc:resources location="classpath:/META-INF/resources/webjars/" mapping="/webjars/**"/>
     *
     * @param registry 资源注册处理对象
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 可以注入多个doket，也就是多个版本的api，可以在看到有三个版本groupName不能是重复的，v1和v2是ant风格匹配，配置文件
     * 可通过globalOperationParameters(globalOperationParameters());设置全局header
     *
     * @return doket
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .globalOperationParameters(globalOperationParameters())
            .groupName("v1")
            .select()
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
            .paths(PathSelectors.any())
            .build();
    }

    /**
     * 生成API信息
     * <p>
     * swagger.title=标题
     * swagger.description=描述
     * swagger.version=版本
     * swagger.license=许可证
     * swagger.licenseUrl=许可证URL
     * swagger.termsOfServiceUrl=服务条款URL
     * swagger.contact.name=维护人
     * swagger.contact.url=维护人URL
     * swagger.contact.email=维护人email
     * swagger.base-package=swagger扫描的基础包，默认：全扫描
     * swagger.base-path=需要处理的基础URL规则，默认：/**
     * swagger.exclude-path=需要排除的URL规则，默认：空
     *
     * @return API信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            // 任意，请稍微规范点
            .title("接口列表 v1.1.0")
            // 任意，请稍微规范点
            .description("接口测试")
            // 将“url”换成自己的ip:port
            .termsOfServiceUrl("http://localhost:8769/api-authority/swagger-ui.html")
            // 无所谓（这里是作者的别称）
            .contact(new Contact("thirteen", "http://blog.csdn.net/u014231523", "thirteen@163.com"))
            .version("1.1.0")
            .build();
    }

    /**
     * 全局header
     *
     * @return 全局header
     */
    private List<Parameter> globalOperationParameters() {
        List<Parameter> aParameters = new ArrayList<Parameter>();
        Parameter parameter = new ParameterBuilder()
            // 参数类型支持header, cookie, body, query etc
            .parameterType("query")
            // 参数名
            .name("token")
            // 默认值
            .defaultValue("token")
            .description("query中token字段测试")
            // 指定参数值的类型
            .modelRef(new ModelRef("string"))
            // 非必需，这里是全局配置，然而在登陆的时候是不用验证的
            .required(false).build();
        aParameters.add(parameter);
        return aParameters;
    }

}
