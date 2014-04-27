import play.GlobalSettings;
import play.api.mvc.EssentialFilter;
import play.filters.csrf.CSRFFilter;


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
}
