package de.gishmo.gwt.mvp4g2.core.internal.history;

import de.gishmo.gwt.mvp4g2.core.history.annotation.History;

/**
 * meta data history annotation
 */
public abstract class HistoryMetaData {

  /* generator of the history converter */
  private History.HistoryConverterType type;

  public HistoryMetaData(History.HistoryConverterType type) {
    this.type = type;
  }

  public History.HistoryConverterType getType() {
    return type;
  }

}