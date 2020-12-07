package com.huaban.analysis.jieba;

public class SegToken {
    public String word;

    public int startOffset;

    public int endOffset;
    
    public int count = 1;


     public SegToken() {
		
	}
    
    public SegToken(String word, int startOffset, int endOffset) {
        this.word = word;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }
    
    public void countPlus() {
		// TODO Auto-generated method stub
    	this.count++;
	}

    @Override
    public String toString() {
        return "[" + word + ", " + startOffset + ", " + endOffset + "]";
    }

}
