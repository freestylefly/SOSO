package info;
/*
 * ���Ѽ�¼
 * @author ����
 */
public class ConsumInfo {
	public String cardNumber;
	public String type;//���ͣ�ͯ�������š�������
	public int consumDate;//��ǰ������ 
	
	public ConsumInfo() {
	}
	public ConsumInfo(String cardNumber, String type, int consumDate) {
		super();
		this.cardNumber = cardNumber;
		this.type = type;
		this.consumDate = consumDate;
	}
}
