package com.goodamcodes.configuration;

import com.goodamcodes.model.NavigationLink;
import com.goodamcodes.repository.NavigationLinkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class NavigationLinksInitializerConfiguration {

    @Bean
    public CommandLineRunner initNavigationLinks(NavigationLinkRepository repository) {
        return args -> {
            List<String> linkNames = List.of(
                    "home", "about",  "skills", "services", "projects",
                    "explore", "contacts", "subskills", "clients"
            );

            for (String name : linkNames) {
                Optional<NavigationLink> existing = repository.findByName(name);
                if (existing.isEmpty()) {
                    NavigationLink link = new NavigationLink();
                    link.setName(name);
                    repository.save(link);
                }
            }
        };
    }
}
