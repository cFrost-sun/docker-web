package net.eulerframework.web.config;

import net.eulerframework.common.util.PropertyReader;
import net.eulerframework.common.util.exception.PropertyReadException;

public class SystemProperties {

    private final static PropertyReader properties = new PropertyReader("/system.properties");
    
    public static String frameworkVersion() {
        try {
            return properties.get("version");
        } catch (PropertyReadException e) {
            throw new RuntimeException(e);
        }
    }
}
