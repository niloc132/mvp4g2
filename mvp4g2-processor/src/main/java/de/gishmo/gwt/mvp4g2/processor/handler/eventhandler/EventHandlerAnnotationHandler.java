package de.gishmo.gwt.mvp4g2.processor.handler.eventhandler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import de.gishmo.gwt.mvp4g2.client.ui.AbstractEventHandler;
import de.gishmo.gwt.mvp4g2.client.ui.annotation.EventHandler;
import de.gishmo.gwt.mvp4g2.client.ui.internal.EventHandlerMetaData;
import de.gishmo.gwt.mvp4g2.processor.ProcessorException;
import de.gishmo.gwt.mvp4g2.processor.ProcessorUtils;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Set;

// TODO check, that @Eventhandler is annoted at a class that extends AbstractEventHandler!
public class EventHandlerAnnotationHandler {

  private final static String IMPL_NAME = "MetaData";

  private ProcessorUtils processorUtils;

  private ProcessingEnvironment processingEnvironment;
  private RoundEnvironment      roundEnvironment;

  @SuppressWarnings("unused")
  private EventHandlerAnnotationHandler(Builder builder) {
    super();

    this.processingEnvironment = builder.processingEnvironment;
    this.roundEnvironment = builder.roundEnvironment;

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

  public void process()
    throws ProcessorException, IOException {
    // check, whether we have o do something ...
    if (roundEnvironment.getElementsAnnotatedWith(EventHandler.class)
                        .size() == 0) {
      return;
    }
    // valildate @Application annotation
    this.validate();
    // generate code
    for (Element element : this.roundEnvironment.getElementsAnnotatedWith(EventHandler.class)) {
      this.generate(element);
    }
  }

  private void validate()
    throws ProcessorException {
    // get elements annotated with Presenter annotation
    Set<? extends Element> elementsWithPresenterAnnotation = this.roundEnvironment.getElementsAnnotatedWith(EventHandler.class);
    for (Element element : elementsWithPresenterAnnotation) {
      if (element instanceof TypeElement) {
        TypeElement typeElement = (TypeElement) element;
        // check, that the presenter annotion is only used with classes
        if (!typeElement.getKind()
                        .isClass()) {
          throw new ProcessorException(typeElement.getSimpleName()
                                                  .toString() + ": @EventHandler can only be used with as class!");
        }
        // check, that the typeElement extends AbstarctEventHandler
        if (!this.processorUtils.extendsClassOrInterface(this.processingEnvironment.getTypeUtils(),
                                                         typeElement.asType(),
                                                         this.processingEnvironment.getElementUtils()
                                                                                   .getTypeElement(AbstractEventHandler.class.getCanonicalName())
                                                                                   .asType())) {
          throw new ProcessorException(typeElement.getSimpleName()
                                                  .toString() + ": @EventHandler must extend AbstractEventHandler.class!");
        }
      }
    }


  }

  private void generate(Element element)
    throws IOException {
    EventHandler presenter = element.getAnnotation(EventHandler.class);
    TypeElement typeElement = (TypeElement) element;

    String className = this.processorUtils.createFullClassName(this.processorUtils.getPackageAsString(element),
                                                               element.getSimpleName()
                                                                      .toString()) + EventHandlerAnnotationHandler.IMPL_NAME;
    TypeSpec.Builder typeSpec = TypeSpec.classBuilder(this.processorUtils.setFirstCharacterToUpperCase(className))
                                        .superclass(ParameterizedTypeName.get(ClassName.get(EventHandlerMetaData.class),
                                                                              ClassName.get(this.processorUtils.getPackageAsString(typeElement),
                                                                                            typeElement.getSimpleName()
                                                                                                       .toString())))
                                        .addModifiers(Modifier.PUBLIC,
                                                      Modifier.FINAL);

    // constructor ...
    MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
                                               .addModifiers(Modifier.PUBLIC)
                                               .addStatement("super($S, $T.EVENT_HANDLER, new $T())",
                                                             this.processorUtils.getCanonicalClassName(typeElement),
                                                             ClassName.get(EventHandlerMetaData.Kind.class),
                                                             ClassName.get(this.processorUtils.getPackageAsString(typeElement),
                                                                           typeElement.getSimpleName()
                                                                                      .toString()));
    typeSpec.addMethod(constructor.build());
    JavaFile javaFile = JavaFile.builder(this.processorUtils.getPackageAsString(element),
                                         typeSpec.build())
                                .build();
    javaFile.writeTo(this.processingEnvironment.getFiler());
  }

  public static class Builder {

    ProcessingEnvironment processingEnvironment;
    RoundEnvironment      roundEnvironment;

    public Builder processingEnvironment(ProcessingEnvironment processingEnvironment) {
      this.processingEnvironment = processingEnvironment;
      return this;
    }

    public Builder roundEnvironment(RoundEnvironment roundEnvironment) {
      this.roundEnvironment = roundEnvironment;
      return this;
    }

    public EventHandlerAnnotationHandler build() {
      return new EventHandlerAnnotationHandler(this);
    }

  }
}