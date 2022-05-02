package ducletran.tech.imutransformer.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ducletran.tech.imutransformer.data.database.entity.Label
import kotlinx.coroutines.flow.Flow

@Dao
interface LabelDao {
    @Query("SELECT * FROM label")
    fun getAll(): Flow<List<Label>>

    @Query("SELECT * FROM label WHERE id=:id")
    suspend fun findLabelById(id: Int): Label

    @Insert
    suspend fun insertAll(vararg labels: Label)

    @Delete
    suspend fun delete(label: Label)
}