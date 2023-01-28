package com.khoavna.learnmockk

import io.mockk.mockk
import org.junit.Test
import org.junit.Assert.*
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(Enclosed::class)
class ExampleUnitTest {



    class MethodTest {

        @Test
        fun test_method() {

            val p = mockk<Preference>()
            p.logic = true
//            every { anyConstructed<Preference>() } answers { p }

//            val actual = userCase.method()
//            actual

        }
    }
}