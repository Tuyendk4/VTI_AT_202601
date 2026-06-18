package vn.edu.vitacademy.pages;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import vn.edu.vitacademy.common.keywords.WebUI;
import vn.edu.vitacademy.pages.components.LeftMenu;

public class PageManager {

  private final WebUI webUI;
  private final Map<Class<?>, Object> pageCache = new HashMap<>();

  public PageManager(WebUI webUI) {
    this.webUI = webUI;
  }

  public EmployeesPage employeesPage() {
    return get(EmployeesPage.class);
  }

  public TextBoxPage textBoxPage() {
    return get(TextBoxPage.class);
  }

  public LeftMenu leftMenu() {
    return get(LeftMenu.class);
  }

  public <T> T get(Class<T> pageClass) {
    Object page = pageCache.computeIfAbsent(pageClass, this::createPage);
    return pageClass.cast(page);
  }

  private Object createPage(Class<?> pageClass) {
    try {
      Constructor<?> constructor = pageClass.getConstructor(WebUI.class);
      return constructor.newInstance(webUI);
    } catch (Exception e) {
      throw new IllegalArgumentException(
          "Cannot create page/component " + pageClass.getSimpleName()
              + ". It must have a public constructor with WebUI parameter.",
          e);
    }
  }
}
