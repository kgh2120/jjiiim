package com.kk.jjiiim;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JacocoTest {


    private Jacoco jacoco = new Jacoco();

    @Test
    void addTest() throws Exception {
        //given
        jacoco.add(10,20);

    }

    @Test
    void maxA() throws Exception {
        //given

        int a = 20;
        int b = 10;
        int max = jacoco.max(a, b);

        Assertions.assertThat(max).isSameAs(a);
    }

    @Test
    void maxB() throws Exception {
        //given
        int a = 10;
        int b = 20;
        int max = jacoco.max(a, b);

        Assertions.assertThat(max).isSameAs(b);


    }

}