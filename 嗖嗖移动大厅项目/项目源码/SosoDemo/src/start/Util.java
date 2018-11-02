package start;

import info.ConsumInfo;
import info.MobileCard;
import info.Scene;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import Service.CallService;
import Service.NetSevice;
import Service.SendService;

import myPackage.NetPackage;
import myPackage.ServicePackage;
import myPackage.SuperPackage;
import myPackage.TalkPackage;

/*
 * 业务类(工具类）
 * @author 江彬
 */
public class Util {

	private static final ServicePackage TalkPackage = null;
	private static final ServicePackage NetPackage = null;
	private static final ServicePackage SuperPackage = null;
	Scanner input = new Scanner(System.in);
	ServicePackage sp;
	MobileCard card = new MobileCard();
	// map集合储存卡号信息，并通过卡号查到对应的姓名等信息
	Map<String, MobileCard> map = new HashMap<String, MobileCard>();
	// 所有卡号的消费记录列表
	Map<String, List<ConsumInfo>> consumInfo = new HashMap<String, List<ConsumInfo>>();
	// 储存一个卡号的所有消费记录用list集合
	List<ConsumInfo> list = new ArrayList<ConsumInfo>();
	// 储存场景列表
	Map<Integer, Scene> scenes = new HashMap<Integer, Scene>();
	// 构造6个使用场景
	Scene scene0 = new Scene("通话", 90, "问候客户，怎知其如此慢，通话90分钟");
	Scene scene1 = new Scene("通话", 600, "给老婆打电话，问问工作和生活情况，通话600分钟");
	Scene scene2 = new Scene("短信", 7, "给10086发短信查询相关业务，发送短信7条");
	Scene scene3 = new Scene("短信", 40, "给妈妈发短信问最近身体状况，发送短信40条");
	Scene scene4 = new Scene("上网", 2048, "用手机在线观看视频，使用流量2GB");
	Scene scene5 = new Scene("上网", 1024, "和老婆语音童话，通宵一晚，使用流量1GB");

	/**
	 * 用户数据初始化
	 */
	public void initData() {
		// 初始化三张手机卡,并添加进集合
		MobileCard card1 = new MobileCard("13979387057", "江益生", "1234",
				new TalkPackage(), 68, 32, 200, 20, 300);// 话痨套餐
		MobileCard card2 = new MobileCard("13979387058", "江龙美", "12345",
				new NetPackage(), 78, 22, 100, 10, 3000);// 网虫套餐
		MobileCard card3 = new MobileCard("13979387059", "江彬", "123456",
				new SuperPackage(), 88, 12, 200, 20, 500);// 超人套餐
		map.put("13979387057", card1);
		map.put("13979387058", card2);
		map.put("13979387059", card3);
		// 消费记录初始化
		ConsumInfo info = new ConsumInfo("13979387057", "通话", 200);
		list.add(info);
		consumInfo.put("13979387057", list);
	}

	/**
	 * 产生随机的电话号码,返回数组
	 */
	public String[] getNum(int count) {// 传入产生几个随机的电话号码
		String[] nums = new String[9];
		String number = null;
		for (int i = 0; i < count; i++) {// count代表循环count次，产生count个随机号码
			number = "139";// 定义电话号码以139开头
			Random random = new Random();// 定义random，产生随机数
			for (int j = 0; j < 8; j++) {
				// 生成0~9 随机数
				number += random.nextInt(9);
			}
			nums[i] = number;
		}
		return nums;
	}

