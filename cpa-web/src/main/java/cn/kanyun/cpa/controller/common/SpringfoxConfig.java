package cn.kanyun.cpa.controller.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 * 访问地址 ip:port/docs.html
 * 需要注意的一点是 swagger api 的默认地址是/v2/api-docs 所以swagger-ui-layer也读取的是默认地址
 * 所以在new Docket()的时候不能指定group参数，否则 swagger api 的地址会在后面加入group的参数导致swagger-ui-layer不能正确请求到数据
 *
 * swagger2常用注解：https://www.cnblogs.com/JealousGirl/p/swagger2.html
 */
@Configuration // 配置注解，自动在本类上下文加载一些环境变量信息
@EnableWebMvc
@EnableSwagger2  //启用swagger2
@ComponentScan(basePackages = {"cn.kanyun.cpa.controller"}) //需要扫描的包路径
public class SpringfoxConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("cn.kanyun.cpa.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 构建 api文档的详细信息函数
     * @return
     */
    private ApiInfo apiInfo() {
        springfox.documentation.service.Contact contact = new springfox.documentation.service.Contact("kanyun","949955482@qq.com","https://kanyun.cn");
        return new ApiInfoBuilder()
                //页面标题
                .title("CPA使用 Swagger2 构建RESTful API")
                .termsOfServiceUrl("http://localhost/")
                .contact(contact)
                //版本号
                .version("1.0")
                //描述
                .description("API 描述")
                .build();
    }
}