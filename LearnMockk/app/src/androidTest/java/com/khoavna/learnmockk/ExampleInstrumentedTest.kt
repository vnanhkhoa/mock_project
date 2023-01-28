package com.khoavna.learnmockk

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import io.mockk.*
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith


@RunWith(Enclosed::class)
class ExampleInstrumentedTest {

    private companion object {
        val context: Context = ApplicationProvider.getApplicationContext()
        val userCase = UserCase(context)
    }

    class MethodTest {

        @Test
        fun test_method() = mockkConstructor(Preference::class) {
//            val p = Preference(context)
//            p.logic = true
            every { anyConstructed<Preference>() } returns mockk {
                every { logic } returns true
            }

            val actual = userCase.method()
            actual


        }
    }
}
