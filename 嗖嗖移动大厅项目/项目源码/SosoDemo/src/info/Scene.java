package info;

import java.util.Random;

/*
 * 使用场景
 * @author 江彬
 */
public class Scene {
	public String type;//场景类型
	public int data;//场景消费数据
	public String description;//场景描述
	
	public Scene() {
		super();
	}
	public Scene(String type, int data, String description) {
		super();
		this.type = type;
		this.data = data;
		this.description = description;
	}
}
