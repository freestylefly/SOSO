package start;

import java.util.Scanner;

/*
 * ������
 * @author ����
 */
public class Manage {
	
	/**
	 * ���������˵���
	 */
	public void start(){
		Scanner input = new Scanner(System.in);
		Util util = new Util();
		boolean iscontinue = true;// �Ƿ����
		do {
			System.out.println();
			System.out.println("*************��ӭʹ�����ƶ�ҵ�����***************");
			System.out.println("1.�û���¼   2.�û�ע��   3.ʹ����   4.���ѳ�ֵ  5.�ʷ�˵��  6.�˳�ϵͳ");
			System.out.print("��ѡ��");
			int option = input.nextInt();
			switch (option) {
			case 1:
				util.login();
				break;
			case 2:
				util.signIn();
				break;
			case 3:
				util.uesSoso();
				break;
			case 4:
				util.addPayMoney();
				break;
			case 5:
				util.ziFei();
				break;
			case 6:
				System.out.println("��ӭ���´μ���ʹ�ã�");
				iscontinue=false;
				break;
			default:
				System.out.println("������������������");
				break;
			}
		} while (iscontinue);
	}

}
