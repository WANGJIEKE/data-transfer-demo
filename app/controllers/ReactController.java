package controllers;

import com.typesafe.config.Config;
import play.api.http.HttpErrorHandler;
import play.api.mvc.AbstractController;
import play.api.mvc.Action;
import play.api.mvc.AnyContent;
import play.api.mvc.ControllerComponents;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ReactController extends AbstractController {
    private Assets assets;
    private HttpErrorHandler httpErrorHandler;
    private Config config;

    @Inject
    public ReactController(Assets assets, HttpErrorHandler errorHandler, Config config, ControllerComponents controllerComponents) {
        super(controllerComponents);
        this.assets = assets;
        this.httpErrorHandler = errorHandler;
        this.config = config;
    }

    public Action<AnyContent> index() {
        return assets.at("index.html");
    }

    public Action<AnyContent> assetOrDefault(String res) {
//        System.out.println(res);
//        if (res.startsWith(config.getString("apiPrefix"))) {
//            return Action().async((r) -> httpErrorHandler.onClientError(r, NOT_FOUND(), "Not found"));
//        } else {
//            if (res.contains("."))
//                return assets.at(res);
//            return index();
//        }
        return assets.at(res);
    }
}
