package es.iessaladillo.pedrojoya.pr170.db;

import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.migration.BaseMigration;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

@Migration(version = 0, database = Instituto.class)
public class MigrationInitial extends BaseMigration {

    @Override
    public void migrate(DatabaseWrapper database) {
        // Datos iniciales de la tabla Curso.
        database.execSQL("INSERT INTO Curso VALUES (null, '1º CFGM SMR')");
        database.execSQL("INSERT INTO Curso VALUES (null, '2º CFGM SMR')");
        database.execSQL("INSERT INTO Curso VALUES (null, '1º CFGS DAM')");
        database.execSQL("INSERT INTO Curso VALUES (null, '2º CFGM DAM')");
        // Datos iniciales de la tabla Asignatura.
        database.execSQL("INSERT INTO Asignatura VALUES (null, 'PMDMO')");
        database.execSQL("INSERT INTO Asignatura VALUES (null, 'PSPRO')");
    }

}