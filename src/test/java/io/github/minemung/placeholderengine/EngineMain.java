/**
 * MIT License
 * <p>
 * Copyright (c) 2016 xkd <minemung@naver.com>
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.minemung.placeholderengine;

import io.github.minemung.placeholderengine.xkdfinal.XkdFinalProcessorB;
import net.epcipla.placer.test.PlacerParser;

/**
 * 메인
 * @author xkd
 */
public class EngineMain {
    /**
     * 시작 지점
     * @param args 실행 매개 변수 (사용되지 않음)
     */
    public static void main(String[] args) {
        // 새 테스트 엔진 생성
        TestEngine engine = new TestEngine();

        // 검증 조건을 설정한다
        {
            // 기본 검증 조건 (1)
            Condition condition = engine.getNewCondition()
                    .placeholder("<50>", "aa")
                    .placeholder("<ab>", "bb");

            String a = "";

            for (int i = 0; i <= 100; i++) {
                if (i == 50) {
                    a = a + "<<" + i + ">" + "asdf>";
                }
                a = a + "<" + i + ">";
            }

            condition.source(a).result("<0><1><2><3><4><5><6><7><8><9><10><11><12><13><14><15><16><17><18><19><20><21><22><23><24><25><26><27><28><29><30><31><32><33><34><35><36><37><38><39><40><41><42><43><44><45><46><47><48><49><aaasdf>aa<51><52><53><54><55><56><57><58><59><60><61><62><63><64><65><66><67><68><69><70><71><72><73><74><75><76><77><78><79><80><81><82><83><84><85><86><87><88><89><90><91><92><93><94><95><96><97><98><99><100>");
        }

        {
            // finalchild님의 검증 조건
            Condition condition = engine.getNewCondition();
            String a = "";

            for (int i = 0; i <= 100; i++) {
                if (i == 50) {
                    a = a + "<<" + i + ">" + "asdf>";
                }
                a = a + "<" + i + ">";
            }

            condition.source(a).result("no0;no1;no2;no3;no4;no5;no6;no7;no8;no9;no10;no11;no12;no13;no14;no15;no16;no17;no18;no19;no20;no21;no22;no23;no24;no25;no26;no27;no28;no29;no30;no31;no32;no33;no34;no35;no36;no37;no38;no39;no40;no41;no42;no43;no44;no45;no46;no47;no48;no49;<aaasdf>aano51;no52;no53;no54;no55;no56;no57;no58;no59;no60;no61;no62;no63;no64;no65;no66;no67;no68;no69;no70;no71;no72;no73;no74;no75;no76;no77;no78;no79;no80;no81;no82;no83;no84;no85;no86;no87;no88;no89;no90;no91;no92;no93;no94;no95;no96;no97;no98;no99;<100>");

            for (int i = 0; i < 100; i++) {
                condition.placeholder("<" + i + ">", "no" + i + ";");
            }

            condition.placeholder("<50>", "aa");
            condition.placeholder("<ab>", "bb");
        }

        {
            // 기본 검증 조건 (2)
            engine.getNewCondition()
                    .source("<50<50<50<50<50<50<50<50<50<50>>>>>>>>>>")
                    .result("")
                    .placeholder("<50>", "");
        }

        // 프로세서를 추가한다
        engine
                .addProcessor(new XkdFinalProcessorB())
                .addProcessor(new PlacerParser())
                .addProcessor(new XkdFinalProcessorB())
                .addProcessor(new PlacerParser())
                .addProcessor(new XkdFinalProcessorB());

        // 실행 조건을 설정한다
        engine
                .setBatchRepeat(5)
                .setBatchUnit(100000);

        // 테스트를 실행한다
        engine.execute();
    }
}