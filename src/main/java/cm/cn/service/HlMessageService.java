package cm.cn.service;

import cm.cn.po.HlMessage;

public interface HlMessageService {
	//用于发送短信通知
    public boolean sendMessage(HlMessage hlMessage);
}

