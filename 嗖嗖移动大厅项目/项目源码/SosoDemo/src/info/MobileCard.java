package info;

import myPackage.ServicePackage;

/*
 * ������
 * @author ����
 */
public class MobileCard {
	 public String cardNumble;//����
	 public String uesrName;//����
	 public String passWord;//����
	 public ServicePackage setPackage;//ѡ����ײ�
	 public double consumAmount;//���ѽ��=�ײͷ�+ʵ�����ѽ��
	 public double money;//��ǰ���=��ֵǮ��-consumAmount�����ѽ�
	 public int realTalkTime;//ͨ��ʱ��
	 public int realSMSCount;//��������
	 public int realFlow;//����
	
	 
	 public MobileCard() {
	}
	 

	public MobileCard(String cardNumble, String uesrName, String passWord,
			ServicePackage setPackage, double consumAmount, double money,
			int realTalkTime, int realSMSCount, int realFlow) {
		super();
		this.cardNumble = cardNumble;
		this.uesrName = uesrName;
		this.passWord = passWord;
		this.setPackage = setPackage;
		this.consumAmount = consumAmount;
		this.money = money;
		this.realTalkTime = realTalkTime;
		this.realSMSCount = realSMSCount;
		this.realFlow = realFlow;
	}


	public void showMeg() {
		 System.out.println("���ţ�" + this.cardNumble + "   �û�����" + this.uesrName + "  ��ǰ��" + this.money+"Ԫ");
	 }
	
}
