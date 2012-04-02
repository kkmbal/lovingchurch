package lc.common.util;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {
    /**
     * 확장할 수 있는 패딩상수 최대크기
     */
    private static final int PAD_LIMIT = 8192;

    /**
     * <p>	문자열 좌측의 공백을 제거하는 메소드
     * </p><pre>
     * String str = null;
     * String leftTrim = StringUtil.leftTrim(str);</pre>
     * @return	trimed string with white space removed from the front
     */
    public static String leftTrim(String str) {
        int len = str.length();
        int idx = 0;

        while ((idx < len) && (str.charAt(idx) <= ' ')) {
            idx++;
        }

        return str.substring(idx, len);
    }

    /**
     * <p>	문자열 우측의 공백을 제거하는 메소드
     * </p><pre>
     * String str = null;
     * String rightTrim = StringUtil.rightTrim(str);</pre>
     * @return	trimed string with white space removed from the end.
     */
    public static String rightTrim(String str) {
        int len = str.length();

        while ((0 < len) && (str.charAt(len - 1) <= ' ')) {
            len--;
        }

        return str.substring(0, len);
    }


    /**
     * 지정된 크기만큼 문자열 앞부분을 분리하는 메소드<br>
     * 입력 str이 null이면 빈 문자열("")을 반환한다.
     *
     * <p>	지정된 크기만큼 문자열 앞부분을 분리하는 메소드
     * </p><pre>
     * String str = ""abcdefghijk;
     * int size = 3;
     * String splitHead = StringUtil.splitHead(str,size); //splitHead = "abc"</pre>
     * @return	splitted string from the source string.
     */
    public static String splitHead(String str, int size) {
        if (str == null) {
            return "";
        }

        if (str.length() > size) {
            //str = str.substring(0, size) + "...";
        	str = str.substring(0, size);
        }

        return str;
    }

    /**
     * 지정된 크기만큼 문자열 뒷부분을 분리하는 메소드<br>
     * 입력 str이 null이면 빈 문자열("")을 반환한다.
     *
     * <p>	지정된 크기만큼 문자열 뒷부분을 분리하는 메소드
     * </p><pre>
     * String str = "abcdefgh";
     * int size = 3 ;
     * String splitTail = StringUtil.splitTail(str,size); //splitHead = "fgh"</pre>
     * @return	splitted string from the source string.
     */
    public static String splitTail(String str, int size) {
        if (str == null) {
            return "";
        }

        if (str.length() > size) {
            str = "..."+str.substring(str.length() - size);
            str = str.substring(str.length() - size);
        }

        return str;
    }

    /**
     * 문자열에서 char문자를 제거하는 메소드<br>
     * 입력 str이 null이면 빈 문자열("")을 반환한다.
     *
     * <p>	문자열에서 char문자를 제거하는 메소드
     * </p><pre>
     * String str = "abcdefgh";
     * char ch = 'c';
     * String removeChar = StringUtil.removeChar(str,ch); // removeChar = "abdefgh"</pre>
     * @return	String
     */
    public static String removeChar(String str, char ch) {
        if (str == null || str.trim().length() == 0) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ch) {
                continue;
            }
            sb.append(str.charAt(i));
        }

        return sb.toString();
    }

    /**
     * 문자열에서 '/', '-', ':', ',', '.', '%'의 char문자와 공백를 제거하는 메소드
     *
     * <p>	char문자와 공백를 제거하는 메소드
     * </p><pre>
     * String str = null;
     * String removeCharAll = StringUtil.removeCharAll(str);</pre>
     * @return	String
     */
    public static String removeCharAll(String str) {
        String value = null;
        value = removeChar(str, '/'); // '/' 제거
        value = removeChar(value, '-'); // '-' 제거
        value = removeChar(value, ':'); // ':' 제거
        value = removeChar(value, ','); // ',' 제거
        value = removeChar(value, '.'); // '.' 제거
        value = removeChar(value, '%'); // '%' 제거
        value = value.trim(); // 문자열 앞뒤 공백제거

        return value;
    }

    /**
     * <p>	해당 소스 문자열 내에서 대상 목록의 문자를 삭제한다.
     * </p><pre>
     * String source = "abcdefghabcedfeeee";
     * char[] targetChars = = {'c','d','e'};
     * String removeCharAll = StringUtil.removeCharAll(source, targetChars); // abfghabf</pre>
     * @param source 원본 문자열
     * @param targetChars 삭제 대상 문자 목록
     * @return	String
     */
    public static String removeCharAll(String source, char[] targetChars) {
        String value = source;
        for (int i = 0; i < targetChars.length; i++) {
            value = removeChar(value, targetChars[i]);
        }

        return value.trim();
    }

    /**
     * <p>	원하는 길이만큼 스페이스를 붙인다.
     * </p><pre>
     * String str = null;
     * int i ;
     * String addSpace = StringUtil.addSpace(str,i);</pre>
     * @return	String
     */
    public static String addSpace(String str, int i) {
        StringBuilder sb = null;

        if (str != null) {
            sb = new StringBuilder(str.length() + i);
            sb.append(str);
        }
        else {
            if (i != 0) {
                sb = new StringBuilder(i);
            }
            else {
                return "";
            }
        }

        for (int j = 0; j < i; j++) {
            sb.append(' ');
        }
        return sb.toString();
    }

    /**
     * <p>	입력된 문자열의 값이 null이면 빈 문자열을 반환함
     * </p><pre>
     * String str = null;
     * String nullToSpace = StringUtil.nullToSpace(str);</pre>
     * @return	String
     */
    public static String nullToSpace(String str) {
        if (str == null || str.length() <= 0) {
            return "";
        }
        else {
            return str;
        }
    }

    /**
     * <p>	문장에서 특정 문자를 찾아 다른 문자로 대체
     * </p><pre>
     * String str = null;
     * String searchChars = null;
     * String replaceChars = null
     * String replaceChars = StringUtil.replaceChars(str,searchChars,replaceChars);</pre>
     * @param str 원본 문자열
     * @param searchChars 교체될 문자열
     * @param replaceChars 교체할 문자열
     * @return	String
     */
    public static String replaceChars(String str, String searchChars, String replaceChars) {
        if (isEmpty(str) || isEmpty(searchChars)) {
            return str;
        }
        if (replaceChars == null) {
            replaceChars = "";
        }
        boolean modified = false;
        StringBuffer buf = new StringBuffer(str.length());
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            int index = searchChars.indexOf(ch);
            if (index >= 0) {
                modified = true;
                if (index < replaceChars.length()) {
                    buf.append(replaceChars.charAt(index));
                }
            }
            else {
                buf.append(ch);
            }
        }
        if (modified) {
            return buf.toString();
        }
        else {
            return str;
        }
    }

    /**
     * <p>	문자열 검사 : null,''검사
     * </p><pre>
     * String str = null;
     * boolean isEmpty = StringUtil.isEmpty(str);</pre>
     * @return	boolean
     */
