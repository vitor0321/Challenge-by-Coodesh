package com.example.pharmainc.framework.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.repository.PatientRemoteDataSource
import com.example.core.domain.model.Patient
import com.example.pharmainc.domain.model.modelnetwork.Result

class PatientPagingSource(
    private val resultRemoteDataSource: PatientRemoteDataSource<Result>,
    private val query: String
) : PagingSource<Int, Patient>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Patient> {
        return try {
            // na primeira vez ele vai estar null e se tiver null vai começar com 0
            val offset = params.key ?: 0

            val queries = hashMapOf(
                "offset" to offset.toString()
            )
            if (query.isNotEmpty()) {
                queries["nameStartsWith"] = query
            }
            val response = resultRemoteDataSource.fetchCharacter(queries)
            val responseOffset = response.data.offset
            val totalCharacters = response.data.total
            LoadResult.Page(
                data = response.data.results.map { it.toCharacterModel() },
                prevKey = null,
                nextKey = if (responseOffset < totalCharacters) {
                    responseOffset + LIMIT
                } else null
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Patient>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(LIMIT)
        }
    }

    companion object {
        private const val LIMIT = 20
    }
}