package com.naver.cafe.goldbigdragon.xkd.parsertester;

import com.naver.cafe.goldbigdragon.ah.placeholderparser.AhParser;
import com.naver.cafe.goldbigdragon.xkd.placeholderparser.XkdParser;
import net.epicpla.placer.Placer;
import net.epicpla.placer.legacy.OldPlacer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserTester
{
    // 테스트용 식을 준비한다
    private static String prepareTestExpression()
    {
        String a = "";

        for (int i = 0; i <= 100; i++)
        {
            if (i == 50)
            {
                a = a + "<<" + i + ">" + "asdf>";
            }
            a = a + "<" + i + ">";
        }

        return a;
    }

    public static void main(String[] args)
    {
        System.out.println("GoldenMine님과 Kiwiyou님의 파서는 GPLv3 라이센스이므로 호환되지 않아 제거하였습니다.");

        // 플레이스홀더 준비
        Map<String, String> placeholder = new HashMap<>();
        // 플레이스홀더 값을 많이 설정해서 성능 테스트할 때 주석 처리 해제하세요.
        /*
        for (int i = 0; i < 100; i ++) {
            placeholder.put("<" + i + ">", "no" + i + ";");
        }
        */
        placeholder.put("<50>", "aa");
        placeholder.put("<ab>", "bb");

        // 치환식 준비
        String exp = prepareTestExpression();

        // 파싱 반복 횟수 지정
        int REPEAT = 10000;
        //REPEAT = 	 999999;

        // 파서 준비. 어느 것을 먼저 하는지에 따라 속도가 달라지는 경우가 있으므로 Placer, OldPlacer를 여러 개 배치함
        List<AbstractParser> parsers = new ArrayList<>();
        parsers.add(new Placer());
        parsers.add(new OldPlacer());
        parsers.add(new XkdParser());
        parsers.add(new AhParser());
        parsers.add(new OldPlacer());
        parsers.add(new Placer());

        // 오류 검증용 결과값 모음
        List<String> result = new ArrayList<>();

        // 각 파서에 대해서
        for (AbstractParser parser : parsers)
        {
            // 파서 이름 가져오기
            String name = parser.getClass().getSimpleName();

            // 먼저 프리프로세싱이 필요한 파서에게 데이터를 보내준다.
            long prepare = System.currentTimeMillis();

            if (parser.prepare(exp, placeholder))
            {
                long preparetime = System.currentTimeMillis() - prepare;
                System.out.println(name + ": 준비 완료! (" + preparetime + "ms 소요됨)");
            }

            // 3번 테스트
            for (int count = 1; count <= 3; count++)
            {
                // 시간을 잰다
                long work = System.currentTimeMillis();
                String ret = null;

                for (int repeat = 0; repeat < REPEAT ; repeat++)
                {
                    ret = parser.parse(exp, placeholder);
                }

                result.add(ret);
                long worktime = System.currentTimeMillis() - work;

                System.out.println(name + ": " + worktime + "ms 소요됨. (" + count + "회)");
            }
        }

        // 오류 검증.
        checkError(result);
    }

    private static void checkError(List<String> result)
    {
        // 비교 대상할 기존 값을 저장할 임시 변수
        String back = null;

        // 각각에 값에 대해서
        for (String cur : result)
        {
            // 첫 값이면
            if (back == null)
            {
                // 다음값으로 이동
                back = cur;
                continue;
            }

            // 기존 값과 이번 값이 다르면
            if (!cur.equals(back))
            {
                // 다시 만들어와야만 한다!
                System.out.println("오류 발견, 결과 값이 서로 다릅니다!");
                System.out.println(back);
                System.out.println(cur);
                return;
            }
        }

        // 검사 성공!
        System.out.println("검증 완료! 모든 결과 값이 같습니다.");
    }
}