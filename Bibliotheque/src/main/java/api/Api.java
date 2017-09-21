package api;

import java.util.HashSet;
import java.util.Set;

//import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

public class Api extends Application {
	public Set<Class<?>> getClasses() {
        Set<Class<?>> routes = new HashSet<Class<?>>();
        routes.add(api.Auteur.class);
        routes.add(api.Auteurs.class);
        return routes;
    }
	

}