package com.blas.romanempirecounter.presentation.widget

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.text.Text
import com.blas.romanempirecounter.domain.model.DayModel
import com.blas.romanempirecounter.domain.usecase.GetLastDayUseCase
import com.blas.romanempirecounter.domain.usecase.InsertDayUseCase
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

object RomanEmpireCounterWidget: GlanceAppWidget() {

    val lastDayCountKey = intPreferencesKey("count")

    private var lastDay: DayModel = DayModel(0,"",0)
    private lateinit var todayDay: DayModel
    private var counter = 0

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val lastDay: DayModel = DayModel(0,"",0)
        actionRunCallback<IncrementActionCallback>()

        provideContent {
            val preferencesCount = currentState(key = lastDayCountKey) ?: 0
            Column(
                horizontalAlignment = Alignment.Center.horizontal
            ) {
                Text(
                    text = preferencesCount.toString()
                )
                Button(
                    text = "+1 Imperio Romano",
                    onClick = actionRunCallback(GetDayActionCallback::class.java)
                )
            }
        }


    }

    private fun getTodayDate(): String{
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return LocalDateTime.now().format(formatter)
    }

}

class RomanEmpireCounterWidgetReceiver() : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = RomanEmpireCounterWidget
}

class IncrementActionCallback: ActionCallback {

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        /*lastDay = getLastDayUseCase()
        val today = getTodayDate()

        if (lastDay.date == today) {
            counter = lastDay.count ?: 0
            todayDay = lastDay.copy()
        } else {
            todayDay = DayModel(
                uid = lastDay.uid + 1,
                date = today,
                count = 0
            )
            insertDayUseCase(todayDay)
        }*/
    }
}

class GetDayActionCallback : ActionCallback {

        override suspend fun onAction(
            context: Context,
            glanceId: GlanceId,
            parameters: ActionParameters
        ) {
            updateAppWidgetState(context,glanceId){ prefs ->
                val currentCount = prefs[RomanEmpireCounterWidget.lastDayCountKey]
                if (currentCount != null){
                    prefs[RomanEmpireCounterWidget.lastDayCountKey] = currentCount + 1
                } else {
                    prefs[RomanEmpireCounterWidget.lastDayCountKey] = 1
                }
                RomanEmpireCounterWidget.update(context,glanceId)
            }
        }
    }


