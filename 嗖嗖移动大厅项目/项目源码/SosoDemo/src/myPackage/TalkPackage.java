package myPackage;

import info.MobileCard;
import Service.CallService;
import Service.SendService;

/*
 * �����ײ�
 * @author ����
 */
public class TalkPackage extends ServicePackage implements CallService,SendService{
	/*
	 * ͨ��ʱ��500����
	 * ����30��
	 * �ʷ�58
	 */
	public int talkTime=500;//ͨ��ʱ��
	public int smsCount=30;//����
	public int flow=0;//����
	
	public TalkPackage() {
		super();
		super.price=58;
	}
	
	public TalkPackage(int talkTime, int smsCount, int flow) {
		super();
		this.talkTime = talkTime;
		this.smsCount = smsCount;
		this.flow = flow;
	}

	public void showInfo() {
		System.out.println("�����ײͣ�ͨ��ʱ��Ϊ500����/�£���������Ϊ30��/�£��ʷ�Ϊ58.0Ԫ/�¡�");
	}
	public int send(int count, MobileCard card)throws Exception {
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
	public int call(int miniCount, MobileCard card) throws Exception{
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
}
