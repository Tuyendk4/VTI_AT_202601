package vn.edu.vtiacademy.common.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

/**
 * Gan {@link RetryAnalyzer} cho MOI {@code @Test} mot cach tu dong.
 *
 * <p>Khong co class nay thi moi test phai tu viet
 * {@code @Test(retryAnalyzer = RetryAnalyzer.class)} - lap lai 15+ lan va chac chan
 * se co cho quen. Day la ly do DRY de class nay ton tai.
 *
 * <p>Duoc TestNG nap tu dong qua ServiceLoader
 * ({@code META-INF/services/org.testng.ITestNGListener}), khong can khai bao
 * {@code <listeners>} trong tung file suite XML.
 */
public class RetryTransformer implements IAnnotationTransformer {

  @Override
  public void transform(ITestAnnotation annotation, Class testClass,
      Constructor testConstructor, Method testMethod) {
    // Ton trong retryAnalyzer da khai bao tay tren test cu the (neu co).
    if (annotation.getRetryAnalyzerClass() == null
        || annotation.getRetryAnalyzerClass() == org.testng.internal.annotations.DisabledRetryAnalyzer.class) {
      annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
  }
}
