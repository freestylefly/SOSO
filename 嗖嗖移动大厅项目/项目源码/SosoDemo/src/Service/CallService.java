package Service;

import info.MobileCard;

/*
 * 打电话（接口）
 * @author 江彬
 */
public interface CallService {
	/**
	 * 套餐内通话免费
	 * 套餐外通话每分钟0.2元
	 * 超出电话余额抛出异常提示余额不足
	 * @param miniCount
	 * @param card
	 * @throws Exception 
	 */
	public  int call(int miniCount,MobileCard card) throws Exception;
}
