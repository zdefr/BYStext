package tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.huaban.analysis.jieba.SegToken;

public class BYStools {
	private ToWords toWords = new ToWords();
	private String rootpath = new String();
	private String outPath = new String();
	private long Allsize = 0;
	private List<PCA> OriPca = new ArrayList<>();

	/*
	 * @root:�洢Դ���ݵĸ�Ŀ¼
	 * 
	 * @out:����ݴ����ݵ����Ŀ¼
	 */
	public BYStools(String root, String out) {
		this.rootpath = root;
		this.outPath = out;
	}

	/*
	 * @return:���ش��������ַ�����
	 */
	public String[] getClasses() {
		return new File(rootpath).list();
	}

	/*
	 * ͳ�Ƹ�Ŀ¼��ÿ��������ݣ��������� �����.txt�� ����ʽ������outĿ¼
	 */
	public void StatisticDiv() throws Exception {
		String[] classes = getClasses();

		for (String path : classes) {
			String midString = FileReader.readDir(rootpath + File.separator + path);
			List<SegToken> midtoken = toWords.SentenToWords(midString);
			FileWriter.writeTokens(midtoken, outPath + File.separator + path + ".txt");
			System.out.println(path + "ͳ�����");
		}
	}

	/*
	 * ����outĿ¼�µ�ͳ���ļ���������ͳ���ļ�
	 * 
	 * @return���ж�Ԥ�����ļ��Ƿ����
	 */
	public boolean StatisticAll() throws Exception {
		if (new File(outPath).list().length < 14) {
			return false;
		}

		String[] classes = getClasses();
		List<SegToken> finist = new ArrayList<>();

		for (int i = 0; i < classes.length; i++) {
			List<SegToken> seg = FileReader.readOuTokens(outPath + File.separator + classes[i] + ".txt");

			for (int j = 0; j < seg.size(); j++) {
				SegToken check = seg.get(j);
				int n = 0;
				for (SegToken segToken : finist) {
					if (segToken.word.equals(check.word)) {
						segToken.count += check.count;
						n = 1;
						break;
					}
				}

				if (n == 0) {
					finist.add(check);
				}
			}
		}

		for (SegToken segToken : finist) {
			OriPca.add(new PCA(segToken.word, 0));
		}

		FileWriter.writeTokens(toWords.sorTokens(finist), outPath + File.separator + "finList.txt");
		return true;
	}

	/*
	 * ����ָ���������������
	 * 
	 * @className������
	 * 
	 * @return���������
	 */
	public double getPc(String className) {
		long s = 0;
		File cFile = new File(rootpath + File.separator + className);
		File[] cFiles = cFile.listFiles();
		for (int i = 0; i < cFiles.length; i++) {
			s += cFiles[i].length();
			// System.out.println(s);
		}

		if (Allsize == 0) {
			String[] classes = getClasses();
			for (String string : classes) {
				File aFile = new File(rootpath + File.separator + string);
				File[] aFiles = aFile.listFiles();
				for (int i = 0; i < aFiles.length; i++) {
					Allsize += aFiles[i].length();
				}
			}
		}

		System.out.println(className);
		System.out.println(cFile.length());
		System.out.println(Allsize);

		return (double) s / Allsize;
	}

	/*
	 * ����ָ��������������
	 * 
	 * @className������
	 * 
	 * @return����������
	 */
	public List<PCA> getPCA(String className) throws Exception {
		if (OriPca.size()==0) {
			List<SegToken> l = FileReader.readOuTokens(outPath + File.separator + "finList.txt");
			for (SegToken segToken : l) {
				OriPca.add(new PCA(segToken.word, 0));
			}
		}
		
		List<PCA> pca = new ArrayList<>();
		long s = 0;
		List<SegToken> Tokens = FileReader.readOuTokens(outPath + File.separator + className + ".txt");
		for (SegToken segToken : Tokens) {
			s += segToken.count;
		}
		
		
		System.out.println(s);
		for (PCA p : OriPca) {
			int n = 0;
			for (int j = 0; j < Tokens.size(); j++) {
				if (p.word.equals(Tokens.get(j).word)) {
					pca.add(new PCA(p.word, ((double) Tokens.get(j).count + 1) / (s + pca.size())));
					n = 1;
					break;
				}
			}
			if (n == 0) {
				pca.add(new PCA(p.word, p.p = 1.0 / (s + pca.size())));
			}
		}
		//System.out.println(pca.size());
		return pca;
	}
}
