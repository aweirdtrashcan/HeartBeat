package br.stimply.heartbeat

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface Api {

    @GET("bpm")
    suspend fun getAllBpm(): List<Beat>

    companion object {
        private var api: Api? = null
        fun createApiAccess(): Api {
            if (api == null) {
                api = Retrofit.Builder()
                    .baseUrl("http://192.168.202.223:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(Api::class.java)
            }
            return api!!
        }
    }

}