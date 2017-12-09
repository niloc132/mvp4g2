package de.gishmo.gwt.mvp4g2.processor.eventbus.filterAnnotationWithoutExtendsIsEventBus;

import de.gishmo.gwt.mvp4g2.client.eventbus.IsEventBus;
import de.gishmo.gwt.mvp4g2.client.eventbus.IsEventFilter;

public class MockFilter
  implements IsEventFilter<FilterAnnotationWithoutExtendsIsEventBus> {

  @Override
  public boolean filterEvent(FilterAnnotationWithoutExtendsIsEventBus eventBus,
                             String eventName,
                             Object... params) {
    return true;
  }

}