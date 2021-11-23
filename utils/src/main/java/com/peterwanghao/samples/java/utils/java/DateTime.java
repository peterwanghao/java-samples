package com.peterwanghao.samples.java.utils.java;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateTime {
	public static void main(String[] args) {
		// 获取当前时间戳
		// Instant类由一个静态的工厂方法now()可以返回当前时间戳
		Instant timestamp = Instant.now();
		log.info("当前的时间戳是：" + timestamp);
		
		// 获取当前时间毫秒数
		Date date = new Date();
		log.info("" + date.getTime());
		log.info("" + Calendar.getInstance().getTimeInMillis());
		Instant timestamp1 = Instant.now();
		log.info("" + timestamp1.toEpochMilli());
		
		System.out.println("---------------------------------------------");
		
		// 获取当前日期
		LocalDate today = LocalDate.now();
		log.info("今天的日期：" + today);
		
		// 获取当前的年月日
		int year = today.getYear();
		int month = today.getMonthValue();
		int day = today.getDayOfMonth();
		log.info("年：" + year +" 月："+ month +" 日："+ day);
		
		// 获取某个特定的日期
		// 可以创建出任意一个日期，它接受年月日的参数，然后返回一个等价的LocalDate实例。
		LocalDate dateOfBirth = LocalDate.of(2020, 11, 23);
		log.info("生日：" + dateOfBirth);
		
		// 检查两个日期是否相等
		// LocalDate重写了equals方法来进行日期的比较
		log.info("是否相等：" + dateOfBirth.equals(today));
		
		// 使用MonthDay类。这个类由月日组合，不包含年信息，可以用来代表每年重复出现的一些日期或其他组合
		MonthDay a = MonthDay.of(dateOfBirth.getMonth(),dateOfBirth.getDayOfMonth());
		MonthDay b = MonthDay.from(today);
		log.info("是否相等：" + a.equals(b));
		
		// 如何判断某个日期在另一个日期的前面还是后面或者相等，
		// 在java8中，LocalDate类中使用isBefore()、isAfter()、equals()方法来比较两个日期。
		// 如果调用方法的那个日期比给定的日期要早的话，isBefore()方法会返回true。
		LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
		log.info("是否在之前：" + tomorrow.isBefore(today));
		log.info("是否在之后：" + tomorrow.isAfter(today));
		
		
		// 获取1周后的日期
		LocalDate oneToday = today.plus(1, ChronoUnit.WEEKS);
		log.info("一周后的日期：" + oneToday);
		
		// 获取1年前后的日期
		LocalDate preYear = today.minus(1, ChronoUnit.YEARS);
		log.info("一年前的日期：" + preYear);
		LocalDate nextYear = today.plus(1, ChronoUnit.YEARS);
		log.info("一年后的日期：" + nextYear);
		System.out.println("---------------------------------------------");
		
		// 获取当前时间 这里用的是LocalTime类，默认的格式是hh:mm:ss:nnn
		LocalTime now = LocalTime.now();
		log.info("现在的时间：" + now);
		
		// 增加时间里面的小时数
		LocalTime two = now.plusHours(2);
		log.info("两小时后的时间：" + two);
		
		// java8自带了Clock类，可以用来获取某个时区下（所以对时区是敏感的）当前的瞬时时间、日期。
		// 用来代替System.currentTimelnMillis()与TimeZone.getDefault()方法
		Clock clock = Clock.systemUTC();
		log.info("clock：" + clock);
		Clock clock1 = Clock.systemDefaultZone();
		log.info("clock：" + clock1);
		
		// java8中不仅将日期和时间进行了分离，同时还有时区。
		// 比如ZonId代表的是某个特定时区，ZonedDateTime代表带时区的时间，等同于以前的GregorianCalendar类。
		// 使用该类，可以将本地时间转换成另一个时区中的对应时间。
		LocalDateTime localDateTime = LocalDateTime.now();
		ZoneId zone = ZoneId.of(ZoneId.SHORT_IDS.get("ACT"));
		ZonedDateTime dateAndTimeInNewYork = ZonedDateTime.of(localDateTime, zone);
		log.info("现在时区的时间和在特定时区的时间：" + dateAndTimeInNewYork);
		
		// 检查闰年
		// LocalDate类有一个isLeapYear()方法来返回当前LocalDate对应的那年是否是闰年
		log.info("{} 是否是闰年：{}", today,today.isLeapYear());
		
		// 两个日期之间包含多少天，多少月
		LocalDate date1 = LocalDate.of(2021, 11, 12);
		LocalDate date2 = LocalDate.of(2021, 11, 23);
		Period dValue = Period.between(date1, date2);
		log.info("日期{}和日期{}相差{}个月", date1, date2, dValue.getMonths());
		log.info("日期{}和日期{}相差{}天", date1, date2, dValue.get(ChronoUnit.DAYS));
		
		LocalDate startDate = LocalDate.of(2015, 2, 20);
		LocalDate endDate = LocalDate.of(2017, 2, 15);
		Period period = Period.between(startDate, endDate);
		log.info("Years:" + period.getYears() + 
				  " months:" + period.getMonths() + 
				  " days:"+period.getDays());
		
		// 取两个日期直接相差多少天
		// 方法1
		long daysBetween = ChronoUnit.DAYS.between(date1, date2);
		log.info("日期{}和日期{}相差{}天", date1, date2, daysBetween);
		// 方法2
		long minusDay = date2.toEpochDay() - date1.toEpochDay();
		log.info("日期{}和日期{}相差{}天", date1, date2, minusDay);
		long minusDay2 = endDate.toEpochDay() - startDate.toEpochDay();
		log.info("日期{}和日期{}相差{}天", startDate, endDate, minusDay2);
		
		// 两个日期之间相差多少秒，多少纳秒
		Instant start = Instant.parse("2017-10-03T09:15:30.00Z");
		Instant end = Instant.parse("2017-10-03T10:16:30.00Z");
		Duration duration = Duration.between(start, end);
		log.info("日期{}和日期{}相差{}秒",start, end, duration.getSeconds());
		
		
		
		System.out.println("---------------------------------------------");
		
		// 如何在java8中使用预定义的格式器来对日期进行解析/格式化
		// 在java8之前，时间日期的格式化非常麻烦，经常使用SimpleDateFormat来进行格式化，但是SimpleDateFormat并不是线程安全的。
		// 在java8中，引入了一个全新的线程安全的日期与时间格式器。并且预定义好了格式。
		String dateString = "20211123";
		LocalDate formatted = LocalDate.parse(dateString, DateTimeFormatter.BASIC_ISO_DATE);
		log.info("字符串{}格式化后的日期格式是{}", dateString, formatted);
		
		// 使用自定义的格式器来解析日期
		String holidayString = "2021-11-23 11:11:11";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime holiday = LocalDateTime.parse(holidayString, formatter);
		log.info("字符串{}格式化后的日期格式是{}", holidayString, holiday);
		
		// 对日期进行格式化，转换成字符串
		LocalDateTime arriveDate = LocalDateTime.now();
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String landing = arriveDate.format(formatter1);
		log.info("时间{}格式化后的字符串是{}", arriveDate, landing);
	}
}
