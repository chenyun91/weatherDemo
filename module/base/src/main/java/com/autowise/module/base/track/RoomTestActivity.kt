package com.autowise.module.base.track

import android.util.Log
import com.autowise.module.base.BaseActivity
import com.autowise.module.base.R
import com.autowise.module.base.common.utils.ext.showToast
import com.autowise.module.base.databinding.ActTestRoomBinding
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.*

/**
 * User: chenyun
 * Date: 2022/9/21
 * Description:
 * FIXME
 */
class RoomTestActivity : BaseActivity<ActTestRoomBinding>() {

    override fun getLayoutId() = R.layout.act_test_room

    override fun initView() {
        TrackUploadInfoUtils.initUploadDataLooper()

        v.btnAdd.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val trackEntity =
                    TrackEntity(EventType.CLICK, EventID.IM_MESSAGE, PageID.IM, JSONObject().apply {
                        put("123", Random().nextInt(100))
                    }.toString())
                AppDataBaseHelper.dataBase.getTrackDao().insertAll(trackEntity)
            }
        }
        v.btnSelect.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val mList = AppDataBaseHelper.dataBase.getTrackDao().loadAll()
                mList.forEach {
                    Log.i("RoomTestActivity", "" + Gson().toJson(it))
                }
//                withContext(Dispatchers.Main) {
//                    userList.forEach {
//                        println(Gson().toJson(it))
//                    }
//                }
            }
        }

        v.btnDelete.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val mList = AppDataBaseHelper.dataBase.getTrackDao().loadAll()
                mList.forEach {
                    Log.i("RoomTestActivity", "" + Gson().toJson(it))
                    AppDataBaseHelper.dataBase.getTrackDao().delete(it)
                }

            }
        }
        v.btnTotalCount.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val count = AppDataBaseHelper.dataBase.getTrackDao().totalCount()
                withContext(Dispatchers.Main) {
                    showToast("count= ${count}")
                }

            }
        }

    }

}