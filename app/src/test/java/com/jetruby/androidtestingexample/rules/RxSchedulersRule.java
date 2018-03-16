package com.jetruby.androidtestingexample.rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Artem Leushin on 11.01.2018.
 */

public class RxSchedulersRule implements TestRule {

    private Scheduler SCHEDULER_INSTANCE = Schedulers.trampoline();

    private Function<Scheduler, Scheduler> schedulerMapper = scheduler -> SCHEDULER_INSTANCE;
    private Function<Callable<Scheduler>, Scheduler> schedulerMapperLazy = schedulerCallable -> SCHEDULER_INSTANCE;

    @Override
    public Statement apply(Statement base, Description description) {

        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxAndroidPlugins.reset();
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerMapperLazy);

                RxJavaPlugins.reset();
                RxJavaPlugins.setIoSchedulerHandler(schedulerMapper);
                RxJavaPlugins.setNewThreadSchedulerHandler(schedulerMapper);
                RxJavaPlugins.setComputationSchedulerHandler(schedulerMapper);

                base.evaluate();

                RxAndroidPlugins.reset();
                RxJavaPlugins.reset();
            }
        };
    }
}
