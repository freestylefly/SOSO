package Service;

import info.MobileCard;

/*
 * �������ӿڣ�
 * @author ����
 */
public interface NetSevice {
	/**
	 * �ײ��������������
	 * �ײ���ÿMB0.1Ԫ
	 * �����׳��쳣��ʾ����
	 * @return 
	 * @throws Exception 
	 */
	public int netPlay(int flow,MobileCard card) throws Exception;
		
}
