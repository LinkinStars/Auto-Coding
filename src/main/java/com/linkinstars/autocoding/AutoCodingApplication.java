package com.linkinstars.autocoding;

import com.linkinstars.autocoding.util.TemplateUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * @author LinkinStar
 */
@SpringBootApplication
public class AutoCodingApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(AutoCodingApplication.class, args);
        TemplateUtil.init();
    }
}
