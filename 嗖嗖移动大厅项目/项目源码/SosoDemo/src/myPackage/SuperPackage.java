package myPackage;

import info.MobileCard;
import Service.CallService;
import Service.NetSevice;
import Service.SendService;

/*
 * 超人套餐
 * @author 江彬
 */
public class SuperPackage extends ServicePackage implements CallService,SendService,NetSevice{
	/*
	 * 通话时长200分钟
	 * 上网流量1024MB
	 * 短信50条
	 * 资费78元
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
		System.out.println("超人套餐：通话时长为200分钟/月，上网流量为1GB/月,短信条数为50条/月，资费为78.0元/月。");
	}
	public int send(int count, MobileCard card) throws Exception{
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
	public int call(int miniCount, MobileCard card) throws Exception {
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

	@Override
	public int netPlay(int flow, MobileCard card)throws Exception {
		int temp=flow;
		for(int i=0;i<flow;i++){
			if(this.flow-card.realFlow>=1){
				card.realFlow++;
			}else if(card.money>=0.1){
				card.realFlow++;
				//账户余额增加0.1元,消费金额也增加0。1
				 card.money-=0.1;
				 card.consumAmount+=0.1;
			}else{
				temp=i;
				throw new Exception("本次上网"+i+"分钟，您的余额不足，请充值后再使用！");
			}
		}
		return temp;
		
	}

	
	
}
