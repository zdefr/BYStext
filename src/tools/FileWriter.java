package tools;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import com.huaban.analysis.jieba.SegToken;

public class FileWriter {
	public static int writeTokens(List<SegToken> l, String path) {
		try {
			 File out = new File(path);
			 out.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(out);
			for (SegToken w : l) {
				String mid = w.word+":"+w.count;
				outputStream.write(mid.getBytes());
				outputStream.write('\n');
			}
			outputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}

}
