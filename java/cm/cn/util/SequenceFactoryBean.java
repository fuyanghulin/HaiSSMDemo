package cm.cn.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.FactoryBean;

public class SequenceFactoryBean implements FactoryBean<String> {
	 
    private static long counter = 0;
     
    public synchronized String getObject() throws Exception {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + " + ";
        String sequ = new DecimalFormat("00000000").format(counter ++);
        return date + sequ;
    }
 
    public Class<String> getObjectType() {
        return String.class;
    }
 
    public boolean isSingleton() {
        return false;
    }
 
    public static void reset() {
        SequenceFactoryBean.counter = 0;
    }
    public static void main(String[] args) {
    	SequenceFactoryBean sequenceFactoryBean = new SequenceFactoryBean();
    	try {
			System.out.println(sequenceFactoryBean.getObject());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
