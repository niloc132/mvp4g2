package com.github.mvp4g.mvp4g2.core.application.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.github.mvp4g.mvp4g2.core.eventbus.IsEventBus;
import com.github.mvp4g.mvp4g2.core.application.IsApplicationLoader;
import com.github.mvp4g.mvp4g2.core.internal.application.NoApplicationLoader;

/**
 * <p>This annotation is used to annotate an interface in mvp4g and mark it as mvp4g application.</p>
 * <br><br>
 * The annotation has the following attributes:
 * <ul>
 * <li>eventBus: defines the eventbus of this application</li>
 * <li>loader: a loader that will be executed in case the application loads. If no loader
 * is defined, the NoApplicationLoader.class will be used. In this case, the loader will do nothing.</li>
 * <li>historyOnStart: if true, the current history state will be fired when the application starts.</li>
 * </ul>
 *
 * @author Frank Hossfeld
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Application {

  Class<? extends IsEventBus> eventBus();

  Class<? extends IsApplicationLoader> loader() default NoApplicationLoader.class;

  boolean historyOnStart() default false;

}
