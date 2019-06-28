package chpt4.eg4;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * �����á������־ͱ��ɵ�
 * 
 * @author yfs
 *
 */
public class WeakRef {

	private static ReferenceQueue<User> weakQueue;

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

	private static class UserWeakReference extends WeakReference<User> {

		private int uid;

		public UserWeakReference(User referent, ReferenceQueue<User> queue) {
			super(referent, queue);
			this.uid = referent.id;
		}

	}

	private static class CheckRefQueueThread extends Thread {

		@Override
		public void run() {

			while (true) {

				if (weakQueue != null) {

					UserWeakReference userWeakReference = null;

					try {
						userWeakReference = (UserWeakReference) weakQueue.remove();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (userWeakReference != null) {

						System.out.println("user id " + userWeakReference.uid + " is deleted...");

					}

				}

			}

		}

	}

	public static void main(String[] args) {

		Thread thread = new CheckRefQueueThread();

		thread.setDaemon(true);

		thread.start();

		User user = new User(1, "yfs");

		weakQueue = new ReferenceQueue<>();

		UserWeakReference userWeakReference = new UserWeakReference(user, weakQueue);

		// ǿ����ʧЧ
		user = null;

		// �ڴ����, ����gc
		System.out.println(userWeakReference.get());

		// �ֶ����� Full GC
		System.gc();

		System.out.println("After GC");

		// �����ڴ��Ƿ��ԣ, ֻҪgc, �ͻ�������û���
		System.out.println(userWeakReference.get());

	}

}
/*
����Ҫ���κ�jvm����, ִ�н������
User [id=1, name=yfs]
After GC
null
user id 1 is deleted...
*/