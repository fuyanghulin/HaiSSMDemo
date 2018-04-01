package cm.cn.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cm.cn.mapper.HlMessageMapper;
import cm.cn.po.HlMessage;
import cm.cn.service.HlMessageService;
import cm.cn.util.GetCheckCode;

@Service
public class HlMessageServiceImpl implements HlMessageService{
     @Autowired
     private HlMessageMapper hlMessageMapper;
	/**   
	 * Title: sendMessage   
	 * Description:   
	 * @param hlMessage
	 * @return   
	 * @see cm.cn.service.HlMessageService#sendMessage(cm.cn.po.HlMessage)   
	 */
	@Override
	public boolean sendMessage(HlMessage hlMessage) {
		boolean sendFeedback = GetCheckCode.sendMessage(hlMessage.getReceiverContact(), hlMessage.getMessage());
		if(sendFeedback){
			hlMessageMapper.insertSelective(hlMessage);
		}
		return sendFeedback;
	}

}

