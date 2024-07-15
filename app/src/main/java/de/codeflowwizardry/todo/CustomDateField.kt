package de.codeflowwizardry.todo

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.time.DateTimeException
import java.time.LocalDate

@Composable
fun CustomDateField(
    value: LocalDate,
    onValueChanged: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(modifier) {
        OutlinedTextField(
            label = { Text("Year") },
            modifier = Modifier.fillMaxWidth(0.4f),
            value = value.year.toString(),
            onValueChange = {
                // Change year
                val newDate = changeYearOrNull(it, value)
                if (newDate != null) {
                    onValueChanged(newDate)
                }
            }
        )


        OutlinedTextField(
            label = { Text("Month") },
            modifier = Modifier.fillMaxWidth(0.6f),
            value = value.monthValue.toString(),
            onValueChange = {
                // Change year
                val newDate = changeMonthOrNull(it, value)
                if (newDate != null) {
                    onValueChanged(newDate)
                }
            }
        )


        OutlinedTextField(
            label = { Text("Day") },
            modifier = Modifier.fillMaxWidth(1f),
            value = value.dayOfMonth.toString(),
            onValueChange = {
                // Change year
                val newDate = changeDayOrNUll(it, value)
                if (newDate != null) {
                    onValueChanged(newDate)
                }
            }
        )
    }
}

fun changeYearOrNull(year: String, currentDate: LocalDate): LocalDate? {
    return toLocalDateOrNull(
        year,
        currentDate.monthValue.toString(),
        currentDate.dayOfMonth.toString()
    )
}

fun changeMonthOrNull(month: String, currentDate: LocalDate): LocalDate? {
    return toLocalDateOrNull(
        currentDate.year.toString(),
        month,
        currentDate.dayOfMonth.toString()
    )
}

fun changeDayOrNUll(day: String, currentDate: LocalDate): LocalDate? {
    return toLocalDateOrNull(
        currentDate.year.toString(),
        currentDate.monthValue.toString(),
        day
    )
}

fun toLocalDateOrNull(year: String, month: String, day: String): LocalDate? {
    if (!isInteger(year) ||
        !isInteger(month) ||
        !isInteger(day)
    ) {
        return null
    }

    return try {
        LocalDate.of(year.toInt(), month.toInt(), day.toInt())
    } catch (ex: DateTimeException) {
        null
    }
}

fun isInteger(number: String): Boolean {
    return number.toIntOrNull() != null
}