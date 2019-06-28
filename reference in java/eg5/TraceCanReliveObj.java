package chpt4.eg5;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public class TraceCanReliveObj {

	private static TraceCanReliveObj obj;

	/** ���ö��� */
	private static ReferenceQueue<TraceCanReliveObj> phantomQueue = null;

	private static class CheckRefQueueThread extends Thread {

		@Override
		public void run() {

			while (true) {

				if (phantomQueue != null) {

					PhantomReference<TraceCanReliveObj> phantomReference = null;

					try {
						phantomReference = (PhantomReference<TraceCanReliveObj>) phantomQueue.remove();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (phantomReference != null) {

						System.out.println("TraceCanReliveObj is deleted By GC...");

					}

				}

			}

		}

	}

	/**
	 * �����������κζ������������ֻ�ܵ���һ�Σ�
	 */
	@Override
	protected void finalize() throws Throwable {

		super.finalize();

		System.out.println("TraceCanReliveObj finalize called...");

		// ����
		obj = this;

	}

	public static void main(String[] args) throws InterruptedException {

		Thread thread = new CheckRefQueueThread();

		thread.setDaemon(true);

		thread.start();

		phantomQueue = new ReferenceQueue<>();

		obj = new TraceCanReliveObj();

		// obj��Ӧ�Ķ�ʵ����������
		PhantomReference<TraceCanReliveObj> phantomReference = new PhantomReference<TraceCanReliveObj>(obj,
				phantomQueue);

		// ǿ����ʧЧ, Ψ��������
		obj = null;

		System.out.println("��һ��gc...");

		// �ֶ�����gc, ���ø�ʵ����finalize����, ������, ���ܱ�gc�� ����phantomQueue�в����������õ�, ��phantomReference��null
		System.gc();

		Thread.sleep(1000);

		if (obj == null) {

			System.out.println("obj �� null");

		} else {

			// ��ӡ���, ��Ϊfinalize�ж��󸴻�, ���ᱻgc
			System.out.println("obj ����");

		}

		System.out.println("�ڶ���gc...");

		// �ٴ���ǿ����ʧЧ, Ψ��������
		obj = null;

		// �ֶ�����gc, ���ٵ��ø�ʵ����finalize����, ���󱻻���(��Ϊһ�������finalize����ֻ�ᱻ����һ��)
		System.gc();

		Thread.sleep(1000);

		if (obj == null) {

			// ��ӡ���, ��Ϊfinalize�������ٱ�����
			System.out.println("obj is null");

		} else {

			System.out.println("obj ����");

		}

	}

}
/*
����Ҫ���κ�jvm��������ӡ
��һ��gc...
TraceCanReliveObj finalize called...
obj ����
�ڶ���gc...
TraceCanReliveObj is deleted By GC...
obj is null
*/