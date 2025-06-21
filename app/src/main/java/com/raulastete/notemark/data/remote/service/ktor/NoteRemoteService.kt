package com.raulastete.notemark.data.remote.service.ktor

import com.raulastete.notemark.data.remote.dto.note.NoteRequest
import com.raulastete.notemark.data.remote.dto.note.NoteResponse
import com.raulastete.notemark.domain.DataError
import com.raulastete.notemark.domain.Result
import io.ktor.client.HttpClient

class NoteRemoteService(private val httpClient: HttpClient) {

    suspend fun createNote(noteRequest: NoteRequest): Result<NoteResponse, DataError.Network> {
        val result = httpClient.post<NoteRequest, NoteResponse>(
            route = ApiUrl.NOTES,
            body = noteRequest
        )
        return result
    }

    suspend fun updateNote(noteRequest: NoteRequest): Result<NoteResponse, DataError.Network> {
        val result = httpClient.put<NoteRequest, NoteResponse>(
            route = ApiUrl.NOTES,
            body = noteRequest
        )
        return result
    }

    suspend fun deleteNote(noteId: String): Result<Unit, DataError.Network> {
        val result = httpClient.delete<Unit>(
            route = "${ApiUrl.NOTES}/$noteId",
            queryParameters = emptyMap()
        )
        return result
    }
}