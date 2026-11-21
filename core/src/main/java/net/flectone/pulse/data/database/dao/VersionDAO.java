package net.flectone.pulse.data.database.dao;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.flectone.pulse.data.database.Database;
import net.flectone.pulse.data.database.sql.VersionSQL;
import org.jspecify.annotations.NonNull;
import java.util.Optional;
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class VersionDAO implements BaseDAO<VersionSQL> {
    private final Database database;
    @Override
    public Database database() {
        return database;
    }
    @Override
    public Class<VersionSQL> sqlClass() {
        return VersionSQL.class;
    }
    public Optional<String> find() {
        return withHandle(VersionSQL::find);
    }
    public void insert(@NonNull String name) {
        useHandle(sql -> sql.insert(name));
    }
    public void update(@NonNull String name) {
        useHandle(sql -> sql.update(name));
    }
    public void insertOrUpdate(@NonNull String name) {
        Optional<String> versionName = find();
        if (versionName.isEmpty()) {
            insert(name);
        } else if (!versionName.get().equals(name)) {
            update(name);
        }
    }
}