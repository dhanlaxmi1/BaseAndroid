package com.example.basesetupforandroid.simple

import android.util.Log
import com.ai.live.hd.practical.RetrofitHelper
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface APIINterface {
//    @GET("comments")
//    fun getCommentsByPostId(@Query("postId") postId: Int): Call<List<Comment>>

//    cells.forEach { it.isHighlighted = false }
//    cells.forEach {
//        if (it.row == cell.row || it.col == cell.col || Math.abs(it.row - cell.row) == Math.abs(it.col -cell.col)){
//            it.isHighlighted = true
//        }
//    }
//    cell.isQueen = true


    //USED
//    val api = RetrofitHelper.apiiNterface
//    api.getCommentsByPostId(1).enqueue(object : Callback<List<Comment>> {
//        override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
//            if (response.isSuccessful){
//                Log.e("---->C", "onResponse: ${Gson().toJson(response.body())}", )
//            }
//        }
//
//        override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
//            Log.e("---->C", "onFailure: ${t.message.toString()}", )
//        }
//
//    })

//use pref
//    val prefHelper = PrefHelper(this@MainActivity)
//    prefHelper.saveObj("comment_key", comment)
//    Log.e("---->C", "Comment stored in preferences")
//
//    // Retrieve the stored comment
//    val storedComment: Comment? = prefHelper.getObj("comment_key", Comment::class.java)


}