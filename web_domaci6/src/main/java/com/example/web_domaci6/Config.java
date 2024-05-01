package com.example.web_domaci6;

import com.example.web_domaci6.repo.ICommentRepository;
import com.example.web_domaci6.repo.IPostRepository;
import com.example.web_domaci6.repo.IUserRepository;
import com.example.web_domaci6.repo.impl.CommentRepository;
import com.example.web_domaci6.repo.impl.PostRepository;
import com.example.web_domaci6.repo.impl.UserRepository;
import com.example.web_domaci6.services.PostService;
import com.example.web_domaci6.services.UserService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class Config extends ResourceConfig {

    public Config() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(PostRepository.class).to(IPostRepository.class).in(Singleton.class);
                this.bind(CommentRepository.class).to(ICommentRepository.class).in(Singleton.class);
                this.bind(UserRepository.class).to(IUserRepository.class).in(Singleton.class);
                this.bindAsContract(PostService.class);
                this.bindAsContract(UserService.class);
            }
        };
        register(binder);

        packages("com.example.web_domaci6");
    }
}