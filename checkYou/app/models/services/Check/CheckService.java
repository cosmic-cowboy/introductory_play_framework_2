package models.services.Check;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utils.ConfigUtil;
import utils.OptionUtil;
import play.libs.F.Option;

public class CheckService {
	
    /**
     * 診断を行う
     * @param name
     * @return
     */
    public Option<String> getResult(String name) {
    	@SuppressWarnings("serial")
		List<String> defaultList = new ArrayList<String>(){{add("2.1.3 Java");}};
    	List<String> versions = 
    			ConfigUtil.getByList("checkyou.setting.answer")
    			.getOrElse(defaultList);
    	Collections.shuffle(versions);
    	return getResultText(name, versions.get(0));
    }
    
    /**
     * 診断結果
     * @param name
     * @param version
     * @return
     */
    public Option<String> getResultText(String name, String version) {
    	StringBuilder result = new StringBuilder();
    	result.append(name);
    	result.append(ConfigUtil.get("checkyou.setting.message.result").getOrElse("."));
    	result.append(version);
    	result.append(ConfigUtil.get("checkyou.setting.message.resultSuffix").getOrElse("."));
    	return OptionUtil.apply(result.toString());
    }

}
