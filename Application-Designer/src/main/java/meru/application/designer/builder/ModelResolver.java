package meru.application.designer.builder;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ModelResolver implements EntityResolver {
	
	
	private static final String APP_HOME = "$APPLICATION_HOME";
    private String appHome;
    private Map<String, String> systemIds;
	
	
	public ModelResolver(File appHome)  {
		this.appHome = appHome.getAbsolutePath().replace('\\', '/');
		systemIds = new HashMap<String,String>(1);
	}
	
	
    public InputSource resolveEntity(String publicId,
                                     String systemId) throws SAXException,
                                                     IOException {

        systemId = URLDecoder.decode(systemId,
                                     "UTF-8");
        
        int index = systemId.indexOf(APP_HOME);
        if (index > 0 ) {
        	index += APP_HOME.length();
        	//systemId = "file:/"+appHome+systemId.substring(index);
        	systemId = "file:"+appHome+systemId.substring(index);
        }
        
        index = systemId.lastIndexOf('/');
        String entityName = systemId;
        if (index > 0) {
            entityName = systemId.substring(index+1);
        }

        String uri = systemIds.get(entityName);
        if (uri == null) {
          systemIds.put(entityName, systemId);
          uri = systemId;
        }
        
        System.out.println("     Resolving " + uri);

        return new InputSource(uri);
    }

}