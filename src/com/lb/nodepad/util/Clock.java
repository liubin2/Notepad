package com.lb.nodepad.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.lb.nodepad.view.NotepadMainFrame;

/**
 * 获取时间
 * 
 * @author liubin
 *
 */
public class Clock extends Thread {

	public void run() {
		while (true) {
			GregorianCalendar time = new GregorianCalendar();
			int hour = time.get(Calendar.HOUR_OF_DAY);
			int min = time.get(Calendar.MINUTE);
			int second = time.get(Calendar.SECOND);
			NotepadMainFrame.label1.setText(" 当前时间：" + hour + ":" + min + ":" + second);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException exception) {
			}
		}
	}
}