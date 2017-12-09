package de.gishmo.gwt.mvp4g2.processor.event.debugAnnotationOnAClass;

import de.gishmo.gwt.mvp4g2.client.eventbus.IsEventBus;
import de.gishmo.gwt.mvp4g2.client.eventbus.IsEventFilter;
import de.gishmo.gwt.mvp4g2.client.eventbus.PresenterRegistration;
import de.gishmo.gwt.mvp4g2.client.eventbus.annotation.Debug;
import de.gishmo.gwt.mvp4g2.client.eventbus.annotation.EventBus;
import de.gishmo.gwt.mvp4g2.client.history.IsNavigationConfirmation;
import de.gishmo.gwt.mvp4g2.client.history.PlaceService;
import de.gishmo.gwt.mvp4g2.client.ui.IsPresenter;

import de.gishmo.gwt.mvp4g2.processor.mock.MockShellPresenter;

@EventBus(shell = MockShellPresenter.class)
public interface DebugAnnotationOnAClass
  extends IsEventBus {

  @Debug
  void event();

}