package test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.huaban.analysis.jieba.SegToken;

import tools.BYStools;
import tools.BYStrain;
import tools.FileReader;
import tools.FileWriter;
import tools.ToWords;

public class test {
	private static String rootpath = "C:\\Users\\27352\\Documents\\THUCNews";
	private static String outpath = "C:\\Users\\27352\\Documents\\out";
	private static String testpath = "C:\\Users\\27352\\Documents\\THUCNews\\财经";
	private static BYStools byStools = new BYStools(rootpath, outpath);
	private static ToWords toWords = new ToWords();

	public static void main(String[] args) throws Exception {
		byStools.StatisticDiv();
		byStools.StatisticAll();
		String[] classes = byStools.getClasses();
		List<BYStrain> byses = new ArrayList<>();
		int count = 0;
		for (String string : classes) {
			byses.add(new BYStrain(string, byStools));
		}

		for (BYStrain byStrain : byses) {
			System.out.println(byStrain.toString());
		}

		//test
		File testFile = new File(testpath);
		String[] list = testFile.list();
		for (String t : list) {
			System.out.println(testpath+File.separator+t);
			List<SegToken> mList = toWords.SentenToWords(FileReader.readfileString(testpath+File.separator+t));
			String max="空";
			double maxc=0;
			System.out.println("-------------------------------");
			for (BYStrain b : byses) {
				double mid = b.BYS(mList);
				System.out.println(b.className+":"+mid);
				if (mid>maxc) {
					maxc=mid;
					max=b.className;
				}
			}
			System.out.println(max);
			if (max.equals("财经")) {
				count++;
			}
		}
		
		System.out.println(count);
	}
}
