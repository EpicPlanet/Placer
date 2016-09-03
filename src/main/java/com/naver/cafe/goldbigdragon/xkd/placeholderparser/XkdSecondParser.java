package com.naver.cafe.goldbigdragon.xkd.placeholderparser;

import com.naver.cafe.goldbigdragon.xkd.parsertester.AbstractParser;

import java.util.HashMap;
import java.util.Map;

// '내' 파서.. (xkd), 약간 개선된 두번째
public class XkdSecondParser extends AbstractParser
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
        procOuter(placeholder, result, 0);
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

    private int procInner(Map<String, String> placeholder, StringBuilder sb, int pos)
    {
        int start = pos;
        int len = sb.length();

        while (pos < len)
        {
            char ch = sb.charAt(pos);
            pos++;

            // 대부분의 스크립터는 플레이스 홀더를
            // <<<abc> def> gef> 처럼 쓰지 않고
            // <playername>님 환영합니다! 처럼 사용하기 때문에
            // depth가 1 이상일 경우에는 >가 나올 확률이 크다.
            // 따라서 >에 대한 조건문을 앞단에 배치한다.
            if (ch == '>') break;
            else if (ch == '<')
            {
                pos = procInner(placeholder, sb, pos);
                len = sb.length();
            }
        }

        String key = sb.substring(start, pos - 1);
        String val = placeholder.get(key);

        if (val != null)
        {
            sb.delete(start - 1, pos);
            sb.insert(start - 1, val);
            pos = start - 1 + val.length();
        }

        return pos;
    }

    // 바깥용
    private void procOuter(Map<String, String> placeholder, StringBuilder sb, int pos)
    {
        int len = sb.length();

        while (pos < len)
        {
            char ch = sb.charAt(pos);
            pos++;

            // procInner에서 >까지 처리 완료하여 돌아오므로
            // >는 처리할 필요가 없다.
            if (ch == '<')
            {
                pos = procInner(placeholder, sb, pos);
                len = sb.length(); // 시간을 줄이기 위해 len도 변경이 예상되는 경우에만 재설정한다.
            }
        }
    }
}