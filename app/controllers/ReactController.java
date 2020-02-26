package controllers;

import play.api.mvc.AbstractController;
import play.api.mvc.Action;
import play.api.mvc.AnyContent;
import play.api.mvc.ControllerComponents;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ReactController extends AbstractController {
    private Assets assets;

    @Inject
    public ReactController(Assets assets, ControllerComponents controllerComponents) {
        super(controllerComponents);
        this.assets = assets;
    }

    public Action<AnyContent> index() {
        return assets.at("index.html");
    }

    public Action<AnyContent> assetOrDefault(String res) {
        return assets.at(res);
    }
}
