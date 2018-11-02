package myPackage;

import info.MobileCard;
import Service.CallService;
import Service.SendService;

/*
 * 话唠套餐
 * @author 江彬
 */
public class TalkPackage extends ServicePackage implements CallService,SendService{
	/*
	 * 通话时长500分钟
	 * 短信30条
	 * 资费58
	 */
	public int talkTime=500;//通话时长
	public int smsCount=30;//短信
	public int flow=0;//上网
	
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
		System.out.println("话唠套餐：通话时长为500分钟/月，短信条数为30条/月，资费为58.0元/月。");
	}
	public int send(int count, MobileCard card)throws Exception {
		int temp=count;
		for(int i=0;i<count;i++){
			if(this.smsCount-card.realSMSCount>=1){
				card.realSMSCount++;
			}else if(card.money>=0.1){
				card.realSMSCount++;
				//账户余额增加0.1元,消费金额也增加0。1
				 card.money-=0.1;
				 card.consumAmount+=0.1;
			}else{
				temp=i;
				throw new Exception("本次发短信"+i+"分钟，您的余额不足，请充值后再使用！");
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
				//账户余额增加0.2元,消费金额也增加0。2
				 card.money-=0.2;
				 card.consumAmount+=0.2;
			}else{
				temp=i;
				throw new Exception("本次通话"+i+"分钟，您的余额不足，请充值后再使用！");
			}
		}
		return temp;
	}
}
