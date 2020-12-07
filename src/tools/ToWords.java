package tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;

public class ToWords {
	// 结巴分词器对象
	private static JiebaSegmenter segmenter = new JiebaSegmenter();
	//禁用词表
	private static List<String> bans = new ArrayList<>();
	
	public ToWords() {
		try {
			//读取禁用词表
			InputStream in = new FileInputStream("C:\\Users\\27352\\Documents\\baidu_stopwords.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			while (true) {
				String midString = reader.readLine();
				if (midString == null) {
					break;
				}
				bans.add(midString);
			}
			reader.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//添加几个禁用词
		bans.add("?");
		bans.add("…");
		bans.add("?");
		bans.add("?");
	}
	
	// 对单个字符串分词，过滤禁用词，并从大到小排序
	public List<SegToken> SentenToWords(String senten) {
		// 分词
		List<SegToken> t = segmenter.process(senten, SegMode.INDEX);
		//禁用词处理，排序
		return BanToken(t);
	}

	// 禁用词过滤
	private boolean BanWords(SegToken s) {
		
		if (s.word.charAt(0)>40959 || s.word.charAt(0)<11904) {
			return true;
		}
		
		//System.out.println(s.word.charAt(0));
		
		//禁用词判断
		for (int i = 0; i < bans.size(); i++) {
			if (s.word.equals(bans.get(i))) {
				return true;
			}
		}

		return false;
	}

	// 词频排序
	private List<SegToken> BanToken(List<SegToken> l) {
		//存储统计后的token
		List<SegToken> af = new ArrayList<SegToken>();
		//存储禁用词后的token
		List<SegToken> rList = new ArrayList<SegToken>();
		
		//统计所有token
		for (SegToken segToken : l) {
			int check = 0;
			for (SegToken aft : af) {
				if (segToken.word.equals(aft.word)) {
					aft.countPlus();
					check = 1;
					break;
				}
			}
			if (check == 1) {
				continue;
			}
			af.add(segToken);
			//System.out.println("统计");
		}

		//禁用词处理
		for (SegToken segToken : af) {
			if (!BanWords(segToken)) {
				rList.add(segToken);
			}
			//System.out.println("bans");
		}

		//词排序
		return sorTokens(rList);
	}
	
	public List<SegToken> sorTokens(List<SegToken> rList){
		List<SegToken> result = new ArrayList<>();
		
		for (int i = 0; i < rList.size(); i++) {
			SegToken mid = rList.get(0);
			int switchs = 0;
			for (int j = 1; j < rList.size(); j++) {
				if (mid.count < rList.get(j).count) {
					mid = rList.get(j);
					switchs = j;
				}
			}
			rList.remove(switchs);
			result.add(mid);
			//System.out.println("词频");
		}
		
		return result;
	}
}
