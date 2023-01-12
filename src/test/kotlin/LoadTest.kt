import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import ru.itmo.iad.photorecognize.service.PhotoGetter
import ru.itmo.iad.photorecognize.telegram.commands.photos.RecognizePhotoCommand
import java.io.FileOutputStream
import java.io.OutputStream
import kotlin.system.measureNanoTime


class LoadTest {

    @Mock
    private lateinit var photoGetter: PhotoGetter

    @InjectMocks
    private lateinit var recogniser: RecognizePhotoCommand

    @BeforeEach
    fun initMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun checkTestLoad() {
        val numbersOf: List<Int> = listOf(5, 10, 15, 20, 30, 50, 70, 100, 200, 500, 1000, 5000, 10000, 200000)
        val results: MutableList<List<Long>> = mutableListOf()

        runBlocking {
            numbersOf.forEach {
                val r: List<Long> = (1..it).map {
                    async { doJob() }
                }.awaitAll()
                results.add(r)
            }
        }

        FileOutputStream("task_load.csv").apply { writeCsv(numbersOf, results) }
    }

    private fun doJob(): Long {
        return measureNanoTime {
            recogniser.execute()
        }
    }

    private fun OutputStream.writeCsv(n: List<Int>, arr: MutableList<List<Long>>) {
        val writer = bufferedWriter()
        arr.forEachIndexed { index, ns ->
            writer.write("${n[index]}; ${ns.joinToString(";")}")
            writer.newLine()
        }
        writer.flush()
    }
}