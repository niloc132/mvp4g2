package com.github.mvp4g.mvp4g2.core.internal.ui;

import com.github.mvp4g.mvp4g2.core.ui.IsLazyReverseView;
import com.github.mvp4g.mvp4g2.core.ui.IsPresenter;
import com.github.mvp4g.mvp4g2.core.ui.annotation.Presenter;

/**
 * Preenter meta data information.
 *
 * @param <P> the meta data presenter
 * @param <P> the meta data view
 */
public abstract class PresenterMetaData<P extends IsPresenter<?, ?>, V extends IsLazyReverseView<?>>
  extends AbstractHandlerMetaData {

  protected P                              presenter;
  protected V                              view;
  private   boolean                        multiple;
  private   Presenter.VIEW_CREATION_METHOD viewCreationMethod;

  public PresenterMetaData(String canonicalName,
                           Kind kind,
                           boolean multiple,
                           Presenter.VIEW_CREATION_METHOD viewCreationMethod) {
    super(canonicalName,
          kind);
    this.multiple = multiple;
    this.viewCreationMethod = viewCreationMethod;
  }

  public P getPresenter() {
    return presenter;
  }

  public void setPresenter(P presenter) {
    this.presenter = presenter;
  }

  public V getView() {
    return view;
  }

  public void setView(V view) {
    this.view = view;
  }

  public boolean isMultiple() {
    return multiple;
  }

  public Presenter.VIEW_CREATION_METHOD getViewCreationMethod() {
    return viewCreationMethod;
  }
}
