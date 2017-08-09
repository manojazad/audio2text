package ai.observe;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class SpringApplication {

  public static void main( String[] args ) {
    org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
  }
}
