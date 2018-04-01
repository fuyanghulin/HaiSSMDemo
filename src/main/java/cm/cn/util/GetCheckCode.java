package cm.cn.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class GetCheckCode {
	/**
	 * 获取验证码
	 * @param phone
	 */
	//产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAIZvVw1RmuWUOT";
    static final String accessKeySecret = "suSDZj8sM8tdMZfPXYrhK6EGNsBLD7";
    
	/**   
	 * @Title: getCode   
	 * @Description: 通过阿里云发送验证码   
	 * @param: @param phone
	 * @param: @param check_code
	 * @param: @return      
	 * @return: boolean      
	 * @throws   
	 */
	public static boolean getCode(String phone,String check_code){
//		String check_code = String.valueOf((int)((Math.random()*9+1)*100000));
		boolean flag = false;
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		} catch (ClientException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("海梁科技");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_129746717");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
        request.setTemplateParam("{\"code\":\""+check_code+"\"}");
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        try {
        	SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        	 System.out.println("短信接口返回的数据----------------");
             System.out.println("Code=" + sendSmsResponse.getCode());
             System.out.println("Message=" + sendSmsResponse.getMessage());
             System.out.println("RequestId=" + sendSmsResponse.getRequestId());
             System.out.println("BizId=" + sendSmsResponse.getBizId());
        	if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")){
        		System.out.println("正确");
        		flag = true;
            }else{
            	System.out.println("错误");
            }
        }catch (Exception e) {
			e.printStackTrace();
		}
        return flag;
	}
	
	public static boolean sendMessage(String phone,String message){
		
		boolean flag = false;
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		} catch (ClientException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("海梁科技");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_129756899");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
        request.setTemplateParam("{\"message\":\""+message+"\"}");
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        try {
        	SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        	 System.out.println("短信接口返回的数据----------------");
             System.out.println("Code=" + sendSmsResponse.getCode());
             System.out.println("Message=" + sendSmsResponse.getMessage());
             System.out.println("RequestId=" + sendSmsResponse.getRequestId());
             System.out.println("BizId=" + sendSmsResponse.getBizId());
        	if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")){
        		System.out.println("正确");
        		flag = true;
            }else{
            	System.out.println("错误");
            }
        }catch (Exception e) {
			e.printStackTrace();
		}
        return flag;
	}
}
