package tools;

import java.util.ArrayList;
import java.util.List;

import com.huaban.analysis.jieba.SegToken;

public class BYStrain {
	public String className;
	private double pc;
	private List<PCA> pca = new ArrayList<>();
	
	public BYStrain(String className,BYStools byStools) throws Exception {
		this.className = className;
		pc=byStools.getPc(className);
		pca=byStools.getPCA(className);
	}
	
	@Override
	public String toString() {
		return "\nclass:"+className+"\npc:"+pc;
	}
	
	public double BYS(List<SegToken> test) {
		double finP = 1;
		for (SegToken segToken : test) {
			for (int i = 0; i < pca.size(); i++) {
				if (segToken.word.equals(pca.get(i).word)) {
					//System.out.println(segToken.word);
					for (int j = 0; j < segToken.count; j++) {
						finP*=(10000.0*pca.get(i).p);
						//System.out.println("p:"+pca.get(i).p+"----"+finP);
					}
				}
			}
		}
		return finP;
	}
}
