package de.gishmo.gwt.mvp4g2.processor.application.applicationAnnotationOkWithoutLoader;

import de.gishmo.gwt.mvp4g2.client.application.IsApplication;
import de.gishmo.gwt.mvp4g2.client.application.annotation.Application;

@Application(eventBus = MockEventBus.class)
public interface ApplicationAnnotationOkWithoutLoader
  extends IsApplication {
}