//    public static boolean isEmpty(String str) {
//        if (str != null) {
//            String trimCheck = str.trim();
//            if (trimCheck.length() == 0) {
//                return true;
//            }
//        }
//        else {
//            return true;
//        }
//
//        return false;
//    }

    /**
     * <p>	문자열 검사 : null,''검사
     * </p><pre>
     * String str = null;
     * boolean isEmpty = StringUtil.isEmpty(str);</pre>
     * @return	boolean
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * <p>	공백없애고 문자열이 같은지 비교함.
     * </p><pre>
     * String baseStr = null;
     * String targetStr = null;
     * boolean trimEquals = StringUtil.trimEquals(str);</pre>
     * @return	boolean
     */
    public static boolean trimEquals(String baseStr, String targetStr) {
        String trimBaseStr = baseStr.trim();
        String trimTargetStr = targetStr.trim();

        return trimBaseStr.equals(trimTargetStr);
    }

    /**
     * 지정된 문자가 제공된 길이만큼 반복된 문자열을 반환한다.
     * <p>
     *
     * <pre>
     *          StringUtil.padding(0, 'e')  = &quot;&quot;
     *          StringUtil.padding(3, 'e')  = &quot;eee&quot;
     *          StringUtil.padding(-2, 'e') = IndexOutOfBoundsException
     * </pre>
     *
     * <p>	지정된 문자가 제공된 길이만큼 반복된 문자열을 반환한다.
     * </p><pre>
     * int repeat ;
     * char padChar = null;
     * String padding = StringUtil.padding(repeat,padChar);</pre>
     * @param repeat 지정된 문자가 반복될 수
     * @param padChar 반복될 문자
     * @return 지정된 문자가 제공된 길이만큼 반복된 문자열
     * @throws IndexOutOfBoundsException if <code>repeat &lt; 0</code>
     */
    private static String padding(int repeat, char padChar) {
        // 패딩에 사용될 문자열의 배열
        // 효율적인 공간 패딩에 사용되며 각 문자열의 길이는 필요한 만큼 확장된다.
        String[] PADDING = new String[Character.MAX_VALUE];
        // be careful of synchronization in this method
        // we are assuming that get and set from an array index is atomic
        String pad = PADDING[padChar];
        if (pad == null) {
            pad = String.valueOf(padChar);
        }
        while (pad.length() < repeat) {
            pad = pad.concat(pad);
        }
        PADDING[padChar] = pad;
        return pad.substring(0, repeat);
    }

    /**
     * 지정된 문자로 입력된 사이즈만큼 왼쪽에 패딩된 문자열을 반환한다.
     *
     * <pre>
     *          StringUtil.leftPad(null, *, *)     = null
     *          StringUtil.leftPad(&quot;&quot;, 3, 'z')     = &quot;zzz&quot;
     *          StringUtil.leftPad(&quot;bat&quot;, 3, 'z')  = &quot;bat&quot;
     *          StringUtil.leftPad(&quot;bat&quot;, 5, 'z')  = &quot;zzbat&quot;
     *          StringUtil.leftPad(&quot;bat&quot;, 1, 'z')  = &quot;bat&quot;
     *          StringUtil.leftPad(&quot;bat&quot;, -1, 'z') = &quot;bat&quot;
     * </pre>
     *
     * <p>	지정된 문자로 입력된 사이즈만큼 왼쪽에 패딩된 문자열을 반환한다.
     * </p><pre>
     * String str = null;
     * int size ;
     * char padChar = null;
     * String leftPad = StringUtil.leftPad(str,size,padChar);</pre>
     * @param str 패딩될 문자열(널이 입력될 수 있다.)
     * @param size 패딩할 사이즈
     * @param padChar 패딩에 사용될 문자
     * @return 왼쪽에 패딩된 문자열 또는 패딩할 필요가 없다면 원래의 문자열 또는 입력된 문자열이 널이면 널을 반환.
     */
    public static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) {
            return leftPad(str, String.valueOf(padChar), size);
        }
        return padding(pads, padChar).concat(str);
    }

    /**
     * 지정된 문자열로 입력된 사이즈만큼 왼쪽에 패딩된 문자열을 반환한다.
     *
     * <pre>
     *          StringUtil.leftPad(null, *, *)      = null
     *          StringUtil.leftPad(&quot;&quot;, 3, &quot;z&quot;)      = &quot;zzz&quot;
     *          StringUtil.leftPad(&quot;bat&quot;, 3, &quot;yz&quot;)  = &quot;bat&quot;
     *          StringUtil.leftPad(&quot;bat&quot;, 5, &quot;yz&quot;)  = &quot;yzbat&quot;
     *          StringUtil.leftPad(&quot;bat&quot;, 8, &quot;yz&quot;)  = &quot;yzyzybat&quot;
     *          StringUtil.leftPad(&quot;bat&quot;, 1, &quot;yz&quot;)  = &quot;bat&quot;
     *          StringUtil.leftPad(&quot;bat&quot;, -1, &quot;yz&quot;) = &quot;bat&quot;
     *          StringUtil.leftPad(&quot;bat&quot;, 5, null)  = &quot;  bat&quot;
     *          StringUtil.leftPad(&quot;bat&quot;, 5, &quot;&quot;)    = &quot;  bat&quot;
     * </pre>
     *
     * <p>	지정된 문자열로 입력된 사이즈만큼 왼쪽에 패딩된 문자열을 반환한다.
     * </p><pre>
     * String str = null;
     * int size ;
     * String addStr = null;
     * String leftPad = StringUtil.leftPad(str,addStr,size);</pre>
     * @param str 패딩될 문자열(널이 입력될 수 있다.)
     * @param addStr 패딩에 사용될 문자열
     * @param size 패딩할 사이즈
     * @return 왼쪽에 패딩된 문자열 또는 패딩할 필요가 없다면 원래의 문자열 또는 입력된 문자열이 널이면 널을 반환.
     */
    public static String leftPad(String str, String addStr, int size) {
        if (str == null) {
            return null;
        }
        if (addStr == null || addStr.length() == 0) {
            addStr = " ";
        }
        int padLen = addStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return leftPad(str, size, addStr.charAt(0));
        }

        if (pads == padLen) {
            return addStr.concat(str);
        }
        else if (pads < padLen) {
            return addStr.substring(0, pads).concat(str);
        }
        else {
            char[] padding = new char[pads];
            char[] padChars = addStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return new String(padding).concat(str);
        }

        // if (str == null) {
        // return "";
        // }
        //
        // StringBuffer sb = new StringBuffer();
        // for (int i = str.length(); i < count; i++) {
        // sb.append(addStr);
        // }
        // sb.append(str);
        //
        // return sb.toString();
    }

    /**
     * 공백문자(' ')로 입력된 사이즈만큼 왼쪽에 패딩된 문자열을 반환한다.
     * <p>
     *
     * <pre>
     *          StringUtil.leftPad(null, *)   = null
     *          StringUtil.leftPad(&quot;&quot;, 3)     = &quot;   &quot;
     *          StringUtil.leftPad(&quot;bat&quot;, 3)  = &quot;bat&quot;
     *          StringUtil.leftPad(&quot;bat&quot;, 5)  = &quot;  bat&quot;
     *          StringUtil.leftPad(&quot;bat&quot;, 1)  = &quot;bat&quot;
     *          StringUtil.leftPad(&quot;bat&quot;, -1) = &quot;bat&quot;
     * </pre>
     *
     * <p>	공백문자(' ')로 입력된 사이즈만큼 왼쪽에 패딩된 문자열을 반환한다.
     * </p><pre>
     * String str = null;
     * int size ;
     * String leftPad = StringUtil.leftPad(str,size);</pre>
     * @param str 패딩될 문자열(널이 입력될 수 있다.)
     * @param size 패딩할 사이즈
     * @return 왼쪽에 패딩된 문자열 또는 패딩할 필요가 없다면 원래의 문자열 또는 입력된 문자열이 널이면 널을 반환.
     */
    public static String leftPad(String str, int size) {
        return leftPad(str, size, ' ');
    }

    /**
     * 지정된 문자로 입력된 사이즈만큼 오른쪽에 패딩된 문자열을 반환한다.
     * <p>
     *
     * <pre>
     *          StringUtil.rightPad(null, *, *)     = null
     *          StringUtil.rightPad(&quot;&quot;, 3, 'z')     = &quot;zzz&quot;
     *          StringUtil.rightPad(&quot;bat&quot;, 3, 'z')  = &quot;bat&quot;
     *          StringUtil.rightPad(&quot;bat&quot;, 5, 'z')  = &quot;batzz&quot;
     *          StringUtil.rightPad(&quot;bat&quot;, 1, 'z')  = &quot;bat&quot;
     *          StringUtil.rightPad(&quot;bat&quot;, -1, 'z') = &quot;bat&quot;
     * </pre>
     *
     * <p>	지정된 문자로 입력된 사이즈만큼 오른쪽에 패딩된 문자열을 반환한다.
     * </p><pre>
     * String str = null;
     * int size ;
     * char padChar = null;
     * String rightPad = StringUtil.rightPad(str,size,padChar);</pre>
     * @param str 패딩될 문자열(널이 입력될 수 있다.)
     * @param size 패딩할 사이즈
     * @param padChar 패딩에 사용될 문자
     * @return 오른쪽에 패딩된 문자열 또는 패딩할 필요가 없다면 원래의 문자열 또는 입력된 문자열이 널이면 널을 반환.
     */
    public static String rightPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) {
            return rightPad(str, String.valueOf(padChar), size);
        }
        return str.concat(padding(pads, padChar));
    }

    /**
     * 지정된 문자열로 입력된 사이즈만큼 오른쪽에 패딩된 문자열을 반환한다.
     * <p>
     *
     * <pre>
     *          StringUtil.rightPad(null, *, *)      = null
     *          StringUtil.rightPad(&quot;&quot;, 3, &quot;z&quot;)      = &quot;zzz&quot;
     *          StringUtil.rightPad(&quot;bat&quot;, 3, &quot;yz&quot;)  = &quot;bat&quot;
     *          StringUtil.rightPad(&quot;bat&quot;, 5, &quot;yz&quot;)  = &quot;batyz&quot;
     *          StringUtil.rightPad(&quot;bat&quot;, 8, &quot;yz&quot;)  = &quot;batyzyzy&quot;
     *          StringUtil.rightPad(&quot;bat&quot;, 1, &quot;yz&quot;)  = &quot;bat&quot;
     *          StringUtil.rightPad(&quot;bat&quot;, -1, &quot;yz&quot;) = &quot;bat&quot;
     *          StringUtil.rightPad(&quot;bat&quot;, 5, null)  = &quot;bat  &quot;
     *          StringUtil.rightPad(&quot;bat&quot;, 5, &quot;&quot;)    = &quot;bat  &quot;
     * </pre>
     *
     * <p>	지정된 문자열로 입력된 사이즈만큼 오른쪽에 패딩된 문자열을 반환한다.
     * </p><pre>
     * String str = null;
     * int count ;
     * String addStr = null;
     * String rightPad = StringUtil.rightPad(str,addStr,count);</pre>
     * @param str 패딩될 문자열(널이 입력될 수 있다.)
     * @param addStr 패딩에 사용될 문자열
     * @param count 사이즈
     * @return 오른쪽에 패딩된 문자열 또는 패딩할 필요가 없다면 원래의 문자열 또는 입력된 문자열이 널이면 널을 반환.
     */
    public static String rightPad(String str, String addStr, int count) {
        if (str == null) {
            return null;
        }
        if (addStr == null || addStr.length() == 0) {
            addStr = " ";
        }
        int padLen = addStr.length();
        int strLen = str.length();
        int pads = count - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return rightPad(str, count, addStr.charAt(0));
        }

        if (pads == padLen) {
            return str.concat(addStr);
        }
        else if (pads < padLen) {
            return str.concat(addStr.substring(0, pads));
        }
        else {
            char[] padding = new char[pads];
            char[] padChars = addStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return str.concat(new String(padding));
        }
        // if (str == null) {
        // return "";
        // }
        //
        // StringBuffer sb = new StringBuffer();
        // sb.append(str);
        // for (int i = str.length(); i < count; i++) {
        // sb.append(addStr);
        // }
        //
        // return sb.toString();
    }

    /**
     * 공백문자(' ')로 입력된 사이즈만큼 오른쪽에 패딩된 문자열을 반환한다.
     * <p>
     *
     * <pre>
     *          StringUtil.rightPad(null, *)   = null
     *          StringUtil.rightPad(&quot;&quot;, 3)     = &quot;   &quot;
     *          StringUtil.rightPad(&quot;bat&quot;, 3)  = &quot;bat&quot;
     *          StringUtil.rightPad(&quot;bat&quot;, 5)  = &quot;bat  &quot;
     *          StringUtil.rightPad(&quot;bat&quot;, 1)  = &quot;bat&quot;
     *          StringUtil.rightPad(&quot;bat&quot;, -1) = &quot;bat&quot;
     * </pre>
     *
     * <p>	공백문자(' ')로 입력된 사이즈만큼 오른쪽에 패딩된 문자열을 반환한다.
     * </p><pre>
     * String str = null;
     * int size ;
     * String rightPad = StringUtil.rightPad(str,size);</pre>
     * @param str 패딩될 문자열(널이 입력될 수 있다.)
     * @param size 패딩할 사이즈
     * @return 오른쪽에 패딩된 문자열 또는 패딩할 필요가 없다면 원래의 문자열 또는 입력된 문자열이 널이면 널을 반환.
     */
    public static String rightPad(String str, int size) {
        return rightPad(str, size, ' ');
    }

    /**
     * SQL Injection을 막기 위해 특수문자를 대체한다. 특수문자들 - '";)%<>(&+ 바꿀 수 없는 문자들 - ";)%
     * 업무에 따라 대체 가능한 문자들 - <>'(&+
     *
     * <p>	SQL Injection을 막기 위해 특수문자를 대체한다
     * </p><pre>
     * String str = null;
     * String includeCharters = null ;
     * String replaceSQLString = StringUtil.replaceSQLString(str,includeCharters);</pre>
     * @param str 원본 문자열
     * @param includeCharters 대체할 특수문자중에서 업무적으로 허용할 문자열
     * @return 허용할 문자열을 제외한 나머지 특수문자를 대체하여 원본 문자열을 리턴
     */
    public static String replaceSQLString(String str, String includeCharters) {
        char[] mustExclude = { '"', ';', ')', '%' };
        char[] optionalExclude = { '\'', '<', '>', '(', '&', '+' };
        char[] include = {};

        if (includeCharters != null && includeCharters.length() > 0) {
            include = includeCharters.toCharArray();
        }

        int optionalSize = optionalExclude.length;
        int includeSize = include.length;

        int[] mustIdx = new int[optionalSize];
        int totalCheckCount = 0;

        for (int i = 0; i < optionalSize; i++) {
            boolean isContain = false;
            for (int j = 0; j < includeSize; j++) {
                if (optionalExclude[i] == include[j]) {
                    isContain = true;
                    break;
                }
            }
            if (!isContain) {
                mustIdx[totalCheckCount++] = i;
            }
        }

        // 특수문자 변환
        int mustSize = mustExclude.length;
        for (int k = 0; k < mustSize; k++) {
            str = str.replace(mustExclude[k], '_');
        }

        // 허용할 문자열이 빠진 변환할 문자로 특수문자 변환
        for (int k = 0; k < totalCheckCount; k++) {
            str = str.replace(optionalExclude[mustIdx[k]], '_');
        }

        return str;
    }

    /**
     * SQL Injection을 막기 위해 특수문자를 대체한다. 특수문자들 - '";)%<>(&+ 바꿀 수 없는 문자들 - ";)%
     * 업무에 따라 대체 가능한 문자들 - <>'(&+
     *
     * <p>	SQL Injection을 막기 위해 특수문자를 대체한다
     * </p><pre>
     * String str = null;
     * String replaceSQLString = StringUtil.replaceSQLString(str);</pre>
     * @param str 원본 문자열
     * @return 특수문자를 대체하여 원본 문자열을 리턴
     */
    public static String replaceSQLString(String str) {
        return replaceSQLString(str, "");
    }

    /**
     * <p>문자열에서 정의된 모든 공백문자를 제거한다.</p>
     *
     * <pre>
     * StringUtil.trim(null)         = null
     * StringUtil.trim("")           = ""
     * StringUtil.trim("abc")        = "abc"
     * StringUtil.trim("   ab  c  ") = "abc"
     * </pre>
     *
     * @param str  the String to delete whitespace from, may be null
     * @return the String without whitespaces, <code>null</code> if null String input
     */
    public static String trim(String str) {
        if (isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        char[] chs = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                chs[count++] = str.charAt(i);
            }
        }
        if (count == sz) {
            return str;
        }
        return new String(chs, 0, count);
    }

    /**
     * <p>문자열에서 정의된 모든 공백문자를 제거한다.</p>
     *
     * <pre>
     * StringUtil.trim(null)         = null
     * StringUtil.trim("")           = ""
     * StringUtil.trim("abc")        = "abc"
     * StringUtil.trim("   ab  c  ") = "abc"
     * </pre>
     *
     * @param str  the String to delete whitespace from, may be null
     * @return the String without whitespaces, <code>null</code> if null String input
     */
    public static boolean isWhitespace(String str) {
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }


    /**

     * 전달된 문자가  null이거나 빈 문자열인지 체크
     *  <pre>
     * StringUtil.isEmptyStr(null)		= true
     * StringUtil.isEmptyStr("")			= true
     * StringUtil.isEmptyStr("abc")   = false
     * </pre>
     *
     * @name_ko Null 또는 빈 문자열 체크
     * @param inStr 입력된문자열
     * @return boolean 입력된 문자가 Null 또는 빈 문자열인 경우 true, 아니면 false
     */

    public static final boolean isEmptyStr(String inStr) {

        if (inStr == null || StringUtil.isWhitespace(inStr))

            return true;

        else

            return false;

    }



    /**

     * 전달된 ArrayList가  null이거나 size()가 0인지 체크
     *
     * @name_ko Null 또는 빈 배열 체크
     * @param inArr 입력된 ArrayList
     * @return boolean 입력된  ArrayList가  null이거나 size()가 0인 경우 true, 아니면 false

     */

    public static final boolean isEmptyArr(ArrayList inArr) {

        if (inArr == null || inArr.size() == 0)
            return true;
        else
            return false;
    }
    
    public static String convertToCamelCase(String data) {
		StringBuffer	sb	=	new	StringBuffer();
		String[] 		str	= 	data.split("_");
		boolean			firstTime	=	true;
		
		for	(String temp : str) 
		{
			if	(firstTime) 
			{
				sb.append	(temp.toLowerCase());
				firstTime 	= 	false;
			} 
			else 
			{
				sb.append	(Character.toUpperCase(temp.charAt(0)));
				sb.append	(temp.substring(1).toLowerCase());
			}
		}
        return sb.toString(); 		
	}
	
	public	static	String	capitalizeCamelCase(String data) {
		
		StringBuffer	sb	=	new	StringBuffer();
		String	temp	=	convertToCamelCase(data);
		
		sb.append	(Character.toUpperCase(temp.charAt(0)));
		sb.append	(temp.substring(1));
		
		return sb.toString();
	}
	