	/**
	 * 注册
	 */
	public void signIn() {
		System.out.println("*****可选择的卡号*****");
		String[] nums = getNum(9);// 产生9个随机号码
		// 遍历数组
		for (int i = 0; i < nums.length; i++) {
			System.out.print((i + 1) + "、" + nums[i] + "\t");
			// 保证每一行只显示三个号码
			if ((i + 1) % 3 == 0) {
				System.out.println();
			}
		}
		System.out.print("请选择卡号（输入1~9的序号）：");
		int cardChose = input.nextInt();
		if (cardChose < 1 || cardChose > 9) {
			System.out.println("输入错误！");
		} else if (!map.containsKey(nums[cardChose - 1])) {
			card.cardNumble = nums[cardChose - 1];
		} else {
			System.out.println("对不起，此号码已被注册，请重新选择");
		}

		System.out.print("1.话唠套餐  2.网虫套餐  3.超人套餐，  请选择套餐(输入序号)：");
		int packageChose = input.nextInt();
		System.out.print("请输入姓名：");
		card.uesrName = input.next();
		System.out.print("请输入密码：");
		card.passWord = input.next();
		System.out.print("请输入预存话费金额：");
		double pay = input.nextInt();
		switch (packageChose) {
		case 1:
			sp = new TalkPackage();
			break;
		case 2:
			sp = new NetPackage();
			break;
		case 3:
			sp = new SuperPackage();
			break;
		default:
			System.out.println("输入有误！");
			break;
		}
		card.setPackage = sp;
		card.consumAmount = sp.price;
		card.money = pay - card.consumAmount;

		// 判断存入的金额是否够叫月租费，如果不够交要循坏让其充值金额。直到金额符合标准方可
		while (card.money < 0) {
			System.out.print("您预存的话费金额不足以支付本月固定套餐资费，请重新充值：");
			pay = input.nextInt();
			card.money = pay - card.consumAmount;
		}
		// 将信息放入集合
		map.put(card.cardNumble, card);
		if (map.containsKey(card.cardNumble)) {
			card.showMeg();
			sp.showInfo();
			System.out.println("注册成功！");
		} else {
			System.out.println("注册失败，请重新注册！");
		}
	}

	/**
	 * 登陆
	 */
	public void login() {
		System.out.print("请输入手机卡号：");
		String yourNumber = input.next();
		System.out.print("请输入密码：");
		String yourPassWord = input.next();
		if (map.containsKey(yourNumber)) {
			if (map.get(yourNumber).passWord.equals(yourPassWord)) {
				boolean flag = true;// 循环标记
				do {
					System.out.println();
					System.out.println("*****嗖嗖移动用户菜单*****");
					System.out
							.print("1.本月账单查询\n2.套餐余量查询\n3.打印消费详单\n4.套餐变更\n5.办理退网\n请选择(输入1~5选择功能，其他键返回上一级)：");
					int functionChose = input.nextInt();
					switch (functionChose) {
					case 1:
						// 本月账单查询
						showAmountDetail(yourNumber);
						break;
					case 2:
						// 套餐余量查询
						showRemaiDetail(yourNumber);
						break;
					case 3:
						// 打印消费详单
						printAmountDetail(yourNumber);
						break;
					case 4:
						// 套餐变更
						changePackage(yourNumber);
						break;
					case 5:
						// 办理退网
						delateCard(yourNumber);
						break;
					default:
						// 退出二级菜单
						flag = false;
						break;
					}
				} while (flag);

			} else {
				System.out.println("密码输入错误！");
			}

		} else {
			System.out.println("对不起，您输入的信息有误，无法登录！");
		}

	}

	/**
	 * 当月账单查询
	 */
	public void showAmountDetail(String yourNumber) {
		// 本月账单查询
		System.out.println();
		System.out.println("*****本月账单查询******");
		System.out.println("您的卡号：" + yourNumber + "，当月账单：");
		System.out.println("套餐资费：" + sp.price + "元");
		System.out.println("合计：" + dataFormat(map.get(yourNumber).consumAmount)
				+ "元");
		System.out.println("账户余额：" + dataFormat(map.get(yourNumber).money)
				+ "元");
	}

	/**
	 * 套餐余量查询
	 */
	public void showRemaiDetail(String yourNumber) {
		System.out.println();
		System.out.println("*****套餐余量查询******");
		System.out.println("您的卡号：" + yourNumber + "，套餐内剩余：");
		StringBuffer meg = new StringBuffer();
		int remainTalkTime;
		int remainSmsCount;
		int remainFlow;
		if (map.get(yourNumber).setPackage instanceof TalkPackage) {
			TalkPackage cardPackage = (TalkPackage) (map.get(yourNumber).setPackage);
			// 若是话痨套餐，若套餐内通话时间比实际通话时间大。则用套餐内通话时间-实际通话时间。否则返回0.
			remainTalkTime = cardPackage.talkTime > map.get(yourNumber).realTalkTime ? cardPackage.talkTime
					- card.realTalkTime
					: 0;
			meg.append("童话时长：" + remainTalkTime + "分钟\n");
			remainSmsCount = cardPackage.smsCount > map.get(yourNumber).realSMSCount ? cardPackage.smsCount
					- map.get(yourNumber).realSMSCount
					: 0;
			meg.append("短信条数：" + remainSmsCount + "条\n");
		} else if (map.get(yourNumber).setPackage instanceof NetPackage) {
			NetPackage cardPackage = (NetPackage) (map.get(yourNumber).setPackage);
			remainFlow = cardPackage.flow > map.get(yourNumber).realFlow ? cardPackage.flow
					- map.get(yourNumber).realFlow
					: 0;
			meg.append("上网流量:" + remainFlow + "M\n");
		} else if (map.get(yourNumber).setPackage instanceof SuperPackage) {
			SuperPackage cardPackage = (SuperPackage) (map.get(yourNumber).setPackage);
			remainTalkTime = cardPackage.talkTime > map.get(yourNumber).realTalkTime ? cardPackage.talkTime
					- map.get(yourNumber).realTalkTime
					: 0;
			meg.append("童话时长：" + remainTalkTime + "分钟\n");
			remainSmsCount = cardPackage.smsCount > map.get(yourNumber).realSMSCount ? cardPackage.smsCount
					- map.get(yourNumber).realSMSCount
					: 0;
			meg.append("短信条数：" + remainSmsCount + "条\n");
			remainFlow = cardPackage.flow > map.get(yourNumber).realFlow ? cardPackage.flow
					- map.get(yourNumber).realFlow
					: 0;
			meg.append("上网流量:" + remainFlow + "M\n");
		}
		System.out.println(meg);
	}

