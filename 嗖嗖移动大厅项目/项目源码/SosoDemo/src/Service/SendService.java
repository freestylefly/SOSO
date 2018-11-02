package Service;

import info.MobileCard;

public interface SendService {
	/**
	 * 套餐内短信免费
	 * 套餐外短信每条1毛
	 * 余额不足抛出异常
	 * @return 
	 */
	public  int send(int count,MobileCard card)throws Exception;
}