//	/*******************************************************************
//	 * <pre>
//	 * Camel 표기법으로 처리된 변수명에서 컬럼명을 추출한다.
//	 * </pre>
//	 * @param data
//	 * @return
//	 *****************************************************************
//	 */
//	public static String convClmnId(String data) {
//		
//		if	(data.startsWith(SmartConstants.SQL_NXT_PREFIX))
//		{
//			data	=	toLowerFirstLetter(data.replaceFirst(SmartConstants.SQL_NXT_PREFIX, ""));	
//		}
//		else	if	(data.startsWith(SmartConstants.SQL_PRED_PREFIX))
//		{
//			data	=	toLowerFirstLetter(data.replaceFirst(SmartConstants.SQL_PRED_PREFIX, ""));	
//		}
//		else	if	(data.startsWith(SmartConstants.SQL_PARAM_INDI))
//		{
//			data	=	data.replaceFirst(SmartConstants.SQL_PARAM_INDI, "");	
//		}
//		
//		StringBuffer	sb	=	new	StringBuffer();
//		char[]		ca		=	data.toCharArray();
//		
////		Camel 표기법중 앞에 소문자로 표현된 부문을 테이블 ID로 인식 할 것
////		첫자리는 preFix이기에 제외함
////		대문자가 나오는 경우네는 "_"를 앞에 붙여서 컬럼명으로 전환
//		for (int i = 0; i < ca.length; i++) 
//		{
//			
//			if	(	ca[i]	>=	'A'	&&	ca[i]	<=	'Z'	)
//			{
//				sb.append	("_");
//			}
//			
//			if	(sb.length()	>	0)	sb.append(ca[i]);
//		}
//        return sb.toString().replaceFirst("^_","").toUpperCase(); 		
//	}

	
	/**
	 * <p>
	 * 문자열중 첫문자를 소문자로 변환하는 메소드
	 * </p>
	 * @param data 입력 문자열
	 * @return String 변환 문자열
	 */
	public static String toLowerFirstLetter(String data) {
		String firstLetter = data.substring(0, 1).toLowerCase();
		String restLetters = data.substring(1);
		return firstLetter + restLetters;
	}
	
	/* <p>
	 * 문자열중 첫문자를 대문자로 변환하는 메소드
	 * </p>
	 * @param data 입력 문자열
	 * @return String 변환 문자열
	 */
	public static String toUpperFirstLetter(String data) {
		String firstLetter = data.substring(0, 1).toUpperCase();
		String restLetters = data.substring(1);
		return firstLetter + restLetters;
	}
	
	/**
	 * startText 와  endText를 제거하고 안에 있는 내용만 보존
	 * 
	 * @param startText  
	 * @param endText
	 * @param text
	 * @return
	 */
	 public static String replaceAllPattern( String startText, String endText,String replaceStartText,String replaceEndText ,String text )
	 {

		// 정규식 생성
		  StringBuffer regExp = new StringBuffer();
		  regExp.append( getUnicodeForRegExp( startText ) ).append( "(.*?)" )
		    .append( getUnicodeForRegExp( endText ) );

		  // 정규식 셋팅
		  Pattern p = Pattern.compile( regExp.toString() );

		  Matcher m = p.matcher( text );

		  // 검색된 문자열 저장 변수
		  String temp = null;

		  StringBuffer sb = new StringBuffer();

		  while( m.find() )
		  {
		   temp = m.group();
		   System.out.println("temp------------>"+temp);	
		   m.appendReplacement( sb,replaceStartText+ temp.substring( startText.length(),
		     temp.length() - endText.length() ).trim()+ replaceEndText);
		  }

		  m.appendTail( sb );

		  return sb.toString();
	 }

	 private static String getUnicodeForRegExp( String text )
	 {
	  String temp = null;

	  StringBuffer sb = new StringBuffer();

	  for( int i = 0; i < text.length(); i++ )
	  {
	   temp = Integer.toHexString( (int) text.charAt( i ) ).toUpperCase();

	   sb.append( "\\u" );

	   if( temp.length() != 4 )
	    sb.append( "00" ).append( temp );
	   else
	    sb.append( temp );
	  }

	  return sb.toString();
	 }

	
	
	public static String toUpperFirstAlias(String table, String column){
		if(isEmpty(table)){
			return toUpperFirstLetter(convertToCamelCase(column));
		}else{
			return toUpperFirstLetter(replaceChars(table,"_", "").toLowerCase()) + capitalizeCamelCase(column);
		}
//		return StringUtil.convertToCamelCase(table) + StringUtil.capitalizeCamelCase(column);
	}

	public static String commaMask(String str, int pos) {
		if(str == null) return str;
		String resultValue = null;
		try{
			DecimalFormat df = new DecimalFormat();
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setGroupingSeparator(',');
			df.setGroupingSize(pos);
			df.setDecimalFormatSymbols(dfs);
			resultValue = df.format(Double.parseDouble(str)).toString();
		}catch(Exception e){
			//e.printStackTrace();
			System.out.println(e);
			return str;
		}

		return resultValue;
	}

	public static String commaMask(long src, int pos) {
		return commaMask((new Long(src)).toString(), pos);
	}

	public static String commaMask(double src, int pos) {
		return commaMask((new Double(src)).toString(), pos);
	}

	public static String commaMask(String str) {
		return commaMask(str, 3);
	}

	public static String commaMask(long src) {
		return commaMask((new Long(src)).toString(), 3);
	}

	public static String commaMask(double src) {
		return commaMask((new Double(src)).toString(), 3);
	}
	
	public static boolean isHangul(char inputChar) {
		String unicodeBlock = Character.UnicodeBlock.of(inputChar).toString();
		return ((unicodeBlock.equals("HANGUL_JAMO"))
				|| (unicodeBlock.equals("HANGUL_SYLLABLES")) || (unicodeBlock
				.equals("HANGUL_COMPATIBILITY_JAMO")));
	}

	public static boolean isHangul(String inputStr, boolean full) {
		char[] chars = inputStr.toCharArray();
		if (!(full)) {
			for (int i = 0; i < chars.length; ++i) {
				if (isHangul(chars[i]))
					return true;
			}
			return false;
		}
		for (int i = 0; i < chars.length; ++i) {
			if (!(isHangul(chars[i])))
				return false;
		}
		return true;
	}
	
	/**
	 * jhlee 한글 길이로 자르기
	 * @param str
	 * @param size
	 * @return
	 */
	public static String cutTailHan(String str, int size) {
		if (str == null) return "";
		String value = null;
		int i = 0;
		int b = 0;
		boolean isHangul = false;
		byte[] strBytes = str.getBytes();
		if (strBytes.length <= size * 2) {
			value = str;
		}
		else {
			for (i = 0; i < str.length(); i++) {
				isHangul = (str.charAt(i) >= '\uAC00' && str.charAt(i) <= '\uD7A3') ? true : false;
				if (isHangul) {
					b = b + 2;
				}
				else {
					b = b + 1;
				}
				if (b >= size * 2) break;
			}
			value = new String(strBytes, 0, b).toString();
			value = value + "...";
		}
		return value;
	}	
	
	public static String getDateString(String date){
		if(date == null || date.equals("") || date.length() < 14) return date;
		String yyyy = date.substring(0 ,4);
		String MM = date.substring(4,6);
		String dd = date.substring(6,8);
		String hh = date.substring(8,10);
		String mm = date.substring(10,12);
		String ss = date.substring(12);
		
		StringBuilder sb = new StringBuilder();
		return sb.append(yyyy).append("-").append(MM).append("-").append(dd).append(" ").append(hh).append(":").append(mm).append(":").append(ss).toString();
	}
	
	public static String authdataUPLUS(String mid, String tid) throws Exception{
		String LGD_MERTKEY = "22ca2abd8da9d80b0dae7e086f80bd01";

			StringBuffer sb = new StringBuffer();
			sb.append(mid);
			sb.append(tid);
			sb.append(LGD_MERTKEY);

			byte[] bNoti = sb.toString().getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(bNoti);

			StringBuffer strBuf = new StringBuffer();
			for (int i=0 ; i < digest.length ; i++) {
			int c = digest[i] & 0xff;
			if (c <= 15){
			strBuf.append("0");
			}
			strBuf.append(Integer.toHexString(c));
			}

			String authdata = strBuf.toString();
			return authdata;
	}
}
