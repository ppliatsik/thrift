package pp.cassandra.repositories;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import pp.cassandra.accessors.InfoAccessor;
import pp.model.InfoModel;

import java.util.List;

/**
 * Created by panos pliatsikas.
 */
public class InfoRepository {

    private Mapper<InfoModel> mapper;

    private InfoAccessor infoAccessor;

    public InfoRepository(MappingManager mappingManager) {
        this.mapper = mappingManager.mapper(InfoModel.class);
        this.infoAccessor = mappingManager.createAccessor(InfoAccessor.class);
    }

    /**
     * Gets all info.
     *
     * @return List of InfoModel.
     */
    public List<InfoModel> getAll() {
        Result<InfoModel> result = infoAccessor.getAll();
        return result.all();
    }

    /**
     * Saves given InfoModel to database.
     *
     * @param infoModel The info to save.
     */
    public void save(InfoModel infoModel) {
        mapper.save(infoModel);
    }
}
