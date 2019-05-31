package com.peterwanghao.samples.java.poi.word;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import com.peterwanghao.samples.java.poi.word.bean.KetWord;

/**
 * @ClassName: KetWordExport
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: wanghao
 * @date: 2019年5月30日 下午4:33:30
 * @version V1.0
 * 
 */
public class KetWordExport {
	public ArrayList<KetWord> readXml(String fileName) throws IOException, DocumentException {
		ArrayList<KetWord> words = new ArrayList<KetWord>();
		HashMap<Integer, String> texts = new HashMap<Integer, String>();

		// 读XML文件填充到HashMap里
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(fileName));
		Element root = document.getRootElement();

		String key = "";
		String value = "";
		int count = 0;
		Iterator<?> it = root.elementIterator();
		while (it.hasNext()) {
			Element element = (Element) it.next();
//			System.out
//					.println(element.getName() + ": " + element.getText().trim() + ": " + isNumeric(element.getText()));
			String text = element.getText().trim();
			if (isNumeric(text)) {
				count++;
				if (count > 1)
					texts.put(Integer.valueOf(key), value);
				key = text;
				value = text;
			} else {
				value += "+" + text;
			}
		}
		texts.put(Integer.valueOf(key), value);
		System.out.println("count : " + count);

		// 转换成KetWord对象
		Set<Integer> set = texts.keySet();
		Object[] arr = set.toArray();// 按key排序
		Arrays.sort(arr);
		for (Object key1 : arr) {
			KetWord word = new KetWord();
			System.out.println(key1 + ": " + texts.get(key1));
			String[] array = texts.get(key1).split("\\+");
			word.setOrder(Integer.valueOf(array[0]).intValue());
			word.setWord(array[1]);
			word.setPhonetic(array[2]);
			String temp = "";
			for (int i = 3; i < array.length; i++) {
				temp += array[i];
				if (isPoint(array[i]))
					temp += " ";
			}
			word.setMeans(temp);
			words.add(word);
		}

		return words;
	}

	/**
	 * 利用正则表达式判断字符串是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static boolean isPoint(String str) {

		if (str.indexOf('.') == -1) {
			return false;
		}
		return true;
	}

	public void exportWord(String title, String fileName, ArrayList<KetWord> words) throws IOException {
		// Blank Document
		XWPFDocument document = new XWPFDocument();

		// Write the Document in file system
		FileOutputStream out = new FileOutputStream(new File(fileName)); // 下载路径/文件名称

		// 添加标题
		XWPFParagraph titleParagraph = document.createParagraph();
		// 设置段落居中
		titleParagraph.setAlignment(ParagraphAlignment.CENTER);

		XWPFRun titleParagraphRun = titleParagraph.createRun();
		titleParagraphRun.setText(title);
		titleParagraphRun.setColor("000000");
		titleParagraphRun.setFontSize(20);

		// 表格
		XWPFTable ComTable = document.createTable();

		// 列宽自动分割
		CTTblWidth comTableWidth = ComTable.getCTTbl().addNewTblPr().addNewTblW();
		comTableWidth.setType(STTblWidth.DXA);
		comTableWidth.setW(BigInteger.valueOf(9072));

		XWPFTableRow comTableRow = null;
		KetWord word = null;
		for (int i = 0; i < words.size(); i++) {
			if (i == 0) {
				comTableRow = ComTable.getRow(0);

				word = words.get(i);
				comTableRow.getCell(0).setText(String.valueOf(word.getOrder()));
				comTableRow.addNewTableCell().setText(word.getWord());
				comTableRow.addNewTableCell().setText(word.getPhonetic());
				comTableRow.addNewTableCell().setText(word.getMeans());
				i++;
				word = words.get(i);
				comTableRow.addNewTableCell().setText("");
				comTableRow.addNewTableCell().setText(String.valueOf(word.getOrder()));
				comTableRow.addNewTableCell().setText(word.getWord());
				comTableRow.addNewTableCell().setText(word.getPhonetic());
				comTableRow.addNewTableCell().setText(word.getMeans());
			} else {
				if (i % 2 == 0)
					comTableRow = ComTable.createRow();
				word = words.get(i);
				comTableRow.getCell(0).setText(String.valueOf(word.getOrder()));
				comTableRow.getCell(1).setText(word.getWord());
				comTableRow.getCell(2).setText(word.getPhonetic());
				comTableRow.getCell(3).setText(word.getMeans());
				i++;
				if (i < words.size()) {
					word = words.get(i);
					comTableRow.getCell(4).setText("");
					comTableRow.getCell(5).setText(String.valueOf(word.getOrder()));
					comTableRow.getCell(6).setText(word.getWord());
					comTableRow.getCell(7).setText(word.getPhonetic());
					comTableRow.getCell(8).setText(word.getMeans());
				}
			}

		}

		document.write(out);
		out.close();
		document.close();
		System.out.println("create_table document written success.");
	}

	public static void main(String[] args) throws Exception {
		KetWordExport export = new KetWordExport();
		ArrayList<KetWord> words = null;

		try {
			words = export.readXml("d://Day2.xml");
			System.out.println("words size:" + words.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		String title = "Day14";
		String fileName = "d://ket_day14.docx";
		try {
			export.exportWord(title, fileName, words);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
