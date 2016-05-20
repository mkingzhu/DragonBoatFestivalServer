package com.mkingzhu.dragon.server.web;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.mkingzhu.dragon.server.web.resources.UserFeedResource;

public class DragonApplication extends Application {
    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());

        router.attach("/users", UserFeedResource.class);

        return router;
    }
}
