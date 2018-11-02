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
 * ҵ����(�����ࣩ
 * @author ����
 */
public class Util {

	private static final ServicePackage TalkPackage = null;
	private static final ServicePackage NetPackage = null;
	private static final ServicePackage SuperPackage = null;
	Scanner input = new Scanner(System.in);
	ServicePackage sp;
	MobileCard card = new MobileCard();
	// map���ϴ��濨����Ϣ����ͨ�����Ų鵽��Ӧ����������Ϣ
	Map<String, MobileCard> map = new HashMap<String, MobileCard>();
	// ���п��ŵ����Ѽ�¼�б�
	Map<String, List<ConsumInfo>> consumInfo = new HashMap<String, List<ConsumInfo>>();
	// ����һ�����ŵ��������Ѽ�¼��list����
	List<ConsumInfo> list = new ArrayList<ConsumInfo>();
	// ���泡���б�
	Map<Integer, Scene> scenes = new HashMap<Integer, Scene>();
	// ����6��ʹ�ó���
	Scene scene0 = new Scene("ͨ��", 90, "�ʺ�ͻ�����֪���������ͨ��90����");
	Scene scene1 = new Scene("ͨ��", 600, "�����Ŵ�绰�����ʹ��������������ͨ��600����");
	Scene scene2 = new Scene("����", 7, "��10086�����Ų�ѯ���ҵ�񣬷��Ͷ���7��");
	Scene scene3 = new Scene("����", 40, "�����跢�������������״�������Ͷ���40��");
	Scene scene4 = new Scene("����", 2048, "���ֻ����߹ۿ���Ƶ��ʹ������2GB");
	Scene scene5 = new Scene("����", 1024, "����������ͯ����ͨ��һ��ʹ������1GB");

	/**
	 * �û����ݳ�ʼ��
	 */
	public void initData() {
		// ��ʼ�������ֻ���,����ӽ�����
		MobileCard card1 = new MobileCard("13979387057", "������", "1234",
				new TalkPackage(), 68, 32, 200, 20, 300);// �����ײ�
		MobileCard card2 = new MobileCard("13979387058", "������", "12345",
				new NetPackage(), 78, 22, 100, 10, 3000);// �����ײ�
		MobileCard card3 = new MobileCard("13979387059", "����", "123456",
				new SuperPackage(), 88, 12, 200, 20, 500);// �����ײ�
		map.put("13979387057", card1);
		map.put("13979387058", card2);
		map.put("13979387059", card3);
		// ���Ѽ�¼��ʼ��
		ConsumInfo info = new ConsumInfo("13979387057", "ͨ��", 200);
		list.add(info);
		consumInfo.put("13979387057", list);
	}

	/**
	 * ��������ĵ绰����,��������
	 */
	public String[] getNum(int count) {// ���������������ĵ绰����
		String[] nums = new String[9];
		String number = null;
		for (int i = 0; i < count; i++) {// count����ѭ��count�Σ�����count���������
			number = "139";// ����绰������139��ͷ
			Random random = new Random();// ����random�����������
			for (int j = 0; j < 8; j++) {
				// ����0~9 �����
				number += random.nextInt(9);
			}
			nums[i] = number;
		}
		return nums;
	}

	/**
	 * ע��
	 */
	public void signIn() {
		System.out.println("*****��ѡ��Ŀ���*****");
		String[] nums = getNum(9);// ����9���������
		// ��������
		for (int i = 0; i < nums.length; i++) {
			System.out.print((i + 1) + "��" + nums[i] + "\t");
			// ��֤ÿһ��ֻ��ʾ��������
			if ((i + 1) % 3 == 0) {
				System.out.println();
			}
		}
		System.out.print("��ѡ�񿨺ţ�����1~9����ţ���");
		int cardChose = input.nextInt();
		if (cardChose < 1 || cardChose > 9) {
			System.out.println("�������");
		} else if (!map.containsKey(nums[cardChose - 1])) {
			card.cardNumble = nums[cardChose - 1];
		} else {
			System.out.println("�Բ��𣬴˺����ѱ�ע�ᣬ������ѡ��");
		}

		System.out.print("1.�����ײ�  2.�����ײ�  3.�����ײͣ�  ��ѡ���ײ�(�������)��");
		int packageChose = input.nextInt();
		System.out.print("������������");
		card.uesrName = input.next();
		System.out.print("���������룺");
		card.passWord = input.next();
		System.out.print("������Ԥ�滰�ѽ�");
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
			System.out.println("��������");
			break;
		}
		card.setPackage = sp;
		card.consumAmount = sp.price;
		card.money = pay - card.consumAmount;

		// �жϴ���Ľ���Ƿ񹻽�����ѣ����������Ҫѭ�������ֵ��ֱ�������ϱ�׼����
		while (card.money < 0) {
			System.out.print("��Ԥ��Ļ��ѽ�����֧�����¹̶��ײ��ʷѣ������³�ֵ��");
			pay = input.nextInt();
			card.money = pay - card.consumAmount;
		}
		// ����Ϣ���뼯��
		map.put(card.cardNumble, card);
		if (map.containsKey(card.cardNumble)) {
			card.showMeg();
			sp.showInfo();
			System.out.println("ע��ɹ���");
		} else {
			System.out.println("ע��ʧ�ܣ�������ע�ᣡ");
		}
	}

	/**
	 * ��½
	 */
	public void login() {
		System.out.print("�������ֻ����ţ�");
		String yourNumber = input.next();
		System.out.print("���������룺");
		String yourPassWord = input.next();
		if (map.containsKey(yourNumber)) {
			if (map.get(yourNumber).passWord.equals(yourPassWord)) {
				boolean flag = true;// ѭ�����
				do {
					System.out.println();
					System.out.println("*****���ƶ��û��˵�*****");
					System.out
							.print("1.�����˵���ѯ\n2.�ײ�������ѯ\n3.��ӡ�����굥\n4.�ײͱ��\n5.��������\n��ѡ��(����1~5ѡ���ܣ�������������һ��)��");
					int functionChose = input.nextInt();
					switch (functionChose) {
					case 1:
						// �����˵���ѯ
						showAmountDetail(yourNumber);
						break;
					case 2:
						// �ײ�������ѯ
						showRemaiDetail(yourNumber);
						break;
					case 3:
						// ��ӡ�����굥
						printAmountDetail(yourNumber);
						break;
					case 4:
						// �ײͱ��
						changePackage(yourNumber);
						break;
					case 5:
						// ��������
						delateCard(yourNumber);
						break;
					default:
						// �˳������˵�
						flag = false;
						break;
					}
				} while (flag);

			} else {
				System.out.println("�����������");
			}

		} else {
			System.out.println("�Բ������������Ϣ�����޷���¼��");
		}

	}

	/**
	 * �����˵���ѯ
	 */
	public void showAmountDetail(String yourNumber) {
		// �����˵���ѯ
		System.out.println();
		System.out.println("*****�����˵���ѯ******");
		System.out.println("���Ŀ��ţ�" + yourNumber + "�������˵���");
		System.out.println("�ײ��ʷѣ�" + sp.price + "Ԫ");
		System.out.println("�ϼƣ�" + dataFormat(map.get(yourNumber).consumAmount)
				+ "Ԫ");
		System.out.println("�˻���" + dataFormat(map.get(yourNumber).money)
				+ "Ԫ");
	}

	/**
	 * �ײ�������ѯ
	 */
	public void showRemaiDetail(String yourNumber) {
		System.out.println();
		System.out.println("*****�ײ�������ѯ******");
		System.out.println("���Ŀ��ţ�" + yourNumber + "���ײ���ʣ�ࣺ");
		StringBuffer meg = new StringBuffer();
		int remainTalkTime;
		int remainSmsCount;
		int remainFlow;
		if (map.get(yourNumber).setPackage instanceof TalkPackage) {
			TalkPackage cardPackage = (TalkPackage) (map.get(yourNumber).setPackage);
			// ���ǻ����ײͣ����ײ���ͨ��ʱ���ʵ��ͨ��ʱ��������ײ���ͨ��ʱ��-ʵ��ͨ��ʱ�䡣���򷵻�0.
			remainTalkTime = cardPackage.talkTime > map.get(yourNumber).realTalkTime ? cardPackage.talkTime
					- card.realTalkTime
					: 0;
			meg.append("ͯ��ʱ����" + remainTalkTime + "����\n");
			remainSmsCount = cardPackage.smsCount > map.get(yourNumber).realSMSCount ? cardPackage.smsCount
					- map.get(yourNumber).realSMSCount
					: 0;
			meg.append("����������" + remainSmsCount + "��\n");
		} else if (map.get(yourNumber).setPackage instanceof NetPackage) {
			NetPackage cardPackage = (NetPackage) (map.get(yourNumber).setPackage);
			remainFlow = cardPackage.flow > map.get(yourNumber).realFlow ? cardPackage.flow
					- map.get(yourNumber).realFlow
					: 0;
			meg.append("��������:" + remainFlow + "M\n");
		} else if (map.get(yourNumber).setPackage instanceof SuperPackage) {
			SuperPackage cardPackage = (SuperPackage) (map.get(yourNumber).setPackage);
			remainTalkTime = cardPackage.talkTime > map.get(yourNumber).realTalkTime ? cardPackage.talkTime
					- map.get(yourNumber).realTalkTime
					: 0;
			meg.append("ͯ��ʱ����" + remainTalkTime + "����\n");
			remainSmsCount = cardPackage.smsCount > map.get(yourNumber).realSMSCount ? cardPackage.smsCount
					- map.get(yourNumber).realSMSCount
					: 0;
			meg.append("����������" + remainSmsCount + "��\n");
			remainFlow = cardPackage.flow > map.get(yourNumber).realFlow ? cardPackage.flow
					- map.get(yourNumber).realFlow
					: 0;
			meg.append("��������:" + remainFlow + "M\n");
		}
		System.out.println(meg);
	}

	/**
	 * ��ӡ�����굥
	 */
	public void printAmountDetail(String yourNumber) {
		Set<String> numbles = consumInfo.keySet();// ��ȡ���е����Ѽ�¼map�����еĺ���
		// ����set���ϣ��жϴο����Ƿ�������Ѽ�¼
		Iterator<String> it = numbles.iterator();
		// �ж������б����Ƿ��д˿������Ѽ�¼
		boolean isExist = false;// false������
		while (it.hasNext()) {
			String numbleKey = it.next();
			if (numbleKey.equals(yourNumber)
					&& consumInfo.get(yourNumber).size() != 0) {
				isExist = true;
			}
		}
		// ������ڴ˿����Ѽ�¼�����ӡ����������˵��
		if (isExist) {
			StringBuffer brf = new StringBuffer("*******" + yourNumber
					+ "���Ѽ�¼*******\n");
			brf.append("���\t����\t���ݣ�ͨ�������ӣ�/������M��/���ţ�����\n");
			List<ConsumInfo> infos = consumInfo.get(yourNumber);
			// �������Ѽ�¼�б�
			for (int i = 0; i < infos.size(); i++) {
				brf.append((i + 1) + "\t" + infos.get(i).type + "\t"
						+ infos.get(i).consumDate + "\n");
			}
			// �����ַ��������ӡ
			Writer os = null;
			BufferedWriter bw = null;
			try {
				os = new FileWriter("���Ѽ�¼.txt");
				bw = new BufferedWriter(os);
				bw.write(brf.toString());
				bw.flush();
				System.out.println("���Ѽ�¼��ӡ��ϣ�");
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
			System.out.println("�Բ��𣬲����ڴ˿������Ѽ�¼�����ܴ�ӡ��");
		}
	}

	/**
	 * �ײͱ��
	 */
	public void changePackage(String yourNumber) {
		boolean isCoreact = true;// ��ǣ��ײ��Ƿ���ȷ���
		System.out.print("1.�����ײ�  2.�����ײ�  3.�����ײͣ�  ������ѡ���ײ�(�������)��");
		int optionrevie = input.nextInt();
		switch (optionrevie) {
		case 1:
			sp = new TalkPackage();
			if (map.get(yourNumber).setPackage instanceof TalkPackage) {
				System.out.println("�����Ǵ��ײͣ���������");
			} else {
				map.get(yourNumber).setPackage = TalkPackage;
				System.out.println("�ײ͸��ĳɹ���");
			}

			break;
		case 2:
			sp = new NetPackage();
			if (map.get(yourNumber).setPackage instanceof NetPackage) {
				System.out.println("�����Ǵ��ײͣ���������");
			} else {
				map.get(yourNumber).setPackage = NetPackage;
				System.out.println("�ײ͸��ĳɹ���");
			}
			break;
		case 3:
			sp = new SuperPackage();
			if (map.get(yourNumber).setPackage instanceof SuperPackage) {
				System.out.println("�����Ǵ��ײͣ���������");
			} else {
				map.get(yourNumber).setPackage = SuperPackage;
				System.out.println("�ײ͸��ĳɹ���");
			}

			break;
		default:
			System.out.println("��������ȷ����ţ�");
			break;
		}

	}

	/**
	 * ��������
	 */
	public void delateCard(String yourNumber) {
		// �Ӹ��жϷ�ֹ�û������������
		System.out.print("��ȷ��ע�����ֻ�������Y/N:");
		String chouseyes = input.next();
		if (chouseyes.equalsIgnoreCase("Y")) {
			map.remove(yourNumber);
			System.out.println("���ѳɹ�ע�����ֻ��ţ�");
		}
	}

	/**
	 * ʹ����
	 */
	public void uesSoso() {
		// ����ʼ���ĳ�������map�����У�ͨ���ֻ��ż�ֵ��Ӧ����
		scenes.put(0, scene0);
		scenes.put(1, scene1);
		scenes.put(2, scene2);
		scenes.put(3, scene3);
		scenes.put(4, scene4);
		scenes.put(5, scene5);

		System.out.print("�������ֻ����ţ�");
		String yourNumber = input.next();
		System.out.print("���������룺");
		String yourPassWord = input.next();
		if (map.containsKey(yourNumber)) {
			if (map.get(yourNumber).passWord.equals(yourPassWord)) {
				card = map.get(yourNumber);
				ServicePackage pack = card.setPackage;
				Random random = new Random();
				int ranNum = 0;
				int temp = 0;// ��¼ÿ�������е�ʵ������
				boolean iscontinued = true;// ͣ��
				do {
					ranNum = random.nextInt(6);// �������0-5�������
					Scene scene = scenes.get(ranNum);// ���������һ��ֵ��Ӧһ������scene
					switch (ranNum) {
					case 0:
					case 1:
						// �жϿ��������ײ��Ƿ��д�绰�Ĺ���
						if (pack instanceof CallService) {
							System.out.println(scene.description);
							try {
								temp = ((CallService) pack).call(scene.data,
										card);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							// ���һ�����Ѽ�¼
							list.add(new ConsumInfo(yourNumber, scene.type,
									temp));
							iscontinued = false;
							break;

						} else {
							// ����ÿ��ײͲ�֧��ͨ�����ܣ����������������ѡ����������
							continue;
						}

					case 2:
					case 3:
						// �жϿ��������ײ��Ƿ��з����ŵĹ���
						if (pack instanceof SendService) {
							System.out.println(scene.description);
							try {
								temp = ((SendService) pack).send(scene.data,
										card);
							} catch (Exception e) {
								e.printStackTrace();
							}
							// ���һ�����Ѽ�¼
							list.add(new ConsumInfo(yourNumber, scene.type,
									temp));
							iscontinued = false;
							break;
						} else {
							// ����ÿ��ײͲ�֧�ַ����Ź��ܣ����������������ѡ����������
							continue;
						}
					case 4:
					case 5:
						// �жϿ��������ײ��Ƿ��������Ĺ���
						if (pack instanceof NetSevice) {
							System.out.println(scene.description);
							try {
								temp = ((NetSevice) pack).netPlay(scene.data,
										card);
							} catch (Exception e) {
								e.printStackTrace();
							}
							// ���һ�����Ѽ�¼
							list.add(new ConsumInfo(yourNumber, scene.type,
									temp));
							iscontinued = false;
							break;
						} else {
							// ����ÿ��ײͲ�֧���������ܣ����������������ѡ����������
							continue;
						}
					}
					System.out.println("���һ�����Ѽ�¼��");
					consumInfo.put(yourNumber, list);
				} while (iscontinued);
			} else {
				System.out.println("�����������");
			}

		} else {
			System.out.println("�Բ������������Ϣ�����޷���¼��");
		}

	}

	/**
	 * ���ѳ�ֵ
	 */
	public void addPayMoney() {
		System.out.print("��������Ҫ��ֵ���ֻ����룺");
		String yourNumber = input.next();
		System.out.print("��������Ҫ��ֵ�ֻ���������룺");
		String yourPassWord = input.next();
		if (map.containsKey(yourNumber)) {
			if (map.get(yourNumber).passWord.equals(yourPassWord)) {
				System.out.print("��������Ҫ��ֵ�Ľ�");
				double payMoney = input.nextDouble();
				map.get(yourNumber).money += payMoney;
				System.out.println("���ѳ�ֵ�ɹ�������ǰ�������" + map.get(yourNumber).money
						+ "Ԫ");
			} else {
				System.out.println("�����������");
			}
		} else {
			System.out.println("�Բ������������Ϣ�����޷���¼��");
		}

	}

	/**
	 * �ʷ�˵��
	 */
	public void ziFei() {
		System.out.println();
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			is = new FileInputStream("�ʷ�˵��.txt");
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
	 * ��ѧ�����ѧ������ת����ʽ(������λС���ķ���)
	 */
	public String dataFormat(double data) {
		DecimalFormat format = new DecimalFormat("##.00");
		return format.format(data);
	}

	/**
	 * ��λ���������һλС���ķ���
	 */
	public double sub(double num1, double num2) {
		return (num1 * 10 - num2 * 10) / 10;
	}
}
