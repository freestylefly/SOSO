package info;

import myPackage.ServicePackage;

/*
 * 卡号类
 * @author 江彬
 */
public class MobileCard {
	 public String cardNumble;//卡号
	 public String uesrName;//姓名
	 public String passWord;//密码
	 public ServicePackage setPackage;//选择的套餐
	 public double consumAmount;//消费金额=套餐费+实际消费金额
	 public double money;//当前余额=充值钱数-consumAmount（消费金额）
	 public int realTalkTime;//通话时长
	 public int realSMSCount;//短信条数
	 public int realFlow;//流量
	
	 
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
		 System.out.println("卡号：" + this.cardNumble + "   用户名：" + this.uesrName + "  当前余额：" + this.money+"元");
	 }
	
}
