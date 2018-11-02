package myPackage;

import info.MobileCard;
import Service.CallService;
import Service.NetSevice;
import Service.SendService;

/*
 * �����ײ�
 * @author ����
 */
public class SuperPackage extends ServicePackage implements CallService,SendService,NetSevice{
	/*
	 * ͨ��ʱ��200����
	 * ��������1024MB
	 * ����50��
	 * �ʷ�78Ԫ
	 */
	public int talkTime=200;
	public int smsCount=50;
	public int flow=1024;
	
	public SuperPackage(){
		super();
		super.price=78;
	}
	public SuperPackage(int talkTime, int smsCount, int flow) {
		super();
		this.talkTime = talkTime;
		this.smsCount = smsCount;
		this.flow = flow;
	}


	public void showInfo() {
		System.out.println("�����ײͣ�ͨ��ʱ��Ϊ200����/�£���������Ϊ1GB/��,��������Ϊ50��/�£��ʷ�Ϊ78.0Ԫ/�¡�");
	}
	public int send(int count, MobileCard card) throws Exception{
		int temp=count;
		for(int i=0;i<count;i++){
			if(this.smsCount-card.realSMSCount>=1){
				card.realSMSCount++;
			}else if(card.money>=0.1){
				card.realSMSCount++;
				//�˻��������0.1Ԫ,���ѽ��Ҳ����0��1
				 card.money-=0.1;
				 card.consumAmount+=0.1;
			}else{
				temp=i;
				throw new Exception("���η�����"+i+"���ӣ��������㣬���ֵ����ʹ�ã�");
			}
		}
		return temp;
		
	}

	@Override
	public int call(int miniCount, MobileCard card) throws Exception {
		int temp=miniCount;
		for(int i=0;i<miniCount;i++){
			if(this.talkTime-card.realTalkTime>=1){
				card.realTalkTime++;
			}else if(card.money>=0.2){
				card.realTalkTime++;
				//�˻��������0.2Ԫ,���ѽ��Ҳ����0��2
				 card.money-=0.2;
				 card.consumAmount+=0.2;
			}else{
				temp=i;
				throw new Exception("����ͨ��"+i+"���ӣ��������㣬���ֵ����ʹ�ã�");
			}
		}
		return temp;
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
