package com.example.appdatas

import android.os.Bundle
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appdatas.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btvalidar.setOnClickListener {
            val date1 = getDateFromDatePicker(binding.dtPicker1)
            val date2 = getDateFromDatePicker(binding.dtPicker2)
            val date3 = getDateFromDatePicker(binding.dtPicker3)

            if (isValidDate(date1) && isValidDate(date2) && isValidDate(date3)) {
                val dates = listOf(date1, date2, date3).sorted()
                displaySortedDates(dates)
            } else {
                // Show a toast message if any date is invalid
                Toast.makeText(this, "Uma ou mais datas são inválidas. As datas devem estar entre 01/01/1900 e 31/12/2029.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getDateFromDatePicker(datePicker: DatePicker): Date {
        val calendar = Calendar.getInstance()
        calendar.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
        return calendar.time
    }

    private fun isValidDate(date: Date): Boolean {
        val minDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse("01/01/1900")!!
        val maxDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse("31/12/2029")!!
        return date.after(minDate) && date.before(maxDate)
    }

    private fun displaySortedDates(dates: List<Date>) {
        binding.tvDate1.text = "Data 1: ${formatDate(dates[0])}"
        binding.tvDate2.text = "Data 2: ${formatDate(dates[1])}"
        binding.tvDate3.text = "Data 3: ${formatDate(dates[2])}"

        binding.layoutValidatedDates.visibility = LinearLayout.VISIBLE
    }

    private fun formatDate(date: Date): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
    }
}
