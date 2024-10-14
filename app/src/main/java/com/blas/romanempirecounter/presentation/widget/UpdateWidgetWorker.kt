import android.content.Context
import androidx.glance.appwidget.updateAll
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.blas.romanempirecounter.domain.model.DayModel
import com.blas.romanempirecounter.domain.usecase.GetLastDayUseCase
import com.blas.romanempirecounter.domain.usecase.InsertDayUseCase
import com.blas.romanempirecounter.presentation.widget.RomanEmpireCounterWidget
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

// Worker inyectado con Hilt
@HiltWorker
class UpdateWidgetWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val getLastDayUseCase: GetLastDayUseCase,
    private val insertDayUseCase: InsertDayUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        // Llamar al caso de uso para obtener los datos
        val lastDay = getLastDayUseCase.invoke()



        // Actualizar el widget con los datos obtenidos
        /*RomanEmpireCounterWidget().apply {
            updateAll(applicationContext) // Actualizar el widget
        }*/

        return Result.success()
    }
}