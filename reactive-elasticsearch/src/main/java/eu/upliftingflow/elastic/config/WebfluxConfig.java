package eu.upliftingflow.elastic.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@EnableWebFlux
@ComponentScan("eu.upliftingflow.elastic")
public class WebfluxConfig {
}
