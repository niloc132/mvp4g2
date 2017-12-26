/*
 * Copyright (C) 2016 Frank Hossfeld
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.gishmo.gwt.mvp4g2;

import de.gishmo.gwt.mvp4g2.processor.application.ApplicationTest;
import de.gishmo.gwt.mvp4g2.processor.event.StartEventTest;
import de.gishmo.gwt.mvp4g2.processor.eventbus.DebugTest;
import de.gishmo.gwt.mvp4g2.processor.event.EventTest;
import de.gishmo.gwt.mvp4g2.processor.eventbus.EventbusTest;
import de.gishmo.gwt.mvp4g2.processor.eventbus.FilterTest;
import de.gishmo.gwt.mvp4g2.processor.eventhandler.HandlerTest;
import de.gishmo.gwt.mvp4g2.processor.eventhandler.PresenterTest;
import de.gishmo.gwt.mvp4g2.processor.model.intern.ClassNameModelTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ApplicationTest.class,
                     ClassNameModelTest.class,
                     DebugTest.class,
                     EventTest.class,
                     EventbusTest.class,
                     HandlerTest.class,
                     PresenterTest.class,
                     FilterTest.class,
                     StartEventTest.class})
public class AllTests {
}
