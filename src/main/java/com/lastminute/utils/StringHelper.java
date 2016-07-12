package com.lastminute.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class StringHelper {
	public static String getMessage(Exception exception) {
		String message=exception.getLocalizedMessage();
		if(message==null) {
      message = "";
			String [] stackFrames=ExceptionUtils.getStackFrames(exception);
			if(stackFrames.length>1) {
				String shortMethod=stackFrames[1];
				shortMethod=shortMethod.replaceAll("[(][^:]*:", ":");
				shortMethod=shortMethod.replaceAll("\\)$", "");
				shortMethod=shortMethod.replaceAll(".*\\.(\\w*\\.\\w*)", "$1");
				message=shortMethod;
				message+=" - ";
			}
			message+=ExceptionUtils.getMessage(exception);
			message=message.trim();
			message=message.replaceAll("[:;.-]*$", "");
		}
		
		String rootCauseMessage=ExceptionUtils.getRootCauseMessage(exception);
		if(rootCauseMessage!=null
				&& !message.replaceAll("[^A-Za-z0-9]", "").contains(rootCauseMessage.replaceAll("[^A-Za-z0-9]", ""))
				&& !rootCauseMessage.replaceAll("[^A-Za-z0-9]", "").contains(message.replaceAll("[^A-Za-z0-9]", ""))) {
			message+="\n"+rootCauseMessage;
		}
		return message;
	}
	
	public static String getShortMessage(Exception exception) {
		return getMessage(exception).replaceAll("(?m)^[ \\t]*at[ \\t].*", "")/*.replaceAll("\n.*", "")*/.trim();	
	}
	   
    public static String prettyPrintHex(byte[] data, long addressOffset) {
        int i, j;
        long lineAddress=addressOffset;
        if (data.length == 0) {
            return "";
        }
     
        StringBuilder sb = new StringBuilder();
     
        //Loop through every input byte
        String hexLine = "";
        String asciiLine = "";
        for (i = 0; i < data.length; i++, lineAddress++) {
             if ((i % 16) == 0) {
                if (i != 0) {
                    sb.append(hexLine);
                    sb.append("\t...\t");
                    sb.append(asciiLine).append("\n");
                }
                asciiLine = "";
                hexLine = String.format("%#08x ", lineAddress).substring(2);
            }
     
            hexLine = hexLine.concat(String.format("%#04x ", data[i]).substring(2));
            if (data[i] > 31 && data[i] < 127) {
                asciiLine = asciiLine.concat(String.valueOf((char) data[i]));
            } else {
                asciiLine = asciiLine.concat(".");
            }
        }
        if (i % 16 > 0) {
            for (j = 0; j < 16 - (i % 16); j++) {
                hexLine = hexLine.concat("   ");
            }
            sb.append(hexLine);
            sb.append("\t...\t");
            sb.append(asciiLine);
        }
        return sb.toString();
    }
    
    public static Map<String, String> stringMap(String ... keyValues) {
    	if(keyValues.length%1==1) {
    		throw new IllegalArgumentException("Expecting an even number of arguments to create a Map");
    	}
    	Map<String, String> stringMap=new HashMap<>();
    	for(int i=0;i<keyValues.length;i+=2) {
    		stringMap.put(keyValues[i], keyValues[i+1]);
    	}
    	return stringMap;
    }
    
  /**
   * Convert a Camel case string to an underscore delimited string.
   * 
   * @param s
   * @return 
   */
	public static String camelCaseToUpperUnderscoreDelimited(String s) {
		return s.replaceAll("([a-z0-9])([A-Z])", "$1_$2").replaceAll("_+", "_").toUpperCase();
	}  
}
