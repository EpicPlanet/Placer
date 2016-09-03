package com.naver.cafe.goldbigdragon.xkd.parsertester;

import java.util.Map;

public abstract class AbstractParser 
{
	// 프리 프로세서; 필요시 상속하여 재정의하세요.
	public boolean prepare(String source, Map<String, String> placeholder)
	{
		return false;
	}
	
	// 파서 구현부.
	// 원본 문자열과 플레이스홀더맵을 던져주면 결과값을 받아온다
	public abstract String parse(String source, Map<String, String> placeholder);
}
