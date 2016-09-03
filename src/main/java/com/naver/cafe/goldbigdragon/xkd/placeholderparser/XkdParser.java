package com.naver.cafe.goldbigdragon.xkd.placeholderparser;

import com.naver.cafe.goldbigdragon.xkd.parsertester.AbstractParser;

import java.util.HashMap;
import java.util.Map;

// '내' 파서.. (xkd)
public class XkdParser extends AbstractParser
{
	Map<String, String> placeholder;
	
	public boolean prepare(String s, Map<String, String> placeholder_original)
	{
		// 플레이스홀더를 빠른 속도로 사용하기 위해 준비한다
		placeholder = preparePlaceHolder(placeholder_original);
		System.out.println("이 파서는 스크립트 변경 시마다 전처리기 실행이 필요합니다!");
		return true;
	}
	
	public String parse(String s, Map<String, String> placeholder_original)
	{
		StringBuilder result = new StringBuilder(s);
		proc(placeholder, result, 0, false);
		return result.toString();
	}
	
	private Map<String, String> preparePlaceHolder(Map<String, String> pl)
	{
		// <과 >가 있으면 속도가 저하된다. 따라서 실행 전 제거한다.
		Map<String, String> ret = new HashMap<String, String>();
		
		for (String key : pl.keySet())
		{
			// 맨 앞 글자와 맨 뒷 글자임.
			String key_ = key.substring(1, key.length() - 1);
			String val = pl.get(key);
			ret.put(key_, val);
		}
		
		return ret;
	}
	
	private int proc(Map<String, String> placeholder, StringBuilder sb, int pos, boolean inner)
    {
    	int start = pos;
    	
    	while (pos < sb.length())
    	{
    		char ch = sb.charAt(pos);
    		pos++;
    		
    		if (ch == '<') pos = proc(placeholder, sb, pos, true);
    		else if (ch == '>') break;
    	}
    	
    	if (inner)
    	{
    		String key = sb.substring(start, pos - 1);
    		String val = placeholder.get(key);
    		
    		if (val != null)
    		{
    			sb.delete(start - 1, pos);
    			sb.insert(start - 1, val);
    			pos = start - 1 + val.length();
    		}
    	}
    	
    	return pos;
    }
}
