package start;

import java.util.Scanner;

/*
 * 管理类
 * @author 江彬
 */
public class Manage {
	
	/**
	 * 启动（主菜单）
	 */
	public void start(){
		Scanner input = new Scanner(System.in);
		Util util = new Util();
		boolean iscontinue = true;// 是否继续
		do {
			System.out.println();
			System.out.println("*************欢迎使用嗖嗖移动业务大厅***************");
			System.out.println("1.用户登录   2.用户注册   3.使用嗖嗖   4.话费充值  5.资费说明  6.退出系统");
			System.out.print("请选择：");
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
				System.out.println("欢迎您下次继续使用！");
				iscontinue=false;
				break;
			default:
				System.out.println("输入有误！请重新输入");
				break;
			}
		} while (iscontinue);
	}

}
