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
	// ��ͷִ�������
	private static JiebaSegmenter segmenter = new JiebaSegmenter();
	//���ôʱ�
	private static List<String> bans = new ArrayList<>();
	
	public ToWords() {
		try {
			//��ȡ���ôʱ�
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
		
		//��Ӽ������ô�
		bans.add("?");
		bans.add("��");
		bans.add("?");
		bans.add("?");
	}
	
	// �Ե����ַ����ִʣ����˽��ôʣ����Ӵ�С����
	public List<SegToken> SentenToWords(String senten) {
		// �ִ�
		List<SegToken> t = segmenter.process(senten, SegMode.INDEX);
		//���ôʴ�������
		return BanToken(t);
	}

	// ���ôʹ���
	private boolean BanWords(SegToken s) {
		
		if (s.word.charAt(0)>40959 || s.word.charAt(0)<11904) {
			return true;
		}
		
		//System.out.println(s.word.charAt(0));
		
		//���ô��ж�
		for (int i = 0; i < bans.size(); i++) {
			if (s.word.equals(bans.get(i))) {
				return true;
			}
		}

		return false;
	}

	// ��Ƶ����
	private List<SegToken> BanToken(List<SegToken> l) {
		//�洢ͳ�ƺ��token
		List<SegToken> af = new ArrayList<SegToken>();
		//�洢���ôʺ��token
		List<SegToken> rList = new ArrayList<SegToken>();
		
		//ͳ������token
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
			//System.out.println("ͳ��");
		}

		//���ôʴ���
		for (SegToken segToken : af) {
			if (!BanWords(segToken)) {
				rList.add(segToken);
			}
			//System.out.println("bans");
		}

		//������
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
			//System.out.println("��Ƶ");
		}
		
		return result;
	}
}
