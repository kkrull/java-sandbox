package com.github.kkrull.misc;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SmallQuickTest {
    @Test
    public void runsJUnitTests() throws Exception {
        assertThat(42, equalTo(42));
    }
}
