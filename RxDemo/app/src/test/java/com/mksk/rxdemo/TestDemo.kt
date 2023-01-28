package com.mksk.rxdemo

import com.mksk.rxdemo.utils.callback.TableService
import io.mockk.*
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith

@RunWith(Enclosed::class)
class TestDemo {

    class TestSong {

        @Test
        fun test_eq_song(){
            val tableService = spyk(TableService(),recordPrivateCalls = true)

            val a: String? = null
            every { tableService getProperty "a" } returns a
            every { tableService }

            tableService.getDataFromDb("abc")
            verify { tableService.getDataFromDb(withArg {
                isNull()
            }) }

//            val a = tableService['a']
        }
    }
}