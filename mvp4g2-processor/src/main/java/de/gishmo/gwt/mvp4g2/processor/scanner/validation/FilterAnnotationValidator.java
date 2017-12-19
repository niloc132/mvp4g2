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

import de.gishmo.gwt.mvp4g2.client.eventbus.IsEventBus;
import de.gishmo.gwt.mvp4g2.client.eventbus.IsEventFilter;
import de.gishmo.gwt.mvp4g2.client.eventbus.annotation.Filters;
import de.gishmo.gwt.mvp4g2.processor.ProcessorException;
import de.gishmo.gwt.mvp4g2.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FilterAnnotationValidator {

  private ProcessorUtils        processorUtils;
  private ProcessingEnvironment processingEnvironment;
  private RoundEnvironment      roundEnvironment;
  private TypeElement eventBusTypeElement;

  @SuppressWarnings("unused")
  private FilterAnnotationValidator() {
  }

  private FilterAnnotationValidator(Builder builder) {
    this.processingEnvironment = builder.processingEnvironment;
    this.roundEnvironment = builder.roundEnvironment;
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
    // get elements annotated with EventBus annotation
    Set<? extends Element> elementsWithFilterAnnotation = this.roundEnvironment.getElementsAnnotatedWith(Filters.class);
    // at least there should only one Application annotation!
    if (elementsWithFilterAnnotation.size() > 1) {
      throw new ProcessorException("There should be at least only one interface, that is annotated with @Filter");
    }
    // annotated element has to be a interface
    for (Element element : elementsWithFilterAnnotation) {
      if (element instanceof TypeElement) {
        TypeElement typeElement = (TypeElement) element;
        if (!typeElement.getKind()
                        .isInterface()) {
          throw new ProcessorException("@Filter can only be used with an interface");
        }
        // @Filter can only be used on a interface that extends IsEventBus
        if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                         typeElement.asType(),
                                                         this.processingEnvironment.getElementUtils()
                                                                                   .getTypeElement(IsEventBus.class.getCanonicalName())
                                                                                   .asType())) {
          throw new ProcessorException("@Filter can only be used on interfaces that extends IsEventBus");
        }
        // test, that all filters implement IsEventFilter!
        List<String> eventFilterAsStringList = this.getEventFiltersAsList((TypeElement) eventBusTypeElement);
        for (String eventFilterClassname : eventFilterAsStringList) {
          TypeElement filterElement = this.processingEnvironment.getElementUtils()
                                                                .getTypeElement(eventFilterClassname);
          if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                           filterElement.asType(),
                                                           this.processingEnvironment.getElementUtils()
                                                                                     .getTypeElement(IsEventFilter.class.getCanonicalName())
                                                                                     .asType())) {
            throw new ProcessorException("@Filter - the filters attribute needs class that implements IsEventFilter");
          }
        }
      } else {
        throw new ProcessorException("@Filter can only be used on a type (interface)");
      }
    }
  }

  public List<String> getEventFiltersAsList(TypeElement typeElement) {
    Element filterAnnotation = this.processingEnvironment.getElementUtils()
                                                         .getTypeElement(Filters.class.getName());
    TypeMirror filterAnnotationAsTypeMirror = filterAnnotation.asType();
    return typeElement.getAnnotationMirrors()
                      .stream()
                      .filter(annotationMirror -> annotationMirror.getAnnotationType()
                                                                  .equals(filterAnnotationAsTypeMirror))
                      .flatMap(annotationMirror -> annotationMirror.getElementValues()
                                                                   .entrySet()
                                                                   .stream())
                      .findFirst().<List<String>>map(entry -> Arrays.stream(entry.getValue()
                                                                                 .toString()
                                                                                 .replace("{",
                                                                                          "")
                                                                                 .replace("}",
                                                                                          "")
                                                                                 .replace(" ",
                                                                                          "")
                                                                                 .split(","))
                                                                    .map((v) -> v.substring(0,
                                                                                            v.indexOf(".class")))
                                                                    .collect(Collectors.toList())).orElse(null);
  }

  public static final class Builder {

    ProcessingEnvironment processingEnvironment;
    RoundEnvironment      roundEnvironment;
    TypeElement eventBusTypeElement;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public Builder eventBusTypeElement(TypeElement eventBusTypeElement) {
      this.eventBusTypeElement = eventBusTypeElement;
      return this;
    }

    public FilterAnnotationValidator build() {
      return new FilterAnnotationValidator(this);
    }
  }
}
