package myPackage;

import info.MobileCard;
import Service.NetSevice;

/*
 * �����ײ�
 * @author ����
 */
public class NetPackage extends ServicePackage implements NetSevice {
	/*
	 * ��������3GB(3*1024M) 
	 * �ʷ�68Ԫ
	 */
	public int talkTime = 0;
	public int smsCount = 0;
	public int flow=3072;
	
	
	public NetPackage() {
		super();
		super.price=68;
	}
	
	public NetPackage(int talkTime, int smsCount, int flow) {
		super();
		this.talkTime = talkTime;
		this.smsCount = smsCount;
		this.flow = flow;
	}

	public void showInfo() {
		System.out.println("�����ײͣ���������Ϊ3GB/�£��ʷ�Ϊ68.0Ԫ/�¡�");
	}


	@Override
	public int netPlay(int flow, MobileCard card)throws Exception {
		int temp=flow;
		for(int i=0;i<flow;i++){
			if(this.flow-card.realFlow>=1){
				card.realFlow++;
			}else if(card.money>=0.1){
				card.realFlow++;
				//�˻��������0.1Ԫ,���ѽ��Ҳ����0��1
				 card.money-=0.1;
				 card.consumAmount+=0.1;
			}else{
				temp=i;
				throw new Exception("��������"+i+"���ӣ��������㣬���ֵ����ʹ�ã�");
			}
		}
		return temp;

	}
}
