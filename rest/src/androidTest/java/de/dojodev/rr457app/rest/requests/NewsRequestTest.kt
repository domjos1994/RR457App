package de.dojodev.rr457app.rest.requests

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class NewsRequestTest {
    @Test
    fun getNewsTest() {
        runBlocking {
            val request = NewsRequest()
            val items = request.getNews().first()
            assertNotNull(items)
            assertNotEquals(0, items.count())
            assertEquals("", request.message.value)
        }
    }
}