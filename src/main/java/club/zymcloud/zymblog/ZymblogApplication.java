package club.zymcloud.zymblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = {"classpath:kaptcha.xml"})
public class ZymblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZymblogApplication.class, args);
    }

}
