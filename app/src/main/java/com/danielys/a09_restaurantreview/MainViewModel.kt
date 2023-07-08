package com.danielys.a09_restaurantreview

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel() : ViewModel() {

    private val _listgithubusers = MutableLiveData<List<ItemsItem>?>()
    val listgithubusers: MutableLiveData<List<ItemsItem>?> = _listgithubusers

    private val _userDetail = MutableLiveData<DetailUserResponse>()
    val userDetail: LiveData<DetailUserResponse> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFollowerFollowing = MutableLiveData<List<ItemsItem>?>()
    val listFollowerFollowing: MutableLiveData<List<ItemsItem>?> = _listFollowerFollowing


    companion object {
        private const val TAG = "MainViewModel"
        private const val QUERY = "daniel"
    }

    init {
        getUserGithub(QUERY)
    }



    fun getFollowingFollowers(username: String, isFollowers: Boolean) {
        _isLoading.value = true

        val client = if (isFollowers) ApiConfig.getApiService()
            .getFollowers(username) else ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d("Following", "data following :" + response.body())
                    _listFollowerFollowing.value = response.body()
                } else {
                    Log.e(
                        "Following",
                        "getfollowing() ada di onFailure pesannya : ${response.message()}"
                    )
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }


    fun getUserDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                } else {
                    Log.e(TAG, "getUserGithub() ada di onFailure pesannya :: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }

    fun getUserGithub(q: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubUsers(q)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listgithubusers.value = response.body()?.items
                } else {
                    Log.e(TAG, "getUserGithub() ada di onFailure pesannya :: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "getUserGithub() ada di onFailure pesannya : ${t.message.toString()}")
            }

        })
    }
}

