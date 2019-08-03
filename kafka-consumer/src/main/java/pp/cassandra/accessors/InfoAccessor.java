package pp.cassandra.accessors;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import pp.model.InfoModel;

/**
 * Created by panos pliatsikas.
 */
@Accessor
public interface InfoAccessor {

    @Query("select * from info")
    Result<InfoModel> getAll();
}