	/**
	 * 打印消费详单
	 */
	public void printAmountDetail(String yourNumber) {
		Set<String> numbles = consumInfo.keySet();// 获取所有的消费记录map集合中的号码
		// 遍历set集合，判断次卡号是否存在消费记录
		Iterator<String> it = numbles.iterator();
		// 判断消费列表中是否有此卡的消费记录
		boolean isExist = false;// false不存在
		while (it.hasNext()) {
			String numbleKey = it.next();
			if (numbleKey.equals(yourNumber)
					&& consumInfo.get(yourNumber).size() != 0) {
				isExist = true;
			}
		}
		// 如果存在此卡消费记录，则打印，不存在则说明
		if (isExist) {
			StringBuffer brf = new StringBuffer("*******" + yourNumber
					+ "消费记录*******\n");
			brf.append("序号\t类型\t数据（通话（分钟）/上网（M）/短信（条）\n");
			List<ConsumInfo> infos = consumInfo.get(yourNumber);
			// 遍历消费记录列表
			for (int i = 0; i < infos.size(); i++) {
				brf.append((i + 1) + "\t" + infos.get(i).type + "\t"
						+ infos.get(i).consumDate + "\n");
			}
			// 采用字符输出流打印
			Writer os = null;
			BufferedWriter bw = null;
			try {
				os = new FileWriter("消费记录.txt");
				bw = new BufferedWriter(os);
				bw.write(brf.toString());
				bw.flush();
				System.out.println("消费记录打印完毕！");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					bw.close();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("对不起，不存在此卡的消费记录，不能打印！");
		}
	}

	/**
	 * 套餐变更
	 */
	public void changePackage(String yourNumber) {
		boolean isCoreact = true;// 标记，套餐是否正确变更
		System.out.print("1.话唠套餐  2.网虫套餐  3.超人套餐，  请重新选择套餐(输入序号)：");
		int optionrevie = input.nextInt();
		switch (optionrevie) {
		case 1:
			sp = new TalkPackage();
			if (map.get(yourNumber).setPackage instanceof TalkPackage) {
				System.out.println("您已是此套餐，无需变更！");
			} else {
				map.get(yourNumber).setPackage = TalkPackage;
				System.out.println("套餐更改成功！");
			}

			break;
		case 2:
			sp = new NetPackage();
			if (map.get(yourNumber).setPackage instanceof NetPackage) {
				System.out.println("您已是此套餐，无需变更！");
			} else {
				map.get(yourNumber).setPackage = NetPackage;
				System.out.println("套餐更改成功！");
			}
			break;
		case 3:
			sp = new SuperPackage();
			if (map.get(yourNumber).setPackage instanceof SuperPackage) {
				System.out.println("您已是此套餐，无需变更！");
			} else {
				map.get(yourNumber).setPackage = SuperPackage;
				System.out.println("套餐更改成功！");
			}

			break;
		default:
			System.out.println("请输入正确的序号！");
			break;
		}

	}

	/**
	 * 办理退网
	 */
	public void delateCard(String yourNumber) {
		// 加个判断防止用户手误输入错误
		System.out.print("您确定注销此手机号码吗？Y/N:");
		String chouseyes = input.next();
		if (chouseyes.equalsIgnoreCase("Y")) {
			map.remove(yourNumber);
			System.out.println("您已成功注销该手机号！");
		}
	}

	/**
	 * 使用嗖嗖
	 */
	public void uesSoso() {
		// 将初始化的场景放入map集合中，通过手机号键值对应起来
		scenes.put(0, scene0);
		scenes.put(1, scene1);
		scenes.put(2, scene2);
		scenes.put(3, scene3);
		scenes.put(4, scene4);
		scenes.put(5, scene5);

		System.out.print("请输入手机卡号：");
		String yourNumber = input.next();
		System.out.print("请输入密码：");
		String yourPassWord = input.next();
		if (map.containsKey(yourNumber)) {
			if (map.get(yourNumber).passWord.equals(yourPassWord)) {
				card = map.get(yourNumber);
				ServicePackage pack = card.setPackage;
				Random random = new Random();
				int ranNum = 0;
				int temp = 0;// 记录每个场景中的实际消费
				boolean iscontinued = true;// 停下
				do {
					ranNum = random.nextInt(6);// 随机产生0-5的随机数
					Scene scene = scenes.get(ranNum);// 随机产生的一个值对应一个场景scene
					switch (ranNum) {
					case 0:
					case 1:
						// 判断卡所属的套餐是否有打电话的功能
						if (pack instanceof CallService) {
							System.out.println(scene.description);
							try {
								temp = ((CallService) pack).call(scene.data,
										card);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							// 添加一条消费记录
							list.add(new ConsumInfo(yourNumber, scene.type,
									temp));
							iscontinued = false;
							break;

						} else {
							// 如果该卡套餐不支持通话功能，则重新生成随机数选择其他场景
							continue;
						}

					case 2:
					case 3:
						// 判断卡所属的套餐是否有发短信的功能
						if (pack instanceof SendService) {
							System.out.println(scene.description);
							try {
								temp = ((SendService) pack).send(scene.data,
										card);
							} catch (Exception e) {
								e.printStackTrace();
							}
							// 添加一条消费记录
							list.add(new ConsumInfo(yourNumber, scene.type,
									temp));
							iscontinued = false;
							break;
						} else {
							// 如果该卡套餐不支持发短信功能，则重新生成随机数选择其他场景
							continue;
						}
					case 4:
					case 5:
						// 判断卡所属的套餐是否有上网的功能
						if (pack instanceof NetSevice) {
							System.out.println(scene.description);
							try {
								temp = ((NetSevice) pack).netPlay(scene.data,
										card);
							} catch (Exception e) {
								e.printStackTrace();
							}
							// 添加一条消费记录
							list.add(new ConsumInfo(yourNumber, scene.type,
									temp));
							iscontinued = false;
							break;
						} else {
							// 如果该卡套餐不支持上网功能，则重新生成随机数选择其他场景
							continue;
						}
					}
					System.out.println("添加一条消费记录！");
					consumInfo.put(yourNumber, list);
				} while (iscontinued);
			} else {
				System.out.println("密码输入错误！");
			}

		} else {
			System.out.println("对不起，您输入的信息有误，无法登录！");
		}

	}

	/**
	 * 话费充值
	 */
	public void addPayMoney() {
		System.out.print("请输入您要充值的手机号码：");
		String yourNumber = input.next();
		System.out.print("请输入您要充值手机号码的密码：");
		String yourPassWord = input.next();
		if (map.containsKey(yourNumber)) {
			if (map.get(yourNumber).passWord.equals(yourPassWord)) {
				System.out.print("请输入您要充值的金额：");
				double payMoney = input.nextDouble();
				map.get(yourNumber).money += payMoney;
				System.out.println("话费充值成功，您当前话费余额" + map.get(yourNumber).money
						+ "元");
			} else {
				System.out.println("密码输入错误！");
			}
		} else {
			System.out.println("对不起，您输入的信息有误，无法登录！");
		}

	}

	/**
	 * 资费说明
	 */
	public void ziFei() {
		System.out.println();
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			is = new FileInputStream("资费说明.txt");
			isr = new InputStreamReader(is, "GBK");
			br = new BufferedReader(isr);
			String info =null;
			while((info=br.readLine())!=null){
				System.out.println(info);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				br.close();
				isr.close();
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 数学计算科学计数法转换格式(保留两位小数的方法)
	 */
	public String dataFormat(double data) {
		DecimalFormat format = new DecimalFormat("##.00");
		return format.format(data);
	}

	/**
	 * 两位数相减保留一位小数的方法
	 */
	public double sub(double num1, double num2) {
		return (num1 * 10 - num2 * 10) / 10;
	}
}
