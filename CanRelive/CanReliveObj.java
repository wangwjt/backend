package chpt4.eg1;

/**
 * �ɸ����
 * 
 * @author yfs
 *
 */
public class CanReliveObj {

	public static CanReliveObj obj;

	@Override
	public void finalize() throws Throwable {

		super.finalize();

		System.out.println("CanReliveObj finalize called...");

		obj = this;

	}

	public static void main(String[] args) throws InterruptedException {

		obj = new CanReliveObj();

		obj = null;

		// �ֹ�����System.gc������һ��Full GC
		System.gc();

		// ���߳�ͣ��100ms(gc�߳��Ծɽ���, �򽫻�����������CanReliveObj�����������ڴ棬 �򸲸ǵ�finalize����ִ��,
		// ���ڴ渴��)
		Thread.sleep(100);

		if (obj == null) {

			System.out.println("obj is null...");

		} else {

			// ��ӡ���, ��Ϊ ���ڴ渴��
			System.out.println("obj����...");

		}

		System.out.println("�ڶ���gc...");

		obj = null;

		System.gc();

		Thread.sleep(100);

		if (obj == null) {

			// ��ӡ���, ��Ϊһ�������finalizeֻ�ܵ���һ��
			System.out.println("obj is null...");

		} else {

			System.out.println("obj����...");

		}

	}

}
