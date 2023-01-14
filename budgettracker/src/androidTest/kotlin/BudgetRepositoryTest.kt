import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.elisakjalava.budgettrackertask.data.BudgetDatabase
import com.elisakjalava.budgettrackertask.data.entities.Budget
import com.elisakjalava.budgettrackertask.data.entities.Entry
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import net.danlew.android.joda.JodaTimeAndroid
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BudgetRepositoryTest {
    var db: BudgetDatabase? = null

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        JodaTimeAndroid.init(context)
        db = Room.inMemoryDatabaseBuilder(context, BudgetDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun testRemainingBudget() = runTest {
        val originalBudget = 1000.0f
        val budget = Budget(0, originalBudget, 1)
        db!!.budgetDao().insert(budget)

        val entry1Amount = 25.5f
        val entry2Amount = 30.0f

        db!!.entryDao().insertEntry(Entry(0, entry1Amount, 1, "description 1", DateTime.now()))
        db!!.entryDao().insertEntry(Entry(0, entry2Amount, 1, "description 2", DateTime.now()))

        val expectedResult = originalBudget - (entry1Amount + entry2Amount)

        val result = db!!.budgetDao().observeRemainingBudgetForMonth(1).first()

        assertEquals(expectedResult, result)
    }

}
