package de.gishmo.gwt.mvp4g2.processor.model;

import de.gishmo.gwt.mvp4g2.processor.model.intern.ClassNameModel;
import de.gishmo.gwt.mvp4g2.processor.model.intern.IsMetaModel;
import de.gishmo.gwt.mvp4g2.processor.model.intern.ModelUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PresenterModel
  implements IsMetaModel {

  private static final String KEY_PRESENTERS           = "presenters";
  private static final String KEY_PRESENTER            = ".presenter";
  private static final String KEY_IS_MULTIPLE          = ".isMultiple";
  private static final String KEY_HANDLED_EVENTS       = ".handledEvents";
  private static final String KEY_VIEW_CLASS           = ".viewClass";
  private static final String KEY_VIEW_INTERFACE       = ".viewInterface";
  private static final String KEY_VIEW_CREATION_METHOD = ".viewCreationMethod";

  private Map<String, PresenterData> presenterDatas;

  public PresenterModel() {
    this.presenterDatas = new HashMap<>();
  }

  public PresenterModel(Properties properties) {
    Set<String> t = properties.stringPropertyNames();
    t.stream()
     .forEach(System.out::println);

//    .getProperty(EventHandlerModel.KEY_EVENT_HANDLER),
//       props.getProperty(EventHandlerModel.KEY_HANDLED_EVENTS)
  }

  public void add(String presenter,
                  String isMultiple,
                  String viewClass,
                  String viewInterface,
                  String viewCreationMethod,
                  String eventHandlers) {
    this.presenterDatas.put(presenter,
                            new PresenterData(presenter,
                                              isMultiple,
                                              viewClass,
                                              viewInterface,
                                              viewCreationMethod,
                                              eventHandlers));
  }

  public void add(String presenter,
                  String isMultiple,
                  String viewClass,
                  String viewInterface,
                  String viewCreationMethod,
                  String... eventHandlers) {
    this.presenterDatas.put(presenter,
                            new PresenterData(presenter,
                                              isMultiple,
                                              viewClass,
                                              viewInterface,
                                              viewCreationMethod,
                                              eventHandlers));
  }

  public Set<String> getPresenterKeys() {
    return this.presenterDatas.keySet();
  }

  public PresenterData getPresenterData(String key) {
    return this.presenterDatas.get(key);
  }

  public Properties createPropertes() {
    Properties props = new Properties();
    props.setProperty(PresenterModel.KEY_PRESENTERS,
                      ModelUtils.stringify(this.presenterDatas.keySet()));
    this.presenterDatas.values()
                       .stream()
                       .forEach(data -> {
                         props.setProperty(data.getPresenter()
                                               .getClassName() + PresenterModel.KEY_PRESENTER,
                                           data.getPresenter()
                                               .getClassName());
                         props.setProperty(data.getPresenter()
                                               .getClassName() + PresenterModel.KEY_IS_MULTIPLE,
                                           data.getIsMultiple());
                         props.setProperty(data.getPresenter()
                                               .getClassName() + PresenterModel.KEY_VIEW_CLASS,
                                           data.getViewClass()
                                               .getClassName());
                         props.setProperty(data.getPresenter()
                                               .getClassName() + PresenterModel.KEY_VIEW_INTERFACE,
                                           data.getViewInterface()
                                               .getClassName());
                         props.setProperty(data.getPresenter()
                                               .getClassName() + PresenterModel.KEY_VIEW_CREATION_METHOD,
                                           data.getViewCreationMethod());
                         props.setProperty(data.getPresenter()
                                               .getClassName() + PresenterModel.KEY_HANDLED_EVENTS,
                                           ModelUtils.stringify(data.handledEvents));
                       });
    return props;
  }

  public class PresenterData {

    private ClassNameModel presenter;
    private String         isMultiple;
    private ClassNameModel viewClass;
    private ClassNameModel viewInterface;
    private String         viewCreationMethod;
    private List<String>   handledEvents;

    public PresenterData(String presenter,
                         String isMultiple,
                         String viewClass,
                         String viewInterface,
                         String viewCreationMethod,
                         String eventHandlers) {
      this(presenter,
           isMultiple,
           viewClass,
           viewInterface,
           viewCreationMethod,
           eventHandlers.split("\\s*,\\s*"));
    }

    public PresenterData(String presenter,
                         String isMultiple,
                         String viewClass,
                         String viewInterface,
                         String viewCreationMethod,
                         String... eventHandlers) {
      this.presenter = new ClassNameModel(presenter);
      this.isMultiple = isMultiple;
      this.viewClass = new ClassNameModel(viewClass);
      this.viewInterface = new ClassNameModel(viewInterface);
      this.viewCreationMethod = viewCreationMethod;
      Arrays.stream(eventHandlers)
            .map(event -> handledEvents.add(event));
    }

    public ClassNameModel getPresenter() {
      return presenter;
    }

    public List<String> getHandledEvents() {
      return handledEvents;
    }

    public boolean isMultiple() {
      return "true".equals(isMultiple);
    }

    public String getIsMultiple() {
      return isMultiple;
    }

    public ClassNameModel getViewClass() {
      return viewClass;
    }

    public ClassNameModel getViewInterface() {
      return viewInterface;
    }

    public String getViewCreationMethod() {
      return viewCreationMethod;
    }
  }
}
