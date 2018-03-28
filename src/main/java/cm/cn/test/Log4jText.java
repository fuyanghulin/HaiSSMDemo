package cm.cn.test;

import org.apache.log4j.Logger;  
import org.apache.log4j.PropertyConfigurator;  
  
 
public class Log4jText {  
    private static Logger logger = Logger.getLogger(Log4jText.class);  
    public static void main(String[] args) {  
        //获取log4j配置文件  
        PropertyConfigurator.configure(ClassLoader.getSystemResource("resource/log4j.properties"));  
        logger.error("ERROR!!!!!");//输出error级别的日志  
        logger.debug("DEBUG~");//输出debug级别的日志  
    }  
}  
