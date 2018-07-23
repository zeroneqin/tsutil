/* Copyright(c), Qin Jun, All right serverd. */
package com.qinjun.autotest.tsutil.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLUtil {
    private final static Logger logger = LoggerFactory.getLogger(XMLUtil.class);

    public static String beanToXML(Object object) {
        XStream xStream = new XStream();
        xStream.processAnnotations(object.getClass());
        return xStream.toXML(object);
    }

    public static <T> T xmlToBean(String xml, Class<T> classT) {
        XStream xStream = new XStream(new DomDriver());
        xStream.processAnnotations(classT);
        T object = (T) xStream.fromXML(xml);
        return object;
    }
}
