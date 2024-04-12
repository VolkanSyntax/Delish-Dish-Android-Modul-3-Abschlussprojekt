package de.syntax.androidabschluss.data.remote.apiinterfaces



import de.syntax.androidabschluss.data.models.request.ChatRequest
import de.syntax.androidabschluss.data.models.response.ChatResponse
import de.syntax.androidabschluss.utils.OPENAI_API_KEY
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiInterface {

    @POST("chat/completions")
    fun createChatCompletion(
        @Body chatgp: ChatRequest,
        @Header("Content-Type") contentType: String = "application/json",
        @Header("Authorization") authorization: String = "Bearer $OPENAI_API_KEY",


        ): Call<ChatResponse>
}