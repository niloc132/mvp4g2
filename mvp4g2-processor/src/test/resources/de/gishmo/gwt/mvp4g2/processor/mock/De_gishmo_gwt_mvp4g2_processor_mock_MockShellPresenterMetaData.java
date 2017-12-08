package  de.gishmo.gwt.mvp4g2.processor.mock;

import de.gishmo.gwt.mvp4g2.client.ui.IsLazyReverseView;
import de.gishmo.gwt.mvp4g2.client.ui.annotation.Presenter;
import de.gishmo.gwt.mvp4g2.client.ui.internal.PresenterHandlerMetaData;
import de.gishmo.gwt.mvp4g2.processor.eventhandler.PresenterOK;

public final class De_gishmo_gwt_mvp4g2_processor_mock_MockShellPresenterMetaData
  extends PresenterHandlerMetaData<PresenterOK> {
  public De_gishmo_gwt_mvp4g2_processor_mock_MockShellPresenterMetaData() {
    super("de.gishmo.gwt.mvp4g2.processor.mock.MockShellPresenter",
          HandlerMetaData.Kind.PRESENTER,
          new MockShellPresenter(),
          false,
          Presenter.VIEW_CREATION_METHOD.FRAMEWORK);
  }
}