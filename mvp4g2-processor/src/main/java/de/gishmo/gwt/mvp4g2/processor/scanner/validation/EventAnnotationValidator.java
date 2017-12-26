/*
 * Copyright 2015-2017 Frank Hossfeld
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.gishmo.gwt.mvp4g2.processor.scanner.validation;

import de.gishmo.gwt.mvp4g2.client.eventbus.annotation.Event;
import de.gishmo.gwt.mvp4g2.processor.ProcessorException;
import de.gishmo.gwt.mvp4g2.processor.ProcessorUtils;
import de.gishmo.gwt.mvp4g2.processor.model.EventBusMetaModel;
import de.gishmo.gwt.mvp4g2.processor.model.EventMetaModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.List;

public class EventAnnotationValidator {

  private ProcessorUtils        processorUtils;
  private ProcessingEnvironment processingEnvironment;
  private RoundEnvironment      roundEnvironment;
  private EventBusMetaModel     eventBusMetaModel;
  private TypeElement           eventBusTypeElement;

  @SuppressWarnings("unused")
  private EventAnnotationValidator() {
  }

  private EventAnnotationValidator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.roundEnvironment = builder.roundEnvironment;
    this.eventBusMetaModel = builder.eventBusMetaModel;
    this.eventBusTypeElement = builder.eventBusTypeElement;
    setUp();
  }

  private void setUp() {
    this.processorUtils = ProcessorUtils.builder()
                                        .processingEnvironment(this.processingEnvironment)
                                        .build();
  }

  public static Builder builder() {
    return new Builder();
  }

  public void validate()
    throws ProcessorException {
    // check if all historyNames are unique!
    List<String> historyNames = new ArrayList<>();
    for (Element element : this.roundEnvironment.getElementsAnnotatedWith(Event.class)) {
      Event eventAnnotation = element.getAnnotation(Event.class);
      if (!Event.DEFAULT_HISTORY_NAME.equals(eventAnnotation.historyName())) {
        if (historyNames.contains(eventAnnotation.historyName())) {
          throw new ProcessorException("EventElement: >>" + element.getSimpleName()
                                                                   .toString() + "<< using a already used historyName -> >>" + eventAnnotation.historyName() + "<<");
        } else {
          historyNames.add(eventAnnotation.historyName());
        }
      }
    }
  }

  public void validate(Element element)
    throws ProcessorException {
    ExecutableElement executableElement = (ExecutableElement) element;










    // check if all historyNames are unique!
    List<String> historyNames = new ArrayList<>();
    // TODO test
    for (EventMetaModel eventMetaModel : this.eventBusMetaModel.getEventMetaModels()) {
      if (!Event.DEFAULT_HISTORY_NAME.equals(eventMetaModel.getHistoryEventName())) {
        for (String savedHistoryEventName : historyNames) {
          if (savedHistoryEventName.equals(eventMetaModel.getHistoryEventName())) {
            throw new ProcessorException("EventElement: >>" + eventMetaModel.getHistoryConverter()
                                                                            .getClassName() + "<< using a already used historyName -> >>" + eventMetaModel.getHistoryEventName() + "<<");
          }
        }
        historyNames.add(eventMetaModel.getHistoryEventName());
      }
    }

    //    // get elements annotated with Debug annotation
    //    Set<? extends Element> elementsWithDebugAnnotation = this.roundEnvironment.getElementsAnnotatedWith(Debug.class);
    //    // at least there should only one Application annotation!
    //    if (elementsWithDebugAnnotation.size() > 1) {
    //      throw new ProcessorException("There should be at least only one interface, that is annotated with @Debug");
    //    }
    //    for (Element element : elementsWithDebugAnnotation) {
    //      if (element instanceof TypeElement) {
    //        TypeElement typeElement = (TypeElement) element;
    //        // @Debug can only be used on a interface
    //        if (!typeElement.getKind()
    //                        .isInterface()) {
    //          throw new ProcessorException("@Debug can only be used with an interface");
    //        }
    //        // @Debug can only be used on a interface that extends IsEventBus
    //        if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
    //                                                         typeElement.asType(),
    //                                                         this.processingEnvironment.getElementUtils()
    //                                                                                   .getTypeElement(IsEventBus.class.getCanonicalName())
    //                                                                                   .asType())) {
    //          throw new ProcessorException("@Debug can only be used on interfaces that extends IsEventBus");
    //        }
    //        // the loggerinside the annotation must extends IsMvp4g2Logger!
    //        TypeElement loggerElement = this.getLogger(typeElement.getAnnotation(Debug.class));
    //        if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
    //                                                         loggerElement.asType(),
    //                                                         this.processingEnvironment.getElementUtils()
    //                                                                                   .getTypeElement(IsMvp4g2Logger.class.getCanonicalName())
    //                                                                                   .asType())) {
    //          throw new ProcessorException("@Debug - the logger attribute needs class that extends IsMvp4g2Logger");
    //        }
    //      } else {
    //        throw new ProcessorException("@Debug can only be used on a type (interface)");
    //      }
    //    }
  }

  //  private TypeElement getLogger(Debug debugAnnotation) {
  //    try {
  //      debugAnnotation.logger();
  //    } catch (MirroredTypeException exception) {
  //      return (TypeElement) this.processingEnvironment.getTypeUtils()
  //                                                     .asElement(exception.getTypeMirror());
  //    }
  //    return null;
  //  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;
    RoundEnvironment      roundEnvironment;
    TypeElement           eventBusTypeElement;
    EventBusMetaModel     eventBusMetaModel;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public Builder eventBusMetaModel(EventBusMetaModel eventBusMetaModel) {
      this.eventBusMetaModel = eventBusMetaModel;
      return this;
    }

    public Builder eventBusTypeElement(TypeElement eventBusTypeElement) {
      this.eventBusTypeElement = eventBusTypeElement;
      return this;
    }

    public EventAnnotationValidator build() {
      return new EventAnnotationValidator(this);
    }
  }
}
