import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  "Application" should {

	  "123 == 123" in {
		  123 must beEqualTo(123);
	  }
	  
	  "123 > 100" in {
		  123 must be_>(100);
	  }
	  
	  "hello contain" in {
		  "Hi! hello everybody" must contain("hello");
	  }
	  
	  "good start" in {
		  "good morning" must startingWith("good");
	  }
	  
  }
}
