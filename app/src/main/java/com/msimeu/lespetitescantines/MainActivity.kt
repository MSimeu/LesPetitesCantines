package com.msimeu.lespetitescantines

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import at.markushi.ui.CircleButton
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import lib.kingja.switchbutton.SwitchMultiButton


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener {
    override fun onClick(v: View?) {

        if (v != null) {
            scrollView.scrollTo(0, scrollView.bottom)
            when (v.id) {
                R.id.button_here -> switchBtn.selectedTab = 1
                R.id.button_take_away -> switchBtn.selectedTab = 0
            }
        }
    }

    private lateinit var scrollView: View
    private lateinit var graph: GraphView
    private lateinit var arrow: ImageView
    private lateinit var spinner: Spinner
    private lateinit var switchBtn: SwitchMultiButton
    private lateinit var btnHere: CircleButton
    private lateinit var btnTakeAway: CircleButton
    private lateinit var dataBaseManager:DataBaseManager

    private var hourSelected = ArrayList<String>()
    private var selectedItem: Int = 0

    val seriesTempsAttente = BarGraphSeries<DataPoint>(
        arrayOf<DataPoint>(
            DataPoint(0.0, 1.0),
            DataPoint(1.0, 5.0),
            DataPoint(2.0, 3.0),
            DataPoint(3.0, 2.0),
            DataPoint(4.0, 6.0),
            DataPoint(5.0, 4.0),
            DataPoint(6.0, 4.0),
            DataPoint(7.0, 5.0),
            DataPoint(8.0, 3.0)
        )
    )

    val seriesOccupation = BarGraphSeries<DataPoint>(
        arrayOf<DataPoint>(
            DataPoint(0.0, 1.0),
            DataPoint(1.0, 4.0),
            DataPoint(2.0, 1.0),
            DataPoint(3.0, 8.0),
            DataPoint(4.0, 9.0),
            DataPoint(5.0, 4.0),
            DataPoint(6.0, 2.0),
            DataPoint(7.0, 1.0),
            DataPoint(8.0, 3.0)
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scrollView = findViewById(R.id.scrollView)
        dataBaseManager = DataBaseManager()
        initSwitchBtn()
        initCircleBtn()
        initSpinner()
        initGraph()
    }

    private fun initCircleBtn() {
        btnHere = findViewById(R.id.button_here)
        btnTakeAway = findViewById(R.id.button_take_away)
        btnHere.setOnClickListener(this)
        btnTakeAway.setOnClickListener(this)
    }

    private fun initSpinner() {
        spinner = findViewById(R.id.spinner)
        spinner.onItemSelectedListener = this
        hourSelected = ArrayList<String>()
        hourSelected.add(0, "Maintenant")
        hourSelected.add(1, "à 11:45")
        hourSelected.add(2, "à 12:00")
        hourSelected.add(3, "à 12:15")
        hourSelected.add(4, "à 12:30")
        hourSelected.add(5, "à 12:45")
        hourSelected.add(6, "à 13:00")
        hourSelected.add(7, "à 13:15")
        hourSelected.add(8, "à 13:30")
        hourSelected.add(9, "à 13:45")
        hourSelected.add(10, "à 14:00")
        // Creating adapter for spinner
        val dataAdapter = ArrayAdapter<String>(this, R.layout.spiner_item, hourSelected)
        spinner.adapter = dataAdapter
    }

    private fun initGraph() {
        graph = findViewById<GraphView>(R.id.graph)
        arrow = findViewById<ImageView>(R.id.iv_arrow)
        seriesTempsAttente.spacing = 10
        seriesOccupation.spacing = 10

        graph.addSeries(seriesTempsAttente)
    }

    private fun initSwitchBtn() {
        var graphSub = findViewById<TextView>(R.id.tv_graph_sub)
        switchBtn = findViewById<SwitchMultiButton>(R.id.switchmultibutton)
        switchBtn.setText("Attente avant caisse", "Taux d'occupation")
            .setOnSwitchListener { position, tabText ->
                Toast.makeText(this@MainActivity, tabText, Toast.LENGTH_SHORT).show()
                if (position == 0) {
                    graph.removeAllSeries()
                    graph.addSeries(seriesTempsAttente)
                    graphSub.text = "Temps d'attente avant caisse"
                } else {
                    graph.removeAllSeries()
                    graph.addSeries(seriesOccupation)
                    graphSub.text = "Taux d'occupation"
                }
            }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        selectedItem = 0
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedItem = position
        Toast.makeText(this, "Je veux manger " + hourSelected[position], Toast.LENGTH_SHORT).show()
    }
}
