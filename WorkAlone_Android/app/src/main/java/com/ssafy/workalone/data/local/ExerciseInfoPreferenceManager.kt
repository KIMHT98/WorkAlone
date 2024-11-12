package com.ssafy.workalone.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.ssafy.workalone.data.model.exercise.ExerciseData

class ExerciseInfoPreferenceManager(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("exercise_info_preference", Context.MODE_PRIVATE)
    private val gson = Gson()

    // 운동 간 쉬는 시간 저장
    fun setRestBtwExercise(restTime: Int) {
        preferences.edit().putInt("rest_btw_exercise", restTime).apply()
    }


    fun setExerciseList(exercises: List<ExerciseData>) {
        val json = gson.toJson(exercises) // 리스트를 JSON으로 변환
        preferences.edit().putString("exercise_list", json).apply()
    }

    // 운동 간 쉬는 시간 가져오기
    fun getRestBtwExercise(): Int {
        return preferences.getInt("rest_btw_exercise", 0)
    }

    // 운동 리스트 가져오기
    fun getExerciseList(): List<ExerciseData> {
        val json = preferences.getString("exercise_list", null) ?: return emptyList()
        val type = object : TypeToken<List<ExerciseData>>() {}.type
        return gson.fromJson(json, type)
    }

    fun setVideoUrl(url : String){
        preferences.edit().putString("video_url", url).apply()
    }

    fun getVideoUrl() : String{
        return preferences.getString("video_url", "") ?: ""
    }

    // 운동이 끝나면 삭제
    fun clearAll() {
        preferences.edit().clear().apply()
    }
}