package com.github.kkrull.fixture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

final class TestFixture {
    private static int _numTimesSetup = 0;

    public static void setup() {
        _numTimesSetup++;
    }

    public static void shouldHaveBeenSetUpOnce() {
        assertThat(_numTimesSetup, equalTo(1));
    }

    public static void shouldNotHaveBeenSetUpAgain() {
        assertThat(_numTimesSetup, lessThanOrEqualTo(1));
    }
}
