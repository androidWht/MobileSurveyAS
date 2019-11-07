package com.sinosoft.ms.utils.component.datadialog;

import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.sinosoft.ms.R;

/**
 * @author kaisong.Yang 时间控件的封装 该控件包含日期控件dateDialog（）和时间控件timeDialog（）
 **/
public class DateDialogUtils {
	private static final int YEAR_START = 2000;
	private static final int YEAR_END = 2020;
	private static final int MONTH_START = 1;
	private static final int MONTH_END = 12;
	public static int DAY_START = 1;
	public static int DAY_END = 31;
	private static int HOUR_START = 0;
	private static int HOUR_END = 23;
	private static int MINUTE_START = 0;
	private static int MINUTE_END = 59;
	private boolean timeScrolled = false;
	private static Dialog dialog;
	private WheelView WheelView_year;
	private WheelView WheelView_month;
	private WheelView WheelView_day;
	private WheelView WheelView_hour;
	private WheelView WheelView_minute;
	private Button time_bt;
	private SetDate setDate;
	private static int curhour = -1, curmin = -1;
	private static int curYear = 0, curMonth = 0, curDay = 0;
	private String tvResultStr;
	private String timeResuleStr;

	/**
	 * 该函数为日期控件函数 1.act为从哪个Activity调用 2.time为点击时间控件的按钮。
	 * 3.cYear,cMonth,cDay为点击该控件时显示的年，月，日。当全为0时则默认每次显示的日期为当前系统日期。
	 **/
	public void dateDialog(Activity act, Button time, int cYear, int cMonth,
			int cDay) {
		time_bt = time;
		curYear = cYear;
		curMonth = cMonth;
		curDay = cDay;
		initDateListener(act, curYear, curMonth, curDay);
	}

	/**
	 * 该函数为时间控件函数 1.act为从哪个Activity调用 2.time为点击时间控件的按钮。
	 * 3.cHour,cMinute为点击该控件时显示的时，分。当全为-1时则默认每次显示的日期为当前系统时间。
	 **/
	public void timeDialog(Activity act, Button time, int cHour, int cMinute) {
		time_bt = time;
		curhour = cHour;
		curmin = cMinute;
		initTimeListener(act, curhour, curmin);
	}

	/**
	 * 初始化Time
	 **/
	private void initTimeListener(Activity act, int cHour, int cMinute) {
		if (-1 == cHour && -1 == cMinute) {
			Calendar c = Calendar.getInstance();
			curhour = c.get(Calendar.HOUR_OF_DAY);
			curmin = c.get(Calendar.MINUTE);

		} else {

			curhour = cHour;
			curmin = cMinute;
		}

		if (null != dialog) {
			if(dialog.isShowing()){
				dialog.dismiss();
			}
			
		}else{
		  dialog = new Dialog(act, R.style.dialog);
		  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		  dialog.setContentView(R.layout.dialog_time);
		}
		initWheel_time();
		((TextView) dialog.findViewById(R.id.dialog_title)).setText("请选择时间");
		((Button) dialog.findViewById(R.id.ok)).setText("确定");
		((Button) dialog.findViewById(R.id.ok))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (null == timeResuleStr) {

							time_bt.setText(curhour + ":" + curmin);
						} else {
							time_bt.setText(timeResuleStr);
						}
//						DataSreachAct.lock = false;
						dialog.dismiss();
					}
				});
		((Button) dialog.findViewById(R.id.cancel))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
