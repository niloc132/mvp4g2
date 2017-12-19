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
package de.gishmo.gwt.mvp4g2.processor.generator;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import de.gishmo.gwt.mvp4g2.processor.ProcessorException;
import de.gishmo.gwt.mvp4g2.processor.model.EventBusMetaModel;

import javax.lang.model.element.Modifier;

// TODO check, that @Filter is annoted at a interface that extends IsEventBus! and other tests ...
public class FilterAnnotationGenerator {

  private EventBusMetaModel eventBusMetaModel;
  private TypeSpec.Builder  typeSpec;

  @SuppressWarnings("unused")
  private FilterAnnotationGenerator() {
  }

  private FilterAnnotationGenerator(Builder builder) {
    this.eventBusMetaModel = builder.eventBusMetaModel;
    this.typeSpec = builder.typeSpec;
  }

  public static Builder builder() {
    return new Builder();
  }

  public void generate()
    throws ProcessorException {
    // method msut always be created!
    MethodSpec.Builder loadFilterConfigurationMethod = MethodSpec.methodBuilder("loadFilterConfiguration")
                                                                 .addAnnotation(Override.class)
                                                                 .addModifiers(Modifier.PUBLIC);
    if (eventBusMetaModel.hasFiltersAnnotation()) {
      loadFilterConfigurationMethod.addStatement("super.setFiltersEnable(true)");
      eventBusMetaModel.getFilters()
                       .stream()
                       .forEach(filterClassName -> loadFilterConfigurationMethod.addStatement("super.eventFilters.add(new $T())",
                                                                                              filterClassName));
    } else {
      loadFilterConfigurationMethod.addStatement("super.setFiltersEnable(false)");
    }
    typeSpec.addMethod(loadFilterConfigurationMethod.build());
  }

  public static final class Builder {

    EventBusMetaModel eventBusMetaModel;
    TypeSpec.Builder  typeSpec;

    /**
     * Set the EventBusMetaModel of the currently generated eventBus
     *
     * @param eventBusMetaModel meta data model of the eventbus
     * @return the Builder
     */
    public Builder eventBusMetaModel(EventBusMetaModel eventBusMetaModel) {
      this.eventBusMetaModel = eventBusMetaModel;
      return this;
    }

    /**
     * Set the typeSpec of the currently generated eventBus
     *
     * @param typeSpec ttype spec of the crruent eventbus
     * @return the Builder
     */
    public Builder typeSpec(TypeSpec.Builder typeSpec) {
      this.typeSpec = typeSpec;
      return this;
    }

    public FilterAnnotationGenerator build() {
      return new FilterAnnotationGenerator(this);
    }
  }
}