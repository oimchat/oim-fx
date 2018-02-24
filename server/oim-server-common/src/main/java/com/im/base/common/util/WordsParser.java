package com.im.base.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class WordsParser {

	public static void main(String args[]) {
		try {
			WordsParser filterEngine = new WordsParser();
			
			filterEngine.addFilterKeyWord("AV", 1);
			filterEngine.addFilterKeyWord("政治", 1);
			filterEngine.addFilterKeyWord("shit", 1);
			
			Vector<Integer> levelSet = new Vector<Integer>();
			String str = "单个的政治，政治运动和强奸和shit";
			SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss.SSS");
			System.out.println("文本长度：" + str.length());
			System.out.println("敏感词汇总数:" + filterEngine.tmpUnionPatternSet.getSet().size());
			Date start = new Date(System.currentTimeMillis());
			System.out.println("过滤开始：" + sf.format(start));

			System.out.println(str);
			System.out.println(filterEngine.parse(new String(str.getBytes(), "utf-8"), levelSet));

			Date end = new Date(System.currentTimeMillis());
			System.out.println("过滤完毕：" + sf.format(end));
			System.out.println("文本中出现敏感词个数：" + levelSet.size());
			System.out.println("耗时：" + (end.getTime() - start.getTime()) + "ms");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private boolean initFlag = false;
	private int maxIndex = (int) java.lang.Math.pow(2, 16);
	private int shiftTable[] = new int[maxIndex];
	@SuppressWarnings("unchecked")
	public Vector<AtomicPattern> hashTable[] = new Vector[maxIndex];
	public UnionPatternSet tmpUnionPatternSet = new UnionPatternSet();

	public boolean addFilterKeyWord(String keyWord, int level) {
		if (initFlag == true) {
			return false;
		}
		UnionPattern unionPattern = new UnionPattern();
		Pattern pattern = new Pattern(keyWord);
		AtomicPattern atomicPattern = new AtomicPattern(pattern);
		unionPattern.addNewAtomicPattrn(atomicPattern);
		unionPattern.setLevel(level);
		atomicPattern.setBelongUnionPattern(unionPattern);
		tmpUnionPatternSet.addNewUnionPattrn(unionPattern);
		return true;
	}

	public String parse(String content, Vector<Integer> levelSet) {
		try {
			if (initFlag == false) {
				init();
			}
			Vector<AtomicPattern> aps = new Vector<AtomicPattern>();
			StringBuilder sb = new StringBuilder();
			char checkChar;
			for (int i = 0; i < content.length();) {
				checkChar = content.charAt(i);
				if (shiftTable[checkChar] == 0) {
					Vector<AtomicPattern> tmpAps = new Vector<AtomicPattern>();
					Vector<AtomicPattern> destAps = hashTable[checkChar];
					int match = 0;
					for (int j = 0; j < destAps.size(); j++) {
						AtomicPattern ap = destAps.get(j);
						if (ap.findMatchInString(content.substring(0, i + 1))) {
							String patternStr = ap.getPattern().str;
							if (match > 0) {
								sb.setLength(sb.length() - patternStr.length());
							} else {
								sb.setLength(sb.length() - patternStr.length() + 1);
							}
							appendStar(patternStr, sb);
							tmpAps.add(ap);
							match++;
						}
					}
					aps.addAll(tmpAps);
					if (tmpAps.size() <= 0) {
						sb.append(checkChar);
					}
					i++;
				} else {
					if (i + shiftTable[checkChar] <= content.length()) {
						sb.append(content.substring(i, i + shiftTable[checkChar]));
					} else {
						sb.append(content.substring(i));
					}
					i = i + shiftTable[checkChar];
				}
			}
			parseAtomicPatternSet(aps, levelSet);
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private static void appendStar(String patternStr, StringBuilder sb) {
		for (int c = 0; c < patternStr.length(); c++) {
			char ch = patternStr.charAt(c);
			if ((ch >= 0x4e00 && ch <= 0x9FA5) || (ch >= 0xF900 && ch <= 0xFA2D)) {
				sb.append("*");
			} else {
				sb.append("*");
			}
		}
	}

	private void parseAtomicPatternSet(Vector<AtomicPattern> aps, Vector<Integer> levelSet) {
		while (aps.size() > 0) {
			AtomicPattern ap = aps.get(0);
			UnionPattern up = ap.belongUnionPattern;
			if (up.isIncludeAllAp(aps)) {
				levelSet.add(new Integer(up.getLevel()));
			}
			aps.remove(0);
		}
	}

	// shift table and hash table of initialize
	private void init() {
		initFlag = true;
		for (int i = 0; i < maxIndex; i++) {
			hashTable[i] = new Vector<AtomicPattern>();
		}
		shiftTableInit();
		hashTableInit();
	}

	public void clear() {
		tmpUnionPatternSet.clear();
		initFlag = false;
	}

	private void shiftTableInit() {
		Vector<UnionPattern> upSet = tmpUnionPatternSet.getSet();
		
		for (int i = 0; i < maxIndex; i++) {
			shiftTable[i] = 2;
		}
		for (int i = 0; i < upSet.size(); i++) {
			Vector<AtomicPattern> apSet = upSet.get(i).getSet();
			for (int j = 0; j < apSet.size(); j++) {
				AtomicPattern ap = apSet.get(j);
				Pattern pattern = ap.getPattern();
				if (shiftTable[pattern.charAtEnd(1)] != 0){
					shiftTable[pattern.charAtEnd(1)] = 1;
				}
				if (shiftTable[pattern.charAtEnd(0)] != 0){
					shiftTable[pattern.charAtEnd(0)] = 0;
				}
			}
		}
	}

	private void hashTableInit() {
		Vector<UnionPattern> upSet = tmpUnionPatternSet.getSet();
		for (int i = 0; i < upSet.size(); i++) {
			Vector<AtomicPattern> apSet = upSet.get(i).getSet();
			for (int j = 0; j < apSet.size(); j++) {
				AtomicPattern ap = apSet.get(j);
				Pattern pattern = ap.getPattern();
				if (pattern.charAtEnd(0) != 0) {
					hashTable[pattern.charAtEnd(0)].add(ap);
				}
			}
		}
	}

	// 取得根目录路径
	public String getCurrentPath() {
		String rootPath = getClass().getResource("/").getFile().toString();
		return rootPath;
	}
}

class Pattern { // string
	Pattern(String str) {
		this.str = str;
	}

	public char charAtEnd(int index) {
		if (str.length() > index) {
			return str.charAt(str.length() - index - 1);
		} else
			return 0;
	}

	public String str;

	public String getStr() {
		return str;
	};
}

class AtomicPattern {

	private Pattern pattern;
	public UnionPattern belongUnionPattern;

	public boolean findMatchInString(String str) {
		if (this.pattern.str.length() > str.length()) {
			return false;
		}
		int beginIndex = str.lastIndexOf(this.pattern.str.charAt(0) + "");
		if (beginIndex != -1) {
			String eqaulLengthStr = str.substring(beginIndex);
			if (this.pattern.str.equalsIgnoreCase(preConvert(eqaulLengthStr))) {
				return true;
			}
		}
		return false;
	}

	private String preConvert(String content) {
		String retStr = new String();
		for (int i = 0; i < content.length(); i++) {
			char ch = content.charAt(i);
			if (this.isValidChar(ch)) {
				retStr = retStr + ch;
			}
		}
		return retStr;
	}

	private boolean isValidChar(char ch) {
		if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
			return true;
		}
		if ((ch >= 0x4e00 && ch <= 0x9FA5) || (ch >= 0xF900 && ch <= 0xFA2D)) {
			return true;
		}
		return false;
	}

	AtomicPattern(Pattern pattern) {
		this.pattern = pattern;
	};

	public UnionPattern getBelongUnionPattern() {
		return belongUnionPattern;
	}

	public void setBelongUnionPattern(UnionPattern belongUnionPattern) {
		this.belongUnionPattern = belongUnionPattern;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}
}

class SameAtomicPatternSet {
	public Vector<AtomicPattern> SAPS;
	
	SameAtomicPatternSet() {
		SAPS = new Vector<AtomicPattern>();
	};
}

class UnionPattern { // union string

	private int level;
	public Vector<AtomicPattern> apSet;

	UnionPattern() {
		this.apSet = new Vector<AtomicPattern>();
	}

	public void addNewAtomicPattrn(AtomicPattern ap) {
		this.apSet.add(ap);
	}

	public Vector<AtomicPattern> getSet() {
		return apSet;
	}

	public boolean isIncludeAllAp(Vector<AtomicPattern> inAps) {
		if (apSet.size() > inAps.size()) {
			return false;
		}
		for (int i = 0; i < apSet.size(); i++) {
			AtomicPattern ap = apSet.get(i);
			if (isInAps(ap, inAps) == false) {
				return false;
			}
		}
		return true;
	}

	private boolean isInAps(AtomicPattern ap, Vector<AtomicPattern> inAps) {
		for (int i = 0; i < inAps.size(); i++) {
			AtomicPattern destAp = inAps.get(i);
			if (ap.getPattern().str.equalsIgnoreCase(destAp.getPattern().str)) {
				return true;
			}
		}
		return false;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return this.level;
	}

}

class UnionPatternSet { // union string set

	public Vector<UnionPattern> unionPatternSet;

	UnionPatternSet() {
		this.unionPatternSet = new Vector<UnionPattern>();
	}

	public void addNewUnionPattrn(UnionPattern up) {
		this.unionPatternSet.add(up);
	}

	public Vector<UnionPattern> getSet() {
		return unionPatternSet;
	}

	public void clear() {
		unionPatternSet.clear();
	}
}