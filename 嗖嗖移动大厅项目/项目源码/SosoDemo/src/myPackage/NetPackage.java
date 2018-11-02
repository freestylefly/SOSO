package myPackage;

import info.MobileCard;
import Service.NetSevice;

/*
 * 网虫套餐
 * @author 江彬
 */
public class NetPackage extends ServicePackage implements NetSevice {
	/*
	 * 上网流量3GB(3*1024M) 
	 * 资费68元
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
		System.out.println("网虫套餐：上网流量为3GB/月，资费为68.0元/月。");
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
