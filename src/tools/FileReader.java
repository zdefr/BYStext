package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.huaban.analysis.jieba.SegToken;

public class FileReader {
	//读取目录下所有文件
	public static String readDir(String path) throws Exception{
		System.out.println(path);
		String meStrings = new String();
		File dirFile = new File(path);
		File[] listFiles = dirFile.listFiles();
		for (File file : listFiles) {
			meStrings+=readfileString(file.toString());
		}
		return meStrings;
	}
	
	public static String readfileString(String path) throws Exception {
		 List<String> list = new ArrayList<String>();
		 try {
				InputStream in = new FileInputStream(path);
//				Scanner scanner = new Scanner(in);
//				System.out.println(senten);
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
				
				while (true) {
					String midString = reader.readLine();
					if (midString == null) {
						break;
					}
					list.add(midString);
				}
				reader.close();
				in.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 String senten = list.get(0);
		 for (int i = 1; i < list.size(); i++) {
			senten += list.get(i);
		}
		 return senten;
	}
	
	public static List<SegToken> readOuTokens(String path) throws Exception{
		List<SegToken> tokens = new ArrayList<>();
		
		try {
			InputStream in = new FileInputStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			while (true) {
				String midString = reader.readLine();
				if (midString == null) {
					break;
				}
				//System.out.println(midString);
				SegToken s = new SegToken();
				String[] l = midString.split(":");
				s.word = l[0];
				s.count = Integer.parseInt(l[1]);
				tokens.add(s);
			}
			reader.close();
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tokens;
	}
}
