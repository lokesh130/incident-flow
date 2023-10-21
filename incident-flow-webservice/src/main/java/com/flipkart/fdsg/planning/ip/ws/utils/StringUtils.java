package com.flipkart.fdsg.planning.ip.ws.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {
    /**
     * Updates a string with valid bpmn constructs if needed.
     * @param str
     * @return String with a valid bpmn construct.
     */
    public static String checkAndAddBpmnXmlConstructs(String str) {
        if (str.matches("(.*)\\.bpmn20\\.xml"))
            return str;
        return str + ".bpmn20.xml";
    }
}
