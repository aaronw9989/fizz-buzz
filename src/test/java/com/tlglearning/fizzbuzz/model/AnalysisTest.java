package com.tlglearning.fizzbuzz.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumSet;
import java.util.Set;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

class AnalysisTest {

  static final Set<State> fizzExpected = EnumSet.of(State.FIZZ);

  final Analysis analysis = new Analysis();

//  @BeforeEach
//  void setUp() {
//    analysis = new Analysis();
//  }

  // We want this test to receive parameters, once for each test case.
  @ParameterizedTest
  @ValueSource(ints = {3, 999_999_999})
  void analyze_fizz(int value) {
    Set<State> expected = EnumSet.of(State.FIZZ);
    assertEquals(expected, analysis.analyze(value));
  }

  @ParameterizedTest
  @ValueSource(ints = {5, 85, 555_555_550})
  void analyze_buzz(int value) {
    Set<State> expected = EnumSet.of(State.BUZZ);
    assertEquals(expected, analysis.analyze(value));
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 15, 999_999_990})
  void analyze_fizz_buzz(int value) {
    Set<State> expected = EnumSet.of(State.BUZZ, State.FIZZ);
    assertEquals(expected, analysis.analyze(value));
  }

  @ParameterizedTest
  @CsvFileSource(resources = "neither.csv", numLinesToSkip = 1)
  void analyze_neither(int value) {
    Set<State> expected = EnumSet.noneOf(State.class);
    assertEquals(expected, analysis.analyze(value));
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, -3, -5, -15})
  void analyze_negative(int value) {
    // we can see the class inside the method because it's a non-static class
    // inside of a non-static method
    class InvalidInvocation implements Executable {
      private final int value;
      public InvalidInvocation(int value) {
        this.value = value;
      }
      // NOTE: we need to use the execute method
      @Override
      public void execute() throws Throwable {
        analysis.analyze(value);
      }
    }
    assertThrows(IllegalArgumentException.class, new InvalidInvocation(value));
  }
}