//						DataSreachAct.lock = false;
						dialog.dismiss();
					}
				});
		
		dialog.show();
		WheelView_hour.setCurrentItem(curhour);
		WheelView_minute.setCurrentItem(curmin);

		OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!timeScrolled) {
					String hourStr = String.valueOf(
							WheelView_hour.getCurrentItem() + 100).substring(1);
					String minuteStr = String.valueOf(
							WheelView_minute.getCurrentItem() + 100).substring(
							1);
					timeResuleStr = hourStr + ":" + minuteStr;
				}
			}
		};

		WheelView_hour.addChangingListener(wheelListener);
		WheelView_minute.addChangingListener(wheelListener);

		OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				timeScrolled = true;
			}

			public void onScrollingFinished(WheelView wheel) {
				timeScrolled = false;
				String hourStr = String.valueOf(
						WheelView_hour.getCurrentItem() + 100).substring(1);
				String minuteStr = String.valueOf(
						WheelView_minute.getCurrentItem() + 100).substring(1);

				timeResuleStr = hourStr + ":" + minuteStr;
			}
		};

		WheelView_hour.addScrollingListener(scrollListener);
		WheelView_minute.addScrollingListener(scrollListener);
	}

	/**
	 * 初始化wheel_time
	 */
	private void initWheel_time() {
		WheelView_hour = (WheelView) dialog.findViewById(R.id.query_hour);
		WheelView_hour.setAdapter(new NumericWheelAdapter(HOUR_START, HOUR_END,
				"%02d"));
		WheelView_hour.setLabel("时");
		WheelView_hour.setCyclic(true);

		WheelView_minute = (WheelView) dialog.findViewById(R.id.query_minute);
		WheelView_minute.setAdapter(new NumericWheelAdapter(MINUTE_START,
				MINUTE_END, "%02d"));
		WheelView_minute.setLabel("分");
		WheelView_minute.setCyclic(true);
	}

	/**
	 * 初始化Date
	 */

	private synchronized void initDateListener(Activity act, int Year,
			int Month, int Day) {
		if (0 == Year && 0 == Month && 0 == Day) {
			Calendar c = Calendar.getInstance();
			curYear = c.get(Calendar.YEAR);
			curMonth = c.get(Calendar.MONTH);
			curDay = c.get(Calendar.DAY_OF_MONTH);
		} else {

			curYear = Year;
			curMonth = Month;
			curDay = Day;
		}
		dialog = new Dialog(act, R.style.dialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_date);
		initWheel();
		((TextView) dialog.findViewById(R.id.dialog_title)).setText("请选择日期");
		((Button) dialog.findViewById(R.id.ok)).setText("确定");
		((Button) dialog.findViewById(R.id.ok))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						if (null == tvResultStr) {
							String curM = String.valueOf(curMonth + 101)
									.substring(1).trim();
							String curD = String.valueOf(curDay + 100)
									.substring(1).trim();
							time_bt.setText(curYear + "-" + curM + "-" + curD);
						} else {
							time_bt.setText(tvResultStr);
							tvResultStr = null;
						}
						dialog.dismiss();
//						DataSreachAct.lock = false;
					}
				});
		((Button) dialog.findViewById(R.id.cancel))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
