package com.xiaoweiweather.android.ui.place

import android.content.Intent
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
import com.xiaoweiweather.android.MainActivity
import com.xiaoweiweather.android.R
import com.xiaoweiweather.android.ui.weather.WeatherActivity


class PlaceFragment: Fragment() {

    val viewModel by lazy { ViewModelProviders.of(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter: PlaceAdapter
    //加载布局
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_place,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(activity is MainActivity&&viewModel.isPlaceSaved()){
            val place=viewModel.getSavedPlace()
            val intent= Intent(context,WeatherActivity::class.java).apply {
                putExtra("location_lng",place.location.lng)
                putExtra("location_lat",place.location.lat)
                putExtra("place_name",place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }
        //获得实例
        val rV=view?.findViewById<RecyclerView>(R.id.recyclerView)
        val bIV=view?.findViewById<ImageView>(R.id.bgImageView)
        val sPE=view?.findViewById<EditText>(R.id.searchPlaceEdit)
        //设置了LayoutManager和适配器
        val layoutManager=LinearLayoutManager(activity)
        rV?.layoutManager=layoutManager
        adapter= PlaceAdapter(this,viewModel.placeList)
        rV?.adapter=adapter
        //监听内容变化
        sPE?.addTextChangedListener{editable->
            val content=editable.toString()
            //有内容
            if(content.isNotEmpty()){
                viewModel.searchPlaces(content) //发起搜索
            }else{
                rV?.visibility=View.GONE    //隐藏显示
                bIV?.visibility=View.VISIBLE    //展示背景图
                viewModel.placeList.clear() //清空数据源保存的地点
                adapter.notifyDataSetChanged()  //重绘当前可见区域
            }
        }
        viewModel.placeLiveData.observe(this, Observer { result->
            val places=result.getOrNull()
            if(places!=null){
                rV?.visibility=View.VISIBLE //展示显示
                bIV?.visibility=View.GONE   //隐藏背景图
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(activity,"未能查询到任何地点",Toast.LENGTH_SHORT).show()  //异常提示
                result.exceptionOrNull()?.printStackTrace() //打印异常原因
            }
        })

    }
}