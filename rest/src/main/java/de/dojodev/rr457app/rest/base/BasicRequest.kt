package de.dojodev.rr457app.rest.base

import kotlinx.serialization.json.Json
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.internal.ignoreIoExceptions
import java.io.IOException
import java.util.concurrent.TimeUnit


open class BasicRequest(url: String, userName: String? = "", password: String? = "") {
    private val jsonType: MediaType = "application/json".toMediaType()

    protected val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
        ignoreIoExceptions {  }
    }
    protected val client: OkHttpClient
    private val url: String

    init {
        if(userName != "") {
            this.client = OkHttpClient.Builder()
                .addInterceptor(BasicAuthInterceptor(userName!!, password ?: ""))
                .callTimeout(120L, TimeUnit.SECONDS)
                .readTimeout(120L, TimeUnit.SECONDS)
                .writeTimeout(300L, TimeUnit.SECONDS)
                .connectTimeout(120L, TimeUnit.SECONDS)
                .build()
        } else {
            this.client = OkHttpClient.Builder()
                .callTimeout(120L, TimeUnit.SECONDS)
                .readTimeout(120L, TimeUnit.SECONDS)
                .writeTimeout(300L, TimeUnit.SECONDS)
                .connectTimeout(120L, TimeUnit.SECONDS)
                .build()
        }
        this.url = url
    }

    protected fun buildRequest(endPoint: String, method: String, body: String?): Request? {
        if(this.url != "") {
            if(method.lowercase()=="get") {
                return Request.Builder()
                    .url("$url$endPoint")
                    .addHeader("OCS-APIRequest", "true")
                    .build()
            } else if(method.lowercase()=="post") {
                return if(body==null) {
                    Request.Builder()
                        .url("${this.url}$endPoint")
                        .addHeader("OCS-APIRequest", "true")
                        .build()
                } else {
                    return Request.Builder()
                        .url("${this.url}$endPoint")
                        .addHeader("OCS-APIRequest", "true")
                        .post(body.toRequestBody(jsonType))
                        .build()
                }
            } else if(method.lowercase()=="delete") {
                return Request.Builder()
                    .url("${this.url}$endPoint")
                    .addHeader("OCS-APIRequest", "true")
                    .delete(body?.toRequestBody(jsonType))
                    .build()
            } else if(method.lowercase()=="patch") {
                return Request.Builder()
                    .url("${this.url}$endPoint")
                    .addHeader("OCS-APIRequest", "true")
                    .patch(body?.toRequestBody(jsonType)!!)
                    .build()
            } else if(method.lowercase()=="put") {
                return if(body != null) {
                    Request.Builder()
                        .url("${this.url}$endPoint")
                        .addHeader("OCS-APIRequest", "true")
                        .put(body.toRequestBody(jsonType))
                        .build()
                } else {
                    Request.Builder()
                        .url("${this.url}$endPoint")
                        .addHeader("OCS-APIRequest", "true")
                        .build()
                }
            }
        }
        return null
    }
}


class BasicAuthInterceptor(user: String, password: String) : Interceptor {
    private val credentials: String = Credentials.basic(user, password)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val authenticatedRequest: Request = request.newBuilder()
            .header("Authorization", credentials).build()
        return chain.proceed(authenticatedRequest)
    }
}