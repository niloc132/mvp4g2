package de.gishmo.gwt.mvp4g2.processor.model;

import de.gishmo.gwt.mvp4g2.processor.model.intern.ClassNameModel;
import de.gishmo.gwt.mvp4g2.processor.model.intern.IsMetaModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class EventBusMetaModel
  implements IsMetaModel {

  private static final String KEY_EVENTBUS         = "eventBus";
  private static final String KEY_HISTORY_ON_START = "historyOnStart";
  private static final String KEY_SHELL            = "shell";

  private static final String KEY_HAS_DEBUG_ANNOTATION = "hasDebugAnnotation";
  private static final String KEY_DEBUG_LOG_LEVEL      = "debugLogLevel";
  private static final String KEY_DEBUG_LOGGER         = "debugLogger";

  private static final String KEY_HAS_FILTERS_ANNOTATION = "hasFiltersAnnotation";
  private static final String KEY_FILTERS                = "filters";

  private ClassNameModel eventBus;
  private ClassNameModel shell;
  private String         historyOnStart;

  private String         hasDebugAnnotation;
  private String         debugLogLevel;
  private ClassNameModel debugLogger;

  private String hasFiltersAnnotation;
  private List<ClassNameModel> filters = new ArrayList<>();


  public EventBusMetaModel(Properties properties) {
    this.eventBus = new ClassNameModel(properties.getProperty(EventBusMetaModel.KEY_EVENTBUS));
    this.historyOnStart = properties.getProperty(EventBusMetaModel.KEY_HISTORY_ON_START);
    this.shell = new ClassNameModel(properties.getProperty(EventBusMetaModel.KEY_SHELL));

    this.hasDebugAnnotation = properties.getProperty(EventBusMetaModel.KEY_HAS_DEBUG_ANNOTATION);
    this.debugLogger = new ClassNameModel(properties.getProperty(EventBusMetaModel.KEY_DEBUG_LOGGER));
    this.debugLogLevel = properties.getProperty(EventBusMetaModel.KEY_DEBUG_LOG_LEVEL);

    this.hasFiltersAnnotation = properties.getProperty(EventBusMetaModel.KEY_HAS_FILTERS_ANNOTATION);
    this.filters = Arrays.stream(properties.getProperty(EventBusMetaModel.KEY_FILTERS)
                                           .split("\\s*,\\s*"))
                         .map(s -> new ClassNameModel(s))
                         .collect(Collectors.toCollection(ArrayList::new));
  }

  public EventBusMetaModel(String eventBus,
                           String shell,
                           String historyOnStart) {
    this.eventBus = new ClassNameModel(eventBus);
    this.historyOnStart = historyOnStart;
    this.shell = new ClassNameModel(shell);
  }

  public ClassNameModel getEventBus() {
    return eventBus;
  }

  public ClassNameModel getShell() {
    return shell;
  }

  public String getHistoryOnStart() {
    return historyOnStart;
  }

  public boolean isHistoryOnStart() {
    return "true".equals(historyOnStart);
  }

  public String getDebugLogLevel() {
    return debugLogLevel;
  }

  public void setDebugLogLevel(String debugLogLevel) {
    this.debugLogLevel = debugLogLevel;
  }

  public ClassNameModel getDebugLogger() {
    return debugLogger;
  }

  public void setDebugLogger(String debugLogger) {
    this.debugLogger = new ClassNameModel(debugLogger);
  }

  public boolean hasDebugAnnotation() {
    return "true".equals(hasDebugAnnotation);
  }

  public void setHasDebugAnnotation(String hasDebugAnnotation) {
    this.hasDebugAnnotation = hasDebugAnnotation;
  }

  public boolean hasFiltersAnnotation() {
    return "true".equals(hasFiltersAnnotation);
  }

  public void setHasFiltersAnnotation(String hasFiltersAnnotation) {
    this.hasFiltersAnnotation = hasFiltersAnnotation;
  }

  public List<ClassNameModel> getFilters() {
    return filters;
  }

  @Override
  public Properties createPropertes() {
    Properties properties = new Properties();
    properties.setProperty(EventBusMetaModel.KEY_EVENTBUS,
                           this.eventBus.getClassName());
    properties.setProperty(EventBusMetaModel.KEY_SHELL,
                           this.shell.getClassName());
    properties.setProperty(EventBusMetaModel.KEY_HISTORY_ON_START,
                           this.historyOnStart);

    properties.setProperty(EventBusMetaModel.KEY_HAS_DEBUG_ANNOTATION,
                           this.hasDebugAnnotation);
    properties.setProperty(EventBusMetaModel.KEY_DEBUG_LOGGER,
                           this.debugLogger.getClassName());
    properties.setProperty(EventBusMetaModel.KEY_DEBUG_LOG_LEVEL,
                           this.debugLogLevel);

    properties.setProperty(EventBusMetaModel.KEY_HAS_FILTERS_ANNOTATION,
                           this.hasFiltersAnnotation);
    properties.setProperty(EventBusMetaModel.KEY_FILTERS,
                           String.join(",",
                                       filters.stream()
                                              .map(c -> c.getClassName())
                                              .collect(Collectors.toCollection(ArrayList::new))));

    return properties;
  }

  public void setFilters(List<String> filters) {
    filters.stream()
           .forEach(s -> this.filters.add(new ClassNameModel(s)));
  }
}