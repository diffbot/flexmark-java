package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("PointlessArithmeticExpression")
public class LineAppendableImplTest {
    @Test
    public void test_appendCharSb() throws Exception {
        StringBuilder sb = new StringBuilder();
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);

        fa.append(' ').appendTo(sb);
        assertEquals("", sb.toString());

        sb = new StringBuilder();
        fa.append('a').appendTo(sb);
        assertEquals("a\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append('a').append('b').append('c').line().appendTo(sb);
        assertEquals("abc\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append('a').append('b').append('\n').line().appendTo(sb);
        assertEquals("ab\n", sb.toString());
    }

    @Test
    public void test_appendChar() {
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);

        fa.append(' ');
        assertEquals("", fa.toString(0, 0));

        fa.append('a');
        assertEquals("a\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append('a').append('b').append('c').line();
        assertEquals("abc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append('a').append('b').append('\n').line();
        assertEquals("ab\n", fa.toString(0, 0));
    }

    @Test
    public void test_appendCharsSb() throws Exception {
        StringBuilder sb = new StringBuilder();
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);

        fa.append(" ").appendTo(sb);
        assertEquals("", sb.toString());

        fa.append("     ").appendTo(sb);
        assertEquals("", sb.toString());

        fa.append("a").appendTo(sb);
        assertEquals("a\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("abc").line().appendTo(sb);
        assertEquals("abc\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("abc").line().line().appendTo(sb);
        assertEquals("abc\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("abc").line().blankLine().appendTo(sb);
        assertEquals("abc\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("abc").line().blankLine().appendTo(sb, 1, 1);
        assertEquals("abc\n\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab\n").line().appendTo(sb);
        assertEquals("ab\n", sb.toString());

        sb = new StringBuilder();
        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab\n    \t \n\t   \n\n").line().appendTo(sb);
        assertEquals("ab\n", sb.toString());
    }

    @Test
    public void test_appendChars() {
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);

        fa.append(" ");
        assertEquals("", fa.toString(0, 0));

        fa.append("     ");
        assertEquals("", fa.toString(0, 0));

        fa.append("a");
        assertEquals("a\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("abc").line();
        assertEquals("abc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("abc").line().line();
        assertEquals("abc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("abc").line().blankLine();
        assertEquals("abc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("abc").line().blankLine();
        assertEquals("abc\n\n", fa.toString(1, 1));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab\n").line();
        assertEquals("ab\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab\n    \t \n\t   \n\n").line();
        assertEquals("ab\n", fa.toString(0, 0));
    }

    @Test
    public void test_lineIf() {
        LineAppendable fa;

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab").lineIf(false).append("c").line();
        assertEquals("abc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab").lineIf(true).append("c").line();
        assertEquals("ab\nc\n", fa.toString(0, 0));
    }

    @Test
    public void test_BlankLine() {
        LineAppendable fa;

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab").blankLine().append("c").line();
        assertEquals("ab\n\nc\n", fa.toString(1, 0));
        assertEquals("ab\nc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab").blankLine().blankLine().append("c").line();
        assertEquals("ab\n\nc\n", fa.toString(1, 0));
        assertEquals("ab\nc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab\n\n").blankLine().blankLine().append("c").line();
        assertEquals("ab\n\nc\n", fa.toString(1, 0));
        assertEquals("ab\nc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab\n    \t \n\t   \n\n").blankLine().append("c");
        assertEquals("ab\n\nc\n", fa.toString(1, 0));
        assertEquals("ab\nc\n", fa.toString(0, 0));
    }

    @Test
    public void test_BlankLineIf() {
        LineAppendable fa;

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab").blankLineIf(false).append("c").line();
        assertEquals("abc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab").blankLineIf(true).blankLine().append("c").line();
        assertEquals("ab\n\nc\n", fa.toString(1, 0));
        assertEquals("ab\nc\n", fa.toString(0, 0));
        assertEquals("ab\nc", fa.toString(0, -1));
    }

    @Test
    public void test_BlankLines() {
        LineAppendable fa;

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab").blankLine(1).append("c").line();
        assertEquals("ab\n\nc\n", fa.toString(1, 0));
        assertEquals("ab\nc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab").blankLine(1).blankLine(0).append("c").line();
        assertEquals("ab\n\nc\n", fa.toString(1, 0));
        assertEquals("ab\nc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab\n\n").blankLine(1).blankLine(2).append("c").line();
        assertEquals("ab\n\n\nc\n", fa.toString(2, 0));
        assertEquals("ab\n\nc\n", fa.toString(1, 0));
        assertEquals("ab\nc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab\n    \t \n\t   \n\n").blankLine(1).append("c");
        assertEquals("ab\n\nc\n", fa.toString(1, 0));
        assertEquals("ab\nc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab\n    \t \n\t   \n\n").blankLine(2).append("c");
        assertEquals("ab\n\n\nc\n", fa.toString(2, 0));
        assertEquals("ab\n\nc\n", fa.toString(1, 0));
        assertEquals("ab\nc\n", fa.toString(0, 0));
    }

    @Test
    public void test_noIndent() {
        String indent = "";
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.setIndentPrefix(indent);

        fa.indent().append(" ").unIndent();
        assertEquals("", fa.toString(0, 0));

        fa.indent().append("     ").unIndent();
        assertEquals("", fa.toString(0, 0));

        fa.indent().append("     ").indent().unIndent().unIndent();
        assertEquals("", fa.toString(0, 0));

        fa.indent().append("a").unIndent();
        assertEquals("a\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("abc").indent().unIndent();
        assertEquals("abc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL);
        fa.append("ab\n    \t \n\t   \n\n").indent().append("c").unIndent();
        assertEquals("ab\nc\n", fa.toString(0, 0));
    }

    @Test
    public void test_indent() {
        String indent = " ";
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append(" ").unIndent();
        assertEquals("", fa.toString(0, 0));

        fa.indent().append("     ").unIndent();
        assertEquals("", fa.toString(0, 0));

        fa.indent().append("     ").indent().unIndent().unIndent();
        assertEquals("", fa.toString(0, 0));

        fa.indent().append("a").unIndent();
        assertEquals(" a\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);
        fa.append("abc").indent().unIndent();
        assertEquals("abc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);
        fa.append("ab").indent().append("c").unIndent();
        assertEquals("ab\n c\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);
        fa.append("ab\n    \t \n\t   \n\n").indent().append("c").unIndent();
        assertEquals("ab\n c\n", fa.toString(0, 0));

        indent = "  ";
        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);
        fa.indent().append("a").unIndent();
        assertEquals("  a\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);
        fa.append("abc").indent().unIndent();
        assertEquals("abc\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);
        fa.append("ab").indent().append("c").unIndent();
        assertEquals("ab\n  c\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);
        fa.append("ab\n    \t \n\t   \n\n").indent().append("c").unIndent();
        assertEquals("ab\n  c\n", fa.toString(0, 0));
    }

    @Test
    public void test_openPreFormatted() {
        String indent = "  ";
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append("<pre>").openPreFormatted(false).append("abc\ndef \n\n").append("hij\n")
                .append("</pre>").closePreFormatted().unIndent();
        assertEquals("  <pre>abc\ndef \n\nhij\n</pre>\n", fa.toString(1, 0));
        assertEquals("  <pre>abc\ndef \n\nhij\n</pre>\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ").openPreFormatted(false).append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted().append("</div>").unIndent();
        assertEquals("  <p>this is a paragraph <div style=''>    some text\n    some more text\n  </div>\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ").line().openPreFormatted(false).append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted().append("</div>").unIndent();
        assertEquals("  <p>this is a paragraph\n  <div style=''>    some text\n    some more text\n  </div>\n", fa.toString(0, 0));

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ").indent().openPreFormatted(false).append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted().unIndent().append("</div>").unIndent();
        assertEquals("  <p>this is a paragraph\n    <div style=''>    some text\n    some more text\n  </div>\n", fa.toString(0, 0));
    }

    @Test
    public void test_lineCount() {
        String indent = "  ";
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append("<pre>")
                .openPreFormatted(false)
                .append("abc\ndef \n\n")
                .append("hij\n")
                .append("</pre>")
                .closePreFormatted()
                .unIndent();
        assertEquals("  <pre>abc\ndef \n\nhij\n</pre>\n", fa.toString(0, 0));
        assertEquals("  <pre>abc\ndef \n\nhij\n</pre>\n", fa.toString(1, 0));
        assertEquals(5, fa.getLineCount());

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);
        fa.append("ab")
                .indent()
                .append("c")
                .unIndent();
        assertEquals("ab\n  c\n", fa.toString(0, 0));
        assertEquals(2, fa.getLineCount());

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);
        fa.append("ab\n    \t \n\t   \n\n")
                .indent()
                .append("c")
                .unIndent();
        assertEquals("ab\n  c\n", fa.toString(0, 0));
        assertEquals(5, fa.getLineCount());

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ").openPreFormatted(false).append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted().append("</div>").unIndent();
        //assertEquals("  <p>this is a paragraph <div style=''>    some text\n    some more text\n  </div>\n", fa.toString(0));
        assertEquals(3, fa.getLineCount());

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);

        fa.indent().append("<p>this is a paragraph ").line().openPreFormatted(false).append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted().append("</div>").unIndent();
        //assertEquals("  <p>this is a paragraph\n  <div style=''>    some text\n    some more text\n  </div>\n", fa.toString(0));
        assertEquals(4, fa.getLineCount());

        fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL).setIndentPrefix(indent);

        fa.indent()
                .append("<p>this is a paragraph ")
                .indent()
                .openPreFormatted(false)
                .append("<div style=''>    some text\n    some more text\n")
                .closePreFormatted()
                .unIndent()
                .append("</div>")
                .unIndent();
        assertEquals("  <p>this is a paragraph\n    <div style=''>    some text\n    some more text\n  </div>\n", fa.toString(0, 0));
        assertEquals(4, fa.getLineCount());
    }

    @Test
    public void test_leadingSpace() {
        String indent = "";
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL & ~(LineAppendable.F_COLLAPSE_WHITESPACE | LineAppendable.F_TRIM_LEADING_WHITESPACE));
        fa.setIndentPrefix(indent);

        fa.append("  abc");
        assertEquals("  abc\n", fa.toString(0, 0));

        fa.append("     def");
        assertEquals("  abc\n     def\n", fa.toString(0, 0));

        fa.append("a");
        assertEquals("  abc\n     def\na\n", fa.toString(0, 0));
    }

    @Test
    public void test_leadingSpaceVaries() {
        String indent = "";
        LineAppendable fa = new LineAppendableImpl(LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);
        fa.setIndentPrefix(indent);

        int saved = fa.getOptions();
        fa.setOptions(saved & ~(LineAppendable.F_COLLAPSE_WHITESPACE | LineAppendable.F_TRIM_LEADING_WHITESPACE));
        fa.append("  abc");
        fa.setOptions(saved);
//        assertEquals("  abc\n", fa.toString(0));

        fa.append("     def\n");
        assertEquals("  abc def\n", fa.toString(0, 0));

        fa.append("a");
        assertEquals("  abc def\na\n", fa.toString(0, 0));
    }

    @Test
    public void test_withBuilder() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.append(sequence.subSequence(10, 19)).line();
        fa.append(sequence.subSequence(20, 29)).line();
        fa.append(sequence.subSequence(30, 39)).line();
        fa.append(sequence.subSequence(40, 49)).line();

        assertEquals("Line: " + 0, sequence.subSequence(0 * 10, 0 * 10 + 10), fa.getLine(0));
        assertEquals("Line: " + 1, sequence.subSequence(1 * 10, 1 * 10 + 10), fa.getLine(1));
        assertEquals("Line: " + 2, sequence.subSequence(2 * 10, 2 * 10 + 10), fa.getLine(2));
        assertEquals("Line: " + 3, sequence.subSequence(3 * 10, 3 * 10 + 10), fa.getLine(3));
        assertEquals("Line: " + 4, sequence.subSequence(4 * 10, 4 * 10 + 10), fa.getLine(4));

        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, f=bp }", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=0, tl=9, l=10, sumPl=0, sumTl=18, sumL=20, f=bp }", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=0, tl=9, l=10, sumPl=0, sumTl=27, sumL=30, f=bp }", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=0, sumTl=36, sumL=40, f=bp }", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=0, sumTl=45, sumL=50, f=bp }", fa.getLineInfo(4).toString());
    }

    @Test
    public void test_prefixAfterEol() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.pushPrefix().addPrefix("  ", true);
        fa.append(sequence.subSequence(10, 19)).line();
        fa.append(sequence.subSequence(20, 29)).line();
        fa.popPrefix(true);
        fa.append(sequence.subSequence(30, 39)).line();
        fa.append(sequence.subSequence(40, 49)).line();

        assertEquals("Line: " + 0, sequence.subSequence(0 * 10, 0 * 10 + 10), fa.getLine(0));
        assertEquals("Line: " + 1, sequence.subSequence(1 * 10, 1 * 10 + 10), fa.getLine(1));
        assertEquals("Line: " + 2, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(2 * 10, 2 * 10 + 10)), fa.getLine(2));
        assertEquals("Line: " + 3, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(3 * 10, 3 * 10 + 10)), fa.getLine(3));
        assertEquals("Line: " + 4, sequence.subSequence(4 * 10, 4 * 10 + 10), fa.getLine(4));

        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, f=bp }", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=0, tl=9, l=10, sumPl=0, sumTl=18, sumL=20, f=bp }", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=2, tl=9, l=12, sumPl=2, sumTl=27, sumL=32, f=bp }", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=2, tl=9, l=12, sumPl=4, sumTl=36, sumL=44, f=bp }", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=4, sumTl=45, sumL=54, f=bp }", fa.getLineInfo(4).toString());

        assertEquals("" +
                "0:2343568\n" +
                "1:2343568\n" +
                "  2:2343568\n" +
                "  3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(true, 2, 2));

        assertEquals("" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(false, 2, 2));
    }

    @Test
    public void test_prefix() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.pushPrefix().addPrefix("  ", false);
        fa.append(sequence.subSequence(10, 19)).line();
        fa.append(sequence.subSequence(20, 29)).line();
        fa.popPrefix(false);
        fa.append(sequence.subSequence(30, 39)).line();
        fa.append(sequence.subSequence(40, 49)).line();

        assertEquals("Line: " + 0, sequence.subSequence(0 * 10, 0 * 10 + 10), fa.getLine(0));
        assertEquals("Line: " + 1, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(1 * 10, 1 * 10 + 10)), fa.getLine(1));
        assertEquals("Line: " + 2, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(2 * 10, 2 * 10 + 10)), fa.getLine(2));
        assertEquals("Line: " + 3, sequence.subSequence(3 * 10, 3 * 10 + 10), fa.getLine(3));
        assertEquals("Line: " + 4, sequence.subSequence(4 * 10, 4 * 10 + 10), fa.getLine(4));

        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, f=bp }", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, f=bp }", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=2, tl=9, l=12, sumPl=4, sumTl=27, sumL=34, f=bp }", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=4, sumTl=36, sumL=44, f=bp }", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=4, sumTl=45, sumL=54, f=bp }", fa.getLineInfo(4).toString());

        assertEquals("" +
                "0:2343568\n" +
                "  1:2343568\n" +
                "  2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(true, 2, 2));

        assertEquals("" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(false, 2, 2));
    }

    @Test
    public void test_setPrefixLength() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.pushPrefix().addPrefix("  ", false);
        fa.append(sequence.subSequence(10, 19)).line();
        fa.append(sequence.subSequence(20, 29)).line();
        fa.popPrefix(false);
        fa.append(sequence.subSequence(30, 39)).line();
        fa.append(sequence.subSequence(40, 49)).line();

        assertEquals("Line: " + 0, sequence.subSequence(0 * 10, 0 * 10 + 10), fa.getLine(0));
        assertEquals("Line: " + 1, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(1 * 10, 1 * 10 + 10)), fa.getLine(1));
        assertEquals("Line: " + 2, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(2 * 10, 2 * 10 + 10)), fa.getLine(2));
        assertEquals("Line: " + 3, sequence.subSequence(3 * 10, 3 * 10 + 10), fa.getLine(3));
        assertEquals("Line: " + 4, sequence.subSequence(4 * 10, 4 * 10 + 10), fa.getLine(4));

        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, f=bp }", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, f=bp }", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=2, tl=9, l=12, sumPl=4, sumTl=27, sumL=34, f=bp }", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=4, sumTl=36, sumL=44, f=bp }", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=4, sumTl=45, sumL=54, f=bp }", fa.getLineInfo(4).toString());

        fa.setPrefixLength(2, 4);

        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, f=bp }", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, f=bp }", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=4, tl=7, l=12, sumPl=6, sumTl=25, sumL=34, f=}", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=6, sumTl=34, sumL=44, f=bp }", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=6, sumTl=43, sumL=54, f=bp }", fa.getLineInfo(4).toString());

        assertEquals("" +
                "0:2343568\n" +
                "  1:2343568\n" +
                "  2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(true, 2, 2));

        assertEquals("" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(false, 2, 2));
    }

    @Test
    public void test_setLine() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.pushPrefix().addPrefix("  ", false);
        fa.append(sequence.subSequence(10, 19)).line();
        fa.append(sequence.subSequence(20, 29)).line();
        fa.popPrefix(false);
        fa.append(sequence.subSequence(30, 39)).line();
        fa.append(sequence.subSequence(40, 49)).line();

        assertEquals("Line: " + 0, sequence.subSequence(0 * 10, 0 * 10 + 10), fa.getLine(0));
        assertEquals("Line: " + 1, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(1 * 10, 1 * 10 + 10)), fa.getLine(1));
        assertEquals("Line: " + 2, PrefixedSubSequence.prefixOf("  ", sequence.subSequence(2 * 10, 2 * 10 + 10)), fa.getLine(2));
        assertEquals("Line: " + 3, sequence.subSequence(3 * 10, 3 * 10 + 10), fa.getLine(3));
        assertEquals("Line: " + 4, sequence.subSequence(4 * 10, 4 * 10 + 10), fa.getLine(4));

        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, f=bp }", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, f=bp }", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=2, tl=9, l=12, sumPl=4, sumTl=27, sumL=34, f=bp }", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=4, sumTl=36, sumL=44, f=bp }", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=4, sumTl=45, sumL=54, f=bp }", fa.getLineInfo(4).toString());

        fa.setLine(2, "", "0123456");
        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, f=bp }", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, f=bp }", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=0, tl=7, l=8, sumPl=2, sumTl=25, sumL=30, f=bp }", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=2, sumTl=34, sumL=40, f=bp }", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=0, tl=9, l=10, sumPl=2, sumTl=43, sumL=50, f=bp }", fa.getLineInfo(4).toString());

        assertEquals("" +
                "0:2343568\n" +
                "  1:2343568\n" +
                "0123456\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(true, 2, 2));

        assertEquals("" +
                "0:2343568\n" +
                "1:2343568\n" +
                "0123456\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "", fa.toString(false, 2, 2));

        fa.setLine(4, "  ", "4:01234");
        assertEquals("Line: " + 0, "LineInfo{i=0, pl=0, tl=9, l=10, sumPl=0, sumTl=9, sumL=10, f=bp }", fa.getLineInfo(0).toString());
        assertEquals("Line: " + 1, "LineInfo{i=1, pl=2, tl=9, l=12, sumPl=2, sumTl=18, sumL=22, f=bp }", fa.getLineInfo(1).toString());
        assertEquals("Line: " + 2, "LineInfo{i=2, pl=0, tl=7, l=8, sumPl=2, sumTl=25, sumL=30, f=bp }", fa.getLineInfo(2).toString());
        assertEquals("Line: " + 3, "LineInfo{i=3, pl=0, tl=9, l=10, sumPl=2, sumTl=34, sumL=40, f=bp }", fa.getLineInfo(3).toString());
        assertEquals("Line: " + 4, "LineInfo{i=4, pl=2, tl=7, l=10, sumPl=4, sumTl=41, sumL=50, f=bp }", fa.getLineInfo(4).toString());

        assertEquals("" +
                "0:2343568\n" +
                "  1:2343568\n" +
                "0123456\n" +
                "3:2343568\n" +
                "  4:01234\n" +
                "", fa.toString(true, 2, 2));

        assertEquals("" +
                "0:2343568\n" +
                "1:2343568\n" +
                "0123456\n" +
                "3:2343568\n" +
                "4:01234\n" +
                "", fa.toString(false, 2, 2));
    }

    @Test
    public void test_maxBlankLines() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.append(sequence.subSequence(10, 19)).blankLine();
        fa.append(sequence.subSequence(20, 29)).blankLine(2);
        fa.append(sequence.subSequence(30, 39)).blankLine(3);
        fa.blankLine();

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(4, 4));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(3, 4));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(2, 4));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(1, 4));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(0, 4));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(4, 3));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n\n" +
                "", fa.toString(4, 2));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n" +
                "", fa.toString(4, 1));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n" +
                "", fa.toString(4, 0));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568" +
                "", fa.toString(4, -1));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(3, 3));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(2, 3));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(1, 3));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(0, 3));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(-1, 3));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n\n" +
                "", fa.toString(2, 2));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n" +
                "3:2343568\n\n\n" +
                "", fa.toString(1, 2));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n\n\n" +
                "", fa.toString(0, 2));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n\n\n" +
                "", fa.toString(-1, 2));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n" +
                "", fa.toString(2, 1));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n" +
                "", fa.toString(2, 0));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568" +
                "", fa.toString(2, -1));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n" +
                "3:2343568\n\n" +
                "", fa.toString(1, 1));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n\n" +
                "", fa.toString(0, 1));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n\n" +
                "", fa.toString(-1, 1));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n" +
                "3:2343568\n" +
                "", fa.toString(1, 0));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n" +
                "3:2343568" +
                "", fa.toString(1, -1));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "", fa.toString(0, 0));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "", fa.toString(-1, 0));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568" +
                "", fa.toString(0, -1));

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568" +
                "", fa.toString(-1, -1));
    }

    @Test
    public void test_normalize1() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.blankLine(0);
        fa.normalizeTo(-1, 0);

        assertEquals("0", "0:2343568\n", fa.toString(0, 0));
        assertEquals("1", "0:2343568\n", fa.toString(1, 1));
        assertEquals("2", "0:2343568\n", fa.toString(2, 2));

        fa.blankLine(0);
        fa.normalizeTo(-1, -1);

        assertEquals("0", "0:2343568\n", fa.toString(0, 0));
        assertEquals("1", "0:2343568\n", fa.toString(1, 1));
        assertEquals("2", "0:2343568\n", fa.toString(2, 2));
    }

    @Test
    public void test_normalize2() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.append(sequence.subSequence(10, 19)).blankLine();
        fa.append(sequence.subSequence(20, 29)).blankLine(2);
        fa.append(sequence.subSequence(30, 39)).blankLine(3);

        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n\n\n" +
                "", fa.toString(3, 3));

        fa.normalizeTo(2, 2);
        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n\n" +
                "", fa.toString(3, 3));

        fa.normalizeTo(2, 1);
        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n\n" +
                "3:2343568\n\n" +
                "", fa.toString(3, 3));

        fa.normalizeTo(1, 1);
        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n" +
                "3:2343568\n\n" +
                "", fa.toString(3, 3));

        fa.normalizeTo(1, 0);
        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n\n" +
                "2:2343568\n\n" +
                "3:2343568\n" +
                "", fa.toString(3, 3));

        fa.normalizeTo(0, 0);
        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "", fa.toString(3, 3));

        fa.normalizeTo(0, -1);
        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "", fa.toString(3, 3));

        fa.normalizeTo(-1, -1);
        assertEquals("0", "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "", fa.toString(3, 3));
    }

    @Test
    public void test_maxTailBlankLinesBuilder() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);
        SequenceBuilder builder = SequenceBuilder.emptyBuilder(sequence);

        fa.append(sequence.subSequence(0, 9)).line();
        fa.blankLine(0);

        fa.appendToSilently(builder, -1, -1);
        assertEquals("-1", "0:2343568", builder.toString());
    }

    @Test
    public void test_appendToNoLine() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        assertEquals("", fa.toString());
    }

    @Test
    public void test_getOffsetWithPending() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        int iMax = sequence.length();
        for (int i = 0; i < iMax; i++) {
            fa.append(sequence.subSequence(i, i + 1));
            assertEquals("i: " + i, i + 1, fa.offsetWithPending());
        }
    }

    @Test
    public void test_getOffset() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendable fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        int iMax = sequence.length();
        int lastEol = 0;
        for (int i = 0; i < iMax; i++) {
            fa.append(sequence.subSequence(i, i + 1));
            if (sequence.charAt(i) == '\n') lastEol = i + 1;
            assertEquals("i: " + i, lastEol, fa.offset());
        }
    }

    @Test
    public void test_appendLineAppendable() {
        String input = "" +
                "0:2343568\n" +
                "1:2343568\n" +
                "2:2343568\n" +
                "3:2343568\n" +
                "4:2343568\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        LineAppendableImpl fa = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);
        LineAppendableImpl fa2 = new LineAppendableImpl(SequenceBuilder.emptyBuilder(sequence), LineAppendable.F_FORMAT_ALL | LineAppendable.F_TRIM_LEADING_WHITESPACE);

        fa.append(sequence.subSequence(0, 9)).line();

        fa2.append(fa);
        assertEquals("0", "" +
                "0:2343568\n" +
                "", fa2.toString(2, 2));

        fa2.append(" ").line();

        fa2.pushPrefix().setPrefix("", false);
        fa2.blankLine().popPrefix(false);

        assertEquals("0", "" +
                "0:2343568\n\n" +
                "", fa2.toString(2, 2));

        fa2.blankLine();
        assertEquals("0", "" +
                "0:2343568\n\n" +
                "", fa2.toString(2, 2));
    }
}

