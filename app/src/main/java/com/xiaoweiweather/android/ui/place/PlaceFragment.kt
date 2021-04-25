package com.xiaoweiweather.android.ui.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaoweiweather.android.R



class PlaceFragment:Fragment() {

    val viewModel by lazy { ViewModelProviders.of(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_place,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val rV=view?.findViewById<RecyclerView>(R.id.recyclerView)
        val bIV=view?.findViewById<ImageView>(R.id.bgImageView)
        val sPE=view?.findViewById<EditText>(R.id.searchPlaceEdit)
        val layoutManager=LinearLayoutManager(activity)
        rV?.layoutManager=layoutManager
        adapter= PlaceAdapter(this,viewModel.placeList)
        rV?.adapter=adapter
        sPE?.addTextChangedListener{editable->
            val content=editable.toString()
            if(content.isNotEmpty()){
                viewModel.searchPlaces(content)
            }else{
                rV?.visibility=View.GONE
                bIV?.visibility=View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.placeLiveData.observe(this, Observer { result->
            val places=result.getOrNull()
            if(places!=null){
                rV?.visibility=View.VISIBLE
                bIV?.visibility=View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(activity,"未能查询到任何地点",Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

    }



}