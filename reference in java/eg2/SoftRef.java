package chpt4.eg2;

import java.lang.ref.SoftReference;

/**
 * ��ʾ���������ڶ��ڴ治���ʱ�򱻻��գ� �� -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+PrintFlagsFinal Ϊ��������
 * 
 * @author yfs
 *
 */
public class SoftRef {

	private static class User {

		public int id;

		public String name;

		public User(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}

		@Override
		public String toString() {
			return "User [id=" + id + ", name=" + name + "]";
		}

	}

	public static void main(String[] args) throws InterruptedException {

		// ջ�ϵ�user ����Userʵ��A(��java���Ϸ���)��ǿ����
		User user = new User(1, "yfs");

		// ջ�ϵ�userSoftReference ���� A��������
		SoftReference<User> userSoftReference = new SoftReference<SoftRef.User>(user);

		user = null;

		// User [id=1, name=yfs] ���������ɿ��Է��ʶ��ڴ�
		System.out.println(userSoftReference.get());

		// ����Full GC
		System.gc();

		System.out.println("After GC...");

		// User [id=1, name=yfs] ��Ϊ�ڴ��㹻, ���Բ��ᵼ��GC
		System.out.println(userSoftReference.get());

		// ����6.32MB�Ķ��ڴ�
		byte[] bs = new byte[7 * 925 * 1024];

		// �ڴ治����, ����һ��GC���������û���, ���������ӡnull, ��ʵ, ����� System.gc() �Ƕ����, ��Ϊ����һ�������XmxΪ10MB, �������6.32MB��,��Ϊ ͨ�� -XX:+PrintFlagsFinal������ӡ������NewRatio ������2, Ҳ������������3MB����, һ������˶���ͻᵼ��������gc�������ñ�����
//		System.gc();

		// ���߳�ֹͣ100ms, gc�߳�����
		Thread.sleep(100);

		// null
		System.out.println(userSoftReference.get());

	}

}
