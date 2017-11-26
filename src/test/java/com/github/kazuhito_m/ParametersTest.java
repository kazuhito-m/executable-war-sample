package com.github.kazuhito_m;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParametersTest {

    @Test
    @DisplayName("何もパラメータが指定されていなければデフォルトを返す。")
    public void nonParameterTest() {
        Parameters sut = new Parameters(new String[]{});
        assertEquals(sut.contextRoot(), "", "コンテキストパスの初期値は空文字。");
        assertEquals(sut.port(), 8080, "ポートの初期値は8080。");
    }

    @Test
    @DisplayName("コンテキストパスの指定で/がなければ付け加える。")
    public void contextPathAddSlash() {
        Parameters sut = new Parameters(new String[]{"--ContextRoot", "testContextPath"});
        assertEquals(sut.contextRoot(), "/testContextPath", "スラッシュがつけくわわってる。");
    }

    @Test
    @DisplayName("コンテキストパスの指定で、先頭に/が連打されてたら削除する。")
    public void contextPathRemoveOnlyOneSlash() {
        Parameters sut = new Parameters(new String[]{"-c", "/////testContextPath"});
        assertEquals(sut.contextRoot(), "/testContextPath", "スラッシュが削除されてる。");
    }

    @Test
    @DisplayName("ポートの指定で、数値を指定すればそのまま数値として出る。")
    public void portStraight() {
        Parameters sut = new Parameters(new String[]{"-p", "1234"});
        assertEquals(sut.port(), 1234, "ポートが取れる。");
    }

    @Test
    @DisplayName("ポートの指定で、数値を以外を指定すれば例外となる。")
    public void portNonNumeric() {
        Parameters sut = new Parameters(new String[]{"--Port", "STRING"});
        assertThrows(NumberFormatException.class, () -> {
            sut.port();
        });
    }

    @Test
    @DisplayName("最後がオプション指定で値自体は指定してなくても無効化されるだけで例外にならない。")
    public void nonThrowExceptionWhenlastParameterIsOption() {
        Parameters sut = new Parameters(new String[]{"-p", "1234", "--ContextRoot"});
        assertEquals(sut.port(), 1234, "例外にならない。");
    }

}
