/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import Service.Codenvy;
import Service.CreateWebdriver;
import Service.PathDriver;
import Utils.Utils;
import com.google.gson.Gson;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.client.RestTemplate;
import restcontroller.TaskController;

@Configuration
@ComponentScans({
    @ComponentScan(basePackages = "Bean")
    ,@ComponentScan(basePackages = "Service")
    ,@ComponentScan(basePackages = "Utils")
    ,@ComponentScan(basePackages = "Entity")
    ,@ComponentScan(basePackages = "restcontroller")
})
public class SpringConfig {

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


 
    @Bean
    public Codenvy codenvy() {
        return new Codenvy();
    }

    @Bean
    public Utils utils() {
        return new Utils();
    }

    @Bean
    public CreateWebdriver createWebdriver() {
        return new CreateWebdriver();
    }

    @Bean
    public PathDriver pathDriver() {
        return new PathDriver();
    }

    @Bean
    public SystemConfig systemConfig() {
        return new SystemConfig();
    }
}
