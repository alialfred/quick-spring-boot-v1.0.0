package com.alisoftclub.frameworks.qsb.common;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ValidationUtilTest {

    @Test
    public void normalizeLong() {
        Assert.assertEquals(ValidationUtil.normalizeLong("+923214226617"), "923214226617");
        Assert.assertEquals(ValidationUtil.normalizeLong("0092-321-422-6617"), "00923214226617");
        Assert.assertEquals(ValidationUtil.normalizeLong("+92-321-422-6617"), "923214226617");
        Assert.assertEquals(ValidationUtil.normalizeLong("+92 321 422-6617"), "923214226617");
    }

    @Test
    public void normalizeDouble() {
        Assert.assertEquals(ValidationUtil.normalizeDouble("123456789"), "123456789");
        Assert.assertEquals(ValidationUtil.normalizeDouble("321-422.-6617"), "321422.6617");
        Assert.assertEquals(ValidationUtil.normalizeDouble("92,321,422.6617"), "92321422.6617");
    }
}
