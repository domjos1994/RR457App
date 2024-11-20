package de.dojodev.rr457app.rest.requests

import androidx.lifecycle.MutableLiveData
import de.dojodev.rr457app.rest.base.BasicRequest
import de.dojodev.rr457app.rest.model.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRequest : BasicRequest("https://rr-457.de/api/news") {
    var state = MutableLiveData<Int>()
    var message = MutableLiveData<String>()

    fun getNews(): Flow<List<News>> {
        val request = super.buildRequest("", "GET", null)

        return flow {
            if(request != null) {
                super.client.newCall(request).execute().use { response ->
                    state.postValue(response.code)
                    message.postValue(response.message)

                    if(response.code < 400) {
                        val data = response.body?.string() ?: ""
                        val news = super.json.decodeFromString<Array<News>>(data)
                        emit(news.toList())
                    }
                }
            }
        }
    }
}