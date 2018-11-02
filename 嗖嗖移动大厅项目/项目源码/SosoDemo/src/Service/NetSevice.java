package Service;

import info.MobileCard;

/*
 * 上网（接口）
 * @author 江彬
 */
public interface NetSevice {
	/**
	 * 套餐内上网流量免费
	 * 套餐外每MB0.1元
	 * 余额不足抛出异常提示余额不足
	 * @return 
	 * @throws Exception 
	 */
	public int netPlay(int flow,MobileCard card) throws Exception;
		
}
