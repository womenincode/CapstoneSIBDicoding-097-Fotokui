package com.capstone.fotokui.ui.formphotographer

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.capstone.fotokui.R
import com.capstone.fotokui.databinding.ActivityFormPhotographerBinding

class FormPhotographerActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityFormPhotographerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormPhotographerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRoleAutoCompleteTextView()
        setupListener()
    }

    private fun setupRoleAutoCompleteTextView() {
        val monthOrYear = listOf(getString(R.string.year), getString(R.string.month))
        val adapter = ArrayAdapter(this, R.layout.item_base_auto_complete_textview, monthOrYear)
        binding.actvMonthOrYear.setAdapter(adapter)
    }

    private fun setupListener() {
        arrayOf(binding.btnCancel, binding.btnSave)
            .forEach { button -> button.setOnClickListener(this) }
    }

    override fun onClick(view: View?) {

    }
}