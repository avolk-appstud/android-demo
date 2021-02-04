/*
 * MIT License
 *
 * Copyright (c) 2017 Appstud
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.dupuis.webtoonfactory

import android.app.Application
import com.anvo.demo.BuildConfig
import com.dupuis.webtoonfactory.di.networkModule
import com.dupuis.webtoonfactory.di.servicesModule
import com.dupuis.webtoonfactory.di.viewModelModule
import io.reactivex.internal.functions.Functions.emptyConsumer
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class AndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogging()

        /**
         * By default, RXJava2 compels to handle an error thrown by the subscriber (i.e
         * our viewModel must receive the error and must not be disposed at this time).
         * If not, RXJava crashes the application with an UndeliverableException.
         * This line tells RxJavaPlugin to do nothing in that case.
         */
        RxJavaPlugins.setErrorHandler(emptyConsumer())

        startKoin {
            androidLogger()
            androidContext(this@AndroidApplication)
            modules(
                listOf(
                    servicesModule,
                    viewModelModule,
                    networkModule
                )
            )
        }
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }

    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
//            val crashlytics = FirebaseCrashlytics.getInstance()
//            crashlytics.log(message)
//            if (throwable != null) {
//                crashlytics.recordException(throwable)
//            }
        }
    }
}
