package com.yfs.finalize;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * �� -Xmx8m Ϊ�������г���
 * 
 * @author yfs
 *
 */
public class SoftRefQ {

	/** �����ö��� */
	private static ReferenceQueue<User> softQueue;

	private static class User {

		private int id;

		private String name;

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

	/**
	 * ������
	 * 
	 * @author yfs
	 *
	 */
	private static class UserSoftReference extends SoftReference<User> {

		/** ��¼ǿ���õ�id */
		public int uid;

		/**
		 * ÿ�������ÿ��Ը���һ�����ö���
		 * 
		 * @param user
		 *            �����������õĶ���ʵ��, ����Ҫ��User�����丸��(��ָ���˷��͵��½�)
		 * @param queue
		 *            ���������ö���
		 */
		public UserSoftReference(User referent, ReferenceQueue<? super User> queue) {

			super(referent, queue);

			uid = referent.id;

		}

	}

	/**
	 * ���ϵĴ����ö����е��Ƴ�����(��Щ�����Ǳ�����������ö���)
	 * 
	 * @author yfs
	 *
	 */
	private static class CheckRefQueueThread extends Thread {

		@Override
		public void run() {

			while (true) {

				if (softQueue != null) {

					UserSoftReference userSoftReference = null;

					// �׳� InterruptedException �����˲�����ʱ, ���ǿ����ж�, ����һ����������
					try {
						userSoftReference = (UserSoftReference) softQueue.remove();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					// ����Ƴ��ɹ�
					if (userSoftReference != null) {

						System.out.println("user id " + userSoftReference.uid + " is deleted..."); // ��Ҫ������֣������ӡ��uid�����Ǳ�gc���Ķ����,���������ö���������ά����

					}

				}

			}

		}

	}

	public static void main(String[] args) throws InterruptedException {

		Thread thread = new CheckRefQueueThread();

		thread.setDaemon(true);

		// ��һ���̼߳��softQueue
		thread.start();

		User user = new User(1, "yfs");

		softQueue = new ReferenceQueue<>();

		// �������ð�һ�����ö���
		UserSoftReference userSoftReference = new UserSoftReference(user, softQueue);

		// ��ǿ����ʧЧ
		user = null;

		// User [id=1, name=yfs]  ͨ�������÷��ʶ��ϵĶ���
		System.out.println(userSoftReference.get());

		// �ֶ�����Full GC
		System.gc();

		System.out.println("After GC");

		// User [id=1, name=yfs] (��Ϊ�ڴ��㹻, ���Ծ���User���ڶ��ϵ�ʵ��ֻ��userSoftReference������,
		// ��Ҳ���ᱻ����,���ܴ˶����ǿ�����Ѿ���ʧ��)
		System.out.println(userSoftReference.get());

		// ����6.32MB ���ڴ棬 ���㲻�ֶ�����gc, Ҳ������������gc��
		byte[] bs = new byte[7 * 925 * 1024];

		// System.gc();

		// null
		System.out.println(userSoftReference.get());

	}

}
/* ִ�н������
User [id=1, name=yfs]
After GC
User [id=1, name=yfs]
Exception in thread "main" user id 1 is deleted...
java.lang.OutOfMemoryError: Java heap space
	at com.yfs.finalize.SoftRefQ.main(SoftRefQ.java:136)
*/