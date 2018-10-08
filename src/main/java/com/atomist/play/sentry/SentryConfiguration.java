package com.atomist.play.sentry;

import io.sentry.Sentry;
import io.sentry.spring.SentryExceptionResolver;
import io.sentry.spring.SentryServletContextInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.GitProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.annotation.PostConstruct;

@Configuration
public class SentryConfiguration {

    @Value("${raven.dsn}")
    private String dsn;

    @Value("${atomist.environment.domain}")
    private String environment;

    @Value("${atomist.environment.pod}")
    private String server;

    private GitProperties gitProperties;
    
    public SentryConfiguration(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Bean
    public HandlerExceptionResolver sentryExceptionResolver() {
        return new SentryExceptionResolver();
    }

    @Bean
    public ServletContextInitializer sentryServletContextInitializer() {
        return new SentryServletContextInitializer();
    }

    @PostConstruct
    public void init() {

        String url = this.gitProperties.get("remote.origin.url");
        String owner = "";
        String repo = "";
        url = url.replace(".git", "");
        if (url.startsWith("git")) {
            int ix = url.lastIndexOf(":");
            String slug = url.substring(ix + 1);
            owner = slug.split("/")[0];
            repo = slug.split("/")[1];
        } else {
            int ix = url.lastIndexOf("/");
            repo = url.substring( ix + 1);
            url = url.substring(0, ix);
            ix = url.lastIndexOf("/");
            owner = url.substring(ix + 1);
        }

        String env = this.environment.replace(":", "_");

        Sentry.init(this.dsn + "?extra=git_sha:" + this.gitProperties.getCommitId()+ ",git_repo:" + repo + ",git_owner:" + owner + ",environment:" + env
                + "&release=" + this.gitProperties.getCommitId() + "&environment=" + this.environment + "&servername=" + this.server);
    }
}