//						DataSreachAct.lock = false;
						dialog.dismiss();

					}
				});
		if (null != dialog) {
			dialog.dismiss();

		}
		dialog.show();

		WheelView_year.setCurrentItem(curYear - 2000);
		WheelView_month.setCurrentItem(curMonth);
		WheelView_day.setCurrentItem(curDay - 1);

		OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!timeScrolled) {
					String yearStr = String.valueOf(WheelView_year
							.getCurrentItem() + 2000);
					String monthStr = String.valueOf(
							WheelView_month.getCurrentItem() + 101)
							.substring(1);
					String dayStr = String.valueOf(
							WheelView_day.getCurrentItem() + 101).substring(1);
					tvResultStr = yearStr + "-" + monthStr + "-" + dayStr;
				}
			}
		};

		WheelView_year.addChangingListener(wheelListener);
		WheelView_month.addChangingListener(wheelListener);
		WheelView_day.addChangingListener(wheelListener);

		OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
			public void onScrollingStarted(WheelView wheel) {
				timeScrolled = true;
			}

			public void onScrollingFinished(WheelView wheel) {
				timeScrolled = false;
				String yearStr = String
						.valueOf(WheelView_year.getCurrentItem() + 2000);
				String monthStr = String.valueOf(
						WheelView_month.getCurrentItem() + 101).substring(1);
				String dayStr = String.valueOf(
						WheelView_day.getCurrentItem() + 101).substring(1);
				/* 设置日期区间 */
				setDate.setDayExtent(Integer.parseInt(yearStr),
						Integer.parseInt(monthStr));
				tvResultStr = yearStr + "-" + monthStr + "-" + dayStr;
				// yearTv.setText(yearStr);
				// monthTv.setText(monthStr);
				// // dayTv.setText(dayStr);
				// /* 设置日期显示及滚轮显示 */
				// dayTv.setText(setDate.getDay(Integer.parseInt(yearStr),
				// Integer.parseInt(monthStr), Integer.parseInt(dayStr)));
				WheelView_day.setCurrentItem(Integer.parseInt(setDate.getDay(
						Integer.parseInt(yearStr), Integer.parseInt(monthStr),
						Integer.parseInt(dayStr) - 1)));
			}
		};

		WheelView_year.addScrollingListener(scrollListener);
		WheelView_month.addScrollingListener(scrollListener);
		WheelView_day.addScrollingListener(scrollListener);

	}

	/**
	 * 初始化wheel
	 */
	private void initWheel() {
		WheelView_year = (WheelView) dialog.findViewById(R.id.query_year);
		WheelView_year.setAdapter(new NumericWheelAdapter(YEAR_START, YEAR_END,
				"%02d"));
		WheelView_year.setLabel("年");
		WheelView_year.setCyclic(true);

		WheelView_month = (WheelView) dialog.findViewById(R.id.query_month);
		WheelView_month.setAdapter(new NumericWheelAdapter(MONTH_START,
				MONTH_END, "%02d"));
		WheelView_month.setLabel("月");
		WheelView_month.setCyclic(true);

		WheelView_day = (WheelView) dialog.findViewById(R.id.query_day);
		WheelView_day.setAdapter(new NumericWheelAdapter(DAY_START, DAY_END,
				"%02d"));
		WheelView_day.setLabel("日");
		WheelView_day.setCyclic(true);

		setDate = new SetDate(WheelView_day);
	}

	public class SetDate {
		/**
		 * 根据年月设置日期
		 */
		private WheelView WheelView_day;

		public SetDate(WheelView day) {
			this.WheelView_day = day;
		}

		/*
		 * 设置日期区间
		 */
		public void setDayExtent(int year, int month) {
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
				if (month == 2) {
					DAY_START = 1;
					DAY_END = 29;
				} else if (month == 1 || month == 3 || month == 5 || month == 7
						|| month == 8 || month == 10 || month == 12) {
					DAY_START = 1;
					DAY_END = 31;
				} else {
					DAY_START = 1;
					DAY_END = 30;
				}
			} else {
				if (month == 2) {
					DAY_START = 1;
					DAY_END = 28;
				} else if (month == 1 || month == 3 || month == 5 || month == 7
						|| month == 8 || month == 10 || month == 12) {
					DAY_START = 1;
					DAY_END = 31;
				} else {
					DAY_START = 1;
					DAY_END = 30;
				}

			}
			WheelView_day.setAdapter(new NumericWheelAdapter(DAY_START,
					DAY_END, "%02d"));
			WheelView_day.setCyclic(true);

		}

		/*
		 * 获得正确日期
		 */
		public String getDay(int year, int month, int day_temp) {
			/* 闰年 */
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {

				if (month == 2) {
					if (day_temp + 1 > 29) {

						day_temp = 29;
					}

				} else if (month == 1 || month == 3 || month == 5 || month == 7
						|| month == 8 || month == 10 || month == 12) {

				} else {
					if (day_temp + 1 > 30) {
						day_temp = 30;
					}

				}
			} /* 平年 */
			else {
				if (month == 2) {
					if (day_temp + 1 > 29) {
						day_temp = 28;
					}

				} else if (month == 1 || month == 3 || month == 5 || month == 7
						|| month == 8 || month == 10 || month == 12) {

				} else {
					if (day_temp + 1 > 30) {
						day_temp = 30;
					}

				}

			}
			return String.valueOf(day_temp + 100).substring(1);
		}
	}

}
