package Service;

import info.MobileCard;

/*
 * ��绰���ӿڣ�
 * @author ����
 */
public interface CallService {
	/**
	 * �ײ���ͨ�����
	 * �ײ���ͨ��ÿ����0.2Ԫ
	 * �����绰����׳��쳣��ʾ����
	 * @param miniCount
	 * @param card
	 * @throws Exception 
	 */
	public  int call(int miniCount,MobileCard card) throws Exception;
}
