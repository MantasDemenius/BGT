package com.project.bgt.common.serviceHelper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceHelperTest {

  private ServiceHelper serviceHelperUnderTest;

  @BeforeEach
  void setUp() {
    serviceHelperUnderTest = new ServiceHelper();
  }

  @Test
  void isOriginal_OriginalGame_True() {
    // Setup

    // Run the test
    final boolean result = serviceHelperUnderTest.isOriginal(0L);

    // Verify the results
    assertThat(result).isTrue();
  }

  @Test
  void isOriginal_NotOriginal_False() {
    // Setup

    // Run the test
    final boolean result = serviceHelperUnderTest.isOriginal(10);

    // Verify the results
    assertThat(result).isFalse();
  }
}
