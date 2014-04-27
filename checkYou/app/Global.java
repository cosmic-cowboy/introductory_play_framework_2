import java.lang.reflect.Method;

import play.GlobalSettings;
import play.Logger;
import play.api.mvc.EssentialFilter;
import play.filters.csrf.CSRFFilter;
import play.libs.Json;
import play.mvc.Action;
import play.mvc.Http.Request;


public class Global extends GlobalSettings{
	
	/* (非 Javadoc)
	 * @see play.GlobalSettings#filters()
	 * CSRFフィルター
	 */
	@Override
	public <T extends EssentialFilter> Class<T>[] filters() {
		return new Class[]{
				CSRFFilter.class
		};
	}
	
	@Override
	public Action onRequest(Request request, Method method) {
		Logger.info(Json.toJson(request.headers()).toString());
		return super.onRequest(request, method);
	}
}
